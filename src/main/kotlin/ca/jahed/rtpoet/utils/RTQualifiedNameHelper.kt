package ca.jahed.rtpoet.utils

import ca.jahed.rtpoet.rtmodel.RTElement
import ca.jahed.rtpoet.rtmodel.RTModel
import ca.jahed.rtpoet.rtmodel.visitors.RTDepthVisitor

class RTQualifiedNameHelper(model: RTModel) : RTDepthVisitor() {
    private val qualifiedNames = mutableMapOf<RTElement, String>()
    private val currentPrefix = mutableListOf<String>()

    init {
        visit(model)
    }

    fun getQualifiedName(element: RTElement): String {
        return qualifiedNames[element]!!
    }

    fun getQualifiedNames(): MutableMap<RTElement, String> {
        return qualifiedNames
    }

    override fun visit(element: RTElement) {
        currentPrefix.add(element.name)
        qualifiedNames[element] = currentPrefix.joinToString(".")
        super.visit(element)
        currentPrefix.removeLast()
    }
}