package ca.jahed.rtpoet.rtmodel.builders

import ca.jahed.rtpoet.rtmodel.RTProtocol
import ca.jahed.rtpoet.rtmodel.RTSignal

interface RTProtocolBuilder {
    fun input(signal: RTSignal): RTProtocolBuilder
    fun input(signal: RTSignalBuilder): RTProtocolBuilder
    fun output(signal: RTSignal): RTProtocolBuilder
    fun output(signal: RTSignalBuilder): RTProtocolBuilder
    fun inOut(signal: RTSignal): RTProtocolBuilder
    fun inOut(signal: RTSignalBuilder): RTProtocolBuilder
    fun build(): RTProtocol
}