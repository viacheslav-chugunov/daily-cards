package io.dailycards.activity

import android.graphics.text.LineBreaker.JUSTIFICATION_MODE_INTER_WORD
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.RequiresApi
import io.dailycards.R
import kotlinx.android.synthetic.main.activity_questions_and_answers.*
import kotlinx.android.synthetic.main.toolbar.*

class QuestionsAndAnswersActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_questions_and_answers)
        setSupportActionBar(toolbar.apply { title = getString(R.string.questions_and_answers) })
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            justifyText()
        }
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    private fun justifyText() {
        info_question_1.justificationMode = JUSTIFICATION_MODE_INTER_WORD
        info_answer_1.justificationMode = JUSTIFICATION_MODE_INTER_WORD
        info_question_2.justificationMode = JUSTIFICATION_MODE_INTER_WORD
        info_answer_2.justificationMode = JUSTIFICATION_MODE_INTER_WORD
        info_question_3.justificationMode = JUSTIFICATION_MODE_INTER_WORD
        info_answer_3.justificationMode = JUSTIFICATION_MODE_INTER_WORD
        info_question_4.justificationMode = JUSTIFICATION_MODE_INTER_WORD
        info_answer_4.justificationMode = JUSTIFICATION_MODE_INTER_WORD
        info_question_5.justificationMode = JUSTIFICATION_MODE_INTER_WORD
        info_answer_5.justificationMode = JUSTIFICATION_MODE_INTER_WORD
        info_question_6.justificationMode = JUSTIFICATION_MODE_INTER_WORD
        info_answer_6.justificationMode = JUSTIFICATION_MODE_INTER_WORD
        info_question_7.justificationMode = JUSTIFICATION_MODE_INTER_WORD
        info_answer_7.justificationMode = JUSTIFICATION_MODE_INTER_WORD
    }
}