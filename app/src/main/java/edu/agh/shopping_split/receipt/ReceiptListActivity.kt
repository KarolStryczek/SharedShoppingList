package edu.agh.shopping_split.receipt

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import edu.agh.shopping_split.R
import edu.agh.shopping_split.client.RestClientFactory
import edu.agh.shopping_split.client.ShoppingRestClient
import edu.agh.shopping_split.dto.response.ReceiptResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ReceiptListActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView;
    private lateinit var adapter: ListRecycleViewAdapter
    private lateinit var session: String
    private lateinit var listCode: String


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        return super.onCreateOptionsMenu(menu)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_receipt_list)
        recyclerView = findViewById(R.id.receiptRecycleView)
        val restClient: ShoppingRestClient = RestClientFactory.getInstance()


        val bundle: Bundle? = intent.extras;
        if (bundle != null) {
            val newSession = bundle.getString("session")!!
            val thisListCode = bundle.getString("listCode")!!
            session = newSession
            listCode = thisListCode
            supportActionBar?.title = "$thisListCode receipt"

            val call = restClient.getUserListReceipts(newSession, thisListCode)
            call.enqueue(object : Callback<List<ReceiptResponse>?> {
                override fun onResponse(call: Call<List<ReceiptResponse>?>, response: Response<List<ReceiptResponse>?>) {
                    adapter = ListRecycleViewAdapter(
                        this@ReceiptListActivity,
                        response.body() ?: listOf(),
                        newSession,
                        thisListCode
                    )
                    recyclerView.adapter = adapter
                    recyclerView.layoutManager = LinearLayoutManager(this@ReceiptListActivity)
                }

                override fun onFailure(call: Call<List<ReceiptResponse>?>, t: Throwable) {
                }
            })

        }

    }
}