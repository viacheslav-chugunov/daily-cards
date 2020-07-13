package io.dailycards.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import io.dailycards.tools.adapter.HomeSectionsAdapter
import io.dailycards.R
import io.dailycards.fragment.HomeDailyFragment
import io.dailycards.tools.db.DB
import io.dailycards.tools.db.Store
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.toolbar.*

class HomeActivity : AppCompatActivity(), HomeDailyFragment.Listener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        setSupportActionBar(toolbar)
        view_pager.adapter = HomeSectionsAdapter(this, supportFragmentManager)
        tab_layout.setupWithViewPager(view_pager)
        Store(DB(this)).init()
    }

    override fun onClickCreationFAB() {
        startActivity(Intent(this, CreationActivity::class.java))
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.home_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.questions_and_answers -> {
                startActivity(Intent(this, QuestionsAndAnswersActivity::class.java))
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}