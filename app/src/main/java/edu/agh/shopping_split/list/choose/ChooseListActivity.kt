package edu.agh.shopping_split.list.choose

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppinglist.list.choose.dto.ListItem
import edu.agh.shopping_split.R
import edu.agh.shopping_split.client.RestClientFactory
import edu.agh.shopping_split.client.ShoppingRestClient
import edu.agh.shopping_split.dto.response.ShoppingListsResponse
import edu.agh.shopping_split.login.LoginActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ChooseListActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView;
    private lateinit var session: String
    private lateinit var adapter: ListRecycleViewAdapter

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        Toast.makeText(this@ChooseListActivity, "Successfully logged out!", Toast.LENGTH_SHORT).show()
        val intent = Intent(this@ChooseListActivity, LoginActivity::class.java)
        startActivity(intent)
        return super.onOptionsItemSelected(item)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose_list)

        recyclerView = findViewById(R.id.listRecycleView)
        val restClient: ShoppingRestClient = RestClientFactory.getInstance()
        supportActionBar?.title = "Your Shopping List"

        val bundle: Bundle? = intent.extras;
        if (bundle != null) {
            val newSession = bundle.getString("session")!!
            session = newSession
            val call = restClient.getAllUserLists(newSession)
            call.enqueue(object : Callback<ShoppingListsResponse?> {
                override fun onResponse(call: Call<ShoppingListsResponse?>, response: Response<ShoppingListsResponse?>) {
                    val responseBody: List<ListItem>? = response.body()?.lists
                    adapter = ListRecycleViewAdapter(
                        this@ChooseListActivity, responseBody?: listOf(), newSession
                    )
                    recyclerView.adapter = adapter
                    recyclerView.layoutManager = LinearLayoutManager(this@ChooseListActivity)
                }

                override fun onFailure(call: Call<ShoppingListsResponse?>, t: Throwable) {
                }
            })


        }
    }

    fun addListClick(view: View) {

        val intent = Intent(this, NewListActivity::class.java)
            .apply { putExtra("session", session) }

        startActivity(intent)

    }

}