package ca.jahed.rtpoet.utils.composition

import ca.jahed.rtpoet.rtmodel.RTModel
import ca.jahed.rtpoet.rtmodel.RTPort

class RTModelFlattener private constructor(private val annotator: RTModelAnnotator? = null) {
    companion object {
        @JvmStatic
        fun flatten(model: RTModel): List<RTSlot> {
            return RTModelFlattener().doFlatten(model)
        }

        @JvmStatic
        fun flatten(model: RTModel, annotator: RTModelAnnotator): List<RTSlot> {
            return RTModelFlattener(annotator).doFlatten(model)
        }
    }

    private fun doFlatten(model: RTModel): List<RTSlot> {
        if (model.top == null) return listOf()
        val topSlot = RTSlot(model.top!!, 0)
        val slots: List<RTSlot> = flattenHierarchy(topSlot)
        findNeighbors(slots)
        return slots
    }

    private fun flattenHierarchy(slot: RTSlot): List<RTSlot> {
        val slots = mutableListOf(slot)
        for (childPart in slot.part.capsule.parts) {
            for (i in 0 until childPart.replication) {
                val childSlot = RTSlot(childPart, i, slot)
                slot.children.add(childSlot)
                slots.addAll(flattenHierarchy(childSlot))
            }
        }

        for (i in 0 until slot.children.size) {
            for (j in i + 1 until slot.children.size) {
                slot.children[i].siblings.add(slot.children[j])
                slot.children[j].siblings.add(slot.children[i])
            }
        }

        if (annotator != null) slot.annotations = annotator.annotate(slot)
        return slots
    }

    private fun findNeighbors(slots: List<RTSlot>) {
        findWiredNeighbors(slots)
        findNoneWiredNeighbors(slots)

        for (slot in slots) {
            for (connections in slot.connections.values) {
                for (connection in connections) {
                    slot.neighbors.add(connection.slot)
                }
            }
        }
    }

    private fun findWiredNeighbors(slots: List<RTSlot>) {
        for (slot in slots) {
            for (connector in slot.part.capsule.connectors) {

                if (connector.end1.part == null && connector.end2.part == null) {
                    slot.addConnection(connector.end1.port, connector.end2.port, slot)
                } else if (connector.end1.part == null) {
                    slot.children.filter { s -> s.part == connector.end2.part }.forEach {
                        slot.addConnection(connector.end1.port, connector.end2.port, it)
                        it.addConnection(connector.end2.port, connector.end1.port, slot)
                    }
                } else if (connector.end2.part == null) {
                    slot.children.filter { s -> s.part == connector.end1.part }.forEach {
                        slot.addConnection(connector.end2.port, connector.end1.port, it)
                        it.addConnection(connector.end1.port, connector.end2.port, slot)
                    }
                } else {
                    slot.children
                        .filter { s1 -> s1.part == connector.end1.part }
                        .forEach { s1 ->
                            slot.children
                                .filter { s2 -> s2.part == connector.end2.part }
                                .forEach { s2 ->
                                    s1.addConnection(connector.end1.port, connector.end2.port, s2)
                                    s2.addConnection(connector.end2.port, connector.end1.port, s1)
                                }
                        }
                }
            }
        }

        resolveRelays(slots)
    }

    private fun resolveRelays(slots: List<RTSlot>) {
        for (slot in slots) {
            for (port in slot.connections.keys) {
                if (port.isRelay()) continue

                val resolvedConnections = mutableSetOf<RTConnection>()
                for (farEnd in slot.connections[port]!!) {
                    val visited = mutableSetOf(RTConnection(port, slot))
                    resolvedConnections.add(resolveFarEnd(farEnd, visited))
                }

                slot.connections[port] = resolvedConnections
            }
        }
        for (slot in slots) {
            slot.connections.keys.filter(RTPort::isRelay)
                .map { port -> slot.connections.remove(port) }
        }
    }

    private fun resolveFarEnd(
        connection: RTConnection,
        visited: MutableSet<RTConnection>,
    ): RTConnection {
        visited.add(connection)
        val port: RTPort = connection.port
        val slot: RTSlot = connection.slot
        if (!port.isRelay()) return connection

        val entries: Set<RTConnection> = slot.connections[port]!!
            .filter { pair -> !visited.contains(pair) }.toSet()
        assert(entries.size == 1)

        return resolveFarEnd(entries.iterator().next(), visited)
    }

    private fun findNoneWiredNeighbors(slots: List<RTSlot>) {
        for (i in slots.indices) {
            for (p1 in slots[i].part.capsule.ports) {
                if (p1.wired || !p1.behaviour) continue

                for (j in i + 1 until slots.size) {
                    for (p2 in slots[j].part.capsule.ports) {
                        if (p2.wired || !p2.behaviour) continue

                        if (p1.service != p2.service
                            && p1.conjugated != p2.conjugated
                            && p1.protocol == p2.protocol
                            && (p1.name == p2.name || p1.registrationOverride == p2.registrationOverride)
                        ) {
                            slots[i].addConnection(p1, p2, slots[j], false)
                            slots[j].addConnection(p2, p1, slots[i], false)
                        }
                    }
                }
            }
        }
    }
}