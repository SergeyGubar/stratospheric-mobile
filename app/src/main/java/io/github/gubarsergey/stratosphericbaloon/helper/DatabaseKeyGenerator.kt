package io.github.gubarsergey.stratosphericbaloon.helper

const val DB_KEY_LENGTH_IN_BYTES = 64


object DatabaseKeyGenerator {
    fun generateKey(): ByteArray {
        val result = ByteArray(DB_KEY_LENGTH_IN_BYTES)
        (0 until DB_KEY_LENGTH_IN_BYTES).forEach {
            result[it] = (Byte.MIN_VALUE..Byte.MAX_VALUE).random().toByte()
        }
        return result
    }
}