package ca.jahed.rtpoet.rtmodel.builders.sm

import ca.jahed.rtpoet.rtmodel.RTCapsule
import ca.jahed.rtpoet.rtmodel.sm.RTGenericState
import ca.jahed.rtpoet.rtmodel.sm.RTStateMachine
import ca.jahed.rtpoet.rtmodel.sm.RTTransition

interface RTStateMachineBuilder {
    fun state(state: RTGenericState): RTStateMachineBuilder
    fun state(state: RTGenericStateBuilder): RTStateMachineBuilder
    fun transition(transition: RTTransition): RTStateMachineBuilder
    fun transition(transition: RTTransitionBuilder): RTStateMachineBuilder
    fun build(): RTStateMachine
    fun build(capsule: RTCapsule): RTStateMachine
}