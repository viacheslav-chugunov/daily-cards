package io.dailycards.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.dailycards.R
import io.dailycards.tools.adapter.ResultCardAdapter
import io.dailycards.tools.getCardContent
import kotlinx.android.synthetic.main.fragment_result.*

class ResultFragment : Fragment() {
    var ID = -1
    var userAnswers = listOf<String>()
    var isReversed = false
    var rightAnswersCount = -1

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_result, container, false)
    }

    override fun onStart() {
        super.onStart()
        right_answers_count.text = "$rightAnswersCount / ${userAnswers.size}"
        (childFragmentManager.findFragmentById(R.id.recycler_fragment) as RecyclerViewFragment)
            .setAdapter(ResultCardAdapter(userAnswers, getCardContent(context!!, ID), isReversed))
        val count = if (userAnswers.isNotEmpty()) userAnswers.size else 1
        pass_status.setText(when (rightAnswersCount * 100 / count) {
            in 0..85 -> R.string.user_dont_passed_card
            else -> R.string.user_passed_card
        })
    }
}