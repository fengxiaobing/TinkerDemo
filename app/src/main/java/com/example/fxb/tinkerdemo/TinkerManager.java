package com.example.fxb.tinkerdemo;

import android.content.Context;

import com.tencent.tinker.lib.tinker.Tinker;
import com.tencent.tinker.lib.tinker.TinkerInstaller;
import com.tencent.tinker.loader.app.ApplicationLike;

/**
 * Created by fxb on 2019/6/24.
 * 对Tinker的所有APi进行封装
 */

public class TinkerManager {
    private static boolean isInstalled = false;

    private static ApplicationLike mAppLike;


    /**
     * 由外部调用，初始化
     * @param applicationLike
     */
    public static void installTinker(ApplicationLike applicationLike){
        mAppLike = applicationLike;
        if(isInstalled){
            return;
        }
        TinkerInstaller.install(mAppLike);//完成初始化
        isInstalled = true;
    }

    //完成patch文件的加载
    public static void loadPath(String patch){
       if(Tinker.isTinkerInstalled()){
           TinkerInstaller.onReceiveUpgradePatch(getApplicationContext(),patch);
       }
    }

    /**
     * 通过ApplicationLike获取Context
     * @return
     */
    private static Context getApplicationContext(){
        if(mAppLike!=null){
            return  mAppLike.getApplication().getApplicationContext();
        }
        return null;
    }

}
