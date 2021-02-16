package ca.jahed.rtpoet.rtmodel.builders.sm

import ca.jahed.rtpoet.rtmodel.RTPort
import ca.jahed.rtpoet.rtmodel.sm.RTTrigger

interface RTTriggerBuilder {
    fun port(port: RTPort): RTTriggerBuilder
    fun build(): RTTrigger
}