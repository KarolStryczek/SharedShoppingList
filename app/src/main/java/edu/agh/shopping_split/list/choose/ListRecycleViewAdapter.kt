package edu.agh.shopping_split.list.choose

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
import edu.agh.shopping_split.list.products.ProductListActivity

class ListRecycleViewAdapter(
    private val context: Context,
    private var dataSet: List<ListItem>,
    private val session: String
) : RecyclerView.Adapter<ListRecycleViewAdapter.ViewHolder>() {


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val listItemName: TextView = view.findViewById(R.id.listItemNameTxt)
        val listItemCodeTxt: TextView = view.findViewById(R.id.listItemCodeTxt)
        val parent: CardView = view.findViewById(R.id.listsParent)
    }


    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.list_list_item, viewGroup, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.listItemName.text = dataSet[position].name
        viewHolder.listItemCodeTxt.text = dataSet[position].code

        viewHolder.parent.setOnClickListener {
            val intent = Intent(context, ProductListActivity::class.java)
                .apply {
                    putExtra("session", session)
                    putExtra("id", position.toString())
                    putExtra("listCode", dataSet[position].code)
                }
            context.startActivity(intent)
        }
    }

    override fun getItemCount() = dataSet.size
}