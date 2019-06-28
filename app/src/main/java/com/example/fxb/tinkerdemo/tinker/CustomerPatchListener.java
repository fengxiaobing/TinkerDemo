package com.example.fxb.tinkerdemo.tinker;

import android.content.Context;
import android.widget.Toast;

import com.tencent.tinker.lib.listener.DefaultPatchListener;
import com.tencent.tinker.loader.shareutil.ShareConstants;

/**
 * Created by fxb on 2019/6/27.
 * 校验patch文件是否合法
 * 启动service去安装patch文件
 */

public class CustomerPatchListener extends DefaultPatchListener {

    private String crrentMD5;

    public void setCrrentMD5(String md5Value) {
        this.crrentMD5 = md5Value;
    }

    public CustomerPatchListener(Context context) {
        super(context);
    }

    @Override
    public int onPatchReceived(String path) {
        //这里可以是安装patch的时候弹出对话框进行提醒，但一般安装时静默
        return super.onPatchReceived(path);
    }

    @Override
    protected int patchCheck(String path, String patchMd5) {
        //这里可以判断md5
//        if(false){
//            return ShareConstants.ERROR_PATCH_DISABLE;
//        }
        Toast.makeText(CustomerTinkerLike.getContext(), super.patchCheck(path, patchMd5)+"", Toast.LENGTH_SHORT).show();
//        return super.patchCheck(path, patchMd5);
        return super.patchCheck(path, patchMd5);

    }
}
