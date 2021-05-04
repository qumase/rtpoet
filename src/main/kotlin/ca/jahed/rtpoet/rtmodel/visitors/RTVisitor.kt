package ca.jahed.rtpoet.rtmodel.visitors

import ca.jahed.rtpoet.rtmodel.*
import ca.jahed.rtpoet.rtmodel.cppproperties.*
import ca.jahed.rtpoet.rtmodel.sm.*
import ca.jahed.rtpoet.rtmodel.types.RTType
import ca.jahed.rtpoet.rtmodel.values.RTValue

abstract class RTVisitor {
    open fun visitModel(model: RTModel): Any = {}
    open fun visitPackage(pkg: RTPackage): Any = {}
    open fun visitCapsule(capsule: RTCapsule): Any = {}
    open fun visitClass(klass: RTClass): Any = {}
    open fun visitProtocol(protocol: RTProtocol): Any = {}
    open fun visitEnumeration(enumeration: RTEnumeration): Any = {}
    open fun visitPart(part: RTCapsulePart): Any = {}
    open fun visitPort(port: RTPort): Any = {}
    open fun visitConnector(connector: RTConnector): Any = {}
    open fun visitSignal(signal: RTSignal): Any = {}
    open fun visitArtifact(artifact: RTArtifact): Any = {}
    open fun visitAttribute(attribute: RTAttribute): Any = {}
    open fun visitAction(action: RTAction): Any = {}
    open fun visitOperation(operation: RTOperation): Any = {}
    open fun visitParameter(param: RTParameter): Any = {}
    open fun visitType(type: RTType): Any = {}
    open fun visitValue(value: RTValue): Any = {}

    open fun visitStateMachine(statemachine: RTStateMachine): Any = {}
    open fun visitCompositeState(state: RTCompositeState): Any = {}
    open fun visitPseudoState(state: RTPseudoState): Any = {}
    open fun visitState(state: RTState): Any = {}
    open fun visitTransition(transition: RTTransition): Any = {}
    open fun visitTrigger(trigger: RTTrigger): Any = {}

    open fun visitArtifactProperties(props: RTArtifactProperties): Any = {}
    open fun visitAttributeProperties(props: RTAttributeProperties): Any = {}
    open fun visitCapsuleProperties(props: RTCapsuleProperties): Any = {}
    open fun visitClassProperties(props: RTClassProperties): Any = {}
    open fun visitEnumerationProperties(props: RTEnumerationProperties): Any = {}
    open fun visitOperationProperties(props: RTOperationProperties): Any = {}
    open fun visitParameterProperties(props: RTParameterProperties): Any = {}
    open fun visitTypeProperties(props: RTTypeProperties): Any = {}

    open fun visit(element: RTElement): Any {
        return when (element) {
            is RTModel -> visitModel(element)
            is RTCapsule -> visitCapsule(element)
            is RTProtocol -> visitProtocol(element)
            is RTPort -> visitPort(element)
            is RTCapsulePart -> visitPart(element)
            is RTSignal -> visitSignal(element)
            is RTEnumeration -> visitEnumeration(element)
            is RTConnector -> visitConnector(element)
            is RTParameter -> visitParameter(element)
            is RTOperation -> visitOperation(element)
            is RTAttribute -> visitAttribute(element)
            is RTArtifact -> visitArtifact(element)
            is RTAction -> visitAction(element)
            is RTPackage -> visitPackage(element)
            is RTClass -> visitClass(element)
            is RTType -> visitType(element)
            is RTValue -> visitValue(element)

            is RTStateMachine -> visitStateMachine(element)
            is RTCompositeState -> visitCompositeState(element)
            is RTPseudoState -> visitPseudoState(element)
            is RTState -> visitState(element)
            is RTTransition -> visitTransition(element)
            is RTTrigger -> visitTrigger(element)

            is RTTypeProperties -> visitTypeProperties(element)
            is RTArtifactProperties -> visitArtifactProperties(element)
            is RTParameterProperties -> visitParameterProperties(element)
            is RTOperationProperties -> visitOperationProperties(element)
            is RTAttributeProperties -> visitAttributeProperties(element)
            is RTEnumerationProperties -> visitEnumerationProperties(element)
            is RTCapsuleProperties -> visitCapsuleProperties(element)
            is RTClassProperties -> visitClassProperties(element)

            else -> throw RuntimeException("Unexpected element type ${element.javaClass.simpleName}")
        }
    }
}