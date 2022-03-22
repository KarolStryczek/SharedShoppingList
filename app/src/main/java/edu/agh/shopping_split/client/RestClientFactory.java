package edu.agh.shopping_split.client;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RestClientFactory {

    private static ShoppingRestClient shoppingListRest = null;

    public static ShoppingRestClient getInstance() {
        if (shoppingListRest == null) {
            shoppingListRest = new Retrofit.Builder()
                    .baseUrl("https://mobile-shopping-list.herokuapp.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(ShoppingRestClient.class);
        }
        return shoppingListRest;
    }
}