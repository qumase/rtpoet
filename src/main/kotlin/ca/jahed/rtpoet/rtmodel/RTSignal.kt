package ca.jahed.rtpoet.rtmodel

import ca.jahed.rtpoet.rtmodel.builders.RTParameterBuilder
import ca.jahed.rtpoet.rtmodel.builders.RTSignalBuilder

open class RTSignal(name: String, var isAny: Boolean = false) : RTElement(name) {
    val parameters = mutableListOf<RTParameter>()

    protected class Builder(private val name: String) : RTSignalBuilder {
        private val parameters = mutableListOf<RTParameter>()
        private val parameterBuilders = mutableListOf<RTParameterBuilder>()

        override fun parameter(param: RTParameter) = apply { parameters.add(param) }
        override fun parameter(param: RTParameterBuilder) = apply { parameterBuilders.add(param) }

        override fun build(): RTSignal {
            val signal = RTSignal(this.name)
            parameterBuilders.forEach { parameters.add(it.build()) }
            parameters.forEach { signal.parameters.add(it) }
            return signal
        }
    }

    companion object {
        fun any(): RTSignal {
            return RTSignal("*", true)
        }

        @JvmStatic
        fun builder(name: String): RTSignalBuilder {
            return Builder(name)
        }
    }
}