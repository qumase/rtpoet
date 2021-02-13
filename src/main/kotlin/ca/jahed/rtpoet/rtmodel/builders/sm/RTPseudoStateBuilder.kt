package ca.jahed.rtpoet.rtmodel.builders.sm

interface RTPseudoStateBuilder : RTGenericStateBuilder {
    fun initial(): RTPseudoStateBuilder
    fun history(): RTPseudoStateBuilder
    fun join(): RTPseudoStateBuilder
    fun junction(): RTPseudoStateBuilder
    fun choice(): RTPseudoStateBuilder
    fun entryPoint(): RTPseudoStateBuilder
    fun exitPoint(): RTPseudoStateBuilder
}