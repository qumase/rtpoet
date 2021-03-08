package ca.jahed.rtpoet.rtmodel.rts

import ca.jahed.rtpoet.rtmodel.rts.classes.RTSystemClass
import ca.jahed.rtpoet.rtmodel.rts.protocols.RTSystemProtocol
import ca.jahed.rtpoet.rtmodel.types.RTType
import ca.jahed.rtpoet.rtmodel.types.primitivetype.RTPrimitiveType

interface RTLibrary {
    fun getProtocol(protocol: RTSystemProtocol): Any
    fun getSystemSignal(protocol: RTSystemProtocol, signal: RTSystemSignal): Any
    fun getSystemClass(klass: RTSystemClass): Any
    fun getSystemSignal(event: RTSystemSignal): Any
    fun getType(type: RTType): Any
    fun getProfile(name: String): Any

    fun getSystemProtocol(name: String): RTSystemProtocol
    fun getSystemClass(name: String): RTSystemClass
    fun getType(name: String): RTPrimitiveType
    fun getSystemSignal(protocol: String, name: String): RTSystemSignal
    fun getSystemSignal(signal: Any): RTSystemSignal
    fun getSystemProtocol(protocol: Any): RTSystemProtocol
    fun getSystemClass(klass: Any): RTSystemClass

    fun isSystemSignal(event: Any): Boolean
    fun isSystemProtocol(protocol: Any): Boolean
    fun isSystemClass(klass: Any): Boolean

}