package ca.jahed.rtpoet.generators

import ca.jahed.rtpoet.rtmodel.*
import ca.jahed.rtpoet.rtmodel.cppproperties.RTCapsuleProperties
import ca.jahed.rtpoet.rtmodel.cppproperties.RTClassProperties
import ca.jahed.rtpoet.rtmodel.rts.RTSystemSignal
import ca.jahed.rtpoet.rtmodel.rts.classes.*
import ca.jahed.rtpoet.rtmodel.rts.protocols.RTSystemProtocol
import ca.jahed.rtpoet.rtmodel.sm.*
import ca.jahed.rtpoet.rtmodel.types.RTType
import ca.jahed.rtpoet.rtmodel.types.primitivetype.*
import ca.jahed.rtpoet.rtmodel.values.RTExpression
import ca.jahed.rtpoet.rtmodel.values.RTLiteralInteger
import ca.jahed.rtpoet.rtmodel.values.RTLiteralReal
import ca.jahed.rtpoet.rtmodel.values.RTLiteralString
import ca.jahed.rtpoet.rtmodel.visitors.RTCachedVisitor
import ca.jahed.rtpoet.utils.RTQualifiedNameHelper
import java.io.File

open class RTTextualModelGenerator(outputPath: String) : RTCachedVisitor() {
    private val typeMap = mapOf(
        Pair(RTString, "String"),
        Pair(RTBoolean, "boolean"),
        Pair(RTInteger, "int"),
        Pair(RTReal, "double"),
        Pair(RTUnlimitedNatural, "int")
    )

    private var qualifiedNames: RTQualifiedNameHelper? = null
    private val topCapsules = mutableListOf<RTCapsule>()
    private val outputDir = File(outputPath)

    init {
        outputDir.mkdirs()
    }

    companion object {
        @JvmStatic
        fun generate(model: RTModel, outputDir: String = ""): String {
            return RTTextualModelGenerator(outputDir).doGenerate(model)
        }
    }

    private fun doGenerate(model: RTModel): String {
        if (qualifiedNames == null)
            qualifiedNames = RTQualifiedNameHelper(model)

//        var names = ""
//        qualifiedNames!!.get().keys.forEach { key ->
//            names += key.name + ": " + qualifiedNames!![key] + "\n"
//        }
//        File(outputDir, "qn.txt").writeText(names)

        if (model.top != null) topCapsules.add(model.top!!.capsule)
        val textualModel = formatOutput(visit(model))
        File(outputDir, "${model.name}.rt").writeText(textualModel)
        return textualModel
    }

    private fun formatOutput(code: String): String {
        var output = ""
        var indentation = 0
        var ignore = false

        code.split("\n").forEach {
            val line = it.trim()
            if (line.isNotEmpty()) {
                val isBlockStart = !ignore && (line.endsWith("{") || line.endsWith("`"))
                val isBlockEnd = (!ignore && line.endsWith("}") || (ignore && line.endsWith("`")))

                if (isBlockStart) output += "\n"
                if (isBlockEnd && indentation > 0) indentation--
                output += "\t".repeat(indentation) + line + "\n"
                if (isBlockStart) indentation++
                if (isBlockEnd) output += "\n"
            }

            if (line.endsWith("`")) ignore = !ignore
        }

        return output.trim()
    }

    override fun visit(element: RTElement): String {
        return super.visit(element) as String
    }

    override fun visitModel(model: RTModel): String {
        model.imports.forEach { doGenerate(it) }

        return """
            model ${model.name} {
                ${model.imports.joinToString("\n") { """import "${it.name}.rt"""" }}
                ${visitPackageBody(model)}
            }
            """
    }

    override fun visitPackage(pkg: RTPackage): String {
        return """
            package ${pkg.name} {
               ${visitPackageBody(pkg)}
            }
            """
    }

    open fun visitPackageBody(pkg: RTPackage): String {
        return """
            ${pkg.packages.joinToString("") { visit(it) }}
            ${pkg.artifacts.joinToString("") { visit(it) }}
            ${pkg.protocols.joinToString("") { visit(it) }}
            ${pkg.classes.joinToString("") { visit(it) }}
            ${pkg.enumerations.joinToString("") { visit(it) }}
            ${pkg.capsules.joinToString("") { visit(it) }}
            """
    }

    override fun visitCapsule(capsule: RTCapsule): String {
        return """
            ${if (capsule in topCapsules) "top " else ""}capsule ${capsule.name} {
               ${visitClassBody(capsule)}
               ${capsule.ports.joinToString("") { visit(it) }}
               ${capsule.parts.joinToString("") { visit(it) }}
               ${capsule.connectors.joinToString("") { visit(it) }}
               ${if (capsule.stateMachine != null) visit(capsule.stateMachine!!) else ""}
            }
            """
    }

