package io.dailycards.activity.abs

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import io.dailycards.R
import io.dailycards.activity.HomeActivity
import io.dailycards.fragment.EditionFragment
import io.dailycards.tools.EncodingMode
import io.dailycards.tools.db.DB
import io.dailycards.tools.extensions.showToast
import io.dailycards.tools.getCardContent
import io.dailycards.tools.getEncodingFilepath
import io.dailycards.tools.getQuestionsAnswersPair
import kotlinx.android.synthetic.main.activity_creation.*
import kotlinx.android.synthetic.main.fragment_edition.*
import kotlinx.android.synthetic.main.toolbar.*
import java.io.BufferedWriter
import java.io.OutputStreamWriter

open class EditionActivity : AppCompatActivity(), EditionFragment.Listener {
    protected val cardContent = mutableListOf<Pair<String, String>>()
    private lateinit var editionFragment: EditionFragment
    protected var actionBarTitle = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_creation)
        setSupportActionBar(toolbar.apply { title = actionBarTitle })
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        editionFragment = edition_fragment as EditionFragment
    }

    override fun onStart() {
        super.onStart()
        editionFragment.setCardContent(cardContent)
    }

    override fun onClickAppendButton() {
        val question = edition_question.text.toString().trim()
        val answer = edition_answer.text.toString().trim()
        when {
            question.isBlank() or answer.isBlank() -> showToast(R.string.blank_question)
            cardContent.contains(Pair(question, answer)) -> showToast(R.string.existing_question)
            else -> {
                cardContent.add(question to answer)
                editionFragment.setCardContent(cardContent)
                edition_question.text = null
                edition_answer.text = null
            }
        }
    }

    override fun onClickDoneButton() {
        val title = edition_title.text.toString().trim()
        val description = edition_description.text.toString().trim()
        when {
            title.isBlank() -> showToast(R.string.blank_title)
            cardContent.size < 2 -> showToast(R.string.blank_card_content)
            else -> {
                DB(this).run {
                    if (contains(DB.TITLE, title)) {
                        showToast(R.string.existing_title)
                        return
                    }
                    val questionsFile = BufferedWriter(OutputStreamWriter(openFileOutput(
                        getEncodingFilepath(title, EncodingMode.QUESTION), Context.MODE_PRIVATE)))
                    val answersFile = BufferedWriter(OutputStreamWriter(openFileOutput(
                        getEncodingFilepath(title, EncodingMode.ANSWER), Context.MODE_PRIVATE)))
                    for ((question, answer) in cardContent) {
                        questionsFile.write("$question\n")
                        answersFile.write("$answer\n")
                    }
                    questionsFile.close()
                    answersFile.close()
                    insert(title, description, cardContent)
                    close()
                }
                startActivity(Intent(this, HomeActivity::class.java))
            }
        }
    }
}