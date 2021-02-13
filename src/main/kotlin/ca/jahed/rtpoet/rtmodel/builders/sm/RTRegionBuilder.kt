package ca.jahed.rtpoet.rtmodel.builders.sm

import ca.jahed.rtpoet.rtmodel.RTCapsule
import ca.jahed.rtpoet.rtmodel.sm.RTGenericState
import ca.jahed.rtpoet.rtmodel.sm.RTRegion
import ca.jahed.rtpoet.rtmodel.sm.RTTransition

interface RTRegionBuilder {
    fun state(state: RTGenericState): RTRegionBuilder
    fun state(state: RTGenericStateBuilder): RTRegionBuilder
    fun transition(transition: RTTransition): RTRegionBuilder
    fun transition(transition: RTTransitionBuilder): RTRegionBuilder
    fun build(): RTRegion
    fun build(capsule: RTCapsule): RTRegion
}