package ca.jahed.rtpoet.rtmodel.builders

import ca.jahed.rtpoet.rtmodel.*
import ca.jahed.rtpoet.rtmodel.builders.cppproperties.RTCapsulePropertiesBuilder
import ca.jahed.rtpoet.rtmodel.builders.sm.RTStateMachineBuilder
import ca.jahed.rtpoet.rtmodel.cppproperties.RTCapsuleProperties
import ca.jahed.rtpoet.rtmodel.sm.RTStateMachine

interface RTCapsuleBuilder {
    fun superClass(superClass: RTClass): RTCapsuleBuilder
    fun attribute(attr: RTAttribute): RTCapsuleBuilder
    fun attribute(attr: RTAttributeBuilder): RTCapsuleBuilder
    fun operation(op: RTOperation): RTCapsuleBuilder
    fun operation(op: RTOperationBuilder): RTCapsuleBuilder
    fun part(part: RTCapsulePart): RTCapsuleBuilder
    fun part(part: RTCapsulePartBuilder): RTCapsuleBuilder
    fun port(port: RTPort): RTCapsuleBuilder
    fun port(port: RTPortBuilder): RTCapsuleBuilder
    fun connector(connector: RTConnector): RTCapsuleBuilder
    fun connector(connector: RTConnectorBuilder): RTCapsuleBuilder
    fun statemachine(stateMachine: RTStateMachine): RTCapsuleBuilder
    fun statemachine(stateMachine: RTStateMachineBuilder): RTCapsuleBuilder
    fun properties(properties: RTCapsuleProperties): RTCapsuleBuilder
    fun properties(properties: RTCapsulePropertiesBuilder): RTCapsuleBuilder
    fun build(): RTCapsule
}