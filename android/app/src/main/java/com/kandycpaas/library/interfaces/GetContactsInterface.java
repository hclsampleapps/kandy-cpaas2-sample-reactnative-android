package com.kandycpaas.library.interfaces;

import com.rbbn.cpaas.mobile.addressbook.model.Contact;
import com.rbbn.cpaas.mobile.utilities.exception.MobileError;

import java.util.List;

public interface GetContactsInterface {

    void onSuccess(List<Contact> list);

    void onFail(MobileError error);
}
