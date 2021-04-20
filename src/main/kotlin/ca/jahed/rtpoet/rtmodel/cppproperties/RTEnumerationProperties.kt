package ca.jahed.rtpoet.rtmodel.cppproperties

import ca.jahed.rtpoet.rtmodel.builders.cppproperties.RTEnumerationPropertiesBuilder

class RTEnumerationProperties(
    var headerPreface: String? = null,
    var headerEnding: String? = null,
    var implementationPreface: String? = null,
    var implementationEnding: String? = null,
    var generate: Boolean = false,
) : RTProperties() {

    private class Builder : RTEnumerationPropertiesBuilder {
        private var headerPreface: String? = null
        private var headerEnding: String? = null
        private var implementationPreface: String? = null
        private var implementationEnding: String? = null
        private var generate = true

        override fun headerPreface(headerPreface: String) = apply { this.headerPreface = headerPreface }
        override fun headerEnding(headerEnding: String) = apply { this.headerEnding = headerEnding }
        override fun implementationPreface(implementationPreface: String) =
            apply { this.implementationPreface = implementationPreface }

        override fun implementationEnding(implementationEnding: String) =
            apply { this.implementationEnding = implementationEnding }

        override fun dontGenerate() = apply { this.generate = false }

        override fun build(): RTEnumerationProperties {
            return RTEnumerationProperties(
                headerPreface,
                headerEnding,
                implementationPreface,
                implementationEnding,
                generate)
        }
    }

    companion object {
        @JvmStatic
        fun builder(): RTEnumerationPropertiesBuilder {
            return Builder()
        }
    }
}