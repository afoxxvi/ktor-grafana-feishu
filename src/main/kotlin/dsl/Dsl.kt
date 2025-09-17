package com.afoxxvi.dsl

class MapBuilder<K, V> {
    private val map = mutableMapOf<K, V>()

    infix fun K.to(value: V) {
        map[this] = value
    }

    fun build(): Map<K, V> = map
}