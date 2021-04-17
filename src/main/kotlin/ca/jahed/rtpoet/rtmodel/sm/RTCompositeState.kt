package ca.jahed.rtpoet.rtmodel.sm

import ca.jahed.rtpoet.rtmodel.RTAction
import ca.jahed.rtpoet.rtmodel.RTCapsule
import ca.jahed.rtpoet.rtmodel.builders.sm.RTCompositeStateBuilder
import ca.jahed.rtpoet.rtmodel.builders.sm.RTGenericStateBuilder
import ca.jahed.rtpoet.rtmodel.builders.sm.RTTransitionBuilder

open class RTCompositeState private constructor(
    name: String,
    private val region: RTRegion,
) : IRTRegion, RTState(name) {

    constructor(name: String) : this(name, RTRegion())

    override fun states(): MutableList<RTGenericState> {
        return region.states
    }

    override fun transitions(): MutableList<RTTransition> {
        return region.transitions
    }

    private class Builder(private val name: String) : RTCompositeStateBuilder {
        var entryAction: RTAction? = null
        var exitAction: RTAction? = null

        var entryActionStr: String? = null
        var exitActionStr: String? = null

        private val regionBuilder = RTRegion.builder()

        override fun state(state: RTGenericState) = apply { regionBuilder.state(state) }
        override fun state(state: RTGenericStateBuilder) = apply { regionBuilder.state(state) }
        override fun transition(transition: RTTransition) = apply { regionBuilder.transition(transition) }
        override fun transition(transition: RTTransitionBuilder) = apply { regionBuilder.transition(transition) }

        override fun entry(action: RTAction) = apply {
            entryAction = action
            entryActionStr = null
        }

        override fun exit(action: RTAction) = apply {
            exitAction = action
            exitActionStr = null
        }

        override fun entry(action: String) = apply {
            entryActionStr = action
            entryAction = null
        }

        override fun exit(action: String) = apply {
            exitActionStr = action
            exitAction = null
        }

        override fun build(): RTCompositeState {
            val state = RTCompositeState(name, regionBuilder.build())
            state.entryAction = entryAction ?: if (entryActionStr != null) RTAction(entryActionStr!!) else null
            state.exitAction = exitAction ?: if (exitActionStr != null) RTAction(exitActionStr!!) else null
            return state
        }

        override fun build(capsule: RTCapsule): RTCompositeState {
            val state = RTCompositeState(name, regionBuilder.build(capsule))
            state.entryAction = entryAction ?: if (entryActionStr != null) RTAction(entryActionStr!!) else null
            state.exitAction = exitAction ?: if (exitActionStr != null) RTAction(exitActionStr!!) else null
            return state
        }
    }

    companion object {
        @JvmStatic
        fun builder(name: String): RTCompositeStateBuilder {
            return Builder(name)
        }
    }
}