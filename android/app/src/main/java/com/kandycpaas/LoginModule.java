package com.kandycpaas;

import android.widget.Toast;

import com.kandycpaas.remote.RestApiClient;
import com.kandycpaas.remote.RestApiInterface;
import com.kandycpaas.remote.models.LoginResponse;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class LoginModule extends ReactContextBaseJavaModule {
    ReactApplicationContext context;

    public LoginModule(ReactApplicationContext reactContext) {
        super(reactContext);
        this.context = reactContext;
    }

    //Mandatory function getName that specifies the module name
    @Override
    public String getName() {
        return "LoginModule";
    }

    private RestApiInterface mRestApiInterface;

    @ReactMethod
    public void initLogin(String clientId, String password, String email,
                          String url,
                          com.facebook.react.bridge.Callback successCallback) {


        Retrofit client = RestApiClient.getClient("https://" + url);
        if (client == null) {
            successCallback.invoke("Fail", "Failed Login");
            Toast.makeText(context, "Please enter correct Fields", Toast.LENGTH_SHORT).show();
            return;
        }
        mRestApiInterface = client.create(RestApiInterface.class);
        if (mRestApiInterface == null) {
            successCallback.invoke("Fail", "Failed Login");
            Toast.makeText(context, "Please enter correct Fields", Toast.LENGTH_SHORT).show();
            return;
        }
        Call<LoginResponse> responseCall;
        responseCall = mRestApiInterface.loginAPI(
                email,
                password,
                clientId,
                "password",
                "openid");
        responseCall.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(@androidx.annotation.NonNull Call<LoginResponse> call,
                                   @androidx.annotation.NonNull Response<LoginResponse> response) {
                LoginResponse body = response.body();
                if (body != null) {
                    MainApplication app = (MainApplication) context.getApplicationContext();
                    app.setCpass(url, body.getAccessToken(), body.getIdToken(), new CpassListner() {
                        @Override
                        public void onCpassSuccess() {
                            successCallback.invoke("Success", "Logged In Successfully");
                        }

                        @Override
                        public void onCpassFail() {
                            successCallback.invoke("Fail", "Failed Login");
                        }
                    });

                } else {
                    successCallback.invoke("Fail", "Failed Login");

                }
            }

            @Override
            public void onFailure(@androidx.annotation.NonNull Call<LoginResponse> call, @androidx.annotation.NonNull Throwable t) {
                call.cancel();
                successCallback.invoke("Fail", "Failed Login");
            }
        });
    }
}