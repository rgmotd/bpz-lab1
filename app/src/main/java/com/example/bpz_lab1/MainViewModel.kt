package com.example.bpz_lab1

import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {

    fun activate(userKey: String): Boolean =
        keys.find { Cipher.decrypt(it, 'o') == userKey } != null

    /**
     * replace real data with fake one
     */
    private val keys = listOf(
        "slavaukraini", "putinxuilo", "zelenskykrasava"
    ).map { Cipher.encrypt(it, 'o') }
}

