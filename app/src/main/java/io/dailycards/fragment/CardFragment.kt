package io.dailycards.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.dailycards.R
import io.dailycards.activity.ResultActivity
import io.dailycards.tools.Extra
import io.dailycards.tools.SavedState.ALL_ANSWERS
import io.dailycards.tools.SavedState.CURRENT_QUESTION
import io.dailycards.tools.SavedState.CURRENT_QUESTIONS_INDEX
import io.dailycards.tools.SavedState.RIGHT_ANSWER
import io.dailycards.tools.SavedState.RIGHT_ANSWERS_COUNT
import io.dailycards.tools.SavedState.USER_ANSWERS
import io.dailycards.tools.adapter.AnswersAdapter
import io.dailycards.tools.db.DB
import io.dailycards.tools.getPossibleAnswers
import io.dailycards.tools.readFile
import kotlinx.android.synthetic.main.fragment_card.*

open class CardFragment : Fragment(), AnswersAdapter.Listener {
    var ID: Int = -1
    protected open val reversedStatus = false
    protected var questions = listOf<String>()
    protected var answers = listOf<String>()
    private lateinit var listFragment: RecyclerViewFragment
    private var curQuestionIndex = 0
    private var curQuestion = ""
    private var rightAnswer = ""
    private var allAnswers = listOf<String>()
    private var rightAnswersCount = 0
    private var userAnswers = mutableListOf<String>()
    private var savedInstanceState: Bundle? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_card, container, false)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.run {
            putInt(CURRENT_QUESTIONS_INDEX, curQuestionIndex)
            putString(CURRENT_QUESTION, curQuestion)
            putString(RIGHT_ANSWER, rightAnswer)
            putStringArray(ALL_ANSWERS, allAnswers.toTypedArray())
            putInt(RIGHT_ANSWERS_COUNT, rightAnswersCount)
            putStringArray(USER_ANSWERS, userAnswers.toTypedArray())
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.savedInstanceState = savedInstanceState
    }

    override fun onStart() {
        super.onStart()
        val db = DB(context!!)
        db.query(arrayOf(DB.ID, DB.QUESTIONS_FILEPATH, DB.ANSWERS_FILEPATH),
            "${DB.ID} = $ID")?.run {
            moveToFirst()
            questions = readFile(context, this, DB.QUESTIONS_FILEPATH)
            answers = readFile(context, this, DB.ANSWERS_FILEPATH)
            close()
        }
        db.close()
        listFragment = (childFragmentManager.findFragmentById(R.id.answers_recycler_view) as RecyclerViewFragment)
        updateRecyclerView()

        val listener = this
        savedInstanceState?.run {
            curQuestionIndex = getInt(CURRENT_QUESTIONS_INDEX)
            curQuestion = getString(CURRENT_QUESTION)!!
            rightAnswer = getString(RIGHT_ANSWER)!!
            allAnswers = getStringArray(ALL_ANSWERS)!!.toList()
            rightAnswersCount = getInt(RIGHT_ANSWERS_COUNT)
            userAnswers = getStringArray(USER_ANSWERS)!!.toMutableList()

            question_number.text = "${curQuestionIndex + 1} / ${questions.size}"
            question.text = curQuestion
            listFragment.setAdapter(AnswersAdapter(allAnswers, listener))
        }
    }

    override fun onItemClicked(pos: Int) {
        if (curQuestionIndex + 1 < questions.size) {
            if (pos != -1) curQuestionIndex++
            processAnswer(pos)
            curQuestion = questions[curQuestionIndex]
            rightAnswer = answers[curQuestionIndex]
            allAnswers = getPossibleAnswers(rightAnswer, answers)
            question_number.text = "${curQuestionIndex + 1} / ${questions.size}"
            question.text = curQuestion
            listFragment.setAdapter(AnswersAdapter(allAnswers, this))
        } else {
            processAnswer(pos)
            context?.let { DB(it).apply {
                update(ID, rightAnswersCount * 100 / questions.size)
                close()
            } }
            startActivity(Intent(context, ResultActivity::class.java).apply {
                putExtra(Extra.ID, ID)
                putExtra(Extra.USER_ANSWERS, userAnswers.toTypedArray())
                putExtra(Extra.REVERSED, reversedStatus)
                putExtra(Extra.RIGHT_ANSWERS_COUNT, rightAnswersCount)
            })
        }
    }

    protected fun updateRecyclerView() = onItemClicked(-1)

    private fun processAnswer(pos: Int) {
        if (pos != -1) {
            if (allAnswers[pos] == rightAnswer) rightAnswersCount++
            userAnswers.add(allAnswers[pos])
        }
    }
}