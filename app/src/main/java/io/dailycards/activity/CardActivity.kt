package io.dailycards.activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import io.dailycards.R
import io.dailycards.fragment.CardFragment
import io.dailycards.fragment.HomeDailyFragment
import io.dailycards.tools.Extra
import io.dailycards.tools.db.DB
import io.dailycards.tools.db.Query
import kotlinx.android.synthetic.main.activity_card.*
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.toolbar.*

open class CardActivity : AppCompatActivity() {
    protected var id = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initActivity()
    }

    protected open fun initActivity() {
        setContentView(R.layout.activity_card)
        id = intent.extras?.getInt(Extra.ID) ?: -1
        Query(this).run {
            setSupportActionBar(toolbar.apply { title = getStringValueFromColumn(id, DB.TITLE) })
            close()
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        (card_fragment as CardFragment).ID = id
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        Query(this).run {
            if (getBooleanValueFromColumn(id, DB.IN_STORE)) {
                menuInflater.inflate(R.menu.store_menu, menu)
            } else {
                menuInflater.inflate(R.menu.card_menu, menu)
            }
        }
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.reversed -> {
                onClickReversedMenuItem()
                return true
            }
            R.id.answers -> {
                onClickAnswersMenuItem()
                return true
            }
            R.id.update -> {
                onCLickUpdateMenuItem()
                return true
            }
            R.id.remove -> {
                onClickRemoveMenuItem()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    protected open fun onClickReversedMenuItem() {
        startActivity(Intent(this, ReversedCardActivity::class.java).apply {
            putExtra(Extra.ID, id)
        })
    }

    protected open fun onClickAnswersMenuItem() {
        startActivity(Intent(this, AnswersActivity::class.java).apply {
            putExtra(Extra.ID, id)
        })
    }

    protected open fun onCLickUpdateMenuItem() {
        startActivity(Intent(this, UpdateActivity::class.java).apply {
            putExtra(Extra.ID, id)
        })
    }

    protected open fun onClickRemoveMenuItem() {
        Query(this).run {
            deleteFile(getStringValueFromColumn(id, DB.QUESTIONS_FILEPATH))
            deleteFile(getStringValueFromColumn(id, DB.ANSWERS_FILEPATH))
            close()
        }
        DB(this).run {
            delete(id)
            close()
        }
        startActivity(Intent(this, HomeActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        })
    }
}