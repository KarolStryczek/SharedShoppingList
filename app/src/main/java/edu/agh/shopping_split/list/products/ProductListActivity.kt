package edu.agh.shopping_split.list.products

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import edu.agh.shopping_split.list.products.dto.ProductItem
import edu.agh.shopping_split.R
import edu.agh.shopping_split.client.RestClientFactory
import edu.agh.shopping_split.client.ShoppingRestClient
import edu.agh.shopping_split.dto.response.ProductsListResponse
import edu.agh.shopping_split.list.choose.ChooseListActivity
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

import android.widget.EditText
import android.widget.Toast
import edu.agh.shopping_split.balance.BalanceListActivity
import edu.agh.shopping_split.dto.request.CreateReceiptForm
import edu.agh.shopping_split.login.LoginActivity
import edu.agh.shopping_split.payment.PaymentListActivity
import edu.agh.shopping_split.receipt.ReceiptListActivity


class ProductListActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var session: String
    private lateinit var listCode: String
    private lateinit var adapter: ListRecycleViewAdapter
    private lateinit var logoutItem: MenuItem
    private lateinit var balanceItem: MenuItem

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.part_menu, menu)
        if (menu != null) {
            balanceItem = menu.findItem(R.id.action_balance)
            logoutItem = menu.findItem(R.id.action_logout)
        }
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item) {
            balanceItem -> {
                val intent = Intent(this@ProductListActivity, BalanceListActivity::class.java)
                    .apply {
                        putExtra("session", session)
                        putExtra("listCode", listCode)
                    }
                startActivity(intent)
                return super.onOptionsItemSelected(item)
            }
            logoutItem -> {
                Toast.makeText(this@ProductListActivity, "Successfully logged out!", Toast.LENGTH_SHORT).show()
                val intent = Intent(this@ProductListActivity, LoginActivity::class.java)
                startActivity(intent)
                return super.onOptionsItemSelected(item)
            }
            else -> {
                Toast.makeText(this@ProductListActivity, "Unknown action restart app!!", Toast.LENGTH_SHORT).show()
                return super.onOptionsItemSelected(item)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_list)


        recyclerView = findViewById(R.id.productRecycleView)
        val restClient: ShoppingRestClient = RestClientFactory.getInstance()


        val bundle: Bundle? = intent.extras;
        if (bundle != null) {
            val newSession = bundle.getString("session")!!
            val thisListCode = bundle.getString("listCode")!!
            session = newSession
            listCode = thisListCode
            supportActionBar?.title = "List $thisListCode"
            val call = restClient.getProductList(newSession, thisListCode)
            call.enqueue(object : Callback<ProductsListResponse?> {
                override fun onResponse(call: Call<ProductsListResponse?>, response: Response<ProductsListResponse?>) {
                    val responseBody: List<ProductItem>? = response.body()?.products
                    adapter = ListRecycleViewAdapter(
                        this@ProductListActivity, responseBody?: listOf(), newSession, thisListCode
                    )
                    recyclerView.adapter = adapter
                    recyclerView.layoutManager = LinearLayoutManager(this@ProductListActivity)
                }

                override fun onFailure(call: Call<ProductsListResponse?>, t: Throwable) {
                }
            })

        }
    }

    override fun onBackPressed() {
        val intent = Intent(this, ChooseListActivity::class.java)
            .apply { putExtra("session", session) }

        startActivity(intent)
        super.onBackPressed()
    }

    fun addProductClick(view: View) {
        val intent = Intent(this, AddProductActivity::class.java)
            .apply {
                putExtra("session", session)
                putExtra("listCode", listCode)
            }

        startActivity(intent)
    }

    fun createReceiptClick(view: View) {
        val restClient: ShoppingRestClient = RestClientFactory.getInstance()
        val input = EditText(this@ProductListActivity)
        input.hint = "Price"
        input.gravity = 0x11
        input.setPadding(0,
            resources.getDimensionPixelOffset(R.dimen.material_filled_edittext_font_1_3_padding_bottom),
            0,
            resources.getDimensionPixelOffset(R.dimen.material_filled_edittext_font_1_3_padding_bottom)
        )
        input.inputType = 0x00002002

        val alert = AlertDialog.Builder(this@ProductListActivity)
            .setTitle("Receipt price")
            .setView(input)
            .setPositiveButton("Submit") { dialog, _ ->
                val price = input.text.toString()
                val priceDouble : Double? = if (price.isEmpty()) null else price.toDouble()
                val call = restClient.createReceipt(session, listCode, CreateReceiptForm(priceDouble))

                call.enqueue(object : Callback<ResponseBody> {
                    override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                        Toast.makeText(this@ProductListActivity, "Added with price $price", Toast.LENGTH_SHORT).show()
                        dialog.cancel()
                        this@ProductListActivity.recreate()
                    }
                    override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                        Toast.makeText(this@ProductListActivity, "Internet connection problem :)", Toast.LENGTH_SHORT).show()
                        dialog.cancel()
                        this@ProductListActivity.recreate()
                    }
                })

            }
            .setNegativeButton("Cancel") { dialog, _ ->
                Toast.makeText(this@ProductListActivity, "Canceled creating receipt!!!!", Toast.LENGTH_SHORT).show()
                dialog.cancel()
            }.create()

        alert.show()


    }

}