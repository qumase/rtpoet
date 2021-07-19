package ca.jahed.rtpoet.utils.composition

import ca.jahed.rtpoet.rtmodel.RTElement
import ca.jahed.rtpoet.rtmodel.RTPort

class RTConnection(val port: RTPort, val slot: RTSlot, val wired: Boolean = true) : RTElement() {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as RTConnection

        if (port != other.port) return false
        if (slot != other.slot) return false
        if (wired != other.wired) return false

        return true
    }

    override fun hashCode(): Int {
        var result = port.hashCode()
        result = 31 * result + slot.hashCode()
        result = 31 * result + wired.hashCode()
        return result
    }
}