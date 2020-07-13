package io.dailycards.tools.db

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import io.dailycards.R
import io.dailycards.tools.EncodingMode
import io.dailycards.tools.getCurrentDate
import io.dailycards.tools.getEncodingFilepath
import java.io.BufferedWriter
import java.io.OutputStreamWriter

class DB(val context: Context) {
    private val dbHelper = DBHelper(context)
    private var db: SQLiteDatabase = dbHelper.readableDatabase

    companion object {
        const val TABLE = "cards"
        const val ID = "_id"
        const val TITLE = "title"
        const val DESCRIPTION = "description"
        const val LAST_DATE = "last_date"
        const val ACCURACY = "accuracy"
        const val QUESTIONS_FILEPATH = "questions_filepath"
        const val ANSWERS_FILEPATH = "answers_filepath"
        const val IN_STORE = "in_store"
    }

    enum class Mode {
        READABLE,
        WRITABLE
    }

    fun setMode(mode: Mode) {
        db.close()
        db = when(mode) {
            Mode.READABLE -> dbHelper.readableDatabase
            Mode.WRITABLE -> dbHelper.writableDatabase
        }
    }

    fun query(columns: Array<String>? = null, selection: String? = null,
              selectionArgs: Array<String>? = null, groupBy: String? = null, having: String? = null,
              orderBy: String? = null) =
        db.query(TABLE, columns, selection, selectionArgs, groupBy, having, orderBy)


    fun close() {
        db.close()
        dbHelper.close()
    }

    fun insert(title: String, description: String, cardContent: Collection<Pair<String, String>>,
               lastDate: String = context.getString(R.string.never_passed), accuracy: Int = 0, store: Boolean = false) : Boolean {
        if (!contains(TITLE, title)) {
            setMode(Mode.WRITABLE)
            db.insert(TABLE, null, ContentValues().apply {
                put(TITLE, title)
                put(DESCRIPTION, description)
                put(LAST_DATE, lastDate)
                put(ACCURACY, accuracy)
                put(QUESTIONS_FILEPATH, getEncodingFilepath(title, EncodingMode.QUESTION))
                put(ANSWERS_FILEPATH, getEncodingFilepath(title, EncodingMode.ANSWER))
                if (store) put(IN_STORE, 1)
            })
            val questionFile = BufferedWriter(OutputStreamWriter(context.openFileOutput(
                getEncodingFilepath(title, EncodingMode.QUESTION), Context.MODE_PRIVATE)))
            val answerFile = BufferedWriter(OutputStreamWriter(context.openFileOutput(
                getEncodingFilepath(title, EncodingMode.ANSWER), Context.MODE_PRIVATE)))
            for ((question, answer) in cardContent) {
                questionFile.write("$question\n")
                answerFile.write("$answer\n")
            }
            questionFile.close()
            answerFile.close()
            return true
        }
        return false
    }

    fun insertToStore(title: String, description: String, cardContent: Collection<Pair<String, String>>,
                      lastDate: String = context.getString(R.string.never_passed), accuracy: Int = 0) : Boolean {
        return insert(title, description, cardContent, lastDate, accuracy, true)
    }

    fun contains(dbCol: String, value: String) : Boolean {
        query(arrayOf(dbCol)).apply {
            if (moveToFirst()) {
                do {
                    if (value == getString(getColumnIndex(dbCol))) {
                        close()
                        return true
                    }
                } while (moveToNext())
            }
            close()
        }
        return false
    }

    fun update(id: Int, accuracy: Int) : Boolean {
        setMode(Mode.WRITABLE)
        if (contains(ID, id.toString())) {
            val content = ContentValues().apply {
                put(ACCURACY, accuracy)
                put(LAST_DATE, getCurrentDate())
            }
            db.update(TABLE, content, "$ID = ?", arrayOf(id.toString()))
            return true
        }
        return false
    }

    fun delete(id: Int) : Boolean {
        if (contains(ID, id.toString())) {
            db.delete(TABLE, "$ID = ?", arrayOf(id.toString()))
            return true
        }
        return false
    }
}