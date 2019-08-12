package com.kandycpaas.library.interfaces;

import com.rbbn.cpaas.mobile.addressbook.model.Contact;
import com.rbbn.cpaas.mobile.presence.api.PresenceList;
import com.rbbn.cpaas.mobile.utilities.exception.MobileError;

import java.util.List;

public interface GetPresenceListInterface {

    void onSuccess(List<PresenceList> list);

    void onFail(MobileError error);
}
