package io.dailycards.activity

import android.content.Intent
import io.dailycards.R
import io.dailycards.fragment.ReversedCardFragment
import io.dailycards.tools.Extra
import io.dailycards.tools.db.DB
import io.dailycards.tools.db.Query
import kotlinx.android.synthetic.main.activity_reversed_card.*
import kotlinx.android.synthetic.main.toolbar.*

class ReversedCardActivity : CardActivity() {

    override fun initActivity() {
        setContentView(R.layout.activity_reversed_card)
        id = intent.extras?.getInt(Extra.ID) ?: -1
        val reversed = getString(R.string.reversed_mode)
        Query(this).run {
            val cardTitle = getStringValueFromColumn(id, DB.TITLE)
            setSupportActionBar(toolbar.apply { title = "$cardTitle ($reversed)" })
            close()
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        (reversed_card_fragment as ReversedCardFragment).ID = id
    }

    override fun onClickReversedMenuItem() {
        startActivity(Intent(this, CardActivity::class.java).apply {
            putExtra(Extra.ID, id)
        })
    }

    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this, HomeActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        })
    }
}