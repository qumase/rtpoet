package ca.jahed.rtpoet.rtmodel.rts

import ca.jahed.rtpoet.rtmodel.RTProtocol

open class RTSystemProtocol(name: String) : RTProtocol(name, RTSystemSignal.any()) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        if (!super.equals(other)) return false
        return true
    }
}