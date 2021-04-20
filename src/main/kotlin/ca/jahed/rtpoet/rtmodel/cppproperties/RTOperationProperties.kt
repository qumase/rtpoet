package ca.jahed.rtpoet.rtmodel.cppproperties

import ca.jahed.rtpoet.rtmodel.builders.cppproperties.RTOperationPropertiesBuilder

class RTOperationProperties(
    var kind: OpKind? = null,
    var generateDefinition: Boolean = false,
    var inline: Boolean = false,
    var polymorphic: Boolean = false,
) : RTProperties() {
    enum class OpKind {
        MEMBER, FRIEND, GLOBAL
    }

    private class Builder : RTOperationPropertiesBuilder {
        private var kind: OpKind = OpKind.MEMBER
        private var generateDefinition = true
        private var inline = false
        private var polymorphic = false

        override fun friend() = apply { kind = OpKind.FRIEND }
        override fun global() = apply { kind = OpKind.GLOBAL }
        override fun inline() = apply { inline = true }
        override fun polymorphic() = apply { polymorphic = true }
        override fun dontGenerateDefinition() = apply { generateDefinition = false }

        override fun build(): RTOperationProperties {
            return RTOperationProperties(
                kind,
                generateDefinition,
                inline,
                polymorphic)
        }
    }

    companion object {
        @JvmStatic
        fun builder(): RTOperationPropertiesBuilder {
            return Builder()
        }
    }
}