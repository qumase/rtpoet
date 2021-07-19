package ca.jahed.rtpoet.utils.composition

interface RTModelAnnotator {
    fun annotate(slot: RTSlot): MutableMap<String, Any>
}