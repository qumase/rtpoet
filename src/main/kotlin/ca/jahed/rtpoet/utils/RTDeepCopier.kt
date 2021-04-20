package ca.jahed.rtpoet.utils

import ca.jahed.rtpoet.rtmodel.*
import ca.jahed.rtpoet.rtmodel.cppproperties.*
import ca.jahed.rtpoet.rtmodel.rts.RTSystemSignal
import ca.jahed.rtpoet.rtmodel.rts.classes.RTSystemClass
import ca.jahed.rtpoet.rtmodel.rts.protocols.RTSystemProtocol
import ca.jahed.rtpoet.rtmodel.sm.*
import ca.jahed.rtpoet.rtmodel.types.RTType
import ca.jahed.rtpoet.rtmodel.types.primitivetype.RTPrimitiveType
import ca.jahed.rtpoet.rtmodel.visitors.RTCachedVisitor

class RTDeepCopier(private val ignore: List<Class<*>> = listOf()) : RTCachedVisitor() {
    private val original = mutableMapOf<RTElement, RTElement>()

    override fun visit(element: RTElement): Any {
        if (element::class.java in ignore)
            return element

        val copy = super.visit(element)
        original[copy as RTElement] = element
        return copy
    }

    fun copy(element: RTElement): RTElement {
        return visit(element) as RTElement
    }

    fun getOriginal(element: RTElement): RTElement? {
        return original[element]
    }

    override fun visitModel(model: RTModel): RTModel {
        val copy = RTModel(model.name, visitPart(model.top))
        model.capsules.forEach { copy.capsules.add(visit(it) as RTCapsule) }
        model.classes.forEach { copy.classes.add(visit(it) as RTClass) }
        model.enumerations.forEach { copy.enumerations.add(visit(it) as RTEnumeration) }
        model.protocols.forEach { copy.protocols.add(visit(it) as RTProtocol) }
        model.packages.forEach { copy.packages.add(visit(it) as RTPackage) }
        return copy
    }

    override fun visitPackage(pkg: RTPackage): RTPackage {
        val copy = RTPackage(pkg.name)
        pkg.capsules.forEach { copy.capsules.add(visit(it) as RTCapsule) }
        pkg.classes.forEach { copy.classes.add(visit(it) as RTClass) }
        pkg.enumerations.forEach { copy.enumerations.add(visit(it) as RTEnumeration) }
        pkg.protocols.forEach { copy.protocols.add(visit(it) as RTProtocol) }
        pkg.packages.forEach { copy.packages.add(visit(it) as RTPackage) }
        return copy
    }

    override fun visitCapsule(capsule: RTCapsule): RTCapsule {
        val copy = RTCapsule(capsule.name)
        copy.superClass = if (capsule.superClass != null) visit(capsule.superClass!!) as RTClass else null
        capsule.attributes.forEach { copy.attributes.add(visit(it) as RTAttribute) }
        capsule.operations.forEach { copy.operations.add(visit(it) as RTOperation) }

        capsule.parts.forEach { copy.parts.add(visit(it) as RTCapsulePart) }
        capsule.ports.forEach { copy.ports.add(visit(it) as RTPort) }
        capsule.connectors.forEach { copy.connectors.add(visit(it) as RTConnector) }
        copy.stateMachine = if (capsule.stateMachine != null) visit(capsule.stateMachine!!) as RTStateMachine else null
        copy.properties = if (capsule.properties != null) visit(capsule.properties!!) as RTCapsuleProperties else null
        return copy
    }

    override fun visitClass(klass: RTClass): RTClass {
        if (klass is RTSystemClass) return klass

        val copy = RTClass(klass.name)
        copy.superClass = if (klass.superClass != null) visit(klass.superClass!!) as RTClass else null
        klass.attributes.forEach { copy.attributes.add(visit(it) as RTAttribute) }
        klass.operations.forEach { copy.operations.add(visit(it) as RTOperation) }
        copy.properties = if (klass.properties != null) visit(klass.properties!!) as RTClassProperties else null
        return klass
    }

