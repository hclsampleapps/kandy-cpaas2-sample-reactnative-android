package com.kandycpaas;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.facebook.react.PackageList;
import com.facebook.react.ReactApplication;
import com.kandycpaas.remote.ConfigurationHelper;
import com.oblador.vectoricons.VectorIconsPackage;
import com.rbbn.cpaas.mobile.CPaaS;
import com.rbbn.cpaas.mobile.authentication.api.Authentication;
import com.rbbn.cpaas.mobile.authentication.api.ConnectionCallback;
import com.rbbn.cpaas.mobile.utilities.Configuration;
import com.rbbn.cpaas.mobile.utilities.Globals;
import com.rbbn.cpaas.mobile.utilities.exception.MobileError;
import com.rbbn.cpaas.mobile.utilities.exception.MobileException;
import com.rbbn.cpaas.mobile.utilities.logging.LogLevel;
import com.rbbn.cpaas.mobile.utilities.services.ServiceInfo;
import com.rbbn.cpaas.mobile.utilities.services.ServiceType;
import com.th3rdwave.safeareacontext.SafeAreaContextPackage;
import com.swmansion.rnscreens.RNScreensPackage;
import org.reactnative.maskedview.RNCMaskedViewPackage;
import com.swmansion.gesturehandler.react.RNGestureHandlerPackage;
import com.facebook.react.ReactInstanceManager;
import com.facebook.react.ReactNativeHost;
import com.facebook.react.ReactPackage;
import com.facebook.soloader.SoLoader;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class MainApplication extends Application implements ReactApplication {
    private CPaaS mCpaas;

    public void setCpass(String baseUrl, String mAccessToken, String idToken, CpassListner cpassListner) {
        Context context = getApplicationContext();
        Configuration.getInstance().setUseSecureConnection(true);
        Configuration.getInstance().setRestServerUrl(baseUrl);
        Configuration.getInstance().setLogLevel(LogLevel.TRACE);
        ConfigurationHelper.setConfigurations(baseUrl);
        Globals.setApplicationContext(context);
//        cPaaSCallManager.setContext(context);

        mCpaas = initKandyService(mAccessToken, idToken, cpassListner);
//        try {
//            this.mCpaas.getCallService().setCallApplicationListener(cPaaSCallManager);
//        } catch (MobileException e) {
//            e.printStackTrace();
//        }

    }
    @NonNull
    private static List<ServiceInfo> getServiceInfos() {
        List<ServiceInfo> services = new ArrayList<>();
        services.add(new ServiceInfo(ServiceType.SMS, true));
//        services.add(new ServiceInfo(ServiceType.CALL, true));
        services.add(new ServiceInfo(ServiceType.CHAT, true));
        services.add(new ServiceInfo(ServiceType.ADDRESSBOOK, true));
        services.add(new ServiceInfo(ServiceType.PRESENCE, true));
        return services;
    }

    public static CPaaS initKandyService(String accessToken, String idToken, CpassListner cpassListner) {
        Log.d("CpassSubscribe", "initKAndyService()");
        int lifetime = 3600; //in seconds

        List<ServiceInfo> services = getServiceInfos();

        CPaaS mCpaas = new CPaaS(services);
        Authentication authentication = mCpaas.getAuthentication();
        authentication.setToken(accessToken);

        try {
            authentication.connect(idToken, lifetime, new ConnectionCallback() {
                @Override
                public void onSuccess(String channelInfo) {
                    Log.d("CpassSubscribe", channelInfo);
                    cpassListner.onCpassSuccess();

                }

                @Override
                public void onFail(MobileError mobileError) {
                    if (mobileError != null)
                        Log.d("CpassSubscribe", mobileError.getErrorMessage());
                    else
                        Log.d("CpassSubscribe", "error");
                    cpassListner.onCpassFail();
                }
            });
        } catch (MobileException e) {
            e.printStackTrace();
        }

        return mCpaas;
    }

    public CPaaS getCpass() {
        return mCpaas;
    }

  private final ReactNativeHost mReactNativeHost =
      new ReactNativeHost(this) {
        @Override
        public boolean getUseDeveloperSupport() {
          return BuildConfig.DEBUG;
        }

        @Override
        protected List<ReactPackage> getPackages() {
          @SuppressWarnings("UnnecessaryLocalVariable")
          List<ReactPackage> packages = new PackageList(this).getPackages();
          // Packages that cannot be autolinked yet can be added manually here, for example:
           packages.add(new MyPackage());
          return packages;
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
    initializeFlipper(this, getReactNativeHost().getReactInstanceManager());
  }

  /**
   * Loads Flipper in React Native templates. Call this in the onCreate method with something like
   * initializeFlipper(this, getReactNativeHost().getReactInstanceManager());
   *
   * @param context
   * @param reactInstanceManager
   */
  private static void initializeFlipper(
      Context context, ReactInstanceManager reactInstanceManager) {
    if (BuildConfig.DEBUG) {
      try {
        /*
         We use reflection here to pick up the class that initializes Flipper,
        since Flipper library is not available in release mode
        */
        Class<?> aClass = Class.forName("com.kandycpaas.ReactNativeFlipper");
        aClass
            .getMethod("initializeFlipper", Context.class, ReactInstanceManager.class)
            .invoke(null, context, reactInstanceManager);
      } catch (ClassNotFoundException e) {
        e.printStackTrace();
      } catch (NoSuchMethodException e) {
        e.printStackTrace();
      } catch (IllegalAccessException e) {
        e.printStackTrace();
      } catch (InvocationTargetException e) {
        e.printStackTrace();
      }
    }
  }
}
