package com.kandycpaas.library.interfaces;

import com.rbbn.cpaas.mobile.presence.api.PresenceActivity;
import com.rbbn.cpaas.mobile.presence.api.PresenceList;

public interface PresenceListnerInterface {

    void presenceNotification(String s, PresenceActivity presenceActivity);
    void presenceListNotification(String s, PresenceList presenceList);
}
