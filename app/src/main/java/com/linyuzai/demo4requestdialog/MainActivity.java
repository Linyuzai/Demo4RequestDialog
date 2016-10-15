package com.linyuzai.demo4requestdialog;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.linyuzai.requestdialog.RequestDialog;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button button;
    RequestDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(this);

        dialog = new RequestDialog(this)
                .setTitle("title")
                .setWarningSetting("warning message")
                .setProgressSetting("progress")
                .setSuccessSetting("success")
                .setFailureSetting("failure")
                .setOnRequestCallback(new RequestDialog.OnRequestCallback() {
                    @Override
                    public void onCancel(int position) {

                    }

                    @Override
                    public void onRequest(int position) {
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                dialog.successDialog();
                            }
                        }, 2000);
                    }

                    @Override
                    public void onFinish(int position) {

                    }
                });
    }

    @Override
    public void onClick(View view) {
        dialog.warningDialog(0);
    }
}
