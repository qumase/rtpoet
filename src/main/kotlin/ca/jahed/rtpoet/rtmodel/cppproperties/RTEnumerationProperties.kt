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

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        if (!super.equals(other)) return false

        other as RTEnumerationProperties

        if (headerPreface != other.headerPreface) return false
        if (headerEnding != other.headerEnding) return false
        if (implementationPreface != other.implementationPreface) return false
        if (implementationEnding != other.implementationEnding) return false
        if (generate != other.generate) return false

        return true
    }

    override fun hashCode(): Int {
        var result = headerPreface?.hashCode() ?: 0
        result = 31 * result + (headerEnding?.hashCode() ?: 0)
        result = 31 * result + (implementationPreface?.hashCode() ?: 0)
        result = 31 * result + (implementationEnding?.hashCode() ?: 0)
        result = 31 * result + generate.hashCode()
        return result
    }


}