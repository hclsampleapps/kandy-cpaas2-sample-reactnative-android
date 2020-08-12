package com.kandycpaas;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.rbbn.cpaas.mobile.CPaaS;
import com.rbbn.cpaas.mobile.presence.api.FetchAllPresenceListsCallback;
import com.rbbn.cpaas.mobile.presence.api.PresenceList;
import com.rbbn.cpaas.mobile.presence.api.PresenceService;
import com.rbbn.cpaas.mobile.utilities.exception.MobileError;

import org.json.JSONArray;
import org.json.JSONObject;

import static com.rbbn.cpaas.mobile.utilities.security.TLSSocketFactory.TAG;

public class PresenceModule extends ReactContextBaseJavaModule {
    ReactApplicationContext context;

    public PresenceModule(ReactApplicationContext reactContext) {
        super(reactContext);
        this.context = reactContext;
    }

    //Mandatory function getName that specifies the module name
    @Override
    public String getName() {
        return "PresenceModule";
    }

    @ReactMethod
    public void initPresenceModule(com.facebook.react.bridge.Callback successCallback) {
        MainApplication applicationContext = (MainApplication) context.getApplicationContext();
        CPaaS cpass = applicationContext.getCpass();
        mPresenceService = cpass.getPresenceService();
        successCallback.invoke("Success", "Presence initialized");
    }

    @ReactMethod
    public void getPersence(com.facebook.react.bridge.Callback successCallback) {
        MainApplication applicationContext = (MainApplication) context.getApplicationContext();
        CPaaS cpass = applicationContext.getCpass();
        mPresenceService = cpass.getPresenceService();
        mPresenceService.fetchAllPresenceLists(new FetchAllPresenceListsCallback() {
            @Override
            public void onSuccess(java.util.List<PresenceList> list) {

                try {
                    JSONArray jarr = new JSONArray();
                    for (int i = 0; i < list.size(); i++) {

                        for (int j = 0; j < list.get(i).getPresentities().size(); j++) {
                            JSONObject job = new JSONObject();

                            job.put("userID", list.get(i).getPresentities().get(j).getUserId());
                            job.put("activity", list.get(i).getPresentities().get(j).getActivity());

                            jarr.put(job);
                        }


                    }
                    successCallback.invoke("Success", jarr.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                    successCallback.invoke("Fail", "Message is failed");
                }

            }

            @Override
            public void onFail(MobileError error) {
                android.util.Log.d(TAG, error.getErrorMessage());
            }
        });

        successCallback.invoke("Success", "Presence initialized");
    }

    @ReactMethod
    public void setPersence(String test, com.facebook.react.bridge.Callback successCallback) {
        MainApplication applicationContext = (MainApplication) context.getApplicationContext();
        CPaaS cpass = applicationContext.getCpass();
        mPresenceService = cpass.getPresenceService();
        successCallback.invoke("Success", "Presence initialized");
    }

    PresenceService mPresenceService;
}