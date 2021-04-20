package ca.jahed.rtpoet.rtmodel

import NameUtils
import java.io.Serializable

abstract class RTElement(open var name: String = NameUtils.randomString(8)) : Comparable<RTElement>, Serializable {
    override fun compareTo(other: RTElement): Int {
        if (name == other.name)
            return 0
        return javaClass.simpleName.compareTo(other.javaClass.simpleName)
    }

    override fun toString(): String {
        return "$name(${javaClass.simpleName})"
    }
}