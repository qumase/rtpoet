package ca.jahed.rtpoet.utils

import ca.jahed.rtpoet.exceptions.ValidationException
import ca.jahed.rtpoet.rtmodel.*
import ca.jahed.rtpoet.rtmodel.rts.RTSystemClass
import ca.jahed.rtpoet.rtmodel.rts.RTSystemProtocol
import ca.jahed.rtpoet.rtmodel.sm.*
import java.util.*

class RTModelValidator(private val model: RTModel, private val throwExceptions: Boolean = false) {
    private val log = TreeSet<ValidationMessage>()

    fun validate(ignoreWarnings: Boolean = false): Boolean {
        log.clear()
        checkModel()
        return log.none { it.error } && (ignoreWarnings || log.none())
    }

    fun hasErrors(): Boolean {
        return log.any { it.error }
    }

    fun hasWarnings(): Boolean {
        return log.any { !it.error }
    }

    fun getMessages(): List<String> {
        return log.map { it.toString() }.toList()
    }

    fun getErrors(): List<String> {
        return log.filter { it.error }.map { it.toString() }.toList()
    }

    fun getWarnings(): List<String> {
        return log.filter { !it.error }.map { it.toString() }.toList()
    }

    private fun checkModel() {
        if (!hasCapsule(model, model.top.capsule)) {
            throwOrLog(model, "capsule ${model.top.capsule} not found in model $model", true)
        }

        checkPackage(model)
    }

    private fun checkPackage(pkg: RTPackage) {
        pkg.capsules.forEach { checkCapsule(it) }
        pkg.classes.forEach { checkClass(it) }
        pkg.protocols.forEach { checkProtocol(it) }
        pkg.packages.forEach { checkPackage(it) }
    }

    private fun checkCapsule(capsule: RTCapsule) {
        if (capsule.superClass != null && !hasCapsule(model, capsule)) {
            throwOrLog(capsule, "capsule $capsule not found in model $model", true)
        }

        capsule.parts.forEach {
            if (it.capsule === capsule) {
                throwOrLog(capsule, "recursive capsule part $it", true)
            }

            if (!hasCapsule(model, it.capsule)) {
                throwOrLog(it, "capsule ${it.capsule} not found in model $model", true)
            }
        }

        capsule.ports.forEach {
            if (!hasProtocol(model, it.protocol)) {
                throwOrLog(it, "protocol ${it.protocol} not found in model $model", true)
            }
        }

        capsule.connectors.forEach { checkConnector(capsule, it) }
        capsule.attributes.forEach { checkAttribute(it) }
        capsule.operations.forEach { checkOperation(it) }

        if (capsule.stateMachine != null) checkStateMachine(capsule, capsule.stateMachine!!)
    }

    private fun checkClass(klass: RTClass) {
        if (klass.superClass != null && !hasClass(model, klass)) {
            throwOrLog(klass, "class ${klass.superClass} not found in model $model", true)
        }

        klass.attributes.forEach { checkAttribute(it) }
        klass.operations.forEach { checkOperation(it) }
    }

    private fun checkProtocol(protocol: RTProtocol) {
        protocol.inputSignals.forEach { checkSignal(it) }
        protocol.outputSignals.forEach { checkSignal(it) }
        protocol.inOutSignals.forEach { checkSignal(it) }
    }

    private fun hasCapsule(pkg: RTPackage, capsule: RTCapsule): Boolean {
        if (pkg.capsules.contains(capsule))
            return true
        pkg.packages.forEach { if (hasCapsule(it, capsule)) return true }
        return false
    }

    private fun hasClass(pkg: RTPackage, klass: RTClass): Boolean {
        if (klass is RTSystemClass)
            return true

        if (pkg.classes.contains(klass))
            return true
        pkg.packages.forEach { if (hasClass(it, klass)) return true }
        return false
    }

    private fun hasProtocol(pkg: RTPackage, protocol: RTProtocol): Boolean {
        if (protocol is RTSystemProtocol)
            return true

        if (pkg.protocols.contains(protocol))
            return true
        pkg.packages.forEach { if (hasProtocol(it, protocol)) return true }
        return false
    }

    private fun hasEnumeration(pkg: RTPackage, enumeration: RTEnumeration): Boolean {
        if (pkg.enumerations.contains(enumeration))
            return true
        pkg.packages.forEach { if (hasEnumeration(it, enumeration)) return true }
        return false
    }

    private fun checkConnector(capsule: RTCapsule, connector: RTConnector) {
        if (connector.end1.part != null && !capsule.parts.contains(connector.end1.part)) {
            throwOrLog(connector, "connector end part not a part in capsule $capsule", true)
        }

        if (connector.end2.part != null && !capsule.parts.contains(connector.end2.part)) {
            throwOrLog(connector, "connector end part not a part in capsule $capsule", true)
        }

        val end1Capsule = if (connector.end1.part == null) capsule else connector.end1.part!!.capsule
        val end2Capsule = if (connector.end2.part == null) capsule else connector.end2.part!!.capsule

        if (!end1Capsule.ports.contains(connector.end1.port)) {
            throwOrLog(connector, "connector end port not a port in capsule $end1Capsule", true)
        }

        if (!end2Capsule.ports.contains(connector.end2.port)) {
            throwOrLog(connector, "connector end port not a port in capsule $end2Capsule", true)
        }
    }

