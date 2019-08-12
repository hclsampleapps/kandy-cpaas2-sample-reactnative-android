package com.kandycpaas.library.interfaces;

import com.rbbn.cpaas.mobile.presence.api.PresenceList;
import com.rbbn.cpaas.mobile.presence.api.PresenceSource;
import com.rbbn.cpaas.mobile.utilities.exception.MobileError;

public interface CreatePresenceListInterface {

    void onSuccess(PresenceList presenceList);

    void onFail(MobileError error);
}
