package io.dailycards.tools.adapter

import android.content.Context
import android.database.Cursor
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import io.dailycards.R
import io.dailycards.tools.db.DB
import io.dailycards.tools.getCurrentDate
import io.dailycards.tools.hasAttention
import io.dailycards.tools.inDaily
import io.dailycards.tools.inStore

class CardAdapter(private val cursor: Cursor, private val mode: Mode = Mode.USER_ALL) :
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
                    if (inDaily(cursor) and !inStore(cursor)) addContent()
                } while(cursor.moveToNext())
                Mode.USER_ALL -> do {
                    if (!inStore(cursor)) addContent()
                } while (cursor.moveToNext())
                Mode.ALL -> do {
                    addContent()
                } while(cursor.moveToNext())
                Mode.STORE  -> do {
                    if (inDaily(cursor) and inStore(cursor)) addContent()
                } while(cursor.moveToNext())
            }
        }
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
            viewItem.findViewById<TextView>(R.id.description).run {
                if (description.isBlank()) visibility = View.GONE
                else text = description
            }
            viewItem.findViewById<TextView>(R.id.accuracy).text = "$accuracy%"
            viewItem.findViewById<TextView>(R.id.last_pass).text = lastDate
        }

        fun setAttention() {
            viewItem.findViewById<ImageView>(R.id.attention_char).run {
                setImageResource(R.drawable.baseline_priority_high_black_18dp)
                setColorFilter(ContextCompat.getColor(context, R.color.attention))
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.card_item, parent, false))
    }

    override fun getItemCount(): Int {
        return cards.size
    }

    override fun onBindViewHolder(holder: ViewHolder, pos: Int) {
        val card = cards[pos]
        holder.run {
            insertView(card.title, card.description, card.accuracy.toInt(), card.lastDate)
            if (hasAttention(cursor.apply { moveToPosition(pos) } )) setAttention()
            viewItem.setOnClickListener { listener?.onItemClicked(card.id) }
        }
    }
}