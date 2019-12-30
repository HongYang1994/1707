package com.bawei.a1707;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import com.bawei.a1707.api.Api;
import com.bawei.a1707.utils.OkHttpUtils;
import com.uuzuche.lib_zxing.activity.CodeUtils;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.ed)
    EditText ed;
    @BindView(R.id.img)
    ImageView img;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        CodeUtils.init(this);
    }
    @OnClick(R.id.btn_sc)
    public void sc_btn(View view){
        if (TextUtils.isEmpty(ed.getText().toString())){
            Toast.makeText(this, "请输入关键字!", Toast.LENGTH_SHORT).show();
            return;
        }
        Bitmap bitmap = CodeUtils.createImage(ed.getText().toString(), 400, 400, null);
        img.setImageBitmap(bitmap);
    }
    @OnClick(R.id.btn_sm)
    public void sm_btn(View view){
        CodeUtils.analyzeByCamera(this);
    }
    @OnClick(R.id.btn_xc)
    public void xc_btn(View view){
        CodeUtils.analyzeByPhotos(this);
    }

    @OnClick(R.id.btn_get)
    public void get_btn(View view){
        OkHttpUtils.getInstance().doGet(Api.BANNER_URL, new OkHttpUtils.OkHttpCallback() {
            @Override
            public void onSuccess(String string) {
                Toast.makeText(MainActivity.this, string, Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onError(Throwable throwable) {
                Toast.makeText(MainActivity.this, "请检查网络!", Toast.LENGTH_SHORT).show();
            }
        });
    }
    @OnClick(R.id.btn_post)
    public void post_btn(View view){

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        CodeUtils.onActivityResult(this, requestCode, resultCode, data, new CodeUtils.AnalyzeCallback() {
            @Override
            public void onAnalyzeSuccess(Bitmap mBitmap, String result) {
                Toast.makeText(MainActivity.this, result, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAnalyzeFailed() {
                Toast.makeText(MainActivity.this, "解析失败", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
