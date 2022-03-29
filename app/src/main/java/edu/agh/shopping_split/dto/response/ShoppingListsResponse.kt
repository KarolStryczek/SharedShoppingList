package edu.agh.shopping_split.dto.response

import com.example.shoppinglist.list.choose.dto.ListItem

data class ShoppingListsResponse(
    val lists: List<ListItem>
)
