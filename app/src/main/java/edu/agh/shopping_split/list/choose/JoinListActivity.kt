package edu.agh.shopping_split.list.choose

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import edu.agh.shopping_split.R
import edu.agh.shopping_split.client.RestClientFactory
import edu.agh.shopping_split.client.ShoppingRestClient
import edu.agh.shopping_split.dto.request.JoinListRequest
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class JoinListActivity : AppCompatActivity() {

    private lateinit var listCodeTxt: EditText
    private lateinit var session: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_join_list)
        supportActionBar?.title = "Join New List"

        listCodeTxt = findViewById(R.id.codeEditableTxt)
        val bundle: Bundle? = intent.extras;
        if (bundle != null) {
            session = bundle.getString("session")!!
        }
    }

    fun applyListCodeBtn(view: View) {
        val listCode: String = listCodeTxt.text.toString()
        val restClient: ShoppingRestClient = RestClientFactory.getInstance()
        if (listCode.isEmpty()) {
            Toast.makeText(this, "Please enter name first!", Toast.LENGTH_SHORT).show()
        } else {
            val call = restClient.joinProductList(session, JoinListRequest(listCode))
            call.enqueue(object : Callback<ResponseBody> {
                override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {}
                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {}
            })
            val intent = Intent(this, ChooseListActivity::class.java)
                .apply { putExtra("session", session) }

            startActivity(intent)
        }
    }

}