package com.example.bpz_lab1

interface Observable {

    fun addObserver(observer: Observer)

    fun removeObserver(observer: Observer)

    fun notifyObservers()

    class Base : Observable {
        private val observers = mutableListOf<Observer>()

        override fun addObserver(observer: Observer) {
            observers.add(observer)
        }

        override fun removeObserver(observer: Observer) {
            observers.remove(observer)
        }

        override fun notifyObservers() {
            observers.forEach {
                it.update()
            }
        }

    }
}