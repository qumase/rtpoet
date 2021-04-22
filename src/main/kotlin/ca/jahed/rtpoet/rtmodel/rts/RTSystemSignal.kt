package ca.jahed.rtpoet.rtmodel.rts

import ca.jahed.rtpoet.rtmodel.RTSignal

open class RTSystemSignal(name: String, isAny: Boolean = false) : RTSignal(name, isAny) {
    private object RTBound : RTSystemSignal("rtBound")
    private object RTUnbound : RTSystemSignal("rtUnbound")

    companion object {
        fun any(): RTSystemSignal {
            return RTSystemSignal("*", true)
        }

        fun rtBound(): RTSystemSignal {
            return RTBound
        }

        fun rtUnbound(): RTSystemSignal {
            return RTUnbound
        }
    }
}