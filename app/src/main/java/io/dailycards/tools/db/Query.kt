package io.dailycards.tools.db

import android.content.Context

class Query(context: Context) {
    private val db = DB(context)

    fun close() = db.close()

    fun getBooleanValueFromColumn(dbId: Int, dbColumn: String) : Boolean {
        return getIntValueFromColumn(dbId, dbColumn) == 1
    }

    fun getIntValueFromColumn(dbId: Int, dbColumn: String) : Int {
        return getStringValueFromColumn(dbId, dbColumn).toInt()
    }

    fun getStringValueFromColumn(dbId: Int, dbColumn: String) : String {
        var stringValue = ""
        db.query(arrayOf(DB.ID, dbColumn), "${DB.ID} = $dbId").run {
            if (moveToFirst()) stringValue = getString(getColumnIndex(dbColumn))
            close()
        }
        return stringValue
    }
}