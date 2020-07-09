package io.dailycards.tools

import android.content.Context
import android.database.Cursor
import java.io.BufferedReader
import java.io.InputStreamReader

enum class EncodingMode {
    QUESTION,
    ANSWER,
}

fun getEncodingFilepath(title: String, mode: EncodingMode) = when(mode) {
    EncodingMode.QUESTION -> "daily_cards_q_$title"
    EncodingMode.ANSWER -> "daily_cards_a_$title"
}

fun readFile(context: Context?, cursor: Cursor, cursorColumnIndex: String) : List<String> {
    val fileContent: List<String>
    BufferedReader(InputStreamReader(context?.openFileInput(cursor.getString(cursor
        .getColumnIndex(cursorColumnIndex))))).run {
        fileContent = readLines()
        close()
    }
    return fileContent
}