package ca.jahed.rtpoet.utils

import ca.jahed.rtpoet.rtmodel.RTElement
import ca.jahed.rtpoet.rtmodel.RTModel
import ca.jahed.rtpoet.rtmodel.visitors.RTDepthVisitor

class RTQualifiedNameHelper(model: RTModel) : RTDepthVisitor() {
    private val qualifiedNames = mutableMapOf<RTElement, String>()
    private val currentPrefix = mutableListOf<String>()

    init {
        visit(model)
        model.imports.forEach { visit(it) }
    }

    operator fun get(element: RTElement): String {
        return qualifiedNames[element]!!
    }

    fun get(): MutableMap<RTElement, String> {
        return qualifiedNames
    }

    override fun visit(element: RTElement) {
        currentPrefix.add(element.name)
        qualifiedNames[element] = currentPrefix.joinToString(".")
        super.visit(element)
        currentPrefix.removeLast()
    }
}