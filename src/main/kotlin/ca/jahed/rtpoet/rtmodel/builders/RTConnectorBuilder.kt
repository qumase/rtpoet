package ca.jahed.rtpoet.rtmodel.builders

import ca.jahed.rtpoet.rtmodel.RTCapsule
import ca.jahed.rtpoet.rtmodel.RTConnector
import ca.jahed.rtpoet.rtmodel.RTConnectorEnd

interface RTConnectorBuilder {
    fun end1(end: RTConnectorEnd): RTConnectorBuilder
    fun end1(end: RTConnectorEndBuilder): RTConnectorBuilder
    fun end2(end: RTConnectorEnd): RTConnectorBuilder
    fun end2(end: RTConnectorEndBuilder): RTConnectorBuilder

    fun build(): RTConnector
    fun build(capsule: RTCapsule): RTConnector
}