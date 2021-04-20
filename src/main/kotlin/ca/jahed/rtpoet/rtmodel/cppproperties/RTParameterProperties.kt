package ca.jahed.rtpoet.rtmodel.cppproperties

import ca.jahed.rtpoet.rtmodel.builders.cppproperties.RTParameterPropertiesBuilder

class RTParameterProperties(
    var type: String? = null,
    var pointsToConst: Boolean = false,
    var pointsToVolatile: Boolean = false,
    var pointsToType: Boolean = false,
) : RTProperties() {

    private class Builder : RTParameterPropertiesBuilder {
        private var type: String? = null
        private var pointsToConst = false
        private var pointsToVolatile = false
        private var pointsToType = false

        override fun type(type: String) = apply { this.type = type }
        override fun pointsToConst() = apply { this.pointsToConst = true }
        override fun pointsToVolatile() = apply { this.pointsToVolatile = true }
        override fun pointsToType() = apply { this.pointsToType = true }

        override fun build(): RTParameterProperties {
            return RTParameterProperties(
                type,
                pointsToConst,
                pointsToVolatile,
                pointsToType)
        }
    }

    companion object {
        @JvmStatic
        fun builder(): RTParameterPropertiesBuilder {
            return Builder()
        }
    }
}