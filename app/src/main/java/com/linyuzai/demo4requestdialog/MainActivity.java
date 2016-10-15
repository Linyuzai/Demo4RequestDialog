package com.linyuzai.demo4requestdialog;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.linyuzai.requestdialog.RequestDialog;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button mSuccessButton;
    Button mFailureButton;
    RequestDialog dialog;

    boolean isSuccess;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSuccessButton = (Button) findViewById(R.id.success_button);
        mFailureButton = (Button) findViewById(R.id.failure_button);
        mSuccessButton.setOnClickListener(this);
        mFailureButton.setOnClickListener(this);

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
                                if (isSuccess)
                                    dialog.successDialog();
                                else
                                    dialog.failureDialog();
                                isSuccess = true;
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
        switch (view.getId()) {
            case R.id.success_button:
                isSuccess = true;
                break;
            case R.id.failure_button:
                isSuccess = false;
                break;
        }
        dialog.warningDialog(0);
    }
}
