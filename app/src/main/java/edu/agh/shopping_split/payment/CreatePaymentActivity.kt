package edu.agh.shopping_split.payment

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.EditText
import edu.agh.shopping_split.R
import edu.agh.shopping_split.client.RestClientFactory
import edu.agh.shopping_split.client.ShoppingRestClient
import edu.agh.shopping_split.dto.request.PaymentForm
import edu.agh.shopping_split.dto.response.UserBalanceResponse
import edu.agh.shopping_split.list.choose.ChooseListActivity
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CreatePaymentActivity : AppCompatActivity() {

    private lateinit var paymentByTxt: AutoCompleteTextView
    private lateinit var paymentAmountTxt: EditText
    lateinit var session: String
    lateinit var listCode: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_payment)

        paymentAmountTxt = findViewById(R.id.paymentAmountTxt)

        val bundle: Bundle? = intent.extras;
        if (bundle != null) {
            session = bundle.getString("session")!!
            listCode = bundle.getString("listCode")!!
        }

        val restClient: ShoppingRestClient = RestClientFactory.getInstance()
        val call = restClient.getListUsers(session, listCode)

        call.enqueue(object : Callback<List<UserBalanceResponse>?> {
            override fun onResponse(call: Call<List<UserBalanceResponse>?>, response: Response<List<UserBalanceResponse>?>) {
                val users : List<String> = response.body()?.map { it -> it.userLogin }!!
                val adapter: ArrayAdapter<String> = ArrayAdapter<String>(
                    this@CreatePaymentActivity,
                    android.R.layout.simple_dropdown_item_1line, users
                )
                paymentByTxt = findViewById(R.id.paymentMakerTxt)

                paymentByTxt.setAdapter(adapter)
            }
            override fun onFailure(call: Call<List<UserBalanceResponse>?>, t: Throwable) {
            }
        })
    }



    fun applyPaymentClick(view: View) {
        val paymentRequest = PaymentForm(
            paymentByTxt.text.toString(),
            paymentAmountTxt.text.toString()
        )
        val restClient: ShoppingRestClient = RestClientFactory.getInstance()
        val call = restClient.createPayment(session, listCode, paymentRequest)

        call.enqueue(object : Callback<ResponseBody?> {
            override fun onResponse(call: Call<ResponseBody?>, response: Response<ResponseBody?>) {
                val intent = Intent(this@CreatePaymentActivity, PaymentListActivity::class.java)
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


    override fun onBackPressed() {
        val intent = Intent(this, PaymentListActivity::class.java)
            .apply { putExtra("session", session) }
            .apply { putExtra("listCode", listCode) }

        startActivity(intent)
        super.onBackPressed()
    }

}