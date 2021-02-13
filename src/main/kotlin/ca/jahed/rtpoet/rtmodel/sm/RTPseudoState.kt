package ca.jahed.rtpoet.rtmodel.sm

import NameUtils
import ca.jahed.rtpoet.exceptions.BuildException
import ca.jahed.rtpoet.rtmodel.builders.sm.RTPseudoStateBuilder

class RTPseudoState(name: String, var kind: Kind) : RTGenericState(name) {

    enum class Kind {
        INITIAL, HISTORY, JOIN, JUNCTION, CHOICE, ENTRY_POINT, EXIT_POINT
    }

    private class Builder(private val name: String?) : RTPseudoStateBuilder {
        private var kind: Kind? = null

        override fun initial() = apply { kind = Kind.INITIAL }
        override fun history() = apply { kind = Kind.HISTORY }
        override fun join() = apply { kind = Kind.JOIN }
        override fun junction() = apply { kind = Kind.JUNCTION }
        override fun choice() = apply { kind = Kind.CHOICE }
        override fun entryPoint() = apply { kind = Kind.ENTRY_POINT }
        override fun exitPoint() = apply { kind = Kind.EXIT_POINT }

        override fun build(): RTPseudoState {
            if (kind == null)
                throw BuildException("PsuedoState needs a kind")
            return RTPseudoState(name ?: NameUtils.randomString(5), kind!!)
        }
    }

    companion object {
        @JvmStatic
        fun initial(name: String?): RTPseudoStateBuilder {
            return Builder(name).initial()
        }

        @JvmStatic
        fun history(name: String?): RTPseudoStateBuilder {
            return Builder(name).history()
        }

        @JvmStatic
        fun join(name: String?): RTPseudoStateBuilder {
            return Builder(name).join()
        }

        @JvmStatic
        fun junction(name: String?): RTPseudoStateBuilder {
            return Builder(name).junction()
        }

        @JvmStatic
        fun choice(name: String?): RTPseudoStateBuilder {
            return Builder(name).choice()
        }

        @JvmStatic
        fun entryPoint(name: String?): RTPseudoStateBuilder {
            return Builder(name).entryPoint()
        }

        @JvmStatic
        fun exitPoint(name: String?): RTPseudoStateBuilder {
            return Builder(name).exitPoint()
        }
    }
}