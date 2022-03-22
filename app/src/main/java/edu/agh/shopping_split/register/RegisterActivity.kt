package edu.agh.shopping_split.register

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import edu.agh.shopping_split.R
import edu.agh.shopping_split.client.RestClientFactory
import edu.agh.shopping_split.client.ShoppingRestClient
import edu.agh.shopping_split.dto.request.RegisterRequest
import edu.agh.shopping_split.login.LoginActivity
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterActivity : AppCompatActivity() {


    private lateinit var loginTxt: EditText
    private lateinit var passwordTxt: EditText
    private lateinit var emailTxt: EditText
    private lateinit var phoneTxt: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        loginTxt = findViewById(R.id.loginRegisterTxt)
        passwordTxt = findViewById(R.id.passwordRegisterTxt)
        emailTxt = findViewById(R.id.emailRegisterTxt)
        phoneTxt = findViewById(R.id.phoneRegisterTxt)
    }

    fun makeRegistrationClick(view: View) {
        val login: String = loginTxt.text.toString()
        val password: String = passwordTxt.text.toString()
        val email: String = emailTxt.text.toString()
        val phone: String = phoneTxt.text.toString()
        val restClient: ShoppingRestClient = RestClientFactory.getInstance()
        val call = restClient.register(RegisterRequest(login, password, email, phone))
        supportActionBar?.title = "Register"


        call.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (!response.isSuccessful) {
                    Toast.makeText(this@RegisterActivity, "Login is taken. Try another!", Toast.LENGTH_SHORT).show()
                } else {
                    val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
                    startActivity(intent)
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
            }
        })
    }


}