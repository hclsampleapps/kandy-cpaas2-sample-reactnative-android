package com.kandycpaas;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.rbbn.cpaas.mobile.CPaaS;
import com.rbbn.cpaas.mobile.addressbook.api.AddContactCallback;
import com.rbbn.cpaas.mobile.addressbook.api.AddressBookService;
import com.rbbn.cpaas.mobile.addressbook.api.RetrieveContactsCallback;
import com.rbbn.cpaas.mobile.addressbook.model.Contact;
import com.rbbn.cpaas.mobile.utilities.exception.MobileError;

import org.json.JSONArray;
import org.json.JSONObject;

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
            public void onSuccess(java.util.List<Contact> list) {
                android.util.Log.d("HCL", "got list of conatct");


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
                android.util.Log.d("HCL", "fail list of conatct");
                successCallback.invoke("Fail", "Message is failed");
            }
        });
    }

    @ReactMethod
    public void updateContact(String data, com.facebook.react.bridge.Callback successCallback) {
        MainApplication applicationContext = (MainApplication) context.getApplicationContext();
        CPaaS cpass = applicationContext.getCpass();
        mAddressBookService = cpass.getAddressBookService();
        try {
            JSONObject job = new JSONObject(data);
            Contact contact = new Contact();
            contact.setPrimaryContact(job.getString("contactId"));
            contact.setFirstName(job.getString("firstName"));
            contact.setLastName(job.getString("lastName"));
            contact.setEmailAddress(job.getString("email"));
            contact.setBusinessPhoneNumber(job.getString("businessPhoneNumber"));
            contact.setHomePhoneNumber(job.getString("homePhoneNumber"));
            contact.setMobilePhoneNumber(job.getString("homePhoneNumber"));
            contact.setBuddy(true);

            mAddressBookService.addContact(contact, "default", new AddContactCallback() {
                @Override
                public void onSuccess(Contact contact) {
                    successCallback.invoke("Success", "Add Address success");
                }

                @Override
                public void onFail(MobileError mobileError) {
                    successCallback.invoke("Fail", "Add Address is failed");
                }
            });
        } catch (Exception e) {
            successCallback.invoke("Fail", "Add Address is failed");
        }
    }

    AddressBookService mAddressBookService;


}