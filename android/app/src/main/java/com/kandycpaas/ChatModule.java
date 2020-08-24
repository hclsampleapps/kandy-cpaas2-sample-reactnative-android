package com.kandycpaas;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.modules.core.DeviceEventManagerModule;
import com.rbbn.cpaas.mobile.CPaaS;
import com.rbbn.cpaas.mobile.messaging.api.InboundMessage;
import com.rbbn.cpaas.mobile.messaging.api.MessageDeliveryStatus;
import com.rbbn.cpaas.mobile.messaging.api.MessageState;
import com.rbbn.cpaas.mobile.messaging.api.MessagingCallback;
import com.rbbn.cpaas.mobile.messaging.api.OutboundMessage;
import com.rbbn.cpaas.mobile.messaging.chat.api.ChatConversation;
import com.rbbn.cpaas.mobile.messaging.chat.api.ChatGroupParticipant;
import com.rbbn.cpaas.mobile.messaging.chat.api.ChatListener;
import com.rbbn.cpaas.mobile.messaging.chat.api.ChatService;
import com.rbbn.cpaas.mobile.utilities.exception.MobileError;

public class ChatModule extends ReactContextBaseJavaModule {
    ReactApplicationContext context;

    public ChatModule(ReactApplicationContext reactContext) {
        super(reactContext);
        this.context = reactContext;
    }

    //Mandatory function getName that specifies the module name
    @Override
    public String getName() {
        return "ChatModule";
    }

    @ReactMethod
    public void sendChat(String destinationId, String messageText,
                         com.facebook.react.bridge.Callback successCallback) {

        ChatConversation chatConversation = (ChatConversation) chatService.createConversation(destinationId);
        OutboundMessage message = chatService.createMessage(messageText);
        chatConversation.send(message, new MessagingCallback() {
            @Override
            public void onSuccess() {
                android.util.Log.d("CPaaS.ChatService", "Message is sent");
                successCallback.invoke("Success", "Message is sent");
            }

            @Override
            public void onFail(MobileError error) {
                android.util.Log.d("CPaaS.ChatService", "Message is failed");
                successCallback.invoke("Success", "Message is failed");
            }
        });
    }

    ChatService chatService;

    @ReactMethod
    public void initChatModule(com.facebook.react.bridge.Callback successCallback) {

        MainApplication applicationContext = (MainApplication) context.getApplicationContext();
        CPaaS cpass = applicationContext.getCpass();
        chatService = cpass.getChatService();
        chatService.setChatListener(new ChatListener() {
            @Override
            public void inboundChatMessageReceived(InboundMessage inboundMessage) {
                context.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
                        .emit("ChatmessageReceived", "New message from " + inboundMessage.getSenderAddress()
                                + inboundMessage.getMessage());
            }

            @Override
            public void chatDeliveryStatusChanged(String s, MessageDeliveryStatus messageDeliveryStatus, String s1) {
                android.util.Log.d("CPaaS.ChatService", "Message delivery status changed to " + s1);
            }

            @Override
            public void chatParticipantStatusChanged(ChatGroupParticipant chatGroupParticipant, String s) {

            }

            @Override
            public void outboundChatMessageSent(OutboundMessage outboundMessage) {

            }

            @Override
            public void isComposingReceived(String s, MessageState messageState, long l) {

            }

            @Override
            public void groupChatSessionInvitation(java.util.List<ChatGroupParticipant> list, String s, String s1) {

            }

            @Override
            public void groupChatEventNotification(String s, String s1, String s2) {

            }
        });
        successCallback.invoke("Success", "Chat module initialize");


    }
}