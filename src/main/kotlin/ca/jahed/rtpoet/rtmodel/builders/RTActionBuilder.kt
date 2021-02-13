package ca.jahed.rtpoet.rtmodel.builders

import ca.jahed.rtpoet.rtmodel.RTAction

interface RTActionBuilder {
    fun language(language: String): RTActionBuilder
    fun build(): RTAction
}