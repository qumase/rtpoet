package ca.jahed.rtpoet.rtmodel.cppproperties

import ca.jahed.rtpoet.rtmodel.builders.cppproperties.RTAttributePropertiesBuilder

class RTAttributeProperties(
    var initialization: InitKind? = null,
    var kind: Kind? = null,
    var size: String? = null,
    var type: String? = null,
    var pointsToConstType: Boolean = false,
    var pointsToVolatileType: Boolean = false,
    var pointsToType: Boolean = false,
    var isVolatile: Boolean = false,
) : RTProperties() {
    enum class Kind {
        MEMBER, GLOBAL, MUTABLE_MEMBER, DEFINE
    }

    enum class InitKind {
        ASSIGNMENT, CONSTANT, CONSTRUCTOR
    }

    private class Builder : RTAttributePropertiesBuilder {
        private var initialization: InitKind? = null
        private var kind: Kind? = null
        private var size: String? = null
        private var type: String? = null
        private var pointsToConstType = false
        private var pointsToVolatileType = false
        private var pointsToType = false
        private var isVolatile = false

        override fun initialization(initialization: InitKind) = apply { this.initialization = initialization }
        override fun kind(kind: Kind) = apply { this.kind = kind }
        override fun size(size: String) = apply { this.size = size }
        override fun type(type: String) = apply { this.type = type }
        override fun pointsToConstType() = apply { this.pointsToConstType = true }
        override fun pointsToVolatileType() = apply { this.pointsToVolatileType = true }
        override fun pointsToType() = apply { this.pointsToType = true }
        override fun isVolatile() = apply { this.isVolatile = true }

        override fun build(): RTAttributeProperties {
            return RTAttributeProperties(
                initialization,
                kind,
                size,
                type,
                pointsToConstType,
                pointsToVolatileType,
                pointsToType,
                isVolatile)
        }
    }

    companion object {
        @JvmStatic
        fun builder(): RTAttributePropertiesBuilder {
            return Builder()
        }
    }
}