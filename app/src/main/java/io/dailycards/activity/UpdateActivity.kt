package io.dailycards.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import io.dailycards.R
import io.dailycards.activity.abs.EditionActivity
import io.dailycards.tools.EncodingMode
import io.dailycards.tools.Extra
import io.dailycards.tools.db.DB
import io.dailycards.tools.db.Query
import io.dailycards.tools.extensions.showToast
import io.dailycards.tools.getCardContent
import io.dailycards.tools.getEncodingFilepath
import kotlinx.android.synthetic.main.fragment_edition.*
import java.io.BufferedWriter
import java.io.OutputStreamWriter

class UpdateActivity : EditionActivity() {
    private var id: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        actionBarTitle = getString(R.string.card_updating)
        super.onCreate(savedInstanceState)
        id = intent.extras!!.getInt(Extra.ID)
        Query(this).run {
            edition_title.setText(getStringValueFromColumn(id, DB.TITLE))
            edition_description.setText(getStringValueFromColumn(id, DB.DESCRIPTION))
            close()
        }
        cardContent.addAll(getCardContent(this, id))
    }

    override fun onClickDoneButton() {
        val title = edition_title.text.toString().trim()
        val description = edition_description.text.toString().trim()
        when {
            title.isBlank() -> showToast(R.string.blank_title)
            else -> {
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
                DB(this).run {
                    delete(id)
                    insert(title, description, cardContent)
                    close()
                }
                startActivity(Intent(this, HomeActivity::class.java).apply {
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                })
            }
        }
    }
}