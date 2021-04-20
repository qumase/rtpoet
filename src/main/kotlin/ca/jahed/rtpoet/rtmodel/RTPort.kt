package ca.jahed.rtpoet.rtmodel

import ca.jahed.rtpoet.rtmodel.builders.RTPortBuilder

open class RTPort(name: String, var protocol: RTProtocol) : RTAttribute(name, protocol) {
    var behaviour = false
    var wired = false
    var service = false
    var conjugated = false
    var notification = false
    var publish = false
    var registrationType = RegistrationType.AUTOMATIC
    var registrationOverride = String()

    enum class RegistrationType {
        AUTOMATIC, APPLICATION, AUTOMATIC_LOCKED
    }

    fun isExternal(): Boolean {
        return wired && service && behaviour && visibility == VisibilityKind.PUBLIC
    }

    fun isInternal(): Boolean {
        return wired && service && behaviour && visibility != VisibilityKind.PUBLIC
    }

    fun isSAP(): Boolean {
        return !wired && !service && !publish && behaviour && visibility != VisibilityKind.PUBLIC
    }

    fun isSPP(): Boolean {
        return !wired && !service && behaviour && publish && visibility == VisibilityKind.PUBLIC
    }

    fun isRelay(): Boolean {
        return !behaviour && !publish && service && wired && visibility == VisibilityKind.PUBLIC
    }

    fun inputs(): List<RTSignal> {
        val list = mutableListOf<RTSignal>()
        list.addAll(if (conjugated) protocol.outputs() else protocol.inputs())
        return list
    }

    fun outputs(): List<RTSignal> {
        val list = mutableListOf<RTSignal>()
        list.addAll(if (conjugated) protocol.inputs() else protocol.outputs())
        return list
    }

    private class Builder(private val name: String, private val protocol: RTProtocol) : RTPortBuilder {
        private var replication = 1
        private var behaviour = false
        private var wired = false
        private var service = false
        private var conjugated = false
        private var notification = false
        private var publish = false
        private var registrationType = RegistrationType.AUTOMATIC
        private var registrationOverride = String()
        private var visibility = VisibilityKind.PROTECTED

        override fun external() = apply { behaviour().wired().service().publicVisibility() }
        override fun internal() = apply { behaviour().wired().protectedVisibility() }
        override fun sap() = apply { behaviour().autoRegistration().protectedVisibility() }
        override fun spp() = apply { behaviour().publish().service().autoRegistration().publicVisibility() }
        override fun relay() = apply { service().wired().publicVisibility() }

        override fun replication(replication: Int) = apply { this.replication = replication }
        override fun behaviour() = apply { this.behaviour = true }
        override fun wired() = apply { this.wired = true }
        override fun service() = apply { this.service = true }
        override fun conjugate() = apply { this.conjugated = true }
        override fun notification() = apply { this.notification = true }
        override fun publish() = apply { this.publish = true }
        override fun autoRegistration() = apply { this.registrationType = RegistrationType.AUTOMATIC }
        override fun autoLockedRegistration() = apply { this.registrationType = RegistrationType.AUTOMATIC_LOCKED }
        override fun appRegistration() = apply { this.registrationType = RegistrationType.APPLICATION }
        override fun registrationOverride(override: String) = apply { this.registrationOverride = override }
        override fun publicVisibility() = apply { visibility = VisibilityKind.PUBLIC }
        override fun privateVisibility() = apply { visibility = VisibilityKind.PRIVATE }
        override fun protectedVisibility() = apply { visibility = VisibilityKind.PROTECTED }

        override fun build(): RTPort {
            val port = RTPort(this.name, this.protocol)
            port.replication = replication
            port.behaviour = behaviour
            port.wired = wired
            port.service = service
            port.conjugated = conjugated
            port.notification = notification
            port.publish = publish
            port.registrationType = registrationType
            port.registrationOverride = registrationOverride
            port.visibility = visibility
            return port
        }
    }

    companion object {
        @JvmStatic
        fun builder(name: String, protocol: RTProtocol): RTPortBuilder {
            return Builder(name, protocol)
        }
    }
}