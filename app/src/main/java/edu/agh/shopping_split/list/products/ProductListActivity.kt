package edu.agh.shopping_split.list.products

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

        call.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {}
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {}
        })
    }

}