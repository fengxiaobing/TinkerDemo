package com.example.fxb.tinkerdemo;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.fxb.tinkerdemo.tinker.TinkerManager;
import com.example.fxb.tinkerdemo.tinker.TinkerService;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    private static final String FILE_END = ".apk";
    private String mPatchDir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startAllServide();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        11);

            }else {
            }
        }


        mPatchDir = getExternalCacheDir().getAbsolutePath()+"/tpatch/";
        File file = new File(mPatchDir);
        if(file == null || !file.exists()){
            file.mkdir();
        }
    }

    private void startAllServide() {
        //启动tinker Service
        Intent intent = new Intent(MainActivity.this, TinkerService.class);
        startService(intent);
    }

    public void makebug(View view) {
        Toast.makeText(this, "修复成功", Toast.LENGTH_SHORT).show();
    }

    public void fixbug(View view) {
        TinkerManager.loadPath(getPatchName());
    }

    private String getPatchName() {
        return mPatchDir.concat("bing").concat(FILE_END);
    }

}
