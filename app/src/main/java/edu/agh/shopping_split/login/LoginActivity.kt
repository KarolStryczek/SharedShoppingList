package edu.agh.shopping_split.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import edu.agh.shopping_split.R
import edu.agh.shopping_split.client.RestClientFactory
import edu.agh.shopping_split.client.ShoppingRestClient
import edu.agh.shopping_split.dto.request.LoginRequest
import edu.agh.shopping_split.dto.response.LoginResponse
import edu.agh.shopping_split.list.choose.ChooseListActivity
import edu.agh.shopping_split.register.RegisterActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {
    private lateinit var loginTxt: EditText
    private lateinit var passwordTxt: EditText


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)


        supportActionBar?.title = "Login"

        loginTxt = findViewById(R.id.loginTxt)
        passwordTxt = findViewById(R.id.passwordTxt)

        supportActionBar?.title = "Login"


    }

    fun loginClick(view: View) {
        val login: String = loginTxt.text.toString()
        val password: String = passwordTxt.text.toString()
        val restClient: ShoppingRestClient = RestClientFactory.getInstance()
        val call = restClient.login(LoginRequest(login, password))

        call.enqueue(object : Callback<LoginResponse?> {
            override fun onResponse(call: Call<LoginResponse?>, response: Response<LoginResponse?>) {
                val loginResponse: LoginResponse? = response.body()
                if (!response.isSuccessful) {
                    Toast.makeText(this@LoginActivity, "Wrong login $login try again!", Toast.LENGTH_SHORT).show()
                }
                val session = loginResponse?.sessionId
                if (session.isNullOrBlank()) {
                    Toast.makeText(this@LoginActivity, "Wrong login $login try again!", Toast.LENGTH_SHORT).show()
                } else {
                    val intent = Intent(this@LoginActivity, ChooseListActivity::class.java)
                        .apply { putExtra("session", session) }
                    startActivity(intent)
                }
            }

            override fun onFailure(call: Call<LoginResponse?>, t: Throwable) {
            }
        })
    }

    fun registerClick(view: View) {
        val intent = Intent(this, RegisterActivity::class.java)
        startActivity(intent)
    }
}