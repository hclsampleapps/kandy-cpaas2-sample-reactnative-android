package com.kandycpaas.library;

import android.content.Context;
import androidx.annotation.NonNull;
import android.util.Log;

import com.kandycpaas.MainApplication;
import com.rbbn.cpaas.mobile.CPaaS;
import com.rbbn.cpaas.mobile.messaging.api.InboundMessage;
import com.rbbn.cpaas.mobile.messaging.api.MessagingCallback;
import com.rbbn.cpaas.mobile.messaging.api.OutboundMessage;
import com.rbbn.cpaas.mobile.messaging.chat.api.ChatConversation;
import com.rbbn.cpaas.mobile.messaging.chat.api.ChatGroupParticipant;
import com.rbbn.cpaas.mobile.messaging.chat.api.ChatListener;
import com.rbbn.cpaas.mobile.messaging.chat.api.ChatService;
import com.rbbn.cpaas.mobile.utilities.exception.MobileError;

import java.util.List;

public class ChatModule {

    private ChatService chatService;


    public void initChatService(@NonNull Context context, CPaaS cpass, CpassLibModule.ChatListner chatListner) {
        MainApplication app = new MainApplication();
        //CPaaS cpass = app.getCpass();
        chatService = cpass.getChatService();
        chatService.setChatListener(new ChatListener() {
            @Override
            public void inboundChatMessageReceived(InboundMessage inboundMessage) {
                Log.d("CPaaS.ChatService", "New message from " + inboundMessage.getSenderAddress() + inboundMessage.getMessage());
                chatListner.inboundChatMessageReceived(inboundMessage);
            }

            @Override
            public void chatDeliveryStatusChanged(String s, String s1, String s2) {
                Log.d("CPaaS.ChatService", "Message delivery status changed to " + s1);
                chatListner.chatDeliveryStatusChanged(s, s1, s2);
            }

            @Override
            public void chatParticipantStatusChanged(ChatGroupParticipant chatGroupParticipant, String s) {

            }

            @Override
            public void outboundChatMessageSent(OutboundMessage outboundMessage) {
                Log.d("CPaaS.ChatService", "Message is sent to " + outboundMessage.getSenderAddress());
                chatListner.outboundChatMessageSent(outboundMessage);
            }

            @Override
            public void isComposingReceived(String s, String s1, long l) {
                Log.d("CPaaS.ChatService", "isComposingReceived " + s +", " + s1 + ", " + l);
            }

            @Override
            public void groupChatSessionInvitation(List<ChatGroupParticipant> list, String s, String s1) {
                Log.d("CPaaS.ChatService", "groupChatSessionInvitation " + s +", " + s1);
            }

            @Override
            public void groupChatEventNotification(String s, String s1, String s2) {
                Log.d("CPaaS.ChatService", "groupChatEventNotification " + s +", " + s1 + ", " + s2);
            }
        });

    }

    public void sendMessage(String participant, String txt, CpassLibModule.ChatMessageListner chatMessageListner) {
        ChatConversation chatConversation = (ChatConversation) chatService.createConversation(participant);

        OutboundMessage message = chatService.createMessage(txt);

        chatConversation.send(message, new MessagingCallback() {
            @Override
            public void onSuccess() {
                Log.d("CPaaS.ChatService", "Message is sent");
                chatMessageListner.onSuccess();
            }

            @Override
            public void onFail(MobileError error) {
                Log.d("CPaaS.ChatService", "Message is failed");
                chatMessageListner.onFail(error);

            }
        });
    }
}
