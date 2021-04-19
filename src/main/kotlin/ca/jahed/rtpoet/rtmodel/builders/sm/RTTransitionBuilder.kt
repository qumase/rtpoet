package ca.jahed.rtpoet.rtmodel.builders.sm

import ca.jahed.rtpoet.rtmodel.RTAction
import ca.jahed.rtpoet.rtmodel.RTCapsule
import ca.jahed.rtpoet.rtmodel.sm.RTRegion
import ca.jahed.rtpoet.rtmodel.sm.RTTransition
import ca.jahed.rtpoet.rtmodel.sm.RTTrigger

interface RTTransitionBuilder {
    fun trigger(trigger: RTTrigger): RTTransitionBuilder
    fun trigger(portRegex: String, signal: String): RTTransitionBuilder
    fun guard(guard: RTAction): RTTransitionBuilder
    fun guard(guard: String): RTTransitionBuilder
    fun action(action: RTAction): RTTransitionBuilder
    fun action(action: String): RTTransitionBuilder
    fun build(): RTTransition
    fun build(region: RTRegion, capsule: RTCapsule): RTTransition
}