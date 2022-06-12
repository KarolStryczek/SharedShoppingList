package edu.agh.shopping_split.payment

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppinglist.list.choose.dto.ListItem
import edu.agh.shopping_split.R
import edu.agh.shopping_split.dto.response.PaymentResponse
import edu.agh.shopping_split.list.products.ProductListActivity

class ListRecycleViewAdapter(
    private val context: Context,
    private var dataSet: List<PaymentResponse>,
    private val session: String,
    private val listCode: String
) : RecyclerView.Adapter<ListRecycleViewAdapter.ViewHolder>() {


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val listPaymentTo: TextView = view.findViewById(R.id.paymentToTxt)
        val listPaymentBy: TextView = view.findViewById(R.id.paymentByTxt)
        val listPaymentAmount: TextView = view.findViewById(R.id.listsParent)
    }


    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.payment_list_item, viewGroup, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.listPaymentTo.text = dataSet[position].targetUser
        viewHolder.listPaymentBy.text = dataSet[position].sourceUser
        viewHolder.listPaymentAmount.text = dataSet[position].sourceUser

    }

    override fun getItemCount() = dataSet.size
}