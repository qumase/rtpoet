package ca.jahed.rtpoet.rtmodel.rts.protocols

import ca.jahed.rtpoet.rtmodel.RTProtocol
import ca.jahed.rtpoet.rtmodel.rts.RTSystemSignal

open class RTSystemProtocol(name: String) : RTProtocol(name, RTSystemSignal.any())