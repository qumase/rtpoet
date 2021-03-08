package ca.jahed.rtpoet.rtmodel.types.primitivetype

import ca.jahed.rtpoet.rtmodel.types.RTType

abstract class RTPrimitiveType(name: String) : RTType(name) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        if (!super.equals(other)) return false
        return true
    }

    override fun hashCode(): Int {
        return javaClass.hashCode()
    }
}