package com.kandycpaas.library;

import android.util.Log;
import android.widget.Toast;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Callback;
import com.facebook.react.modules.core.DeviceEventManagerModule;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.kandycpaas.MainApplication;
import com.kandycpaas.library.interfaces.AddContactInterface;
import com.kandycpaas.library.interfaces.CreatePresenceListInterface;
import com.kandycpaas.library.interfaces.CreatePresenceSourceInterface;
import com.kandycpaas.library.interfaces.DeleteContactInterface;
import com.kandycpaas.library.interfaces.FetchPresenceByClientCorrelatorInterface;
import com.kandycpaas.library.interfaces.GetContactsInterface;
import com.kandycpaas.library.interfaces.GetPresenceListInterface;
import com.rbbn.cpaas.mobile.CPaaS;
import com.rbbn.cpaas.mobile.addressbook.model.Contact;
import com.rbbn.cpaas.mobile.messaging.api.InboundMessage;
import com.rbbn.cpaas.mobile.messaging.api.MessagingCallback;
import com.rbbn.cpaas.mobile.messaging.api.OutboundMessage;
import com.rbbn.cpaas.mobile.messaging.sms.api.SMSConversation;
import com.rbbn.cpaas.mobile.messaging.sms.api.SMSListener;
import com.rbbn.cpaas.mobile.messaging.sms.api.SMSService;
import com.rbbn.cpaas.mobile.presence.api.FetchPresenceSourceCallback;
import com.rbbn.cpaas.mobile.presence.api.PresenceActivity;
import com.rbbn.cpaas.mobile.presence.api.PresenceList;
import com.rbbn.cpaas.mobile.presence.api.PresenceSource;
import com.rbbn.cpaas.mobile.presence.api.Presentity;
import com.rbbn.cpaas.mobile.utilities.exception.MobileError;
import com.rbbn.cpaas.mobile.utilities.services.PresenceEnums;
import com.rbbn.cpaas.mobile.utilities.services.ServiceInfo;
import com.rbbn.cpaas.mobile.utilities.services.ServiceType;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.rbbn.cpaas.mobile.utilities.security.TLSSocketFactory.TAG;

public class CpassLibModule extends ReactContextBaseJavaModule {

    private final ReactApplicationContext reactContext;
    private CPaaS mCpaas;
    private SMSService smsService;
    ChatModule chatModule;
    SmsModule smsModule;
    AddressBookModule addressBookModule;
    PresenceModule presenceModule;
    private PresenceSource myPresenceSource;

    public CpassLibModule(ReactApplicationContext reactContext) {
        super(reactContext);
        this.reactContext = reactContext;
    }

    @Override
    public String getName() {
        return "KandyCpaasLib";
    }

    @ReactMethod
    public void show(String message, Callback callBack) {
        Toast.makeText(getReactApplicationContext(), message, Toast.LENGTH_LONG).show();
        String test = "TestCallBack";
        callBack.invoke(test);
    }

    @ReactMethod
    public void initKandyService(String baseUrl, String mAccessToken, String idToken, Promise promise) {
        Log.e("baseUrl", baseUrl);
        Log.e("mAccessToken", mAccessToken);
        Log.e("idToken", idToken);
        MainApplication app = new MainApplication();
        app.setCpass(this.reactContext, baseUrl, mAccessToken, idToken, new CpassListner() {
            @Override
            public void onCpassSuccess() {
                mCpaas = app.getCpass();
                String pass = "success";
                promise.resolve(pass);
                //initSMSService(mCpaas, promise);
            }

            @Override
            public void onCpassFail() {
                String fail = "fail";
                promise.reject("403", fail);
            }
        });

    }

