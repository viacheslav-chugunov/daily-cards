package io.dailycards.tools.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import io.dailycards.R

class AnswersAdapter(private val answers: List<String>, val listener: Listener? = null) :
    RecyclerView.Adapter<AnswersAdapter.ViewHolder>() {

    interface Listener {
        fun onItemClicked(pos: Int)
    }

    class ViewHolder(val viewItem: View) : RecyclerView.ViewHolder(viewItem) {
        fun insertView(pos: Int, answer: String) {
            viewItem.findViewById<LinearLayout>(R.id.layout).setBackgroundResource(when(pos % 2) {
                0 -> R.color.list_item_1
                else -> R.color.list_item_2
            })
            viewItem.findViewById<TextView>(R.id.answer).text = answer
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.answer_item, parent, false))

    override fun getItemCount(): Int {
        return answers.size
    }

    override fun onBindViewHolder(holder: ViewHolder, pos: Int) {
        holder.insertView(pos, answers[pos])
        holder.viewItem.setOnClickListener { listener?.onItemClicked(pos) }
    }

}