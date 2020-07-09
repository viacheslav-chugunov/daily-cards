package io.dailycards.tools.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import io.dailycards.tools.db.DB.Companion.ACCURACY
import io.dailycards.tools.db.DB.Companion.ANSWERS_FILEPATH
import io.dailycards.tools.db.DB.Companion.DESCRIPTION
import io.dailycards.tools.db.DB.Companion.ID
import io.dailycards.tools.db.DB.Companion.IN_STORE
import io.dailycards.tools.db.DB.Companion.LAST_DATE
import io.dailycards.tools.db.DB.Companion.QUESTIONS_FILEPATH
import io.dailycards.tools.db.DB.Companion.TABLE
import io.dailycards.tools.db.DB.Companion.TITLE

class DBHelper(val context: Context) : SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {
    companion object {
        private const val DB_NAME = "cardsDB"
        private const val DB_VERSION = 1
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("create table $TABLE($ID integer primary key autoincrement, $TITLE text, " +
                "$DESCRIPTION text, $LAST_DATE text, $ACCURACY integer, $QUESTIONS_FILEPATH text, " +
                "$ANSWERS_FILEPATH text, $IN_STORE integer default 0)")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("drop table if exists $TABLE")
        for (file in context.fileList()) context.deleteFile(file)
        onCreate(db)
    }

    override fun onDowngrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        onUpgrade(db, oldVersion, newVersion)
    }
}