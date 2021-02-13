package ca.jahed.rtpoet.rtmodel.sm

import ca.jahed.rtpoet.rtmodel.RTAction
import ca.jahed.rtpoet.rtmodel.builders.sm.RTStateBuilder

open class RTState(name: String) : RTGenericState(name) {
    var entryAction: RTAction? = null
    var exitAction: RTAction? = null

    private class Builder(private val name: String) : RTStateBuilder {
        var entryAction: RTAction? = null
        var exitAction: RTAction? = null

        var entryActionStr: String? = null
        var exitActionStr: String? = null

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

        override fun build(): RTState {
            val state = RTState(name)
            state.entryAction = entryAction ?: if (entryActionStr != null) RTAction(entryActionStr!!) else null
            state.exitAction = exitAction ?: if (exitActionStr != null) RTAction(exitActionStr!!) else null
            return state
        }
    }

    companion object {
        @JvmStatic
        fun builder(name: String): RTStateBuilder {
            return Builder(name)
        }
    }
}