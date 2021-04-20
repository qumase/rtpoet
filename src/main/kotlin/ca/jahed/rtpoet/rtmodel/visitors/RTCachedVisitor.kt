package ca.jahed.rtpoet.rtmodel.visitors

import ca.jahed.rtpoet.rtmodel.RTElement

abstract class RTCachedVisitor : RTVisitor() {
    private val cache = mutableMapOf<RTElement, Any>()
    private val listeners = mutableListOf<RTVisitorListener>()

    override fun visit(element: RTElement): Any {
        if (element in cache) return cache[element]!!
        val result = super.visit(element)
        cache[element] = result
        listeners.forEach { it.onVisit(element, result) }
        return result
    }

    fun addListener(listener: RTVisitorListener) {
        listeners.add(listener)
    }

    fun removeListener(listener: RTVisitorListener) {
        listeners.remove(listener)
    }
}