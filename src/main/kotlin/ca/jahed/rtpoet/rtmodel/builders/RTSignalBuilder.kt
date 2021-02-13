package ca.jahed.rtpoet.rtmodel.builders

import ca.jahed.rtpoet.rtmodel.RTParameter
import ca.jahed.rtpoet.rtmodel.RTSignal

interface RTSignalBuilder {
    fun parameter(param: RTParameter): RTSignalBuilder
    fun parameter(param: RTParameterBuilder): RTSignalBuilder
    fun build(): RTSignal
}