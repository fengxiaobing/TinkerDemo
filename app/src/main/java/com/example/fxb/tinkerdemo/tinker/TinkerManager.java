package com.example.fxb.tinkerdemo.tinker;

import android.content.Context;

import com.tencent.tinker.entry.ApplicationLike;
import com.tencent.tinker.lib.patch.AbstractPatch;
import com.tencent.tinker.lib.patch.UpgradePatch;
import com.tencent.tinker.lib.reporter.DefaultLoadReporter;
import com.tencent.tinker.lib.reporter.DefaultPatchReporter;
import com.tencent.tinker.lib.reporter.LoadReporter;
import com.tencent.tinker.lib.reporter.PatchReporter;
import com.tencent.tinker.lib.tinker.Tinker;
import com.tencent.tinker.lib.tinker.TinkerInstaller;

/**
 * Created by fxb on 2019/6/24.
 * 对Tinker的所有APi进行封装
 */

public class TinkerManager {
    private static boolean isInstalled = false;

    private static ApplicationLike mAppLike;
    private static CustomerPatchListener customerPatchListener;

    /**
     * 由外部调用，初始化
     * @param applicationLike
     */
    public static void installTinker(ApplicationLike applicationLike){
        mAppLike = applicationLike;
        if(isInstalled){
            return;
        }

        customerPatchListener = new CustomerPatchListener(getApplicationContext());

//        TinkerInstaller.install(mAppLike);//完成初始化

        LoadReporter loadReporter = new DefaultLoadReporter(getApplicationContext());
        PatchReporter patchReporter = new DefaultPatchReporter(getApplicationContext());

        AbstractPatch upgradePatchProcessor = new UpgradePatch();
        TinkerInstaller.install(mAppLike,
                loadReporter,
                patchReporter,
                customerPatchListener,
                CustomerResultService.class,
                upgradePatchProcessor);//完成初始化


        isInstalled = true;
    }

    //完成patch文件的加载
    public static void loadPath(String patch){
       if(Tinker.isTinkerInstalled()){
           //由服务器传入的正确的md5值
           customerPatchListener.setCrrentMD5("");
           TinkerInstaller.onReceiveUpgradePatch(getApplicationContext(),patch);
       }
    }

    /**
     * 通过ApplicationLike获取Context
     * @return
     */
    private static Context getApplicationContext(){
        if(mAppLike!=null){
            return  mAppLike.getApplication();
        }
        return null;
    }

}
