package ca.jahed.rtpoet.rtmodel

import ca.jahed.rtpoet.rtmodel.builders.RTParameterBuilder
import ca.jahed.rtpoet.rtmodel.builders.cppproperties.RTParameterPropertiesBuilder
import ca.jahed.rtpoet.rtmodel.types.RTType

class RTParameter(name: String, type: RTType) : RTAttribute(name, type) {
    constructor(type: RTType) : this("", type)

    private class Builder(private val name: String, private val type: RTType) : RTParameterBuilder {
        private var replication: Int = 1
        private var propertiesBuilder: RTParameterPropertiesBuilder? = null

        override fun replication(replication: Int) = apply { this.replication = replication }

        override fun properties(properties: RTParameterPropertiesBuilder) = apply {
            this.propertiesBuilder = properties
        }

        override fun build(): RTParameter {
            val parameter = RTParameter(this.name, this.type)
            parameter.replication = replication
            parameter.properties = propertiesBuilder?.build()
            return parameter
        }
    }

    companion object {
        @JvmStatic
        fun builder(name: String, type: RTType): RTParameterBuilder {
            return Builder(name, type)
        }
    }
}