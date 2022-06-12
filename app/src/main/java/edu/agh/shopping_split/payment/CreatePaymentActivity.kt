package edu.agh.shopping_split.payment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import edu.agh.shopping_split.R

class CreatePaymentActivity : AppCompatActivity() {

    lateinit var session: String
    lateinit var listCode: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_payment)

        val bundle: Bundle? = intent.extras;
        if (bundle != null) {
            session = bundle.getString("session")!!
            listCode = bundle.getString("listCode")!!
        }
    }
}