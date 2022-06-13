package edu.agh.shopping_split.balance

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import edu.agh.shopping_split.R
import edu.agh.shopping_split.dto.response.PaymentResponse
import edu.agh.shopping_split.dto.response.UserBalanceResponse

class ListRecycleViewAdapter(
    private val context: Context,
    private var dataSet: List<UserBalanceResponse>,
    private val session: String,
    private val listCode: String
) : RecyclerView.Adapter<ListRecycleViewAdapter.ViewHolder>() {


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val listBalanceUser: TextView = view.findViewById(R.id.balanceUserTxt)
        val listBalanceUserAmount: TextView = view.findViewById(R.id.balanceUserAmountTxt)
    }


    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.balance_list_item, viewGroup, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.listBalanceUser.text = dataSet[position].userLogin
        viewHolder.listBalanceUserAmount.text = dataSet[position].balance.toString()

    }

    override fun getItemCount() = dataSet.size
}