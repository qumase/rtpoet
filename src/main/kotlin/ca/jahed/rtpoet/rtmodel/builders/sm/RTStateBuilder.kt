package ca.jahed.rtpoet.rtmodel.builders.sm

import ca.jahed.rtpoet.rtmodel.RTAction

interface RTStateBuilder : RTGenericStateBuilder {
    fun entry(action: RTAction): RTStateBuilder
    fun entry(action: String): RTStateBuilder
    fun exit(action: RTAction): RTStateBuilder
    fun exit(action: String): RTStateBuilder

}