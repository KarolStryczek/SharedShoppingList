package edu.agh.shopping_split.client;

import edu.agh.shopping_split.dto.request.LoginRequest;
import edu.agh.shopping_split.dto.request.RegisterRequest;
import edu.agh.shopping_split.dto.response.LoginResponse;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ShoppingRestClient {

    @POST("/login")
    Call<LoginResponse> login(
            @Body LoginRequest loginRequest
    );

    @POST("/register")
    Call<ResponseBody> register(
            @Body RegisterRequest registerRequest
    );

}
