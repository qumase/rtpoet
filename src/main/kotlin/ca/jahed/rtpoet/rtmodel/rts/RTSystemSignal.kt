package ca.jahed.rtpoet.rtmodel.rts

import ca.jahed.rtpoet.rtmodel.RTSignal

open class RTSystemSignal(name: String, isAny: Boolean = false) : RTSignal(name, isAny) {
    companion object {
        fun any(): RTSystemSignal {
            return RTSystemSignal("*", true)
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        if (!super.equals(other)) return false
        return true
    }
}