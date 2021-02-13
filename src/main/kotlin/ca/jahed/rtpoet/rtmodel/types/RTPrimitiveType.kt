package ca.jahed.rtpoet.rtmodel.types

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