package io.dailycards.fragment.abs

import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import io.dailycards.R
import io.dailycards.activity.CardActivity
import io.dailycards.tools.Extra
import io.dailycards.tools.adapter.CardAdapter
import io.dailycards.tools.db.DB

abstract class HomeFragment : Fragment(), CardAdapter.Listener {
    private lateinit var db: DB
    protected lateinit var cursor: Cursor

    override fun onAttach(context: Context) {
        super.onAttach(context)
        db = DB(context)
        cursor = db.query(orderBy = DB.ACCURACY)
    }

    override fun onDetach() {
        super.onDetach()
        cursor.close()
        db.close()
    }

    override fun onStart() {
        super.onStart()
        setupRecyclerView()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_home, container, false)

    protected abstract fun setupRecyclerView()

    override fun onItemClicked(id: Int) {
        val intent = Intent(context, CardActivity::class.java).apply {
            putExtra(Extra.ID, id)
        }
        startActivity(intent)
    }
}