    override fun visitClass(klass: RTClass): String {
        return """
            class ${klass.name} {
               ${visitClassBody(klass)}
            }
            """
    }

    open fun visitClassBody(klass: RTClass): String {
        return """
            ${if (klass.properties != null) visit(klass.properties!!) else ""}
            ${klass.attributes.joinToString("") { visitAttribute(it) }}
            ${klass.operations.joinToString("") { visitOperation(it) }} 
            """
    }

    override fun visitProtocol(protocol: RTProtocol): String {
        return """
            protocol ${protocol.name} {
            ${protocol.inputSignals.filter { it !is RTSystemSignal }.joinToString("\n") { "in ${visit(it)}" }}
            ${protocol.outputSignals.filter { it !is RTSystemSignal }.joinToString("\n") { "out ${visit(it)}" }}
            ${protocol.inOutSignals.filter { it !is RTSystemSignal }.joinToString("\n") { "inout ${visit(it)}" }}
            }
            """
    }

    override fun visitEnumeration(enumeration: RTEnumeration): String {
        return """
            enum ${enumeration.name} { ${enumeration.literals.joinToString(", ") { """"$it"""" }} }
            """
    }

    override fun visitPart(part: RTCapsulePart): String {
        return """
            ${visitPartKind(part)} part ${part.name}: ${qualifiedNames!![part.type]}[${part.replication}]
            """
    }

    open fun visitPartKind(part: RTCapsulePart): String {
        return when {
            part.optional -> "optional"
            part.plugin -> "plugin"
            else -> "fixed"
        }
    }

