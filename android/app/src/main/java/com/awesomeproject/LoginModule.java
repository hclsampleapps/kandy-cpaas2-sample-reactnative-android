package com.awesomeproject;

import android.widget.Toast;

import androidx.annotation.NonNull;

import com.awesomeproject.remote.RestApiClient;
import com.awesomeproject.remote.RestApiInterface;
import com.awesomeproject.remote.models.LoginResponse;
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
            Toast.makeText(context, "Please enter correct Fields", Toast.LENGTH_SHORT).show();
            return;
        }
        mRestApiInterface = client.create(RestApiInterface.class);
        if (mRestApiInterface == null) {
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
            public void onResponse(@NonNull Call<LoginResponse> call,
                                   @NonNull Response<LoginResponse> response) {
                LoginResponse body = response.body();
                if (body != null) {
//                    Toast.makeText(LoginActivity.this, "Login Success", Toast.LENGTH_SHORT).show();
//                    Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
//                    intent.putExtra(access_token, body.getAccessToken());
//                    intent.putExtra(id_token, body.getIdToken());
//                    intent.putExtra(base_url, mBaseUrl.getText().toString());
//                    intent.putExtra(login_type, isPasswordGrantLoginType);
                    MainApplication app = (MainApplication) context.getApplicationContext();
                    app.setCpass(url, body.getAccessToken(), body.getIdToken(), new CpassListner() {
                        @Override
                        public void onCpassSuccess() {
                            successCallback.invoke("Success", "Logged In Successfully");
//                            context.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
//                                    .emit("LoginSuccess", body.getAccessToken());
                        }

                        @Override
                        public void onCpassFail() {
                            successCallback.invoke("Fail", "Failed Login");
//                            context.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
//                                    .emit("LoginSuccess", "fail");
                        }
                    });

                } else {
                    successCallback.invoke("Fail", "Failed Login");

                }
            }

            @Override
            public void onFailure(@NonNull Call<LoginResponse> call, @NonNull Throwable t) {
                call.cancel();

            }
        });
    }
}