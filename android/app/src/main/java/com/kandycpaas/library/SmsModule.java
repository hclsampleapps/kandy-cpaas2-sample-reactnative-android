package com.kandycpaas.library;

import android.content.Context;
import androidx.annotation.NonNull;
import android.util.Log;

import com.rbbn.cpaas.mobile.CPaaS;
import com.rbbn.cpaas.mobile.messaging.api.InboundMessage;
import com.rbbn.cpaas.mobile.messaging.api.MessagingCallback;
import com.rbbn.cpaas.mobile.messaging.api.OutboundMessage;
import com.rbbn.cpaas.mobile.messaging.sms.api.SMSConversation;
import com.rbbn.cpaas.mobile.messaging.sms.api.SMSListener;
import com.rbbn.cpaas.mobile.messaging.sms.api.SMSService;
import com.rbbn.cpaas.mobile.utilities.exception.MobileError;
import com.rbbn.cpaas.mobile.utilities.services.ServiceInfo;
import com.rbbn.cpaas.mobile.utilities.services.ServiceType;

import java.util.ArrayList;
import java.util.List;

public class SmsModule {

    private SMSService smsService;

    public void initSmsService(@NonNull Context context, CPaaS mCpaas, CpassLibModule.SmsListner smsListner) {

        List<ServiceInfo> services = new ArrayList<>();
        services.add(new ServiceInfo(ServiceType.SMS, true));

        smsService = mCpaas.getSMSService();
        //smsService.setSMSSender(mEtSender.getText().toString());
        smsService.setSMSListener(new SMSListener() {
            @Override
            public void inboundSMSMessageReceived(InboundMessage inboundMessage) {
                Log.d("CPaaS.SMSService", "New message from " + inboundMessage.getSenderAddress() + inboundMessage.getMessage());
                smsListner.inboundSMSMessageReceived(inboundMessage);
            }

            @Override
            public void SMSDeliveryStatusChanged(String s, String s1, String s2) {
                Log.d("CPaaS.SMSService", "Message delivery status changed to " + s1);
                smsListner.SMSDeliveryStatusChanged(s, s1, s2);
            }

            @Override
            public void outboundSMSMessageSent(OutboundMessage outboundMessage) {
                Log.d("CPaaS.SMSService", "Message is sent to " + outboundMessage.getSenderAddress());
                smsListner.outboundSMSMessageSent(outboundMessage);
            }
        });
    }

    public void sendMessage(String participant, String txt, CpassLibModule.SmsMessageListner smsMessageListner) {
        Log.e("participant", participant);
        List<String> localAdressList = smsService.getLocalAddressList();
        Log.d("localAdressList", localAdressList.toString());
        String localAddress = localAdressList.get(0).toString();//choose one address from the list above
        SMSConversation smsConversation = (SMSConversation) smsService.createConversation(participant, localAddress);

        OutboundMessage message = smsService.createMessage(txt);

        smsConversation.send(message, new MessagingCallback() {
            @Override
            public void onSuccess() {
                Log.d("CPaaS.SMSService", "Message is sent");
                String msg = "Message Sent Successfully";
                smsMessageListner.onSuccess();
            }

            @Override
            public void onFail(MobileError error) {
                Log.d("CPaaS.SMSService", "Message is failed");
                String msg = "error";
                smsMessageListner.onFail(error);

            }
        });
    }
}
