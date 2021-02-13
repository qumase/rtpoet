package ca.jahed.rtpoet.rtmodel

import NameUtils
import ca.jahed.rtpoet.utils.RTEqualityHelper

abstract class RTElement(open var name: String = NameUtils.randomString(8)) : Comparable<RTElement> {
    override fun compareTo(other: RTElement): Int {
        if (name == other.name)
            return 0
        return javaClass.simpleName.compareTo(other.javaClass.simpleName)
    }

    override fun equals(other: Any?): Boolean {
        if (other !is RTElement)
            return false
        return RTEqualityHelper.isEqual(this, other)
    }

    override fun toString(): String {
        return "$name(${javaClass.simpleName})"
    }
}