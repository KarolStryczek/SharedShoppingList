package edu.agh.shopping_split.payment

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import edu.agh.shopping_split.R
import edu.agh.shopping_split.client.RestClientFactory
import edu.agh.shopping_split.client.ShoppingRestClient
import edu.agh.shopping_split.dto.response.PaymentResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PaymentListActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView;
    private lateinit var adapter: ListRecycleViewAdapter
    private lateinit var session: String
    private lateinit var listCode: String

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        return super.onCreateOptionsMenu(menu)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment_list)


        recyclerView = findViewById(R.id.paymentRecycleView)
        val restClient: ShoppingRestClient = RestClientFactory.getInstance()


        val bundle: Bundle? = intent.extras;
        if (bundle != null) {
            val newSession = bundle.getString("session")!!
            val thisListCode = bundle.getString("listCode")!!
            session = newSession
            listCode = thisListCode
            supportActionBar?.title = "$thisListCode payments"

            val call = restClient.getListPayments(newSession, thisListCode)
            call.enqueue(object : Callback<List<PaymentResponse>?> {
                override fun onResponse(call: Call<List<PaymentResponse>?>, response: Response<List<PaymentResponse>?>) {
                    adapter = ListRecycleViewAdapter(
                        this@PaymentListActivity, response.body()?: listOf(), newSession, thisListCode
                    )
                    recyclerView.adapter = adapter
                    recyclerView.layoutManager = LinearLayoutManager(this@PaymentListActivity)
                }

                override fun onFailure(call: Call<List<PaymentResponse>?>, t: Throwable) {
                }
            })

        }

    }

    fun addPaymentClick(view: View) {

        val intent = Intent(this, CreatePaymentActivity::class.java)
            .apply {
                putExtra("session", session)
                putExtra("listCode", listCode)

            }

        startActivity(intent)

    }
}