package io.dailycards.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import io.dailycards.R
import io.dailycards.fragment.RecyclerViewFragment
import io.dailycards.tools.Extra
import io.dailycards.tools.adapter.QuestionAnswerAdapter
import io.dailycards.tools.db.DB
import io.dailycards.tools.db.Query
import io.dailycards.tools.getCardContent
import kotlinx.android.synthetic.main.activity_answers.*
import kotlinx.android.synthetic.main.toolbar.*

class AnswersActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_answers)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val id = intent.extras?.getInt(Extra.ID)
        Query(this).run {
            setSupportActionBar(toolbar.apply { title = getStringValueFromColumn(id!!, DB.TITLE) })
            close()
        }
        (recycler_fragment as RecyclerViewFragment).setAdapter(QuestionAnswerAdapter(
            getCardContent(this, id!!)))
    }

    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this, HomeActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        })
    }
}