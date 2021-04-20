package ca.jahed.rtpoet.rtmodel.visitors

import ca.jahed.rtpoet.rtmodel.RTElement

interface RTVisitorListener {
    fun onVisit(element: RTElement, result: Any)
}