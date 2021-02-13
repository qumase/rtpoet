package ca.jahed.rtpoet.rtmodel.builders

import ca.jahed.rtpoet.rtmodel.RTCapsule
import ca.jahed.rtpoet.rtmodel.RTConnectorEnd

interface RTConnectorEndBuilder {
    fun build(): RTConnectorEnd
    fun build(capsule: RTCapsule): RTConnectorEnd
}