package io.dailycards.tools

import java.text.SimpleDateFormat
import java.util.*

fun getCurrentDate(): String = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
    .format(Calendar.getInstance().time)