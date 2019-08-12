package com.kandycpaas.library;

import android.content.Context;
import androidx.annotation.NonNull;
import android.util.Log;

import com.kandycpaas.library.interfaces.AddContactInterface;
import com.kandycpaas.library.interfaces.DeleteContactInterface;
import com.kandycpaas.library.interfaces.GetContactsInterface;
import com.rbbn.cpaas.mobile.CPaaS;
import com.rbbn.cpaas.mobile.addressbook.api.AddContactCallback;
import com.rbbn.cpaas.mobile.addressbook.api.AddressBookService;
import com.rbbn.cpaas.mobile.addressbook.api.DeleteContactCallback;
import com.rbbn.cpaas.mobile.addressbook.api.RetrieveContactsCallback;
import com.rbbn.cpaas.mobile.addressbook.api.UpdateContactCallback;
import com.rbbn.cpaas.mobile.addressbook.model.Contact;
import com.rbbn.cpaas.mobile.utilities.exception.MobileError;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class AddressBookModule {

    private AddressBookService mAddressBookService;

    public void initAddressBookService(@NonNull Context context, CPaaS cpass) {
        mAddressBookService = cpass.getAddressBookService();
    }

    /* Add Contact */
    public void addContact(JSONObject contactInfo, AddContactInterface addContactInterface) {

        Contact contact = new Contact();
        try {
            contact.setPrimaryContact(contactInfo.get("primaryContact").toString());
            contact.setFirstName(contactInfo.get("firstName").toString());
            contact.setLastName(contactInfo.get("lastName").toString());
            contact.setEmailAddress(contactInfo.get("email").toString());
            contact.setBusinessPhoneNumber(contactInfo.get("businessPhoneNumber").toString());
            contact.setHomePhoneNumber(contactInfo.get("homePhoneNumber").toString());
            contact.setMobilePhoneNumber(contactInfo.get("mobilePhoneNumber").toString());
            contact.setBuddy(true);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        mAddressBookService.addContact(contact, "default", new AddContactCallback() {
            @Override
            public void onSuccess(Contact contact) {
                Log.d("HCL", "Addressbook contact add success");
                addContactInterface.onSuccess(contact);
            }

            @Override
            public void onFail(MobileError mobileError) {
                Log.d("HCL", "Addressbook contact add fail");
                addContactInterface.onFail(mobileError);
            }
        });
    }

    /* Get Contacts List */
    public void getAllContact(GetContactsInterface getContactsInterface){
        mAddressBookService.retrieveContactList("default", new RetrieveContactsCallback() {
            @Override
            public void onSuccess(List<Contact> list) {
                Log.d("HCL", "got list of conatct");
                for (Contact item :
                        list) {
                    Log.d("HCL" ,item.getEmailAddress());
                }
                getContactsInterface.onSuccess(list);
            }

            @Override
            public void onFail(MobileError mobileError) {
                Log.d("HCL", "fail list of conatct");
                getContactsInterface.onFail(mobileError);
            }
        });
    }

    /* Update Contact*/
    public void updateContact(JSONObject contactInfo, AddContactInterface addContactInterface) {

        Contact contact = null;
        try {
            contact = new Contact(contactInfo.get("contactId").toString());
            contact.setPrimaryContact(contactInfo.get("primaryContact").toString());
            contact.setFirstName(contactInfo.get("firstName").toString());
            contact.setLastName(contactInfo.get("lastName").toString());
            contact.setEmailAddress(contactInfo.get("email").toString());
            contact.setBusinessPhoneNumber(contactInfo.get("businessPhoneNumber").toString());
            contact.setHomePhoneNumber(contactInfo.get("homePhoneNumber").toString());
            contact.setMobilePhoneNumber(contactInfo.get("mobilePhoneNumber").toString());
            contact.setBuddy(true);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        mAddressBookService.updateContact(contact, "default", new UpdateContactCallback() {
            @Override
            public void onSuccess(Contact contact) {
                Log.d("HCL", "Addressbook contact update success");
                addContactInterface.onSuccess(contact);
            }

            @Override
            public void onFail(MobileError mobileError) {
                Log.d("HCL", "Addressbook update add fail");
                addContactInterface.onFail(mobileError);
            }
        });
    }

    /* Delete Contact*/
    public void deleteContact(String contactId, DeleteContactInterface deleteContactInterface){

            mAddressBookService.deleteContact(contactId, "default", new DeleteContactCallback() {
                @Override
                public void onSuccess() {
                    Log.d("HCL", "Addressbook contact delete success");
                    deleteContactInterface.onSuccess();
                }

                @Override
                public void onFail(MobileError mobileError) {
                    Log.d("HCL", "Addressbook contact delete failed");
                    deleteContactInterface.onFail(mobileError);
                }
            });

    }
}
