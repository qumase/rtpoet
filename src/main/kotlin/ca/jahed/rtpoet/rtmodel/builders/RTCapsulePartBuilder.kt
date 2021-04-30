package ca.jahed.rtpoet.rtmodel.builders

import ca.jahed.rtpoet.rtmodel.RTCapsulePart

interface RTCapsulePartBuilder {
    fun replication(replication: Int): RTCapsulePartBuilder
    fun fixed(): RTCapsulePartBuilder
    fun optional(): RTCapsulePartBuilder
    fun plugin(): RTCapsulePartBuilder
    fun build(): RTCapsulePart
}
