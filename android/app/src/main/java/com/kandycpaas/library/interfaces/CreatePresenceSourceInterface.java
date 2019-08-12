package com.kandycpaas.library.interfaces;

import com.rbbn.cpaas.mobile.presence.api.PresenceSource;
import com.rbbn.cpaas.mobile.utilities.exception.MobileError;

public interface CreatePresenceSourceInterface {

    void onSuccess(PresenceSource presenceSource);

    void onFail(MobileError error);
}
