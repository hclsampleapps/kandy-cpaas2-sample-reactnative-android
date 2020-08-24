package com.kandycpaas;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.modules.core.DeviceEventManagerModule;
import com.rbbn.cpaas.mobile.CPaaS;
import com.rbbn.cpaas.mobile.messaging.api.InboundMessage;
import com.rbbn.cpaas.mobile.messaging.api.MessageDeliveryStatus;
import com.rbbn.cpaas.mobile.messaging.api.MessagingCallback;
import com.rbbn.cpaas.mobile.messaging.api.OutboundMessage;
import com.rbbn.cpaas.mobile.messaging.sms.api.SMSConversation;
import com.rbbn.cpaas.mobile.messaging.sms.api.SMSListener;
import com.rbbn.cpaas.mobile.messaging.sms.api.SMSService;
import com.rbbn.cpaas.mobile.utilities.exception.MobileError;
import com.rbbn.cpaas.mobile.utilities.services.ServiceInfo;
import com.rbbn.cpaas.mobile.utilities.services.ServiceType;

import java.util.ArrayList;

public class SMSModule extends ReactContextBaseJavaModule {
    ReactApplicationContext context;

    public SMSModule(ReactApplicationContext reactContext) {
        super(reactContext);
        this.context = reactContext;
    }

    //Mandatory function getName that specifies the module name
    @Override
    public String getName() {
        return "SMSModule";
    }

    @ReactMethod
    public void initSendMessage(String destinationNumber, String sourceNumber, String messageText,
                                com.facebook.react.bridge.Callback successCallback) {

        SMSConversation smsConversation = (SMSConversation)
                smsService.createConversation(destinationNumber, sourceNumber);

        OutboundMessage message = smsService.createMessage(messageText);

        smsConversation.send(message, new MessagingCallback() {
            @Override
            public void onSuccess() {
                android.util.Log.d("CPaaS.SMSService", "Message is sent");
                successCallback.invoke("Success", "Message is sent");
            }

            @Override
            public void onFail(MobileError error) {
                android.util.Log.d("CPaaS.SMSService", "Message is failed");
                successCallback.invoke("Fail", "Message is failed");
            }
        });

    }

    SMSService smsService;

    @ReactMethod
    public void initSMSModule(
            com.facebook.react.bridge.Callback successCallback) {

        MainApplication applicationContext = (MainApplication) context.getApplicationContext();

        java.util.List<ServiceInfo> services = new ArrayList<>();
        services.add(new ServiceInfo(ServiceType.SMS, true));

        CPaaS cpass = applicationContext.getCpass();
        smsService = cpass.getSMSService();


        smsService.setSMSListener(new SMSListener() {
            @Override
            public void inboundSMSMessageReceived(InboundMessage inboundMessage) {
                android.util.Log.d("CPaaS.SMSService", "New message from " + inboundMessage.getSenderAddress() + inboundMessage.getMessage());

                context.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
                        .emit("SMSmessageReceived", "New message from " + inboundMessage.getSenderAddress() + inboundMessage.getMessage());
            }

            @Override
            public void SMSDeliveryStatusChanged(String s, MessageDeliveryStatus messageDeliveryStatus, String s1) {
                android.util.Log.d("CPaaS.SMSService", "Message delivery status changed to " + s1);
            }

            @Override
            public void outboundSMSMessageSent(OutboundMessage outboundMessage) {
                android.util.Log.d("CPaaS.SMSService", "Message is sent to " + outboundMessage.getSenderAddress());


            }
        });

        successCallback.invoke("Success", "SMS module initialize");
    }
}