    override fun visitProtocol(protocol: RTProtocol): RTProtocol {
        if (protocol is RTSystemProtocol) return protocol

        val copy = RTProtocol(protocol.name)
        protocol.inputSignals.forEach { copy.inputSignals.add(visit(it) as RTSignal) }
        protocol.outputSignals.forEach { copy.outputSignals.add(visit(it) as RTSignal) }
        protocol.inOutSignals.forEach { copy.inOutSignals.add(visit(it) as RTSignal) }
        return copy
    }

    override fun visitEnumeration(enumeration: RTEnumeration): RTEnumeration {
        val copy = RTEnumeration(enumeration.name)
        enumeration.literals.forEach { copy.literals.add(it) }
        copy.properties =
            if (enumeration.properties != null)
                visit(enumeration.properties!!) as RTEnumerationProperties
            else null
        return copy
    }

    override fun visitPart(part: RTCapsulePart): RTCapsulePart {
        val copy = RTCapsulePart(part.name, visit(part.capsule) as RTCapsule)
        copy.replication = part.replication
        copy.optional = part.optional
        copy.plugin = part.plugin

        return copy
    }

    override fun visitPort(port: RTPort): RTPort {
        val copy = RTPort(port.name, visit(port.protocol) as RTProtocol)
        copy.registrationOverride = port.registrationOverride
        copy.registrationType = port.registrationType
        copy.publish = port.publish
        copy.notification = port.notification
        copy.conjugated = port.conjugated
        copy.service = port.service
        copy.wired = port.wired
        copy.behaviour = port.behaviour
        copy.replication = port.replication
        copy.visibility = port.visibility
        return copy
    }

    override fun visitConnector(connector: RTConnector): RTConnector {
        val part1Copy = if (connector.end1.part != null) visitPart(connector.end1.part!!) else null
        val part2Copy = if (connector.end2.part != null) visitPart(connector.end2.part!!) else null

        val copy = RTConnector(
            RTConnectorEnd(visitPort(connector.end1.port), part1Copy),
            RTConnectorEnd(visitPort(connector.end2.port), part2Copy),
        )
        return copy
    }

    override fun visitSignal(signal: RTSignal): RTSignal {
        if (signal.isAny || signal is RTSystemSignal) return signal

        val copy = RTSignal(signal.name, signal.isAny)
        signal.parameters.forEach { copy.parameters.add(visit(it) as RTParameter) }
        return copy
    }

    override fun visitArtifact(artifact: RTArtifact): RTArtifact {
        val copy = RTArtifact(artifact.name, artifact.fileName)
        copy.properties =
            if (artifact.properties != null) visit(artifact.properties!!) as RTArtifactProperties else null
        return copy
    }

    override fun visitAttribute(attribute: RTAttribute): RTAttribute {
        val copy = RTAttribute(attribute.name, visit(attribute.type) as RTType)
        copy.visibility = attribute.visibility
        copy.replication = attribute.replication
        copy.value = attribute.value
        copy.properties =
            if (attribute.properties != null)
                visit(attribute.properties!!) as RTAttributeProperties
            else null
        return copy
    }

    override fun visitAction(action: RTAction): RTAction {
        return RTAction(action.body, action.language)
    }

    override fun visitOperation(operation: RTOperation): RTOperation {
        val copy = RTOperation(operation.name)
        operation.parameters.forEach { copy.parameters.add(visit(it) as RTParameter) }
        copy.ret = if (operation.ret != null) visit(operation.ret!!) as RTParameter else null
        copy.action = if (operation.action != null) visit(operation.action!!) as RTAction else null
        copy.properties =
            if (operation.properties != null)
                visit(operation.properties!!) as RTOperationProperties
            else null
        return copy
    }

    override fun visitParameter(param: RTParameter): RTParameter {
        val copy = RTParameter(param.name, visit(param.type) as RTType)
        copy.replication = param.replication
        return copy
    }

    override fun visitType(type: RTType): RTType {
        val copy =
            if (type is RTPrimitiveType || type is RTSystemClass) type
            else visit(type as RTClass) as RTClass
        return copy
    }

    override fun visitStateMachine(statemachine: RTStateMachine): RTStateMachine {
        val copy = RTStateMachine()
        statemachine.states().forEach { copy.states().add(visit(it) as RTGenericState) }
        statemachine.transitions().forEach { copy.transitions().add(visit(it) as RTTransition) }
        return copy
    }

