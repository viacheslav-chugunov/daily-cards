package io.dailycards.tools.extensions

import android.app.Activity
import android.widget.Toast

fun Activity.showToast(strRes: Int, duration: Int = Toast.LENGTH_SHORT)
        = Toast.makeText(this, strRes, duration).show()