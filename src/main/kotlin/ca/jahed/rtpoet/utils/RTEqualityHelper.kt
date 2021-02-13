package ca.jahed.rtpoet.utils

import ca.jahed.rtpoet.rtmodel.*
import ca.jahed.rtpoet.rtmodel.cppproperties.RTProperties
import ca.jahed.rtpoet.rtmodel.rts.RTSystemClass
import ca.jahed.rtpoet.rtmodel.rts.RTSystemProtocol
import ca.jahed.rtpoet.rtmodel.sm.*
import ca.jahed.rtpoet.rtmodel.types.RTPrimitiveType
import ca.jahed.rtpoet.rtmodel.types.RTType

class RTEqualityHelper {
    private val cache = mutableMapOf<Pair<RTElement, RTElement>, Boolean>()

    companion object {
        @JvmStatic
        fun isEqual(element1: RTElement, element2: RTElement): Boolean {
            return RTEqualityHelper().check(element1, element2)
        }

        @JvmStatic
        fun diff(element1: RTElement, element2: RTElement): List<Pair<RTElement, RTElement>> {
            return RTEqualityHelper().findDiffs(element1, element2)
        }
    }

    private fun findDiffs(element1: RTElement, element2: RTElement): List<Pair<RTElement, RTElement>> {
        check(element1, element2)
        return cache.filterValues { !it }.keys.toList()
    }

    private fun check(e1: RTElement?, e2: RTElement?): Boolean {
        if (e1 == null && e2 == null)
            return true

        if (e1 == null && e2 != null)
            return false

        if (e1 != null && e2 == null)
            return false

        val a = e1!!
        val b = e2!!

        if (a.javaClass != b.javaClass)
            return false

        return cache.getOrPut(Pair(a, b), {
            when (a) {
                is RTModel -> checkModel(a, b as RTModel)
                is RTCapsule -> checkCapsule(a, b as RTCapsule)
                is RTProtocol -> checkProtocol(a, b as RTProtocol)
                is RTPort -> checkPort(a, b as RTPort)
                is RTCapsulePart -> checkPart(a, b as RTCapsulePart)
                is RTSignal -> checkSignal(a, b as RTSignal)
                is RTEnumeration -> checkEnumeration(a, b as RTEnumeration)
                is RTConnector -> checkConnector(a, b as RTConnector)
                is RTConnectorEnd -> checkConnectorEnd(a, b as RTConnectorEnd)
                is RTParameter -> checkParameter(a, b as RTParameter)
                is RTOperation -> checkOperation(a, b as RTOperation)
                is RTAttribute -> checkAttribute(a, b as RTAttribute)
                is RTArtifact -> checkArtifact(a, b as RTArtifact)
                is RTAction -> checkAction(a, b as RTAction)
                is RTPackage -> checkPackage(a, b as RTPackage)
                is RTClass -> checkClass(a, b as RTClass)
                is RTType -> checkType(a, b as RTType)

                is RTStateMachine -> checkStateMachine(a, b as RTStateMachine)
                is RTCompositeState -> checkCompositeState(a, b as RTCompositeState)
                is RTPseudoState -> checkPseudoState(a, b as RTPseudoState)
                is RTState -> checkState(a, b as RTState)
                is RTTransition -> checkTransition(a, b as RTTransition)
                is RTTrigger -> checkTrigger(a, b as RTTrigger)

                is RTProperties -> a == b

                else -> throw RuntimeException("Unexpected element type ${a.javaClass.simpleName}")
            }
        })
    }

    private fun checkList(l1: List<RTElement>, l2: List<RTElement>): Boolean {
        if (l1.size != l2.size)
            return false

        val b = l2.toMutableList()

        out@ for (i in l1.indices) {
            for (j in b.indices) {
                if (check(l1[i], b[j])) {
                    b.removeAt(j)
                    continue@out
                }
            }
            return false
        }

        return true
    }

    private fun checkOrderedList(a: List<RTElement>, b: List<RTElement>): Boolean {
        if (a.size != b.size)
            return false

        for (i in a.indices)
            if (!check(a[i], b[i]))
                return false
        return true
    }

    private fun checkModel(a: RTModel, b: RTModel): Boolean {
        return check(a.top, b.top)
                && checkList(a.capsules, b.capsules)
                && checkList(a.artifacts, b.artifacts)
                && checkList(a.classes, b.classes)
                && checkList(a.enumerations, b.enumerations)
                && checkList(a.protocols, b.protocols)
                && checkList(a.packages, b.packages)
    }

    private fun checkPackage(a: RTPackage, b: RTPackage): Boolean {
        return checkList(a.capsules, b.capsules)
                && checkList(a.artifacts, b.artifacts)
                && checkList(a.classes, b.classes)
                && checkList(a.enumerations, b.enumerations)
                && checkList(a.protocols, b.protocols)
                && checkList(a.packages, b.packages)
    }

