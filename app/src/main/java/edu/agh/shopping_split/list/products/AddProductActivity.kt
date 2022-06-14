package edu.agh.shopping_split.list.products

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import edu.agh.shopping_split.R
import edu.agh.shopping_split.client.RestClientFactory
import edu.agh.shopping_split.client.ShoppingRestClient
import edu.agh.shopping_split.dto.request.AddProductRequest
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

import android.widget.AutoCompleteTextView

import android.widget.ArrayAdapter
import edu.agh.shopping_split.dto.response.UserBalanceResponse


class AddProductActivity : AppCompatActivity() {

    private lateinit var productNameTxt: EditText
    private lateinit var productByTxt: AutoCompleteTextView
    private lateinit var productShopTxt: EditText
    private lateinit var productNumTxt: EditText
    private lateinit var productCostTxt: EditText
    private lateinit var session: String
    private lateinit var listCode: String



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_product)

        productNameTxt = findViewById(R.id.productNameTxt)
        productShopTxt = findViewById(R.id.productShopTxt)
        productNumTxt = findViewById(R.id.productNumTxt)
        productCostTxt = findViewById(R.id.productCostTxt)

        val bundle: Bundle? = intent.extras;
        if (bundle != null) {
            session = bundle.getString("session")!!
            listCode = bundle.getString("listCode")!!
            supportActionBar?.title = "Add product"
        }

        val restClient: ShoppingRestClient = RestClientFactory.getInstance()
        val call = restClient.getListUsers(session, listCode)

        call.enqueue(object : Callback<List<UserBalanceResponse>?> {
            override fun onResponse(call: Call<List<UserBalanceResponse>?>, response: Response<List<UserBalanceResponse>?>) {
                val users : List<String> = response.body()?.map { it -> it.userLogin }!!
                val adapter: ArrayAdapter<String> = ArrayAdapter<String>(
                    this@AddProductActivity,
                    android.R.layout.simple_dropdown_item_1line, users
                )
                productByTxt = findViewById(R.id.productByTxt)

                productByTxt.setAdapter(adapter)
            }
            override fun onFailure(call: Call<List<UserBalanceResponse>?>, t: Throwable) {
            }
        })
    }

    fun applyProductClick(view: View) {
        val number = productNumTxt.text.toString()
        val cost = productCostTxt.text.toString()
        val productRequest = AddProductRequest(
            productNameTxt.text.toString(),
            productByTxt.text.toString(),
            productShopTxt.text.toString(),
            if (number.isEmpty()) null else number.toDouble(),
            if (cost.isEmpty()) null else cost.toDouble()
        )
        val restClient: ShoppingRestClient = RestClientFactory.getInstance()
        val call = restClient.addProduct(session, listCode, productRequest)

        call.enqueue(object : Callback<ResponseBody?> {
            override fun onResponse(call: Call<ResponseBody?>, response: Response<ResponseBody?>) {
                val intent = Intent(this@AddProductActivity, ProductListActivity::class.java)
                    .apply {
                        putExtra("session", session)
                        putExtra("listCode", listCode)
                    }

                startActivity(intent)
            }

            override fun onFailure(call: Call<ResponseBody?>, t: Throwable) {
            }
        })
    }

}