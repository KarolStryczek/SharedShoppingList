package edu.agh.shopping_split.receipt

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
import edu.agh.shopping_split.dto.response.ReceiptResponse
import edu.agh.shopping_split.list.products.ProductListActivity

class ListRecycleViewAdapter(
    private val context: Context,
    private var dataSet: List<ReceiptResponse>,
    private val session: String,
    private val listCode: String
) : RecyclerView.Adapter<ListRecycleViewAdapter.ViewHolder>() {


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val receiptPayer: TextView = view.findViewById(R.id.receiptPayerTxt)
        val receiptAmount: TextView = view.findViewById(R.id.receiptAmountTxt)
    }


    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.receipt_list_item, viewGroup, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.receiptPayer.text = dataSet[position].userLogin
        viewHolder.receiptAmount.text = dataSet[position].price.toString()

    }

    override fun getItemCount() = dataSet.size
}