package edu.agh.shopping_split.list.products

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import edu.agh.shopping_split.list.products.dto.ProductItem
import edu.agh.shopping_split.R
import edu.agh.shopping_split.client.RestClientFactory
import edu.agh.shopping_split.client.ShoppingRestClient
import edu.agh.shopping_split.dto.request.MarkProductRequest
import edu.agh.shopping_split.dto.response.ProductsListResponse
import edu.agh.shopping_split.list.choose.ChooseListActivity
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.content.DialogInterface


import android.widget.EditText

import android.view.ViewGroup

import android.view.LayoutInflater
import android.widget.Toast
import androidx.core.view.marginBottom
import com.google.android.material.textfield.TextInputLayout


class ProductListActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var session: String
    private lateinit var listCode: String
    private lateinit var adapter: ListRecycleViewAdapter


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
        val call = restClient.createReceipt(session, listCode)
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
                Toast.makeText(this@ProductListActivity, "Added with price ${input.text}", Toast.LENGTH_SHORT).show()
                dialog.cancel()
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.cancel()
            }.create()

        alert.show()

        call.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {}
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {}
        })
    }

}