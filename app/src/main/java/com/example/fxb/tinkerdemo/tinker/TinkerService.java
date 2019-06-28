package com.example.fxb.tinkerdemo.tinker;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;

import java.io.File;

/**
 * Created by fxb on 2019/6/27.
 * 1、从服务器下载patch文件
 * 2、使用TinkerManager完成patch文件加载
 * 3、patch文件会在下次启动时生效
 */

public class TinkerService extends Service {
    private static final String TAG = TinkerService.class.getSimpleName();
    private static final String FILE_END = ".apk";
    private static final int DOWNLOAD_PATCH = 0x01;
    private static final int UPDATE_PATCH = 0x02;

    private String mPatchFileDir; //patch要保存的文件夹
    private String mFilePatch;  //patch要保存的路径
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case UPDATE_PATCH:
                    checkPatchUpdate();
                    break;
                case DOWNLOAD_PATCH:
                    downloadPatch();
                    break;
            }

        }
    };
    @Override
    public void onCreate() {
        super.onCreate();
        init();
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        //检查是否有patch文件更新
        mHandler.sendEmptyMessage(UPDATE_PATCH);

        //START_NOT_STICKY 代表被系统回收后不会自动重启
        return START_NOT_STICKY;
    }
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    //完成文件目录构造
    private void init() {
        mPatchFileDir = getExternalCacheDir().getAbsolutePath() + "/tpatch/";
        File patchDir = new File(mPatchFileDir);
        try {
            if (patchDir == null && !patchDir.exists()) {
                patchDir.mkdir();
            }
        } catch (Exception e) {
            e.printStackTrace();
            stopSelf();
        }
    }
    //检查服务器是否有patch
    private void checkPatchUpdate() {
        //这里就是网络请求
        /**
         * 1、先进行网络请求，如果失败 就终止（stopSelf();），如果成功就判断版本号，开始下载
         */
        mHandler.sendEmptyMessage(DOWNLOAD_PATCH);
    }
    private void downloadPatch() {
        //这里是网络下载   先模拟 这个是下载的文件名称
        mFilePatch =  mPatchFileDir.concat("bing").concat(FILE_END);

        //下载成功之后  加载补丁
        TinkerManager.loadPath(mFilePatch);
    }
}
