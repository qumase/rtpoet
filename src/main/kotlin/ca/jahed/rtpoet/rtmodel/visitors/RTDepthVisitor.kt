package ca.jahed.rtpoet.rtmodel.visitors

import ca.jahed.rtpoet.rtmodel.*
import ca.jahed.rtpoet.rtmodel.sm.RTCompositeState
import ca.jahed.rtpoet.rtmodel.sm.RTState
import ca.jahed.rtpoet.rtmodel.sm.RTStateMachine
import ca.jahed.rtpoet.rtmodel.sm.RTTransition

open class RTDepthVisitor : RTVisitor() {
    override fun visitModel(model: RTModel): Any {
        visitPackage(model)
        if (model.top != null) visit(model.top!!)
        return super.visitModel(model)
    }

    override fun visitPackage(pkg: RTPackage): Any {
        pkg.capsules.forEach { visit(it) }
        pkg.classes.forEach { visit(it) }
        pkg.artifacts.forEach { visit(it) }
        pkg.enumerations.forEach { visit(it) }
        pkg.protocols.forEach { visit(it) }
        pkg.packages.forEach { visit(it) }
        return super.visitPackage(pkg)
    }

    override fun visitCapsule(capsule: RTCapsule): Any {
        capsule.attributes.forEach { visit(it) }
        capsule.operations.forEach { visit(it) }

        capsule.parts.forEach { visit(it) }
        capsule.ports.forEach { visit(it) }
        capsule.connectors.forEach { visit(it) }
        if (capsule.stateMachine != null) visit(capsule.stateMachine!!)
        if (capsule.properties != null) visit(capsule.properties!!)
        return super.visitCapsule(capsule)
    }

    override fun visitClass(klass: RTClass): Any {
        klass.attributes.forEach { visit(it) }
        klass.operations.forEach { visit(it) }
        if (klass.properties != null) visit(klass.properties!!)
        return super.visitClass(klass)
    }

    override fun visitProtocol(protocol: RTProtocol): Any {
        protocol.inputSignals.forEach { visit(it) }
        protocol.outputSignals.forEach { visit(it) }
        protocol.inOutSignals.forEach { visit(it) }
        return super.visitProtocol(protocol)
    }

    override fun visitSignal(signal: RTSignal): Any {
        signal.parameters.forEach { visit(it) }
        return super.visitSignal(signal)
    }

    override fun visitOperation(operation: RTOperation): Any {
        operation.parameters.forEach { visit(it) }
        if (operation.ret != null) visit(operation.ret!!)
        if (operation.action != null) visit(operation.action!!)
        if (operation.properties != null) visit(operation.properties!!)
        return super.visitOperation(operation)
    }

    override fun visitStateMachine(statemachine: RTStateMachine): Any {
        statemachine.states().forEach { visit(it) }
        statemachine.transitions().forEach { visit(it) }
        return super.visitStateMachine(statemachine)
    }

    override fun visitCompositeState(state: RTCompositeState): Any {
        if (state.entryAction != null) visit(state.entryAction!!)
        if (state.exitAction != null) visit(state.exitAction!!)
        state.states().forEach { visit(it) }
        state.transitions().forEach { visit(it) }
        return super.visitCompositeState(state)
    }


    override fun visitState(state: RTState): Any {
        if (state.entryAction != null) visit(state.entryAction!!)
        if (state.exitAction != null) visit(state.exitAction!!)
        return super.visitState(state)
    }

    override fun visitTransition(transition: RTTransition): Any {
        if (transition.guard != null) visit(transition.guard!!)
        if (transition.action != null) visit(transition.action!!)
        transition.triggers.forEach { visit(it) }
        return super.visitTransition(transition)
    }

    override fun visitEnumeration(enumeration: RTEnumeration): Any {
        if (enumeration.properties != null) visit(enumeration.properties!!)
        return super.visitEnumeration(enumeration)
    }

    override fun visitArtifact(artifact: RTArtifact): Any {
        if (artifact.properties != null) visit(artifact.properties!!)
        return super.visitArtifact(artifact)
    }

    override fun visitAttribute(attribute: RTAttribute): Any {
        if (attribute.properties != null) visit(attribute.properties!!)
        return super.visitAttribute(attribute)
    }

    override fun visitParameter(param: RTParameter): Any {
        if (param.properties != null) visit(param.properties!!)
        return super.visitParameter(param)
    }
}