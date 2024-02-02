package datastructures.simplehashmap

import java.util.*

class SimpleHashMap<K, V> {

    private var store: Array<Bucket<K, V>> = Array(DEFAULT_CAPACITY) { Bucket() }
    var size: Int = 0
        private set

    val capacity get() = store.size

    fun get(key: K): V? {
        val bucket = store[findBucketIndex(key)]
        return bucket.get(key)
    }

    fun put(key: K, value: V?) {
        if (value != null && (size + 1f) / store.size >= RESIZE_THRESHOLD_FACTOR) {
            resize()
        }

        if (value == null) {
            val wasRemoved = removeInternal(key)
            if (wasRemoved) {
                size--
            }
        } else {
            putInternal(key, value)
            size++
        }
    }

    private fun putInternal(key: K, value: V) {
        val bucket = store[findBucketIndex(key)]
        bucket.addOrReplace(key, value)
    }

    private fun removeInternal(key: K): Boolean {
        val bucket = store[findBucketIndex(key)]
        return bucket.remove(key)
    }

    private fun resize() {
        val old = store
        store = Array(old.size * RESIZE_MULTIPLIER) { Bucket() }

        old.forEach { bucket ->
            bucket.items().forEach { node ->
                putInternal(node.key, node.value)
            }
        }
    }

    private fun findBucketIndex(key: K): Int {
        return key.hashCode() and (store.size - 1)
    }

    private class Bucket<K, V>(
        private val nodes: LinkedList<Node<K, V>> = LinkedList()
    ) {

        fun get(key: K): V? {
            return nodes.firstOrNull { it.key == key }?.value
        }

        fun addOrReplace(key: K, value: V) {
            val node = nodes.firstOrNull { it.key == key }
            if (node != null) {
                node.value = value
            } else {
                nodes.add(Node(key, value))
            }
        }

        fun remove(key: K): Boolean {
            return nodes.removeIf { it.key == key }
        }

        fun items(): List<Node<K, V>> {
            return nodes
        }
    }

    private class Node<K, V>(
        val key: K,
        var value: V,
    )

    companion object {
        private const val DEFAULT_CAPACITY = 16
        private const val RESIZE_THRESHOLD_FACTOR = 0.75
        private const val RESIZE_MULTIPLIER = 2
    }
}