    private fun checkCapsule(a: RTCapsule, b: RTCapsule): Boolean {
        return check(a.stateMachine, b.stateMachine)
                && check(a.superClass, b.superClass)
                && checkList(a.connectors, b.connectors)
                && checkList(a.parts, b.parts)
                && checkList(a.ports, b.ports)
                && checkList(a.attributes, b.attributes)
                && checkList(a.operations, b.operations)
    }

    private fun checkClass(a: RTClass, b: RTClass): Boolean {
        if ((a is RTSystemClass && b is RTSystemClass))
            return a == b

        return a.superClass == b.superClass
                && checkList(a.attributes, b.attributes)
                && checkList(a.operations, b.operations)
    }

    private fun checkProtocol(a: RTProtocol, b: RTProtocol): Boolean {
        if ((a is RTSystemProtocol && b is RTSystemProtocol))
            return a == b

        return checkList(a.inputSignals, b.inputSignals)
                && checkList(a.outputSignals, b.outputSignals)
                && checkList(a.inOutSignals, b.inOutSignals)
    }

    private fun checkEnumeration(a: RTEnumeration, b: RTEnumeration): Boolean {
        return a.name == b.name
                && a.literals.size == b.literals.size
                && a.literals.containsAll(b.literals)
    }

    private fun checkPart(a: RTCapsulePart, b: RTCapsulePart): Boolean {
        return a.name == b.name
                && a.replication == b.replication
                && a.optional == b.optional
                && a.plugin == b.plugin
                && check(a.capsule, b.capsule)
    }

    private fun checkPort(a: RTPort, b: RTPort): Boolean {
        return a.replication == b.replication
                && a.visibility == b.visibility
                && a.registrationOverride == b.registrationOverride
                && a.registrationType == b.registrationType
                && a.publish == b.publish
                && a.notification == b.notification
                && a.conjugated == b.conjugated
                && a.service == b.service
                && a.wired == b.wired
                && a.behaviour == b.behaviour
                && check(a.protocol, b.protocol)
    }

    private fun checkConnector(a: RTConnector, b: RTConnector): Boolean {
        return (check(a.end1, b.end1) && check(a.end2, b.end2))
                || (check(a.end1, b.end2) && check(a.end2, b.end1))
    }

    private fun checkConnectorEnd(a: RTConnectorEnd, b: RTConnectorEnd): Boolean {
        return check(a.part, b.part) && check(a.port, b.port)
    }

    private fun checkSignal(a: RTSignal, b: RTSignal): Boolean {
        return checkOrderedList(a.parameters, b.parameters)
    }

    private fun checkArtifact(a: RTArtifact, b: RTArtifact): Boolean {
        return a.fileName == b.fileName
    }

    private fun checkAttribute(a: RTAttribute, b: RTAttribute): Boolean {
        return a.value == b.value
                && a.replication == b.replication
                && a.visibility == b.visibility
                && check(a.type, b.type)
    }

    private fun checkAction(a: RTAction, b: RTAction): Boolean {
        return a.language == b.language
                && a.body == b.body
    }

    private fun checkOperation(a: RTOperation, b: RTOperation): Boolean {
        return check(a.ret, b.ret)
                && check(a.action, b.action)
                && checkOrderedList(a.parameters, b.parameters)
    }

    private fun checkParameter(a: RTParameter, b: RTParameter): Boolean {
        return a.replication == b.replication
                && a.visibility == b.visibility
                && check(a.type, b.type)
    }

    private fun checkType(a: RTType, b: RTType): Boolean {
        if ((a is RTPrimitiveType && b is RTPrimitiveType))
            return a == b
        return check(a as RTClass, b as RTClass)
    }

    private fun checkStateMachine(a: RTStateMachine, b: RTStateMachine): Boolean {
        return checkList(a.states(), b.states())
                && checkList(a.transitions(), b.transitions())
    }

    private fun checkCompositeState(a: RTCompositeState, b: RTCompositeState): Boolean {
        return check(a.entryAction, b.entryAction)
                && check(a.exitAction, b.exitAction)
                && checkList(a.states(), b.states())
                && checkList(a.transitions(), b.transitions())
    }

    private fun checkPseudoState(a: RTPseudoState, b: RTPseudoState): Boolean {
        return a.kind == b.kind
    }

    private fun checkState(a: RTState, b: RTState): Boolean {
        return check(a.entryAction, b.entryAction)
                && check(a.exitAction, b.exitAction)
    }

    private fun checkTransition(a: RTTransition, b: RTTransition): Boolean {
        return check(a.guard, b.guard)
                && check(a.action, b.action)
                && check(a.source, b.source)
                && check(a.target, b.target)
                && checkList(a.triggers, b.triggers)
    }

    private fun checkTrigger(a: RTTrigger, b: RTTrigger): Boolean {
        return check(a.port, b.port)
                && check(a.signal, b.signal)
    }


}