package ca.jahed.rtpoet.rtmodel.sm

internal interface IRTRegion {
    fun states(): MutableList<RTGenericState>
    fun transitions(): MutableList<RTTransition>
}