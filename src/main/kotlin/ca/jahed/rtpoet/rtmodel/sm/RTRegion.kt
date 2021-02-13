package ca.jahed.rtpoet.rtmodel.sm

import ca.jahed.rtpoet.rtmodel.RTCapsule
import ca.jahed.rtpoet.rtmodel.RTElement
import ca.jahed.rtpoet.rtmodel.builders.sm.RTCompositeStateBuilder
import ca.jahed.rtpoet.rtmodel.builders.sm.RTGenericStateBuilder
import ca.jahed.rtpoet.rtmodel.builders.sm.RTRegionBuilder
import ca.jahed.rtpoet.rtmodel.builders.sm.RTTransitionBuilder

class RTRegion : RTElement() {
    val states = mutableListOf<RTGenericState>()
    val transitions = mutableListOf<RTTransition>()

    private class Builder : RTRegionBuilder {
        private val states = mutableListOf<RTGenericState>()
        private val transitions = mutableListOf<RTTransition>()

        private val stateBuilders = mutableListOf<RTGenericStateBuilder>()
        private val transitionBuilders = mutableListOf<RTTransitionBuilder>()

        override fun state(state: RTGenericState) = apply { states.add(state) }
        override fun state(state: RTGenericStateBuilder) = apply { stateBuilders.add(state) }
        override fun transition(transition: RTTransition) = apply { transitions.add(transition) }
        override fun transition(transition: RTTransitionBuilder) = apply { transitionBuilders.add(transition) }

        override fun build(): RTRegion {
            val region = RTRegion()
            stateBuilders.forEach { states.add(it.build()) }
            states.forEach { region.states.add(it) }

            transitionBuilders.forEach { transitions.add(it.build()) }
            transitions.forEach { region.transitions.add(it) }
            return region
        }

        override fun build(capsule: RTCapsule): RTRegion {
            val region = RTRegion()
            stateBuilders.forEach {
                if (it is RTCompositeStateBuilder) states.add(it.build(capsule))
                else states.add(it.build())
            }
            states.forEach { region.states.add(it) }

            transitionBuilders.forEach { transitions.add(it.build(region, capsule)) }
            transitions.forEach { region.transitions.add(it) }
            return region
        }
    }

    companion object {
        @JvmStatic
        fun builder(): RTRegionBuilder {
            return Builder()
        }
    }
}