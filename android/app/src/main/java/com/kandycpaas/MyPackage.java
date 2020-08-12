package com.kandycpaas;
import com.facebook.react.bridge.ReactApplicationContext;

import java.util.Arrays;
import java.util.Collections;

public class MyPackage implements com.facebook.react.ReactPackage {
    @Override
    public java.util.List<com.facebook.react.uimanager.ViewManager> createViewManagers(ReactApplicationContext reactContext) {
        return Collections.emptyList();
    }

    @Override
    public java.util.List<com.facebook.react.bridge.NativeModule> createNativeModules(ReactApplicationContext reactContext) {
        return Arrays.<com.facebook.react.bridge.NativeModule>asList(
                new LoginModule(reactContext),
                new SMSModule(reactContext),
                new AddressBookModule(reactContext),
                new DirectoryModule(reactContext),
                new PresenceModule(reactContext),
                new ChatModule(reactContext)
        );
    }
}
