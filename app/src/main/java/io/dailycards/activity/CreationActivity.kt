package io.dailycards.activity

import android.os.Bundle
import io.dailycards.R
import io.dailycards.activity.abs.EditionActivity

class CreationActivity : EditionActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        actionBarTitle = getString(R.string.card_creation)
        super.onCreate(savedInstanceState)
    }
}