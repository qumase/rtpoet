package ca.jahed.rtpoet.rtmodel.sm

import ca.jahed.rtpoet.exceptions.BuildException
import ca.jahed.rtpoet.rtmodel.RTAction
import ca.jahed.rtpoet.rtmodel.RTCapsule
import ca.jahed.rtpoet.rtmodel.RTElement
import ca.jahed.rtpoet.rtmodel.builders.sm.RTTransitionBuilder

class RTTransition(
    var source: RTGenericState,
    var target: RTGenericState,
) : RTElement() {
    val triggers = mutableListOf<RTTrigger>()
    var guard: RTAction? = null
    var action: RTAction? = null

    private abstract class Builder(
        protected var source: RTGenericState? = null,
        protected var target: RTGenericState? = null,
    ) : RTTransitionBuilder {
        protected val triggers: MutableList<RTTrigger> = mutableListOf()
        protected var guard: RTAction? = null
        protected var action: RTAction? = null

        protected var guardStr: String? = null
        protected var actionStr: String? = null

        override fun guard(guard: RTAction) = apply {
            this.guard = guard
        }

        override fun guard(guard: String) = apply {
            this.guardStr = guard
        }

        override fun action(action: RTAction) = apply {
            this.action = action
        }

        override fun action(action: String) = apply {
            this.actionStr = action
        }

        override fun trigger(trigger: RTTrigger): RTTransitionBuilder {
            throw NotImplementedError()
        }

        override fun trigger(portRegex: String, signal: String): RTTransitionBuilder {
            throw NotImplementedError()
        }

        override fun build(): RTTransition {
            throw NotImplementedError()
        }

        override fun build(region: RTRegion, capsule: RTCapsule): RTTransition {
            throw NotImplementedError()
        }
    }

    companion object {
        @JvmStatic
        fun builder(source: RTGenericState, target: RTGenericState): RTTransitionBuilder {
            class Builder(source: RTGenericState, target: RTGenericState) : RTTransition.Builder(source, target) {
                override fun trigger(trigger: RTTrigger) = apply { triggers.add(trigger) }

                override fun build(): RTTransition {
                    val transition = RTTransition(super.source!!, super.target!!)
                    transition.guard = guard ?: if (guardStr != null) RTAction(guardStr!!) else null
                    transition.action = action ?: if (actionStr != null) RTAction(actionStr!!) else null
                    triggers.forEach { transition.triggers.add(it) }
                    return transition
                }
            }

            return Builder(source, target)
        }

        @JvmStatic
        fun builder(source: String, target: String): RTTransitionBuilder {
            class Builder2(
                private var sourceStr: String,
                private var targetStr: String,
            ) : RTTransition.Builder() {
                private val triggerDescs = mutableListOf<Pair<String, String>>()

                override fun trigger(portRegex: String, signal: String) = apply {
                    triggerDescs.add(Pair(portRegex, signal))
                }

                override fun build(region: RTRegion, capsule: RTCapsule): RTTransition {
                    resolveEndPoints(region)
                    resolveTriggers(capsule)

                    val transition = RTTransition(this.source!!, this.target!!)
                    triggers.forEach { transition.triggers.add(it) }
                    transition.guard = guard ?: if (guardStr != null) RTAction(guardStr!!) else null
                    transition.action = action ?: if (actionStr != null) RTAction(actionStr!!) else null
                    return transition
                }

                private fun resolveEndPoints(region: RTRegion? = null) {
                    this.source = this.source ?: region?.states?.find { it.name == this.sourceStr }
                            ?: throw BuildException("""
                                No state matching '${this.sourceStr}' found in statemachine
                            """.trimMargin())

                    this.target = this.target ?: region?.states?.find { it.name == this.targetStr }
                            ?: throw BuildException("""
                                No state matching '${this.targetStr}' found in statemachine
                            """.trimMargin())
                }

                private fun resolveTriggers(capsule: RTCapsule) {
                    triggerDescs.forEach { triggerDesc ->
                        val portPattern = triggerDesc.first.toRegex()
                        val signalStr = triggerDesc.second

                        val matchedPorts = capsule.ports.filter { portPattern.containsMatchIn(it.name) }
                        if (matchedPorts.isEmpty())
                            throw BuildException("""
                                No ports matching '$portPattern' found in capsule $capsule
                            """.trimIndent())

                        val signal = matchedPorts.first().inputs().find { signalStr == it.name }
                            ?: throw BuildException("""
                                Signal $signalStr not an input to port ${matchedPorts.first()}(${matchedPorts.first().protocol})
                            """.trimIndent())

                        matchedPorts.forEach { port ->
                            if (!port.inputs().contains(signal)) throw BuildException("""
                                Signal $signal not an input to port $port(${port.protocol})
                            """.trimIndent())

                            triggers.add(RTTrigger(signal, port))
                        }
                    }

                    triggerDescs.clear()
                }
            }

            return Builder2(source, target)
        }
    }
}