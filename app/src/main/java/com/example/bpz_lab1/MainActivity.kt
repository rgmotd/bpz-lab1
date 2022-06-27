package com.example.bpz_lab1

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem.SHOW_AS_ACTION_ALWAYS
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.google.android.material.appbar.MaterialToolbar
import java.util.*

class MainActivity : AppCompatActivity() {

    val observable = Observable.Base()
    val timer = Timer()

    private val fragmentListener = object : FragmentManager.FragmentLifecycleCallbacks() {
        override fun onFragmentViewCreated(
            fragmentManager: FragmentManager,
            fragment: Fragment,
            view: View,
            savedInstanceState: Bundle?
        ) {
            super.onFragmentViewCreated(fragmentManager, fragment, view, savedInstanceState)
            updateToolbar()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            setSupportActionBar(findViewById(R.id.toolbar))
            supportFragmentManager.beginTransaction().add(R.id.container, ChoosePhotoFragment())
                .commit()
        }

        if (!(application as App).sharedPrefs.getIsActivated()) {
            timer.scheduleAtFixedRate(object : TimerTask() {
                override fun run() = runOnUiThread {
                    observable.notifyObservers()
                }
            }, TEST_TIME, TEST_TIME)
        }

        supportFragmentManager.registerFragmentLifecycleCallbacks(fragmentListener, false)
    }

    private fun setCustomToolbar(action: CustomAction, toolbar: MaterialToolbar) {
        toolbar.menu.add("").apply {
            setShowAsAction(SHOW_AS_ACTION_ALWAYS)
            icon =
                DrawableCompat.wrap(ContextCompat.getDrawable(this@MainActivity, action.iconRes)!!)
            setOnMenuItemClickListener {
                action.onCustomAction()
                true
            }
        }
    }

    private fun updateToolbar() {
        val currentFragment = supportFragmentManager.findFragmentById(R.id.container)
        val toolbar = findViewById<MaterialToolbar>(R.id.toolbar)

        if (currentFragment is HasCustomAction) {
            setCustomToolbar(currentFragment.getCustomAction(), toolbar)
        } else {
            toolbar.menu.clear()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean = true

    override fun onDestroy() {
        super.onDestroy()
        supportFragmentManager.unregisterFragmentLifecycleCallbacks(fragmentListener)
    }

    companion object {
        private const val TIME = 300000L
        private const val TEST_TIME = 10000L
    }
}