package ca.jahed.rtpoet.rtmodel.builders

import ca.jahed.rtpoet.rtmodel.RTPort

interface RTPortBuilder {
    fun sap(): RTPortBuilder
    fun spp(): RTPortBuilder
    fun relay(): RTPortBuilder
    fun external(): RTPortBuilder
    fun internal(): RTPortBuilder
    fun system(): RTPortBuilder

    fun replication(replication: Int): RTPortBuilder
    fun behaviour(): RTPortBuilder
    fun wired(): RTPortBuilder
    fun service(): RTPortBuilder
    fun conjugate(): RTPortBuilder
    fun notification(): RTPortBuilder
    fun publish(): RTPortBuilder
    fun autoRegistration(): RTPortBuilder
    fun autoLockedRegistration(): RTPortBuilder
    fun appRegistration(): RTPortBuilder
    fun registrationOverride(override: String): RTPortBuilder
    fun publicVisibility(): RTPortBuilder
    fun privateVisibility(): RTPortBuilder
    fun protectedVisibility(): RTPortBuilder

    fun build(): RTPort
}