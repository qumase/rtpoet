package ca.jahed.rtpoet.rtmodel.builders.sm

import ca.jahed.rtpoet.rtmodel.RTCapsule
import ca.jahed.rtpoet.rtmodel.sm.RTCompositeState
import ca.jahed.rtpoet.rtmodel.sm.RTGenericState
import ca.jahed.rtpoet.rtmodel.sm.RTTransition

interface RTCompositeStateBuilder : RTStateBuilder {
    fun state(state: RTGenericState): RTCompositeStateBuilder
    fun state(state: RTGenericStateBuilder): RTCompositeStateBuilder
    fun transition(transition: RTTransition): RTCompositeStateBuilder
    fun transition(transition: RTTransitionBuilder): RTCompositeStateBuilder
    fun build(capsule: RTCapsule): RTCompositeState
}