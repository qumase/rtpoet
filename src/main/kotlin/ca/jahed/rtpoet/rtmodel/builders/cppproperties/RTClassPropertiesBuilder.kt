package ca.jahed.rtpoet.rtmodel.builders.cppproperties

import ca.jahed.rtpoet.rtmodel.cppproperties.RTClassProperties

interface RTClassPropertiesBuilder {
    fun struct(): RTClassPropertiesBuilder
    fun union(): RTClassPropertiesBuilder
    fun typedef(): RTClassPropertiesBuilder

    fun headerPreface(headerPreface: String): RTClassPropertiesBuilder
    fun headerEnding(headerEnding: String): RTClassPropertiesBuilder
    fun implementationPreface(implementationPreface: String): RTClassPropertiesBuilder
    fun implementationEnding(implementationEnding: String): RTClassPropertiesBuilder
    fun publicDeclarations(publicDeclarations: String): RTClassPropertiesBuilder
    fun privateDeclarations(privateDeclarations: String): RTClassPropertiesBuilder
    fun protectedDeclarations(protectedDeclarations: String): RTClassPropertiesBuilder
    fun implementationType(implementationType: String): RTClassPropertiesBuilder


    fun dontGenerate(): RTClassPropertiesBuilder
    fun dontGenerateHeader(): RTClassPropertiesBuilder
    fun dontGenerateImplementation(): RTClassPropertiesBuilder
    fun dontGenerateStateMachine(): RTClassPropertiesBuilder
    fun dontGenerateAssignmentOperator(): RTClassPropertiesBuilder
    fun dontGenerateEqualityOperator(): RTClassPropertiesBuilder
    fun dontGenerateInequalityOperator(): RTClassPropertiesBuilder
    fun dontGenerateInsertionOperator(): RTClassPropertiesBuilder
    fun dontGenerateExtractionOperator(): RTClassPropertiesBuilder
    fun dontGenerateCopyConstructor(): RTClassPropertiesBuilder
    fun dontGenerateDefaultConstructor(): RTClassPropertiesBuilder
    fun dontGenerateDestructor(): RTClassPropertiesBuilder

    fun build(): RTClassProperties
}