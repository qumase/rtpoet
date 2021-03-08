package ca.jahed.rtpoet.rtmodel.rts.classes

import ca.jahed.rtpoet.rtmodel.RTClass

open class RTSystemClass(name: String) : RTClass(name) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        if (!super.equals(other)) return false
        return true
    }
}