    private void initSMSService(CPaaS mCpaas, Promise promise) {

        List<ServiceInfo> services = new ArrayList<>();
        services.add(new ServiceInfo(ServiceType.SMS, true));

        //CPaaS cpass = app.getCpass();
        smsService = mCpaas.getSMSService();
        //smsService.setSMSSender(mEtSender.getText().toString());
        smsService.setSMSListener(new SMSListener() {
            @Override
            public void inboundSMSMessageReceived(InboundMessage inboundMessage) {
                Log.d("CPaaS.SMSService", "New message from " + inboundMessage.getSenderAddress() + inboundMessage.getMessage());

                JSONObject receivedData = new JSONObject();
                try {
                    receivedData.put("senderAddress", inboundMessage.getSenderAddress());
                    receivedData.put("receivedMessage", inboundMessage.getMessage());
                    receivedData.put("receivedMessageId", inboundMessage.getMessageId());
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                reactContext
                        .getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
                        .emit("MessageReceived", receivedData.toString());
            }

            @Override
            public void SMSDeliveryStatusChanged(String s, String s1, String s2) {
                Log.d("CPaaS.SMSService", "Message delivery status changed to " + s1);
                reactContext
                        .getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
                        .emit("MessageStatus", s1);
            }

            @Override
            public void outboundSMSMessageSent(OutboundMessage outboundMessage) {
                Log.d("CPaaS.SMSService", "Message is sent to " + outboundMessage.getSenderAddress());
                JSONObject sendData = new JSONObject();
                try {
                    sendData.put("senderAddress", outboundMessage.getSenderAddress());
                    sendData.put("senderMessage", outboundMessage.getMessage());
                    sendData.put("senderMessageId", outboundMessage.getMessageId());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                reactContext
                        .getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
                        .emit("MessageSent", sendData.toString());

            }
        });

    }

    @ReactMethod
    public void initSMSService(Promise promise) {
        smsModule = new SmsModule();
        smsModule.initSmsService(this.reactContext, mCpaas, new SmsListner() {
            @Override
            public void inboundSMSMessageReceived(InboundMessage inboundMessage) {
                Log.d("CPaaS.SMSService", "New message from " + inboundMessage.getSenderAddress() + inboundMessage.getMessage());

                JSONObject receivedData = new JSONObject();
                try {
                    receivedData.put("senderAddress", inboundMessage.getSenderAddress());
                    receivedData.put("receivedMessage", inboundMessage.getMessage());
                    receivedData.put("receivedMessageId", inboundMessage.getMessageId());
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                reactContext
                        .getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
                        .emit("MessageReceived", receivedData.toString());
            }

            @Override
            public void SMSDeliveryStatusChanged(String s, String s1, String s2) {
                Log.d("CPaaS.SMSService", "Message delivery status changed to " + s1);
                reactContext
                        .getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
                        .emit("MessageStatus", s1);
            }

            @Override
            public void outboundSMSMessageSent(OutboundMessage outboundMessage) {
                Log.d("CPaaS.SMSService", "Message is sent to " + outboundMessage.getSenderAddress());
                JSONObject sendData = new JSONObject();
                try {
                    sendData.put("senderAddress", outboundMessage.getSenderAddress());
                    sendData.put("senderMessage", outboundMessage.getMessage());
                    sendData.put("senderMessageId", outboundMessage.getMessageId());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                reactContext
                        .getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
                        .emit("MessageSent", sendData.toString());
            }
        });

    }

    @ReactMethod
    public void sendMessage(String participant, String txt, Promise promise) {
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
                promise.resolve(msg);
            }

            @Override
            public void onFail(MobileError error) {
                Log.d("CPaaS.SMSService", "Message is failed");
                String msg = "error";
                promise.reject("404", msg);

            }
        });
    }

    @ReactMethod
    public void sendSmSMessage(String participant, String txt, Promise promise) {
        smsModule.sendMessage(participant, txt, new SmsMessageListner() {
            @Override
            public void onSuccess() {
                Log.d("CPaaS.SMSService 1", "Message is sent");
                String msg = "Message Sent Successfully";
                promise.resolve(msg);
            }

            @Override
            public void onFail(MobileError error) {
                Log.d("CPaaS.SMSService 1", "Message is failed");
                String msg = "error";
                promise.reject("404", msg);
            }
        });
    }


    @ReactMethod
    public void initChatService(Promise promise) {
        chatModule = new ChatModule();
        chatModule.initChatService(this.reactContext, mCpaas, new ChatListner() {
            @Override
            public void inboundChatMessageReceived(InboundMessage inboundMessage) {
                Log.d("CPaaS.ChatService", "New message from " + inboundMessage.getSenderAddress() + inboundMessage.getMessage());
                JSONObject receivedData = new JSONObject();
                try {
                    receivedData.put("senderAddress", inboundMessage.getSenderAddress());
                    receivedData.put("receivedMessage", inboundMessage.getMessage());
                    receivedData.put("receivedMessageId", inboundMessage.getMessageId());
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                reactContext
                        .getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
                        .emit("chatMessageReceived", receivedData.toString());
            }

            @Override
            public void chatDeliveryStatusChanged(String s, String s1, String s2) {
                Log.d("CPaaS.ChatService", "Message delivery status changed to " + s1);
                reactContext
                        .getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
                        .emit("chatMessageStatus", s1);
            }

            @Override
            public void outboundChatMessageSent(OutboundMessage outboundMessage) {
                Log.d("CPaaS.ChatService", "Message is sent to " + outboundMessage.getSenderAddress());
                JSONObject sendData = new JSONObject();
                try {
                    sendData.put("senderAddress", outboundMessage.getSenderAddress());
                    sendData.put("senderMessage", outboundMessage.getMessage());
                    sendData.put("senderMessageId", outboundMessage.getMessageId());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                reactContext
                        .getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
                        .emit("chatMessageSent", sendData.toString());
            }
        });
    }

    @ReactMethod
    public void sendChatMessage(String participant, String txt, Promise promise) {
        Log.d("chat participant", participant);
        Log.d("chat message", txt);
        chatModule.sendMessage(participant, txt, new ChatMessageListner() {
            @Override
            public void onSuccess() {
                Log.d("CPaaS.ChatService", "Message is sent");
                String msg = "Message Sent Successfully";
                promise.resolve(msg);
            }

            @Override
            public void onFail(MobileError error) {
                Log.d("CPaaS.ChatService", "Message is failed");
                String msg = "error";
                promise.reject("404", msg);
            }
        });
    }

    @ReactMethod
    public void initaddressService(Promise promise) {
        addressBookModule = new AddressBookModule();
        addressBookModule.initAddressBookService(this.reactContext, mCpaas);
    }

    @ReactMethod
    public void addContact(String contactInformation, Promise promise) {

        JSONObject contactInfo = null;
        try {
            contactInfo = new JSONObject(contactInformation);
            addressBookModule.addContact(contactInfo, new AddContactInterface() {
                @Override
                public void onSuccess(Contact contact) {
                    String msg = "Contact Added successfully";
                    promise.resolve(msg);
                }

                @Override
                public void onFail(MobileError error) {
                    String msg = "error";
                    promise.reject("404", msg);
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    @ReactMethod
    public void getContactList(Promise promise) {
        addressBookModule.getAllContact(new GetContactsInterface() {
            @Override
            public void onSuccess(List<Contact> list) {
                Log.e("Contact List", list.toString());
                //JsonObject jsonObject = getJsonOfList(list);
                String contactList = list.toString();
                promise.resolve(contactList);
            }

            @Override
            public void onFail(MobileError error) {
                String msg = "error";
                promise.reject("404", msg);
            }
        });
    }

    @ReactMethod
    public void updateContact(String contactInformation, Promise promise) {

        JSONObject contactInfo = null;
        try {
            contactInfo = new JSONObject(contactInformation);
            addressBookModule.updateContact(contactInfo, new AddContactInterface() {
                @Override
                public void onSuccess(Contact contact) {
                    String msg = "Contact updated successfully";
                    promise.resolve(msg);
                }

                @Override
                public void onFail(MobileError error) {
                    String msg = "error";
                    promise.reject("404", msg);
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @ReactMethod
    public void deleteContact(String contactId, Promise promise) {
        addressBookModule.deleteContact(contactId, new DeleteContactInterface() {
            @Override
            public void onSuccess() {
                String msg = "Contact deleted successfully";
                promise.resolve(msg);
            }

            @Override
            public void onFail(MobileError error) {
                String msg = "error";
                promise.reject("404", msg);
            }
        });
    }

    @ReactMethod
    public void initPresenceService(Promise promise) {
        presenceModule = new PresenceModule();
        presenceModule.initPresenceService(this.reactContext, mCpaas);
        JSONArray jsonArray = new JSONArray();
        for (PresenceEnums p : PresenceEnums.values()){
            jsonArray.put(p);
        }

        String enumValues = jsonArray.toString();
        promise.resolve(enumValues);
    }

    @ReactMethod
    public void fetchAllPresenceLists(Promise promise) {
        presenceModule.fetchAllPresenceLists(new GetPresenceListInterface() {
            @Override
            public void onSuccess(List<PresenceList> list) {
                Log.e("Presence List", ""+list);

                JSONArray jsonArray = new JSONArray();
                for(int i=0; i < list.size(); ++i){
                    JSONObject jsonObject = new JSONObject();
                    String getName = list.get(i).getName();
                    String getRealKey = list.get(i).getName();
                    List<Presentity> list1 = list.get(i).getPresentities();
                    try {
                        jsonObject.put("getName",getName);
                        jsonObject.put("getRealKey",getRealKey);

                        JSONArray jsonArray1 = new JSONArray();

                        for (int j=0; j<list1.size(); ++j){

                            String userId = list1.get(j).getUserId();
                            PresenceActivity presenceActivity = list1.get(j).getActivity();

                            String getActivity = presenceActivity.getActivity();
                            String getStatus = presenceActivity.getStatus();

                            JSONObject jsonObject1 = new JSONObject();
                            jsonObject1.put("userId",userId);
                            jsonObject1.put("getActivity",getActivity);
                            jsonObject1.put("getStatus",getStatus);

                            jsonArray1.put(jsonObject1);
                        }
                        jsonObject.put("getPresentities",jsonArray1);
                        jsonArray.put(jsonObject);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                //JsonObject jsonObject = getJsonOfList(list);
                String presenceList = jsonArray.toString();
                Log.e("JSON List", ""+list);
                promise.resolve(presenceList);
            }

            @Override
            public void onFail(MobileError error) {
                Log.d("CPaaS.PresenceService", "fetchAllPresenceLists is failed");
                String msg = "error";
                promise.reject("404", msg);
            }
        });
    }

    @ReactMethod
    public void fetchPresenceByClientCorrelator(Promise promise) {
        presenceModule.fetchPresenceByClientCorrelator(new FetchPresenceByClientCorrelatorInterface() {
            @Override
            public void onSuccess(PresenceSource presenceSource) {
                myPresenceSource = presenceSource;
                String presenceSourceString = "";
                if (presenceSource == null) {
                    presenceSourceString = "create";
                } else {
                    PresenceActivity presenceActivity = presenceSource.getPresenceActivity();
                    presenceSourceString = presenceActivity.getActivity();
                }

                promise.resolve(presenceSourceString);
            }

            @Override
            public void onFail(MobileError error) {
                Log.d("CPaaS.PresenceService", "fetchPresenceByClientCorrelator is failed");
                String msg = error.getErrorMessage();
                promise.reject("404", msg);
            }
        });
    }

    @ReactMethod
    public void createPresenceSource(Promise promise) {

            presenceModule.createPresenceSource(new CreatePresenceSourceInterface() {
                @Override
                public void onSuccess(PresenceSource presenceSource) {
                    Log.e("PresenceSource", presenceSource.toString());
                    myPresenceSource = presenceSource;
                    PresenceActivity presenceActivity = presenceSource.getPresenceActivity();
                    String presenceSourceString = presenceActivity.getActivity();

                    promise.resolve(presenceSourceString);
                }

                @Override
                public void onFail(MobileError error) {
                    Log.d("CPaaS.PresenceService", "fetchPresenceByClientCorrelator is failed");
                    String msg = error.getErrorMessage();
                    promise.reject("404", msg);
                }
            });

    }

    @ReactMethod
    public void updatePresenceSource(String activity,String status,String other,Promise promise) {
        Log.e("PresenceSource Value",""+myPresenceSource);
        if(myPresenceSource == null){
            presenceModule.createPresenceSource(new CreatePresenceSourceInterface() {
                @Override
                public void onSuccess(PresenceSource presenceSource) {
                    Log.e("PresenceSource", presenceSource.toString());
                    myPresenceSource = presenceSource;
                    PresenceActivity presenceActivity = presenceSource.getPresenceActivity();
                    String presenceSourceString = presenceActivity.getActivity();

                    promise.resolve(presenceSourceString);
                }

                @Override
                public void onFail(MobileError error) {
                    Log.d("CPaaS.PresenceService", "fetchPresenceByClientCorrelator is failed");
                    String msg = error.getErrorMessage();
                    promise.reject("404", msg);
                }
            });
        }else{
            String sourceID = myPresenceSource.getSourceId();
            int duration = 86400;

            PresenceEnums activityValue = PresenceEnums.valueOf(activity.toUpperCase());

            presenceModule.updatePrescnceSource(sourceID, duration, activityValue, status, other, new CreatePresenceSourceInterface() {
                @Override
                public void onSuccess(PresenceSource presenceSource) {
                    myPresenceSource = presenceSource;
                    Log.e("PresenceSource", presenceSource.toString());
                    PresenceActivity presenceActivity = presenceSource.getPresenceActivity();
                    String presenceSourceString = presenceActivity.getActivity();

                    promise.resolve(presenceSourceString);
                }

                @Override
                public void onFail(MobileError error) {
                    Log.d("CPaaS.PresenceService", "fetchPresenceByClientCorrelator is failed");
                    String msg = error.getErrorMessage();
                    promise.reject("404", msg);
                }
            });

        }


    }



    @ReactMethod
    public void createPresenceList(String listId,Promise promise) {
        presenceModule.createPresenceList(listId, new CreatePresenceListInterface() {
            @Override
            public void onSuccess(PresenceList presenceList) {
                String presenceListString = presenceList.toString();
                promise.resolve(presenceListString);
            }

            @Override
            public void onFail(MobileError error) {
                Log.d("CPaaS.PresenceService", "createPresenceList is failed");
                String msg = error.getErrorMessage();
                promise.reject("404", msg);
            }
        });
    }

    public JsonObject getJsonOfList(List<Contact> list) {
        Gson gson = new Gson();
        // convert your list to json
        String jsonContactList = gson.toJson(list);

        JsonParser parser = new JsonParser();
        JsonObject json = (JsonObject) parser.parse(jsonContactList);

        return json;
    }

    public interface CpassListner {

        void onCpassSuccess();

        void onCpassFail();
    }

    public interface ChatListner {

        void inboundChatMessageReceived(InboundMessage inboundMessage);

        void chatDeliveryStatusChanged(String s, String s1, String s2);

        void outboundChatMessageSent(OutboundMessage outboundMessage);
    }

    public interface SmsListner {

        void inboundSMSMessageReceived(InboundMessage inboundMessage);

        void SMSDeliveryStatusChanged(String s, String s1, String s2);

        void outboundSMSMessageSent(OutboundMessage outboundMessage);
    }

    public interface ChatMessageListner {
        void onSuccess();

        void onFail(MobileError error);
    }

    public interface SmsMessageListner {
        void onSuccess();

        void onFail(MobileError error);
    }


}


