package com.kandycpaas.library.interfaces;

import com.rbbn.cpaas.mobile.utilities.exception.MobileError;

public interface DeleteContactInterface {

    void onSuccess();

    void onFail(MobileError error);
}
