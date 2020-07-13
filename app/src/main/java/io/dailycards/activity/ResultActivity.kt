package io.dailycards.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import io.dailycards.R
import io.dailycards.fragment.ResultFragment
import io.dailycards.tools.Extra
import kotlinx.android.synthetic.main.activity_result.*
import kotlinx.android.synthetic.main.toolbar.*

class ResultActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)
        setSupportActionBar(toolbar.apply { title = getString(R.string.result) })
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        (result_fragment as ResultFragment).apply {
            ID = intent.extras?.getInt(Extra.ID)!!
            userAnswers = intent.extras?.getStringArray(Extra.USER_ANSWERS)?.toList()!!
            isReversed = intent.extras?.getBoolean(Extra.REVERSED)!!
            rightAnswersCount = intent.extras?.getInt(Extra.RIGHT_ANSWERS_COUNT)!!
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this, HomeActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        })
    }
}