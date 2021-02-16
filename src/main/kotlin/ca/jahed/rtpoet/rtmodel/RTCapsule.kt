package ca.jahed.rtpoet.rtmodel

import ca.jahed.rtpoet.rtmodel.builders.*
import ca.jahed.rtpoet.rtmodel.builders.cppproperties.RTCapsulePropertiesBuilder
import ca.jahed.rtpoet.rtmodel.builders.sm.RTStateMachineBuilder
import ca.jahed.rtpoet.rtmodel.cppproperties.RTCapsuleProperties
import ca.jahed.rtpoet.rtmodel.sm.RTStateMachine

class RTCapsule(name: String, superClass: RTClass? = null) : RTClass(name, superClass) {
    val parts = mutableListOf<RTCapsulePart>()
    val ports = mutableListOf<RTPort>()
    val connectors = mutableListOf<RTConnector>()
    var stateMachine: RTStateMachine? = null

    private class Builder(private val name: String) : RTCapsuleBuilder {
        private var superClass: RTClass? = null
        private val attributes = mutableListOf<RTAttribute>()
        private val operations = mutableListOf<RTOperation>()
        private val parts = mutableListOf<RTCapsulePart>()
        private val ports = mutableListOf<RTPort>()
        private val connectors = mutableListOf<RTConnector>()
        private var stateMachine: RTStateMachine? = null
        private var properties: RTCapsuleProperties? = null

        private val attributeBuilders = mutableListOf<RTAttributeBuilder>()
        private val operationBuilders = mutableListOf<RTOperationBuilder>()
        private val partBuilders = mutableListOf<RTCapsulePartBuilder>()
        private val portBuilders = mutableListOf<RTPortBuilder>()
        private val connectorBuilders = mutableListOf<RTConnectorBuilder>()
        private var stateMachineBuilder: RTStateMachineBuilder? = null
        private var propertiesBuilder: RTCapsulePropertiesBuilder? = null

        override fun superClass(superClass: RTClass) = apply { this.superClass = superClass }
        override fun attribute(attr: RTAttribute) = apply { attributes.add(attr) }
        override fun attribute(attr: RTAttributeBuilder) = apply { attributeBuilders.add(attr) }
        override fun operation(op: RTOperation) = apply { operations.add(op) }
        override fun operation(op: RTOperationBuilder) = apply { operationBuilders.add(op) }
        override fun part(part: RTCapsulePart) = apply { parts.add(part) }
        override fun part(part: RTCapsulePartBuilder) = apply { partBuilders.add(part) }
        override fun port(port: RTPort) = apply { ports.add(port) }
        override fun port(port: RTPortBuilder) = apply { portBuilders.add(port) }
        override fun connector(connector: RTConnector) = apply { connectors.add(connector) }
        override fun connector(connector: RTConnectorBuilder) = apply { connectorBuilders.add(connector) }

        override fun statemachine(stateMachine: RTStateMachine) = apply {
            this.stateMachine = stateMachine
            this.stateMachineBuilder = null
        }

        override fun statemachine(stateMachine: RTStateMachineBuilder) = apply {
            this.stateMachineBuilder = stateMachine
            this.stateMachine = null
        }


        override fun properties(properties: RTCapsuleProperties) = apply {
            this.properties = properties
            this.propertiesBuilder = null
        }

        override fun properties(properties: RTCapsulePropertiesBuilder) = apply {
            this.propertiesBuilder = properties
            this.properties = null
        }

        override fun build(): RTCapsule {
            val capsule = RTCapsule(this.name, this.superClass)
            attributeBuilders.forEach { attributes.add(it.build()) }
            attributes.forEach { capsule.attributes.add(it) }

            operationBuilders.forEach { operations.add(it.build()) }
            operations.forEach { capsule.operations.add(it) }

            partBuilders.forEach { parts.add(it.build()) }
            parts.forEach { capsule.parts.add(it) }

            portBuilders.forEach { ports.add(it.build()) }
            ports.forEach { capsule.ports.add(it) }

            connectorBuilders.forEach { connectors.add(it.build(capsule)) }
            connectors.forEach { capsule.connectors.add(it) }

            capsule.stateMachine = stateMachineBuilder?.build(capsule) ?: stateMachine
            capsule.properties = propertiesBuilder?.build() ?: properties
            return capsule
        }
    }

    companion object {
        @JvmStatic
        fun builder(name: String): RTCapsuleBuilder {
            return Builder(name)
        }
    }
}