package ca.jahed.rtpoet.rtmodel

import ca.jahed.rtpoet.rtmodel.builders.RTAttributeBuilder
import ca.jahed.rtpoet.rtmodel.builders.RTClassBuilder
import ca.jahed.rtpoet.rtmodel.builders.RTOperationBuilder
import ca.jahed.rtpoet.rtmodel.builders.cppproperties.RTClassPropertiesBuilder
import ca.jahed.rtpoet.rtmodel.cppproperties.RTProperties
import ca.jahed.rtpoet.rtmodel.types.RTType

open class RTClass(name: String, var superClass: RTClass? = null) : RTType(name) {
    val attributes = mutableListOf<RTAttribute>()
    val operations = mutableListOf<RTOperation>()
    open var properties: RTProperties? = null

    protected class Builder(private val name: String) : RTClassBuilder {
        private var superClass: RTClass? = null
        private val attributes = mutableListOf<RTAttribute>()
        private val operations = mutableListOf<RTOperation>()

        private val attributeBuilders = mutableListOf<RTAttributeBuilder>()
        private val operationBuilders = mutableListOf<RTOperationBuilder>()
        private var propertiesBuilder: RTClassPropertiesBuilder? = null

        override fun superClass(parent: RTClass) = apply { this.superClass = parent }
        override fun attribute(attr: RTAttribute) = apply { attributes.add(attr) }
        override fun attribute(attr: RTAttributeBuilder) = apply { attributeBuilders.add(attr) }
        override fun operation(op: RTOperation) = apply { operations.add(op) }
        override fun operation(op: RTOperationBuilder) = apply { operationBuilders.add(op) }
        override fun properties(properties: RTClassPropertiesBuilder) = apply { this.propertiesBuilder = properties }

        override fun build(): RTClass {
            val klass = RTClass(name, superClass)
            attributeBuilders.forEach { attributes.add(it.build()) }
            attributes.forEach { klass.attributes.add(it) }
            operationBuilders.forEach { operations.add(it.build()) }
            operations.forEach { klass.operations.add(it) }
            klass.properties = propertiesBuilder?.build()
            return klass
        }
    }

    companion object {
        @JvmStatic
        fun builder(name: String): RTClassBuilder {
            return Builder(name)
        }
    }
}