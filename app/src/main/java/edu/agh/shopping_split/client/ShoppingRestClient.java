package edu.agh.shopping_split.client;

import edu.agh.shopping_split.dto.request.AddProductRequest;
import edu.agh.shopping_split.dto.request.CreateListRequest;
import edu.agh.shopping_split.dto.request.JoinListRequest;
import edu.agh.shopping_split.dto.request.LoginRequest;
import edu.agh.shopping_split.dto.request.MarkProductRequest;
import edu.agh.shopping_split.dto.request.RegisterRequest;
import edu.agh.shopping_split.dto.request.RemoveProductRequest;
import edu.agh.shopping_split.dto.response.LoginResponse;
import edu.agh.shopping_split.dto.response.ProductsListResponse;
import edu.agh.shopping_split.dto.response.ShoppingListsResponse;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ShoppingRestClient {

    @POST("/login")
    Call<LoginResponse> login(
            @Body LoginRequest loginRequest
    );

    @POST("/register")
    Call<ResponseBody> register(
            @Body RegisterRequest registerRequest
    );

    @POST("/list/create")
    Call<ResponseBody> createProductList(
            @Header("session-id") String sessionId,
            @Body CreateListRequest createListRequest
    );

    @POST("/list/join")
    Call<ResponseBody> joinProductList(
            @Header("session-id") String sessionId,
            @Body JoinListRequest joinListRequest
    );

    @GET("/list/all")
    Call<ShoppingListsResponse> getAllUserLists(
            @Header("session-id") String sessionId
    );

    @POST("/list/{listCode}/add")
    Call<ResponseBody> addProduct(
            @Header("session-id") String sessionId,
            @Path(value = "listCode") String listCode,
            @Body AddProductRequest addProductRequest
    );

    @POST("/list/{listCode}/remove")
    Call<ResponseBody> removeProduct(
            @Header("session-id") String sessionId,
            @Path(value = "listCode") String listCode,
            @Body RemoveProductRequest removeProductRequest
    );

    @POST("/list/{listCode}/mark")
    Call<ResponseBody> markProduct(
            @Header("session-id") String sessionId,
            @Path(value = "listCode") String listCode,
            @Body MarkProductRequest markProductRequest
    );

    @GET("/list/{listCode}")
    Call<ProductsListResponse> getProductList(
            @Header("session-id") String sessionId,
            @Path(value = "listCode") String listCode
    );
}
