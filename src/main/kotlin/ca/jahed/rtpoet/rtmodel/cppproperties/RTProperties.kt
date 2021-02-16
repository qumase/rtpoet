package ca.jahed.rtpoet.rtmodel.cppproperties

import ca.jahed.rtpoet.rtmodel.RTElement

abstract class RTProperties : RTElement() {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        return true
    }

    override fun hashCode(): Int {
        return javaClass.hashCode()
    }
}