    override fun visitPort(port: RTPort): String {
        return """
            ${visitPortKind(port)} ${visitPortRegistrationKind(port)} port ${port.name}: ${if (port.conjugated) "~" else ""}${
            visitPortType(port)
        }[${port.replication}] ${if (port.registrationOverride.isNotEmpty()) """registration "${port.registrationOverride}""" else ""}
            """
    }

    open fun visitPortKind(port: RTPort): String {
        if (port.type is RTSystemProtocol) return "internal"

        return when {
            port.isRelay() -> "relay"
            port.isSAP() -> "sap"
            port.isSPP() -> "spp"
            port.isExternal() -> "external"
            else -> "internal"
        }
    }

    open fun visitPortRegistrationKind(port: RTPort): String {
        return when (port.registrationType) {
            RTPort.RegistrationType.APPLICATION -> "app"
            RTPort.RegistrationType.AUTOMATIC_LOCKED -> "autolock"
            else -> "auto"
        }
    }

    open fun visitPortType(port: RTPort): String {
        if (port.type is RTSystemProtocol) return "RTSLibrary.${port.type.name}"
        return qualifiedNames!![port.type]
    }

    override fun visitConnector(connector: RTConnector): String {
        return """
            connect ${visitConnectorEnd(connector.end1)} to ${visitConnectorEnd(connector.end2)}
            """
    }

    open fun visitConnectorEnd(end: RTConnectorEnd): String {
        return """${if (end.part != null) "${end.part!!.name}." else ""}${end.port.name}"""
    }

    override fun visitSignal(signal: RTSignal): String {
        return """message ${signal.name}(${signal.parameters.joinToString(", ") { visit(it) }})"""
    }

    override fun visitArtifact(artifact: RTArtifact): String {
        return """
            artifact ${artifact.name} {
                filename "${artifact.fileName}"
            }
            """
    }

    override fun visitAttribute(attribute: RTAttribute): String {
        return """
            ${visitVisibilityKind(attribute.visibility)} attribute ${attribute.name}: ${visitType(attribute.type)}[${attribute.replication}]${
            if (attribute.value != null) "= ${
                visitAttributeValue(attribute)
            }" else ""
        }
            """
    }

    open fun visitAttributeValue(attribute: RTAttribute): String {
        return when (val value = attribute.value) {
            is RTLiteralString -> """"${value.value}""""
            is RTExpression -> "`${visitAction(value.value)}`"
            is RTLiteralInteger -> value.value.toString()
            is RTLiteralReal -> value.value.toString()
            else -> "null"
        }
    }

    open fun visitVisibilityKind(visibility: RTVisibilityKind): String {
        return when (visibility) {
            RTVisibilityKind.PUBLIC -> "public"
            RTVisibilityKind.PRIVATE -> "private"
            else -> "protected"
        }
    }

    override fun visitAction(action: RTAction): String {
        return action.body
    }

    override fun visitOperation(operation: RTOperation): String {
        return """
            ${visitVisibilityKind(operation.visibility)} operation ${operation.name}(${
            operation.parameters.joinToString(", ") { visit(it) }
        })${if (operation.ret != null) ": ${visitType(operation.ret!!.type)}" else ""} ${
            if (operation.action != null) """`
                ${visitAction(operation.action!!)}
            `""" else ""
        }
            """
    }

    override fun visitParameter(param: RTParameter): String {
        return """${param.name}: ${visitType(param.type)}[${param.replication}]"""

    }

    override fun visitType(type: RTType): String {
        return when (type) {
            is RTPrimitiveType -> visitPrimitiveType(type)
            is RTSystemClass -> visitSystemClass(type)
            else -> qualifiedNames!![type]
        }
    }

    open fun visitPrimitiveType(type: RTPrimitiveType): String {
        return when {
            type in typeMap -> typeMap[type]!!
            type.name in typeMap.values -> type.name
            else -> "`${type.name}`"
        }
    }

    open fun visitSystemClass(klass: RTSystemClass): String {
        return when (klass) {
            is RTTimerId -> "RTSLibrary.TimerId"
            is RTCapsuleId -> "RTSLibrary.CapsuleId"
            is RTTimespec -> "RTSLibrary.Timespec"
            is RTMessage -> "RTSLibrary.Message"
            else -> "`${klass.name}`"
        }
    }

    override fun visitStateMachine(statemachine: RTStateMachine): String {
        return """
            statemachine {
            ${statemachine.states().joinToString("") { visit(it) }}
            ${statemachine.transitions().joinToString("") { visit(it) }}
            }
            """
    }

    override fun visitCompositeState(state: RTCompositeState): String {
        return """
            composite state ${state.name} {
            ${visitStateActions(state)}
            ${state.states().joinToString("") { visit(it) }}
            ${state.transitions().joinToString("") { visit(it) }}
            }
            """
    }

    override fun visitPseudoState(state: RTPseudoState): String {
        return when {
            state.kind === RTPseudoState.Kind.INITIAL -> "initial ${state.name}"
            state.kind === RTPseudoState.Kind.CHOICE -> "choice ${state.name}"
            state.kind === RTPseudoState.Kind.JUNCTION -> "junction ${state.name}"
            state.kind === RTPseudoState.Kind.JOIN -> "join ${state.name}"
            state.kind === RTPseudoState.Kind.ENTRY_POINT -> "entry ${state.name}"
            state.kind === RTPseudoState.Kind.EXIT_POINT -> "exit ${state.name}"
            else -> "history ${state.name}"
        }
    }

    override fun visitState(state: RTState): String {
        return """
            state ${state.name} ${
            if (state.entryAction != null || state.exitAction != null) """{
                ${visitStateActions(state)}
            }""" else ""
        }
            """
    }

    open fun visitStateActions(state: RTState): String {
        return """
            ${
            if (state.entryAction != null) """
            entry `
                ${visit(state.entryAction!!)}
            `
            """ else ""
        } 
            ${
            if (state.exitAction != null) """
            exit `
                ${visit(state.exitAction!!)}
            `
            """ else ""
        } 
            """
    }

    override fun visitTransition(transition: RTTransition): String {
        return """
            transition {
                from ${transition.source.name}
                to ${transition.target.name}
                ${
            if (transition.triggers.isNotEmpty()) """
                triggers {
                ${transition.triggers.joinToString("") { visit(it) }}
                }
                """ else ""
        }
                ${
            if (transition.guard != null) """
                guard `
                    ${visit(transition.guard!!)}
                `
                """ else ""
        } 
                ${
            if (transition.action != null) """
                action `
                    ${visit(transition.action!!)}
                `
                """ else ""
        } 
            }
            """
    }

    override fun visitTrigger(trigger: RTTrigger): String {
        return """
            from ${trigger.ports.joinToString(", ") { it.name }} on ${trigger.signal.name}
            """
    }

    override fun visitClassProperties(props: RTClassProperties): String {
        return """
            ${
            if (props.headerPreface != null) """header preface `
                ${props.headerPreface}
            `""" else ""
        }
            ${
            if (props.implementationPreface != null) """implementation preface `
                ${props.implementationPreface}
            `""" else ""
        }
            """
    }

    override fun visitCapsuleProperties(props: RTCapsuleProperties): String {
        return """
            ${
            if (props.headerPreface != null) """header preface `
                ${props.headerPreface}
            `""" else ""
        }
            ${
            if (props.implementationPreface != null) """implementation preface `
                ${props.implementationPreface}
            `""" else ""
        }
            """
    }
}