    private fun checkSignal(signal: RTSignal) {
        signal.parameters.forEach { parameter ->
            when (val type = parameter.type) {
                is RTEnumeration ->
                    if (!hasEnumeration(model, type)) {
                        throwOrLog(parameter, "enumeration $type not found in model $model")
                    }

                is RTClass ->
                    if (!hasClass(model, type)) {
                        throwOrLog(parameter, "class $type not found in model $model")
                    }
            }
        }
    }

    private fun checkAttribute(attribute: RTAttribute) {
        when (val type = attribute.type) {
            is RTEnumeration ->
                if (!hasEnumeration(model, type)) {
                    throwOrLog(attribute, "enumeration $type not found in model $model")
                }

            is RTClass ->
                if (!hasClass(model, type)) {
                    throwOrLog(attribute, "class $type not found in model $model")
                }
        }
    }

    private fun checkOperation(operation: RTOperation) {
        val params = mutableListOf(operation.ret)
        params.addAll(operation.parameters)

        params.forEach { parameter ->
            when (val type = parameter?.type) {
                is RTEnumeration ->
                    if (!hasEnumeration(model, type)) {
                        throwOrLog(parameter, "enumeration $type not found in model $model")
                    }

                is RTClass ->
                    if (!hasClass(model, type)) {
                        throwOrLog(parameter, "class $type not found in model $model")
                    }
            }
        }
    }

    private fun checkStateMachine(capsule: RTCapsule, stateMachine: RTStateMachine) {
        stateMachine.states().filterIsInstance<RTCompositeState>().forEach { checkCompositeState(capsule, it) }

        if (stateMachine.states().filterIsInstance<RTPseudoState>()
                .none { it.kind == RTPseudoState.Kind.INITIAL }
        ) {
            throwOrLog(stateMachine, "no initial state")
        }

        stateMachine.states().forEach { state ->
            if (!(state is RTPseudoState && (state.kind != RTPseudoState.Kind.INITIAL
                        || state.kind != RTPseudoState.Kind.HISTORY))
            ) {
                if (stateMachine.transitions().none { it.target == state }) {
                    throwOrLog(stateMachine, "unreachable state $state")
                }
            }
        }

        stateMachine.states()
            .filterIsInstance<RTPseudoState>()
            .filter { it.kind != RTPseudoState.Kind.HISTORY }
            .forEach { pseudostate ->
                if (stateMachine.transitions().none { it.source == pseudostate }) {
                    throwOrLog(stateMachine, "no outgoing transitions from pseudostate $pseudostate")
                }
            }

        stateMachine.transitions().forEach { checkTransition(capsule, stateMachine, it) }
    }

    private fun checkCompositeState(capsule: RTCapsule, compositeState: RTCompositeState) {
        compositeState.states().forEach { state ->
            if (!(state is RTPseudoState && (state.kind != RTPseudoState.Kind.INITIAL
                        || state.kind != RTPseudoState.Kind.HISTORY))
            ) {
                if (compositeState.transitions().none { it.target == state }) {
                    throwOrLog(compositeState, "unreachable state $state")
                }
            }
        }

        compositeState.states()
            .filterIsInstance<RTPseudoState>()
            .filter { it.kind != RTPseudoState.Kind.HISTORY }
            .forEach { pseudostate ->
                if (compositeState.transitions().none { it.source == pseudostate }) {
                    throwOrLog(compositeState, "no outgoing transitions from pseudostate $pseudostate")
                }
            }

        compositeState.transitions().forEach { checkTransition(capsule, compositeState, it) }
    }

    private fun checkTransition(capsule: RTCapsule, region: IRTRegion, transition: RTTransition) {
        if (!region.states().contains(transition.source)) {
            throwOrLog(transition, "source state ${transition.source} not found in $region", true)
        }

        if (!region.states().contains(transition.target)) {
            throwOrLog(transition, "target state ${transition.target} not found in $region", true)
        }

        transition.triggers.forEach { checkTrigger(capsule, it) }
    }

    private fun checkTrigger(capsule: RTCapsule, trigger: RTTrigger) {
        if (!capsule.ports.contains(trigger.port)) {
            throwOrLog(trigger, "${trigger.port} not a port of $capsule", true)
        }

        if (!trigger.port.inputs().contains(trigger.signal)) {
            throwOrLog(trigger, "${trigger.signal} not an input to port ${trigger.port}", true)
        }
    }

    private fun throwOrLog(element: RTElement, message: String, error: Boolean = false) {
        val msg = ValidationMessage(element, message, error)
        log.add(msg)

        if (throwExceptions && msg.error)
            throw ValidationException(msg.message)
    }

    private class ValidationMessage(
        val element: RTElement,
        val message: String,
        val error: Boolean = false,
    ) : Comparable<ValidationMessage> {

        override fun compareTo(other: ValidationMessage): Int {
            if (error == other.error)
                return element.compareTo(other.element)

            return if (error) 0 else 1
        }

        override fun toString(): String {
            return "[${if (error) "Error" else "Warning"}] $element: $message"
        }
    }
}