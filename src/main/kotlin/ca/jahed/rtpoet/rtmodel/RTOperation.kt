package ca.jahed.rtpoet.rtmodel

import ca.jahed.rtpoet.rtmodel.builders.RTActionBuilder
import ca.jahed.rtpoet.rtmodel.builders.RTOperationBuilder
import ca.jahed.rtpoet.rtmodel.builders.RTParameterBuilder
import ca.jahed.rtpoet.rtmodel.builders.cppproperties.RTOperationPropertiesBuilder
import ca.jahed.rtpoet.rtmodel.cppproperties.RTOperationProperties

open class RTOperation(name: String) : RTElement(name) {
    var visibility = RTVisibilityKind.PROTECTED
    val parameters = mutableListOf<RTParameter>()
    var action: RTAction? = null
    var ret: RTParameter? = null
    var properties: RTOperationProperties? = null

    private class Builder(private val name: String) : RTOperationBuilder {
        private var visibility = RTVisibilityKind.PROTECTED
        private val parameters = mutableListOf<RTParameter>()
        private var ret: RTParameter? = null
        private var action: RTAction? = null
        var properties: RTOperationProperties? = null

        private val parameterBuilders = mutableListOf<RTParameterBuilder>()
        private var retBuilder: RTParameterBuilder? = null
        private var actionBuilder: RTActionBuilder? = null
        private var propertiesBuilder: RTOperationPropertiesBuilder? = null

        override fun publicVisibility() = apply { visibility = RTVisibilityKind.PUBLIC }
        override fun privateVisibility() = apply { visibility = RTVisibilityKind.PRIVATE }
        override fun protectedVisibility() = apply { visibility = RTVisibilityKind.PROTECTED }

        override fun parameter(param: RTParameter) = apply { parameters.add(param) }
        override fun parameter(param: RTParameterBuilder) = apply { parameterBuilders.add(param) }

        override fun ret(param: RTParameter) = apply {
            this.ret = param
            this.retBuilder = null
        }

        override fun ret(param: RTParameterBuilder) = apply {
            this.retBuilder = param
            this.ret = null
        }

        override fun action(action: RTAction) = apply {
            this.action = action
            this.actionBuilder = null
        }

        override fun action(action: RTActionBuilder) = apply {
            this.actionBuilder = action
            this.action = null
        }


        override fun properties(properties: RTOperationProperties) = apply {
            this.properties = properties
            this.propertiesBuilder = null
        }

        override fun properties(properties: RTOperationPropertiesBuilder) = apply {
            this.propertiesBuilder = properties
            this.properties = null
        }

        override fun build(): RTOperation {
            val op = RTOperation(this.name)
            op.visibility = visibility
            op.ret = retBuilder?.build() ?: ret
            op.action = actionBuilder?.build() ?: action ?: RTAction()
            op.properties = propertiesBuilder?.build() ?: properties
            parameterBuilders.forEach { parameters.add(it.build()) }
            parameters.forEach { op.parameters.add(it) }
            return op
        }
    }

    companion object {
        @JvmStatic
        fun builder(name: String): RTOperationBuilder {
            return Builder(name)
        }
    }
}