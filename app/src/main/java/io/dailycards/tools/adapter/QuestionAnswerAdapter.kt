package io.dailycards.tools.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import io.dailycards.R

class QuestionAnswerAdapter(private val cardContent: List<Pair<String, String>>) :
    RecyclerView.Adapter<QuestionAnswerAdapter.ViewHolder>() {

    class ViewHolder(private val viewItem: View) : RecyclerView.ViewHolder(viewItem) {

        fun insertView(pos: Int, cardContentItem: Pair<String, String>) {
            viewItem.findViewById<LinearLayout>(R.id.layout).setBackgroundResource(when(pos % 2) {
                0 -> R.color.list_item_1
                else -> R.color.list_item_2
            })
            viewItem.findViewById<TextView>(R.id.question).text = cardContentItem.first
            viewItem.findViewById<TextView>(R.id.answer).text = cardContentItem.second
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.question_item, parent, false))

    override fun getItemCount(): Int = cardContent.size

    override fun onBindViewHolder(holder: ViewHolder, pos: Int) {
        holder.insertView(pos, cardContent[pos])
    }
}