//package com.awesomeproject;
//
//import android.util.Log;
//import android.widget.Toast;
//
//import com.facebook.react.bridge.ReactApplicationContext;
//import com.facebook.react.bridge.ReactContextBaseJavaModule;
//import com.facebook.react.bridge.ReactMethod;
//import com.facebook.react.modules.core.DeviceEventManagerModule;
//import com.rbbn.cpaas.mobile.CPaaS;
//import com.rbbn.cpaas.mobile.authentication.api.Authentication;
//import com.rbbn.cpaas.mobile.authentication.api.ConnectionCallback;
//import com.rbbn.cpaas.mobile.call.api.CallApplicationListener;
//import com.rbbn.cpaas.mobile.call.api.CallInterface;
//import com.rbbn.cpaas.mobile.call.api.CallState;
//import com.rbbn.cpaas.mobile.call.api.IncomingCallInterface;
//import com.rbbn.cpaas.mobile.call.api.MediaAttributes;
//import com.rbbn.cpaas.mobile.call.api.OutgoingCallInterface;
//import com.rbbn.cpaas.mobile.utilities.Configuration;
//import com.rbbn.cpaas.mobile.utilities.Globals;
//import com.rbbn.cpaas.mobile.utilities.exception.MobileError;
//import com.rbbn.cpaas.mobile.utilities.exception.MobileException;
//import com.rbbn.cpaas.mobile.utilities.logging.LogLevel;
//import com.rbbn.cpaas.mobile.utilities.services.ServiceInfo;
//import com.rbbn.cpaas.mobile.utilities.services.ServiceType;
//import com.rbbn.cpaas.mobile.utilities.webrtc.CodecSet;
//import com.rbbn.cpaas.mobile.utilities.webrtc.ICEServers;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//
//public class ToastModule extends ReactContextBaseJavaModule {
//    ReactApplicationContext context;
//    String TAG = "React";
//
//    public ToastModule(ReactApplicationContext reactContext) {
//        super(reactContext);
//        this.context = reactContext;
//    }
//
//    //Mandatory function getName that specifies the module name
//    @Override
//    public String getName() {
//        return "ToastModule";
//    }
//
//    //Custom function that we are going to export to JS
////    @ReactMethod
//    public void showToast(String message) {
//        Toast.makeText(getReactApplicationContext(), message, Toast.LENGTH_SHORT).show();
//    }
//
//
//    @ReactMethod
//    public void initKandyService(String baseUrl, String mAccessToken, String idToken) {
//        Log.e("baseUrl", baseUrl);
//        Log.e("mAccessToken", mAccessToken);
//        Log.e("idToken", idToken);
//
//
//        Configuration.getInstance().setUseSecureConnection(true);
//        ICEServers iceServers = new ICEServers();
//        iceServers.addICEServer("turns:turn-ucc-1.genband.com:443?transport=tcp");
//        iceServers.addICEServer("turns:turn-ucc-2.genband.com:443?transport=tcp");
//        iceServers.addICEServer("stun:turn-ucc-1.genband.com:3478?transport=udp");
//        iceServers.addICEServer("stun:turn-ucc-2.genband.com:3478?transport=udp");
//        Configuration.getInstance().setICEServers(iceServers);
//
//        CodecSet codecSet = new CodecSet();
//        Configuration.getInstance().setPreferredCodecSet(codecSet);
//        Configuration.getInstance().setRestServerUrl(baseUrl);
//        Configuration.getInstance().setLogLevel(LogLevel.TRACE);
//        Globals.setApplicationContext(context);
//
//        int lifetime = 3600; //in seconds
//        List<ServiceInfo> services = new ArrayList<>();
//        services.add(new ServiceInfo(ServiceType.CALL, true));
//
//        CPaaS mCpaas = new CPaaS(services);
//        Authentication authentication = mCpaas.getAuthentication();
//        authentication.setToken(mAccessToken);
//        CpassListner cpassListner = new CpassListner() {
//            @Override
//            public void onCpassSuccess() {
//                showToast("Success");
//            }
//
//            @Override
//            public void onCpassFail() {
//                showToast("Fail");
//            }
//        };
//        try {
//            authentication.connect(idToken, lifetime, new ConnectionCallback() {
//                @Override
//                public void onSuccess(String channelInfo) {
//                    Log.d("CpassSubscribe", channelInfo);
//                    cpassListner.onCpassSuccess();
//                }
//
//                @Override
//                public void onFail(MobileError mobileError) {
//                    if (mobileError != null)
//                        Log.d("CpassSubscribe", mobileError.getErrorMessage());
//                    else
//                        Log.d("CpassSubscribe", "error");
//                    cpassListner.onCpassFail();
//                }
//            });
//            CallApplicationListener callApplicationListener = new CallApplicationListener() {
//                @Override
//                public void callAdditionalInfoChanged(CallInterface call, Map<String, String> events) {
//
//                }
//
//                @Override
//                public void errorReceived(CallInterface call, MobileError error) {
//
//                }
//
//                @Override
//                public void errorReceived(MobileError error) {
//
//                }
//
//                @Override
//                public void establishCallSucceeded(OutgoingCallInterface call) {
//                    Toast.makeText(context, "establishCallSucceeded", Toast.LENGTH_SHORT).show();
//                }
//
//                @Override
//                public void establishCallFailed(OutgoingCallInterface call, MobileError error) {
//                    Toast.makeText(context, "establishCallFailed " + error.getErrorMessage(), Toast.LENGTH_SHORT).show();
//                }
//
//                @Override
//                public void acceptCallSucceed(IncomingCallInterface call) {
//
//                }
//
//                @Override
//                public void acceptCallFailed(IncomingCallInterface call, MobileError error) {
//
//                }
//
//                @Override
//                public void rejectCallSucceeded(IncomingCallInterface incomingCall) {
//                    Log.d(TAG, "rejectCallSucceeded: " + incomingCall.getId());
//                }
//
//                @Override
//                public void rejectCallFailed(IncomingCallInterface call, MobileError error) {
//                    Log.d(TAG, "rejectCallFailed: " + call.getId());
//                    Toast.makeText(context, "rejectCallFailed " + error.getErrorMessage(), Toast.LENGTH_SHORT).show();
//                }
//
//                @Override
//                public void ignoreSucceed(IncomingCallInterface call) {
//
//                }
//
//                @Override
//                public void ignoreFailed(IncomingCallInterface call, MobileError error) {
//                    Toast.makeText(context, "ignoreFailed " + error.getErrorMessage(), Toast.LENGTH_SHORT).show();
//                }
//
//                @Override
//                public void forwardCallSucceeded(IncomingCallInterface incomingCallInterface) {
//
//                }
//
//                @Override
//                public void forwardCallFailed(IncomingCallInterface incomingCallInterface, MobileError mobileError) {
//
//                }
//
//                @Override
//                public void videoStopSucceed(CallInterface call) {
//                    Log.d(TAG, "videoStopSucceed: " + call.getId());
//                }
//
//                @Override
//                public void videoStopFailed(CallInterface call, MobileError error) {
//                    Log.d(TAG, "videoStopFailed: " + call.getId());
//                    Toast.makeText(context, "videoStopFailed " + error.getErrorMessage(), Toast.LENGTH_SHORT).show();
//                }
//
//                @Override
//                public void videoStartSucceed(CallInterface call) {
//                    Log.d(TAG, "videoStartSucceed: " + call.getId());
//                }
//
//                @Override
//                public void videoStartFailed(CallInterface call, MobileError error) {
//                    Log.d(TAG, "videoStartFailed: " + call.getId());
//                    Toast.makeText(context, "videoStartFailed " + error.getErrorMessage(), Toast.LENGTH_SHORT).show();
//                }
//
//                @Override
//                public void muteCallSucceed(CallInterface call) {
//                }
//
//                @Override
//                public void muteCallFailed(CallInterface call, MobileError error) {
//                    Toast.makeText(context, "muteCallFailed " + error.getErrorMessage(), Toast.LENGTH_SHORT).show();
//                }
//
//                @Override
//                public void unMuteCallSucceed(CallInterface call) {
//                }
//
//                @Override
//                public void unMuteCallFailed(CallInterface call, MobileError error) {
//                    Toast.makeText(context, "unMuteCallFailed " + error.getErrorMessage(), Toast.LENGTH_SHORT).show();
//                }
//
//                @Override
//                public void holdCallSucceed(CallInterface call) {
//                    Log.d(TAG, "holdCallSucceed: " + call.getId());
//                }
//
//                @Override
//                public void holdCallFailed(CallInterface call, MobileError error) {
//                    Log.d(TAG, "holdCallFailed: " + call.getId());
//                    Toast.makeText(context, "holdCallFailed " + error.getErrorMessage(), Toast.LENGTH_SHORT).show();
//                }
//
//                @Override
//                public void unHoldCallSucceed(CallInterface call) {
//                    Log.d(TAG, "unHoldCallSucceed: " + call.getId());
//                }
//
//                @Override
//                public void unHoldCallFailed(CallInterface call, MobileError error) {
//                    Log.d(TAG, "unHoldCallFailed: " + call.getId());
//                    Toast.makeText(context, "unHoldCallFailed " + error.getErrorMessage(), Toast.LENGTH_SHORT).show();
//                }
//
//                @Override
//                public void endCallSucceeded(CallInterface call) {
//                    Log.d(TAG, "endCallSucceeded: " + call.getId());
//                }
//
//                @Override
//                public void endCallFailed(CallInterface call, MobileError error) {
//                    Log.d(TAG, "endCallFailed: " + call.getId());
//                    Toast.makeText(context, "endCallFailed " + error.getErrorMessage(), Toast.LENGTH_SHORT).show();
//                }
//
//                @Override
//                public void ringingFeedbackSucceeded(IncomingCallInterface call) {
//
//                }
//
//                @Override
//                public void ringingFeedbackFailed(IncomingCallInterface call, MobileError error) {
//                    Toast.makeText(context, "ringingFeedbackFailed " + error.getErrorMessage(), Toast.LENGTH_SHORT).show();
//                }
//
//                @Override
//                public void transferCallSucceed(CallInterface callInterface) {
//
//                }
//
//                @Override
//                public void transferCallFailed(CallInterface callInterface, MobileError mobileError) {
//
//                }
//
//                @Override
//                public void notifyCallProgressChange(CallInterface call) {
//
//                }
//
//                @Override
//                public void incomingCall(IncomingCallInterface call) {
//                    context
//                            .getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
//                            .emit("CallReceived", "Call Received");
//                    acceptIncomingCall(call);
//                    Log.i(TAG, "incomingCall: id is " + call.getId());
//                }
//
//                @Override
//                public void callStatusChanged(CallInterface callInterface, CallState callState) {
//                    Log.d(TAG, "callStatusChanged: Call state is " + callState.getType() + " call id is " + callInterface.getId());
//                }
//
//                @Override
//                public void mediaAttributesChanged(CallInterface callInterface, MediaAttributes mediaAttributes) {
//                    Log.d(TAG, "mediaAttributesChanged: MediaAttributes are " + mediaAttributes.toString() + " call id is " + callInterface.getId());
//                }
//
//            };
//
//            mCpaas.getCallService().setCallApplicationListener(callApplicationListener);
//        } catch (MobileException e) {
//            e.printStackTrace();
//        }
//
//    }
//
//
//    private void acceptIncomingCall(IncomingCallInterface call) {
//        Toast.makeText(context, "Call Received", Toast.LENGTH_SHORT).show();
//
//        // For video call
//        call.acceptCall(true);
//
//        // For audio call
//        // call.acceptCall(false);
//    }
//
//    public interface CpassListner {
//
//        void onCpassSuccess();
//
//        void onCpassFail();
//    }
//}