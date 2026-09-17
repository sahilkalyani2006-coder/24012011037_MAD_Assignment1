package com.example.madassignment1

import android.app.Activity
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowInsets

open class BaseActivity : Activity() {
    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)

        if (Build.VERSION.SDK_INT >= 35) {
            val content = findViewById<View>(android.R.id.content)
            content.setOnApplyWindowInsetsListener { view, insets ->
                val space = insets.getInsets(
                    WindowInsets.Type.systemBars() or
                        WindowInsets.Type.displayCutout() or WindowInsets.Type.ime()
                )
                view.setPadding(space.left, space.top, space.right, space.bottom)
                WindowInsets.CONSUMED
            }
            content.requestApplyInsets()
        }
    }
}
