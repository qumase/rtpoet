package ca.jahed.rtpoet.rtmodel.visitors

import ca.jahed.rtpoet.rtmodel.RTElement

abstract class RTCachedVisitor : RTVisitor() {
    private val cache = mutableMapOf<RTElement, Any>()

    override fun visit(element: RTElement): Any {
        return cache.getOrPut(element, { super.visit(element) })
    }
}