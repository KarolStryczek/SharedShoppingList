package edu.agh.shopping_split.balance

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import edu.agh.shopping_split.R
import edu.agh.shopping_split.client.RestClientFactory
import edu.agh.shopping_split.client.ShoppingRestClient
import edu.agh.shopping_split.dto.response.UserBalanceResponse
import edu.agh.shopping_split.balance.ListRecycleViewAdapter
import edu.agh.shopping_split.list.products.ProductListActivity
import edu.agh.shopping_split.login.LoginActivity
import edu.agh.shopping_split.payment.PaymentListActivity
import edu.agh.shopping_split.receipt.ReceiptListActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BalanceListActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView;
    private lateinit var session: String
    private lateinit var listCode: String
    private lateinit var adapter: ListRecycleViewAdapter
    private lateinit var logoutItem: MenuItem
    private lateinit var paymentItem: MenuItem
    private lateinit var receiptItem: MenuItem

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.full_menu, menu)
        if (menu != null) {
            paymentItem = menu.findItem(R.id.action_payment)
            logoutItem = menu.findItem(R.id.action_logout)
            receiptItem = menu.findItem(R.id.action_receipt)
        }
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item) {
            paymentItem -> {
                val intent = Intent(this@BalanceListActivity, PaymentListActivity::class.java)
                    .apply {
                        putExtra("session", session)
                        putExtra("listCode", listCode)
                    }
                startActivity(intent)
                return super.onOptionsItemSelected(item)
            }
            logoutItem -> {
                Toast.makeText(this@BalanceListActivity, "Successfully logged out!", Toast.LENGTH_SHORT).show()
                val intent = Intent(this@BalanceListActivity, LoginActivity::class.java)
                startActivity(intent)
                return super.onOptionsItemSelected(item)
            }
            receiptItem -> {
                val intent = Intent(this@BalanceListActivity, ReceiptListActivity::class.java)
                    .apply {
                        putExtra("session", session)
                        putExtra("listCode", listCode)
                    }
                startActivity(intent)
                return super.onOptionsItemSelected(item)
            }
            else -> {
                Toast.makeText(this@BalanceListActivity, "Unknown action restart app!!", Toast.LENGTH_SHORT).show()
                return super.onOptionsItemSelected(item)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_balance_list)

        recyclerView = findViewById(R.id.balanceRecycleView)
        val restClient: ShoppingRestClient = RestClientFactory.getInstance()


        val bundle: Bundle? = intent.extras;
        if (bundle != null) {
            val newSession = bundle.getString("session")!!
            val thisListCode = bundle.getString("listCode")!!
            session = newSession
            listCode = thisListCode
            supportActionBar?.title = "$thisListCode Balances"

            val call = restClient.getListUsers(newSession, thisListCode)
            call.enqueue(object : Callback<List<UserBalanceResponse>?> {
                override fun onResponse(call: Call<List<UserBalanceResponse>?>, response: Response<List<UserBalanceResponse>?>) {
                    adapter = ListRecycleViewAdapter(
                        this@BalanceListActivity,
                        response.body() ?: listOf(),
                        newSession,
                        thisListCode
                    )
                    recyclerView.adapter = adapter
                    recyclerView.layoutManager = LinearLayoutManager(this@BalanceListActivity)
                }

                override fun onFailure(call: Call<List<UserBalanceResponse>?>, t: Throwable) {
                }
            })

        }
    }

    override fun onBackPressed() {
        val intent = Intent(this, ProductListActivity::class.java)
            .apply { putExtra("session", session) }
            .apply { putExtra("listCode", listCode) }

        startActivity(intent)
        super.onBackPressed()
    }
}