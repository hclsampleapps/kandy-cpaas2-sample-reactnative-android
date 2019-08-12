package com.kandycpaas.library;

import android.content.Context;
import androidx.annotation.NonNull;
import android.util.Log;

import com.kandycpaas.library.interfaces.CreatePresenceListInterface;
import com.kandycpaas.library.interfaces.CreatePresenceSourceInterface;
import com.kandycpaas.library.interfaces.FetchPresenceByClientCorrelatorInterface;
import com.kandycpaas.library.interfaces.GetPresenceListInterface;
import com.kandycpaas.library.interfaces.PresenceListnerInterface;
import com.rbbn.cpaas.mobile.CPaaS;
import com.rbbn.cpaas.mobile.presence.api.FetchAllPresenceListsCallback;
import com.rbbn.cpaas.mobile.presence.api.FetchPresenceListCallback;
import com.rbbn.cpaas.mobile.presence.api.FetchPresenceSourceCallback;
import com.rbbn.cpaas.mobile.presence.api.PresenceActivity;
import com.rbbn.cpaas.mobile.presence.api.PresenceCallback;
import com.rbbn.cpaas.mobile.presence.api.PresenceList;
import com.rbbn.cpaas.mobile.presence.api.PresenceListener;
import com.rbbn.cpaas.mobile.presence.api.PresenceService;
import com.rbbn.cpaas.mobile.presence.api.PresenceSource;
import com.rbbn.cpaas.mobile.utilities.exception.MobileError;
import com.rbbn.cpaas.mobile.utilities.services.PresenceEnums;

import java.util.ArrayList;
import java.util.List;

import static com.rbbn.cpaas.mobile.utilities.security.TLSSocketFactory.TAG;

public class PresenceModule {

    private PresenceService mPresenceService;
    private PresenceSource myPresenceSource;
    private List<PresenceList> mPresenceLists = new ArrayList<>();

    public void initPresenceService(@NonNull Context context, CPaaS cpass) {
        mPresenceService = cpass.getPresenceService();
    }

    public void setPresenceListener(PresenceListnerInterface presenceListnerInterface){

        mPresenceService.setPresenceListener(new PresenceListener() {
            @Override
            public void presenceNotification(String s, PresenceActivity presenceActivity) {
                presenceListnerInterface.presenceNotification(s,presenceActivity);
            }

            @Override
            public void presenceListNotification(String s, PresenceList presenceList) {
                presenceListnerInterface.presenceListNotification(s,presenceList);
            }
        });
    }

    public void fetchAllPresenceLists(GetPresenceListInterface getPresenceListInterface) {
        mPresenceService.fetchAllPresenceLists(new FetchAllPresenceListsCallback() {
            @Override
            public void onSuccess(List<PresenceList> lists) {

                getPresenceListInterface.onSuccess(lists);

                mPresenceLists = lists;
                for (PresenceList presenceList : mPresenceLists) {
                    subscribeForPresenceUpdates(presenceList);
                }

            }

            @Override
            public void onFail(MobileError error) {
                Log.d(TAG, error.getErrorMessage());
                getPresenceListInterface.onFail(error);
            }
        });
    }

    public void fetchPresenceByClientCorrelator(FetchPresenceByClientCorrelatorInterface fetchPresenceByClientCorrelatorInterface) {
        mPresenceService.fetchPresenceByClientCorrelator(new FetchPresenceSourceCallback() {
            @Override
            public void onSuccess(PresenceSource presenceSource) {

                fetchPresenceByClientCorrelatorInterface.onSuccess(presenceSource);

                if (presenceSource == null) {
                   // createPresenceSource();
                } else {
                    //updateMyPresence(presenceSource);
                }
            }

            @Override
            public void onFail(MobileError error) {
                Log.d("HCL", error.getErrorMessage());
                fetchPresenceByClientCorrelatorInterface.onFail(error);
            }
        });
    }

    private void subscribeForPresenceUpdates(PresenceList presenceList) {
        String name = presenceList.getName();
        String key = presenceList.getRealKey();
        mPresenceService.createPresenceListSubscription(key, new PresenceCallback() {
            @Override
            public void onSuccess() {
                String msg = "Subscribed to presence updates for " + name + " (" + key + ")";
                Log.d(TAG, msg);
            }

            @Override
            public void onFail(MobileError error) {
                Log.d(TAG, error.getErrorMessage());
            }
        });
    }

    public void createPresenceSource(CreatePresenceSourceInterface createPresenceSourceInterface) {
        mPresenceService.createPresenceSource(86400, new FetchPresenceSourceCallback() {
            @Override
            public void onSuccess(PresenceSource presenceSource) {
                //updateMyPresence(presenceSource);
                myPresenceSource = presenceSource;
                createPresenceSourceInterface.onSuccess(presenceSource);
            }

            @Override
            public void onFail(MobileError error) {
                Log.d("HCL", error.getErrorMessage());
                createPresenceSourceInterface.onFail(error);
            }
        });
    }

    public void updatePrescnceSource(String sourceID, int duration, PresenceEnums activity,String status,String other,CreatePresenceSourceInterface createPresenceSourceInterface){
        mPresenceService.updatePresenceSource(sourceID, duration, activity, status, other,
                new FetchPresenceSourceCallback() {
                    @Override
                    public void onSuccess(PresenceSource presenceSource) {
                        myPresenceSource = presenceSource;
                        createPresenceSourceInterface.onSuccess(presenceSource);
                    }

                    @Override
                    public void onFail(MobileError error) {
                        Log.d(TAG, error.getErrorMessage());
                        createPresenceSourceInterface.onFail(error);
                    }
                }
        );

    }

    public void createPresenceList(String listID, CreatePresenceListInterface createPresenceListInterface) {
        mPresenceService.createPresenceList(listID, new FetchPresenceListCallback() {
            @Override
            public void onSuccess(PresenceList presenceList) {
                createPresenceListInterface.onSuccess(presenceList);
            }

            @Override
            public void onFail(MobileError error) {
                Log.d(TAG, error.getErrorMessage());
                createPresenceListInterface.onFail(error);
            }
        });
    }

}


