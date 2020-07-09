package io.dailycards.tools.adapter

import android.database.Cursor
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import io.dailycards.R
import io.dailycards.tools.db.DB
import io.dailycards.tools.getCurrentDate
import java.lang.Exception

class CardAdapter(private val cursor: Cursor, mode: Mode = Mode.USER_ALL) :
    RecyclerView.Adapter<CardAdapter.ViewHolder>() {

    interface Listener {
        fun onItemClicked(id: Int)
    }
    var listener: Listener? = null

    private var cards = mutableListOf<CardContent>()

    private data class CardContent(val title: String, val description: String, val accuracy: String,
                                   val lastDate: String, val id: Int)

    enum class Mode {
        DAILY,
        USER_ALL,
        ALL,
        STORE,
    }

    init {
        if (cursor.moveToFirst()) {
            when (mode) {
                Mode.DAILY -> do {
                    if (inDaily() and !inStore()) addContent()
                } while(cursor.moveToNext())
                Mode.USER_ALL -> do {
                    if (!inStore()) addContent()
                } while (cursor.moveToNext())
                Mode.ALL -> do {
                    addContent()
                } while(cursor.moveToNext())
                Mode.STORE  -> do {
                    if (inDaily() and inStore()) addContent()
                } while(cursor.moveToNext())
            }
        }
    }

    private fun inStore() : Boolean {
        return cursor.getInt(cursor.getColumnIndex(DB.IN_STORE)) == 1
    }

    private fun inDaily() : Boolean {
        return try {
            val (cD, cM, cY) = getCurrentDate().split(".")
            val (d, m, y) = cursor.getString(cursor.getColumnIndex(DB.LAST_DATE)).split(".")
            val isNew = "$cY.$cM.$cD" > "$y.$m.$d" || cursor.getInt(cursor.getColumnIndex(DB.ACCURACY)) <= 85
            isNew
        } catch (e: Exception) { true }
    }

    private fun addContent() {
        cards.add(CardContent(
            cursor.getString(cursor.getColumnIndex(DB.TITLE)),
            cursor.getString(cursor.getColumnIndex(DB.DESCRIPTION)),
            cursor.getString(cursor.getColumnIndex(DB.ACCURACY)),
            cursor.getString(cursor.getColumnIndex(DB.LAST_DATE)),
            cursor.getInt(cursor.getColumnIndex(DB.ID))
        ))
    }


    class ViewHolder(val viewItem: View) : RecyclerView.ViewHolder(viewItem) {

        fun insertView(title: String, description: String, accuracy: Int, lastDate: String) {
            viewItem.findViewById<LinearLayout>(R.id.layout).setBackgroundResource(when(accuracy) {
                in 0..50 -> R.color.small_accuracy_percent
                in 51..85 -> R.color.medium_accuracy_percent
                else -> R.color.high_accuracy_percent
            })
            viewItem.findViewById<TextView>(R.id.title).text = title
            viewItem.findViewById<TextView>(R.id.description).text = description
            viewItem.findViewById<TextView>(R.id.accuracy).text = "$accuracy%"
            viewItem.findViewById<TextView>(R.id.last_pass).text = lastDate
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.card_item, parent, false))

    override fun getItemCount(): Int {
        return cards.size
    }

    override fun onBindViewHolder(holder: ViewHolder, pos: Int) {
        if (cursor.moveToPosition(pos)) {
            val title = cards[pos].title
            val description = cards[pos].description
            val accuracy = cards[pos].accuracy
            val lastDate = cards[pos].lastDate
            val id = cards[pos].id
            holder.run {
                insertView(title, description, accuracy.toInt(), lastDate)
                viewItem.setOnClickListener { listener?.onItemClicked(id) }
            }
        }
    }
}