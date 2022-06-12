package edu.agh.shopping_split.client;

import java.util.List;

import edu.agh.shopping_split.dto.request.AddProductRequest;
import edu.agh.shopping_split.dto.request.CreateListRequest;
import edu.agh.shopping_split.dto.request.CreateReceiptForm;
import edu.agh.shopping_split.dto.request.JoinListRequest;
import edu.agh.shopping_split.dto.request.LoginRequest;
import edu.agh.shopping_split.dto.request.MarkProductRequest;
import edu.agh.shopping_split.dto.request.PaymentForm;
import edu.agh.shopping_split.dto.request.RegisterRequest;
import edu.agh.shopping_split.dto.request.RemoveProductRequest;
import edu.agh.shopping_split.dto.response.LoginResponse;
import edu.agh.shopping_split.dto.response.PaymentResponse;
import edu.agh.shopping_split.dto.response.ProductsListResponse;
import edu.agh.shopping_split.dto.response.ReceiptResponse;
import edu.agh.shopping_split.dto.response.ShoppingListsResponse;
import edu.agh.shopping_split.dto.response.UserBalanceResponse;
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

    @GET("/list/{listCode}/users")
    Call<List<UserBalanceResponse>> getListUsers(
            @Header("session-id") String sessionId,
            @Path(value = "listCode") String listCode
    );

    @POST("/list/{listCode}/receipt")
    Call<ResponseBody> createReceipt(
            @Header("session-id") String sessionId,
            @Path(value = "listCode") String listCode,
            @Body CreateReceiptForm createReceiptForm
    );

    @GET("/list/{listCode}/receipt/list")
    Call<List<ReceiptResponse>> getUserListReceipts(
            @Header("session-id") String sessionId,
            @Path(value = "listCode") String listCode
    );

    @POST("/list/{listCode}/payment")
    Call<ResponseBody> createPayment(
            @Header("session-id") String sessionId,
            @Path(value = "listCode") String listCode,
            @Body PaymentForm paymentForm
    );

    @GET("/list/{listCode}/payment/list")
    Call<List<PaymentResponse>> getListPayments(
            @Header("session-id") String sessionId,
            @Path(value = "listCode") String listCode
    );
}
