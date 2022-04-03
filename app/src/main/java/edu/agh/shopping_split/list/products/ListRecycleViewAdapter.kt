package edu.agh.shopping_split.list.products

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView

import edu.agh.shopping_split.list.products.dto.ProductItem
import edu.agh.shopping_split.R
import edu.agh.shopping_split.client.RestClientFactory
import edu.agh.shopping_split.client.ShoppingRestClient
import edu.agh.shopping_split.dto.request.MarkProductRequest
import edu.agh.shopping_split.dto.request.RemoveProductRequest
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListRecycleViewAdapter(
    private val context: Context,
    private var dataSet: List<ProductItem>,
    private val session: String,
    private val listCode: String
) : RecyclerView.Adapter<ListRecycleViewAdapter.ViewHolder>() {

    private val purple: Int = Color.parseColor("#FFBB86FC");
    private val white: Int = Color.parseColor("#FFFFFFFF");

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val productNameTxt: TextView = view.findViewById(R.id.productItemNameTxt)
        val productByTxt: TextView = view.findViewById(R.id.productItemByTxt)
        val productShopTxt: TextView = view.findViewById(R.id.productItemShopTxt)
        val productNumTxt: TextView = view.findViewById(R.id.productItemNumTxt)
        val productCostTxt: TextView = view.findViewById(R.id.productItemCostTxt)
        val parent: CardView = view.findViewById(R.id.listsProductParent)
    }


    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.product_list_item, viewGroup, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.productNameTxt.text = dataSet[position].name
        viewHolder.productByTxt.text = dataSet[position].by
        viewHolder.productShopTxt.text = dataSet[position].shop
        viewHolder.productNumTxt.text = dataSet[position].num
        viewHolder.productCostTxt.text = dataSet[position].cost
        val restClient: ShoppingRestClient = RestClientFactory.getInstance()


        viewHolder.parent.setOnClickListener {
            var marked = false
            if (dataSet[position].marked) {
                viewHolder.itemView.setBackgroundColor(white)
            } else {
                viewHolder.itemView.setBackgroundColor(purple)
                marked = true
            }
            dataSet[position].marked = marked
            val call = restClient.markProduct(session, listCode, MarkProductRequest(position, marked))
            call.enqueue(object : Callback<ResponseBody> {
                override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {}
                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {}
            })
        }

        if (dataSet[position].marked) {
            viewHolder.itemView.setBackgroundColor(purple)
        } else {
            viewHolder.itemView.setBackgroundColor(white)
        }

        viewHolder.parent.setOnLongClickListener {
            val call = restClient.removeProduct(session, listCode, RemoveProductRequest(position))
            call.enqueue(object : Callback<ResponseBody> {
                override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {}
                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {}
            })
            val intent = Intent(context, ProductListActivity::class.java)
                .apply {
                    putExtra("session", session)
                    putExtra("id", position.toString())
                    putExtra("listCode", listCode)
                }
            context.startActivity(intent)
            true
        }

    }

    override fun getItemCount() = dataSet.size
}