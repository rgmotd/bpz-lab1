package com.example.bpz_lab1

import kotlin.random.Random

object Cipher {

    fun encrypt(word: String, key: Char): String {
        if (word.isEmpty())
            throw IllegalArgumentException("word must be a non-empty string")
        if (!word.all { it in 'a'..'z' } && key !in 'a'..'z')
            throw IllegalArgumentException("word and key must contain only lowercase Latin letters")

        val encryptedWord = StringBuilder(word.length)
        for (i in word.indices) {
            val sum = word[i].code + key.code
            val char = (if (sum <= 219) sum - 97 else sum - 123).toChar()
            encryptedWord.append(char)
        }

        return encryptedWord.toString()
    }

    fun decrypt(encryptedWord: String, key: Char): String {
        if (encryptedWord.isEmpty())
            throw IllegalArgumentException("encryptedWord and keyword must be a non-empty string")
        if (!encryptedWord.all { it in 'a'..'z' } || key !in 'a'..'z' )
            throw IllegalArgumentException("encryptedWord and key must contain only lowercase Latin letters")

        val decryptedWord = StringBuilder(encryptedWord.length)
        for (i in encryptedWord.indices) {
            val sum = encryptedWord[i].code - key.code
            val char = (if (sum < 0) sum + 123 else sum + 97).toChar()
            decryptedWord.append(char)
        }

        Random.nextLong().toString().map { it.digitToInt().toChar() }

        return decryptedWord.toString()
    }
}