    override fun visitCompositeState(state: RTCompositeState): RTCompositeState {
        val copy = RTCompositeState(state.name)
        copy.entryAction = if (state.entryAction != null) visitAction(state.entryAction!!) else null
        copy.exitAction = if (state.exitAction != null) visitAction(state.exitAction!!) else null
        state.states().forEach { copy.states().add(visit(it) as RTGenericState) }
        state.transitions().forEach { copy.transitions().add(visit(it) as RTTransition) }
        return copy
    }

    override fun visitPseudoState(state: RTPseudoState): RTPseudoState {
        return RTPseudoState(state.name, state.kind)
    }

    override fun visitState(state: RTState): RTState {
        val copy = RTState(state.name)
        copy.entryAction = if (state.entryAction != null) visitAction(state.entryAction!!) else null
        copy.exitAction = if (state.exitAction != null) visitAction(state.exitAction!!) else null
        return copy
    }

    override fun visitTransition(transition: RTTransition): RTTransition {
        val copy = RTTransition(visit(transition.source) as RTGenericState, visit(transition.target) as RTGenericState)
        copy.guard = if (transition.guard != null) visitAction(transition.guard!!) else null
        copy.action = if (transition.action != null) visitAction(transition.action!!) else null
        transition.triggers.forEach { copy.triggers.add(visit(it) as RTTrigger) }
        return copy
    }

    override fun visitTrigger(trigger: RTTrigger): RTTrigger {
        val copy = RTTrigger(visit(trigger.signal) as RTSignal)
        trigger.ports.forEach { copy.ports.add(visit(it) as RTPort) }
        return copy
    }

    override fun visitArtifactProperties(props: RTArtifactProperties): RTArtifactProperties {
        return RTArtifactProperties(props.includeFile, props.sourceFile)
    }

    override fun visitAttributeProperties(props: RTAttributeProperties): RTAttributeProperties {
        return RTAttributeProperties(
            props.initialization,
            props.kind,
            props.size,
            props.type,
            props.pointsToConstType,
            props.pointsToVolatileType,
            props.pointsToType,
            props.isVolatile)
    }

    override fun visitCapsuleProperties(props: RTCapsuleProperties): RTCapsuleProperties {
        return RTCapsuleProperties(
            props.headerPreface,
            props.headerEnding,
            props.implementationPreface,
            props.implementationEnding,
            props.publicDeclarations,
            props.privateDeclarations,
            props.protectedDeclarations,
            props.generateHeader,
            props.generateImplementation)
    }

    override fun visitClassProperties(props: RTClassProperties): RTClassProperties {
        return RTClassProperties(
            props.kind,
            props.headerPreface,
            props.headerEnding,
            props.implementationPreface,
            props.implementationEnding,
            props.publicDeclarations,
            props.privateDeclarations,
            props.protectedDeclarations,
            props.implementationType,
            props.generate,
            props.generateHeader,
            props.generateImplementation,
            props.generateStateMachine,
            props.generateAssignmentOperator,
            props.generateEqualityOperator,
            props.generateInequalityOperator,
            props.generateInsertionOperator,
            props.generateExtractionOperator,
            props.generateCopyConstructor,
            props.generateDefaultConstructor,
            props.generateDestructor)
    }

    override fun visitEnumerationProperties(props: RTEnumerationProperties): RTEnumerationProperties {
        return RTEnumerationProperties(
            props.headerPreface,
            props.headerEnding,
            props.implementationPreface,
            props.implementationEnding,
            props.generate)
    }

    override fun visitOperationProperties(props: RTOperationProperties): RTOperationProperties {
        return RTOperationProperties(
            props.kind,
            props.generateDefinition,
            props.inline,
            props.polymorphic)
    }

    override fun visitParameterProperties(props: RTParameterProperties): RTParameterProperties {
        return RTParameterProperties(
            props.type,
            props.pointsToConst,
            props.pointsToVolatile,
            props.pointsToType)
    }

    override fun visitTypeProperties(props: RTTypeProperties): RTTypeProperties {
        return RTTypeProperties(props.name, props.definitionFile)
    }
}