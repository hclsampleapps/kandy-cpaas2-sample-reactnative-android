package com.kandycpaas;

import android.app.Application;
import android.content.Context;

import com.facebook.react.ReactApplication;
import com.oblador.vectoricons.VectorIconsPackage;
import com.swmansion.gesturehandler.react.RNGestureHandlerPackage;
import com.reactnativecommunity.asyncstorage.AsyncStoragePackage;
import com.kandycpaas.library.CpaasLibPackage;
import com.kandycpaas.library.CpassLibModule;
import com.kandycpaas.library.utility.CpassSubscribe;
import com.rbbn.cpaas.mobile.CPaaS;
import com.rbbn.cpaas.mobile.utilities.Configuration;
import com.rbbn.cpaas.mobile.utilities.Globals;
import com.rbbn.cpaas.mobile.utilities.logging.LogLevel;
import com.facebook.react.ReactNativeHost;
import com.facebook.react.ReactPackage;
import com.facebook.react.shell.MainReactPackage;
import com.facebook.soloader.SoLoader;

import java.util.Arrays;
import java.util.List;

public class MainApplication extends Application implements ReactApplication {

    private CPaaS mCpaas;

    private final ReactNativeHost mReactNativeHost = new ReactNativeHost(this) {
        @Override
        public boolean getUseDeveloperSupport() {
            return BuildConfig.DEBUG;
        }

        @Override
        protected List<ReactPackage> getPackages() {
            return Arrays.<ReactPackage>asList(
                    new MainReactPackage(),
                    new VectorIconsPackage(),
                    new RNGestureHandlerPackage(),
                    new AsyncStoragePackage(),
                    new CpaasLibPackage()
            );
        }

        @Override
        protected String getJSMainModuleName() {
            return "index";
        }
    };

    @Override
    public ReactNativeHost getReactNativeHost() {
        return mReactNativeHost;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        SoLoader.init(this, /* native exopackage */ false);
    }

    public void setCpass(Context context, String baseUrl, String mAccessToken, String idToken, CpassLibModule.CpassListner cpassListner) {

        Configuration.getInstance().setUseSecureConnection(true);
        Configuration.getInstance().setRestServerUrl(baseUrl);
//        Configuration.getInstance().setRestServerPort(8080);
        Configuration.getInstance().setLogLevel(LogLevel.TRACE);
        Globals.setApplicationContext(context);

        mCpaas = CpassSubscribe.initKandyService(mAccessToken, idToken, cpassListner);
    }

    public CPaaS getCpass() {
        return mCpaas;

    }
}
