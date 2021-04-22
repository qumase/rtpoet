package ca.jahed.rtpoet.rtmodel

import ca.jahed.rtpoet.rtmodel.builders.RTProtocolBuilder
import ca.jahed.rtpoet.rtmodel.builders.RTSignalBuilder
import ca.jahed.rtpoet.rtmodel.rts.RTSystemSignal
import ca.jahed.rtpoet.rtmodel.types.RTType

open class RTProtocol(name: String, var anySignal: RTSignal = RTSignal.any()) : RTType(name) {
    val inputSignals = mutableListOf<RTSignal>()
    val outputSignals = mutableListOf<RTSignal>()
    val inOutSignals = mutableListOf<RTSignal>()

    init {
        inOutSignals.add(RTSystemSignal.rtBound())
        inOutSignals.add(RTSystemSignal.rtUnbound())
    }

    fun inputs(): List<RTSignal> {
        val inputs = mutableListOf(anySignal)
        inputs.addAll(inputSignals)
        inputs.addAll(inOutSignals)
        return inputs
    }

    fun outputs(): List<RTSignal> {
        val output = mutableListOf(anySignal)
        output.addAll(outputSignals)
        output.addAll(inOutSignals)
        return output
    }

    private class Builder(private val name: String) : RTProtocolBuilder {
        private val inputSignals = mutableListOf<RTSignal>()
        private val outputSignals = mutableListOf<RTSignal>()
        private val inOutSignals = mutableListOf<RTSignal>()

        private val inputSignalBuilders = mutableListOf<RTSignalBuilder>()
        private val outputSignalBuilders = mutableListOf<RTSignalBuilder>()
        private val inOutSignalBuilders = mutableListOf<RTSignalBuilder>()

        override fun input(signal: RTSignal) = apply { inputSignals.add(signal) }
        override fun input(signal: RTSignalBuilder) = apply { inputSignalBuilders.add(signal) }
        override fun output(signal: RTSignal) = apply { outputSignals.add(signal) }
        override fun output(signal: RTSignalBuilder) = apply { outputSignalBuilders.add(signal) }
        override fun inOut(signal: RTSignal) = apply { inOutSignals.add(signal) }
        override fun inOut(signal: RTSignalBuilder) = apply { inOutSignalBuilders.add(signal) }

        override fun build(): RTProtocol {
            val protocol = RTProtocol(this.name)
            inputSignalBuilders.forEach { inputSignals.add(it.build()) }
            outputSignalBuilders.forEach { outputSignals.add(it.build()) }
            inOutSignalBuilders.forEach { inOutSignals.add(it.build()) }

            inputSignals.forEach { protocol.inputSignals.add(it) }
            outputSignals.forEach { protocol.outputSignals.add(it) }
            inOutSignals.forEach { protocol.inOutSignals.add(it) }
            return protocol
        }
    }

    companion object {
        @JvmStatic
        fun builder(name: String): RTProtocolBuilder {
            return Builder(name)
        }
    }
}