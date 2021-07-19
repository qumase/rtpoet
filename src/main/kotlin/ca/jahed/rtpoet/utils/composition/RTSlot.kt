package ca.jahed.rtpoet.utils.composition

import ca.jahed.rtpoet.rtmodel.RTCapsulePart
import ca.jahed.rtpoet.rtmodel.RTElement
import ca.jahed.rtpoet.rtmodel.RTPort

class RTSlot(val part: RTCapsulePart, val index: Int, val parent: RTSlot? = null) :
    RTElement(
        if (parent != null) parent.name + "." + part.name + "[" + index + "]"
        else part.name + "[" + index + "]"
    ) {

    val children = mutableListOf<RTSlot>()
    val siblings = mutableListOf<RTSlot>()
    val neighbors = mutableListOf<RTSlot>()
    val connections = mutableMapOf<RTPort, MutableSet<RTConnection>>()

    var annotations = mutableMapOf<String, Any>()

    fun addConnection(srcPort: RTPort, destPort: RTPort, destSlot: RTSlot, wired: Boolean = true) {
        connections.getOrPut(srcPort, { mutableSetOf() }).add(RTConnection(destPort, destSlot, wired))
    }

    fun hasAnnotation(key: String): Boolean {
        return key in annotations
    }

    fun getAnnotation(key: String): Any? {
        return annotations[key]
    }
}