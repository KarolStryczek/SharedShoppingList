package edu.agh.shopping_split.list.choose

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import edu.agh.shopping_split.R

class NewListActivity : AppCompatActivity() {

    lateinit var session: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_list)
        supportActionBar?.title = ""


        val bundle: Bundle? = intent.extras;
        if (bundle != null) {
            session = bundle.getString("session")!!
        }
    }

    fun createNewListClick(view: View) {
        val intent = Intent(this, CreateListActivity::class.java)
            .apply { putExtra("session", session) }

        startActivity(intent)
    }

    fun joinListClick(view: View) {
        val intent = Intent(this, JoinListActivity::class.java)
            .apply { putExtra("session", session) }

        startActivity(intent)
    }
}