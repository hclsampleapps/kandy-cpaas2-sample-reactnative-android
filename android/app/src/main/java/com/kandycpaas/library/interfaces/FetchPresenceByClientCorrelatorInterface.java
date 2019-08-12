package com.kandycpaas.library.interfaces;

import com.rbbn.cpaas.mobile.addressbook.model.Contact;
import com.rbbn.cpaas.mobile.presence.api.PresenceSource;
import com.rbbn.cpaas.mobile.utilities.exception.MobileError;

import java.util.List;

public interface FetchPresenceByClientCorrelatorInterface {

    void onSuccess(PresenceSource presenceSource);

    void onFail(MobileError error);
}
