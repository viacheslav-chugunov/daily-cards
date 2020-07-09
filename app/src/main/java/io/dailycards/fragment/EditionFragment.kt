package io.dailycards.fragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.dailycards.R
import io.dailycards.tools.adapter.QuestionAnswerAdapter
import kotlinx.android.synthetic.main.fragment_edition.*

class EditionFragment : Fragment() {

    interface Listener {
        fun onClickAppendButton()
        fun onClickDoneButton()
    }
    private var listener: Listener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as Listener
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_edition, container, false)
    }

    override fun onStart() {
        super.onStart()
        edition_append_button.setOnClickListener { listener?.onClickAppendButton() }
        edition_done_button.setOnClickListener { listener?.onClickDoneButton() }
    }

    fun setCardContent(cardContent: Collection<Pair<String, String>>) {
        listener?.let {
            (childFragmentManager.findFragmentById(R.id.edition_recycler_view)
                    as RecyclerViewFragment).setAdapter(QuestionAnswerAdapter(cardContent.toList()))
        }
    }
}