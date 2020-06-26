package com.awesomeproject;

import android.util.Log;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.rbbn.cpaas.mobile.CPaaS;
import com.rbbn.cpaas.mobile.addressbook.api.AddressBookService;
import com.rbbn.cpaas.mobile.addressbook.api.RetrieveContactsCallback;
import com.rbbn.cpaas.mobile.addressbook.model.Contact;
import com.rbbn.cpaas.mobile.utilities.exception.MobileError;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

public class AddressBookModule extends ReactContextBaseJavaModule {
    ReactApplicationContext context;

    public AddressBookModule(ReactApplicationContext reactContext) {
        super(reactContext);
        this.context = reactContext;
    }

    //Mandatory function getName that specifies the module name
    @Override
    public String getName() {
        return "AddressBookModule";
    }

    @ReactMethod
    public void initAddressBookModule(com.facebook.react.bridge.Callback successCallback) {
        MainApplication applicationContext = (MainApplication) context.getApplicationContext();
        CPaaS cpass = applicationContext.getCpass();
        mAddressBookService = cpass.getAddressBookService();

        mAddressBookService.retrieveContactList("default", new RetrieveContactsCallback() {
            @Override
            public void onSuccess(List<Contact> list) {
                Log.d("HCL", "got list of conatct");

                String[] aa = new String[list.size()];
                for (int i = 0; i < list.size(); i++) {
                    aa[i] = list.get(i).getEmailAddress();
                }
                try {
                    JSONArray jarr = new JSONArray();
                    for (int i = 0; i < list.size(); i++) {
                        JSONObject job = new JSONObject();

                        job.put("contactId", list.get(i).getContactId());
                        job.put("firstName", list.get(i).getFirstName());
                        job.put("lastName", list.get(i).getLastName());
                        job.put("email", list.get(i).getEmailAddress());
                        job.put("homePhoneNumber", list.get(i).getHomePhoneNumber());
                        job.put("businessPhoneNumber", list.get(i).getBusinessPhoneNumber());
                        jarr.put(job);

                    }
                    successCallback.invoke("Success", jarr.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                    successCallback.invoke("Fail", "Message is failed");
                }
            }

            @Override
            public void onFail(MobileError mobileError) {
                Log.d("HCL", "fail list of conatct");
                successCallback.invoke("Fail", "Message is failed");
            }
        });

//        SMSConversation smsConversation = (SMSConversation)
//                smsService.createConversation(destinationNumber, sourceNumber);
//
//        OutboundMessage message = smsService.createMessage(messageText);
//
//        smsConversation.send(message, new MessagingCallback() {
//            @Override
//            public void onSuccess() {
//                Log.d("CPaaS.SMSService", "Message is sent");
//                successCallback.invoke("Success", "Message is sent");
//            }
//
//            @Override
//            public void onFail(MobileError error) {
//                Log.d("CPaaS.SMSService", "Message is failed");
//                successCallback.invoke("Fail", "Message is failed");
//            }
//        });

    }

    AddressBookService mAddressBookService;


}