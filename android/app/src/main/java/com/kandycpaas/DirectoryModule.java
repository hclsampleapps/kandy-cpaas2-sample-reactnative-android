package com.kandycpaas;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.rbbn.cpaas.mobile.CPaaS;
import com.rbbn.cpaas.mobile.addressbook.api.AddressBookService;
import com.rbbn.cpaas.mobile.addressbook.api.RetrieveContactsCallback;
import com.rbbn.cpaas.mobile.addressbook.api.search.FieldType;
import com.rbbn.cpaas.mobile.addressbook.api.search.Search;
import com.rbbn.cpaas.mobile.addressbook.api.search.SearchCallback;
import com.rbbn.cpaas.mobile.addressbook.api.search.SearchFilter;
import com.rbbn.cpaas.mobile.addressbook.api.search.SearchOrder;
import com.rbbn.cpaas.mobile.addressbook.api.search.SearchResult;
import com.rbbn.cpaas.mobile.addressbook.model.Contact;
import com.rbbn.cpaas.mobile.utilities.exception.MobileError;

import org.json.JSONArray;
import org.json.JSONObject;

public class DirectoryModule extends ReactContextBaseJavaModule {
    ReactApplicationContext context;

    public DirectoryModule(ReactApplicationContext reactContext) {
        super(reactContext);
        this.context = reactContext;
    }

    //Mandatory function getName that specifies the module name
    @Override
    public String getName() {
        return "DirectoryModule";
    }

    @ReactMethod
    public void initDirectoryModule(com.facebook.react.bridge.Callback successCallback) {
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

    private FieldType getSearchFilterType(int position) {

        FieldType searchType = FieldType.NAME;
        switch (position) {

            case 1:
                searchType = FieldType.NAME;
                break;
            case 2:
                searchType = FieldType.FIRST_NAME;
                break;
            case 3:
                searchType = FieldType.LAST_NAME;
                break;
            case 4:
                searchType = FieldType.USERNAME;
                break;
            case 5:
                searchType = FieldType.PHONE_NUMBER;
                break;
            default:
                break;

        }

        return searchType;
    }

    private SearchOrder getSearchOrder(int position) {

        SearchOrder searchType = SearchOrder.ASCENDING;
        switch (position) {

            case 1:
                searchType = SearchOrder.ASCENDING;
                break;
            case 2:
                searchType = SearchOrder.DESCENDING;
                break;
            default:
                break;
        }
        return searchType;
    }

    @ReactMethod
    public void searchContactKeyWord(String mCriteria, com.facebook.react.bridge.Callback successCallback) {
        MainApplication applicationContext = (MainApplication) context.getApplicationContext();
        CPaaS cpass = applicationContext.getCpass();
        mAddressBookService = cpass.getAddressBookService();
        try {
//            String mCriteria;
//            JSONObject job = new JSONObject(data);
            FieldType mSearchFilterType = getSearchFilterType(1);
            SearchFilter searchFilter = new SearchFilter(mSearchFilterType, mCriteria);
            Search search = new Search(searchFilter);
            search.sortBy(getSearchFilterType(1));
            search.order(getSearchOrder(1));
            search.limit(10);
            //   Search contact ;//= new Search()
//            contact.setPrimaryContact(job.getString("contactId"));
//            contact.setFirstName(job.getString("firstName"));
//            contact.setLastName(job.getString("lastName"));
//            contact.setEmailAddress(job.getString("email"));
//            contact.setBusinessPhoneNumber(job.getString("businessPhoneNumber"));
//            contact.setHomePhoneNumber(job.getString("homePhoneNumber"));
//            contact.setMobilePhoneNumber(job.getString("homePhoneNumber"));
//            contact.setBuddy(true);
            mAddressBookService.search(search, new SearchCallback() {
                @Override
                public void onSuccess(SearchResult searchResult) {
                    if (searchResult.getContacts().size()>0){
                    try {
                        JSONArray jarr = new JSONArray();
                        for (int i = 0; i < searchResult.getContacts().size(); i++) {
                            JSONObject job = new JSONObject();

                            job.put("contactId", searchResult.getContacts().get(i).getContactId());
                            job.put("firstName", searchResult.getContacts().get(i).getFirstName());
                            job.put("lastName", searchResult.getContacts().get(i).getLastName());
                            job.put("email", searchResult.getContacts().get(i).getEmailAddress());
                            job.put("homePhoneNumber", searchResult.getContacts().get(i).getHomePhoneNumber());
                            job.put("businessPhoneNumber", searchResult.getContacts().get(i).getBusinessPhoneNumber());
                            jarr.put(job);

                        }
                        successCallback.invoke("Success", jarr.toString());
                    } catch (Exception e) {
                        e.printStackTrace();
                        successCallback.invoke("Fail", "Message is failed");
                    }}else{
                        successCallback.invoke("Fail", "Search failed");
                    }
                }

                @Override
                public void onFail(MobileError mobileError) {
                    successCallback.invoke("Fail", "Search failed");
                }
            });

        } catch (Exception e) {
            successCallback.invoke("Fail", "Search is failed");
        }
    }

    AddressBookService mAddressBookService;


}