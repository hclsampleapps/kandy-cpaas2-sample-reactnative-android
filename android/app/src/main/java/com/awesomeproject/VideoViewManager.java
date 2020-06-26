//package com.awesomeproject;
//
//import android.net.Uri;
//import android.widget.VideoView;
//
//import androidx.annotation.NonNull;
//
//import com.facebook.react.uimanager.SimpleViewManager;
//import com.facebook.react.uimanager.ThemedReactContext;
//import com.facebook.react.uimanager.annotations.ReactProp;
//
//public class VideoViewManager extends SimpleViewManager<VideoView> {
//    public static String NAME = "VideoView";
//
//    /**
//     * Returns the name of the main component registered from JavaScript. This is used to schedule
//     * rendering of the component.
//     */
//    @Override
//    public String getName() {
//        return NAME;
//    }
//
//    @NonNull
//    @Override
//    protected VideoView createViewInstance(@NonNull ThemedReactContext reactContext) {
//        return new VideoView(reactContext);
//    }
//
//    @ReactProp(name = "url")
//    public void setVideoPath(VideoView videoView, String urlPath) {
//        Uri uri = Uri.parse(urlPath);
//        videoView.setVideoURI(uri);
//        videoView.start();
//    }
//
//}
