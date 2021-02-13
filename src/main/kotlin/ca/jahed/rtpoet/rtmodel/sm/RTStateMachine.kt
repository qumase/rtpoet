package ca.jahed.rtpoet.rtmodel.sm

import ca.jahed.rtpoet.rtmodel.RTCapsule
import ca.jahed.rtpoet.rtmodel.RTElement
import ca.jahed.rtpoet.rtmodel.builders.sm.RTGenericStateBuilder
import ca.jahed.rtpoet.rtmodel.builders.sm.RTStateMachineBuilder
import ca.jahed.rtpoet.rtmodel.builders.sm.RTTransitionBuilder


class RTStateMachine private constructor(private val region: RTRegion) : IRTRegion, RTElement() {

    constructor() : this(RTRegion())

    override fun states(): MutableList<RTGenericState> {
        return region.states
    }

    override fun transitions(): MutableList<RTTransition> {
        return region.transitions
    }

    private class Builder : RTStateMachineBuilder {
        private val regionBuilder = RTRegion.builder()

        override fun state(state: RTGenericState) = apply { regionBuilder.state(state) }
        override fun state(state: RTGenericStateBuilder) = apply { regionBuilder.state(state) }
        override fun transition(transition: RTTransition) = apply { regionBuilder.transition(transition) }
        override fun transition(transition: RTTransitionBuilder) = apply { regionBuilder.transition(transition) }

        override fun build(): RTStateMachine {
            return RTStateMachine(regionBuilder.build())
        }

        override fun build(capsule: RTCapsule): RTStateMachine {
            return RTStateMachine(regionBuilder.build(capsule))
        }
    }

    companion object {
        @JvmStatic
        fun builder(): RTStateMachineBuilder {
            return Builder()
        }
    }
}