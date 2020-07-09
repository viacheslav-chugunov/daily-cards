package io.dailycards.tools.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import io.dailycards.R

class ResultCardAdapter(private var userAnswers: List<String>,
                        private val cardContent: List<Pair<String, String>>,
                        val reversed: Boolean = false) :
    RecyclerView.Adapter<ResultCardAdapter.ViewHolder>() {

    class ViewHolder(private val viewItem: View) : RecyclerView.ViewHolder(viewItem) {

        fun insertView(userAnswer: String, rightAnswer: String,
                       cardContentItem: Pair<String, String>) {
            viewItem.findViewById<LinearLayout>(R.id.layout).setBackgroundResource(
                if (userAnswer == rightAnswer) R.color.right_user_answer
                else R.color.wrong_user_answer
            )
            viewItem.findViewById<TextView>(R.id.question).text = cardContentItem.first
            viewItem.findViewById<TextView>(R.id.answer).text = cardContentItem.second
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.question_item, parent, false))

    override fun getItemCount(): Int = cardContent.size

    override fun onBindViewHolder(holder: ViewHolder, pos: Int) {
        val rightAnswer = if (reversed) cardContent[pos].first else cardContent[pos].second
        holder.insertView(userAnswers[pos], rightAnswer, cardContent[pos])
    }
}