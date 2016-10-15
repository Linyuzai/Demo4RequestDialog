package com.linyuzai.requestdialog;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.view.KeyEvent;

import com.linyuzai.requestdialog.sweetalertdialog.SweetAlertDialog;


/**
 * Created by Administrator on 2016/8/5 0005.
 */
public class RequestDialog {

    public static final String TAG = RequestDialog.class.getSimpleName();

    private static final String CONFIRM = "ok";
    private static final String TRY_AGAIN = "try again";
    private static final String CANCEL = "cancel";
    private static final String PLEASE_WAIT = "please wait";

    private SweetAlertDialog dialog;
    private Context context;
    private int position;

    private OnRequestCallback callback;
    private OnDismissListener listener;

    private String dialogTitle = TAG;

    private String warningContent;
    private String warningConfirm;
    private String warningCancel;

    private String progressContent;
    private String progressCancel;

    private String successContent;
    private String successConfirm;

    private String failureContent;
    private String failureConfirm;
    private String failureCancel;

    public RequestDialog(Context context) {
        this.context = context;
    }

    public RequestDialog setTitle(String title) {
        dialogTitle = title;
        return this;
    }

    public RequestDialog setOnRequestCallback(OnRequestCallback callback) {
        this.callback = callback;
        return this;
    }

    public RequestDialog setOnDismissListener(OnDismissListener listener) {
        this.listener = listener;
        return this;
    }

    public RequestDialog setWarningSetting(String content) {
        return setWarningSetting(content, CONFIRM, CANCEL);
    }

    public RequestDialog setWarningSetting(String content, String confirm, String cancel) {
        warningContent = content;
        warningConfirm = confirm;
        warningCancel = cancel;
        return this;
    }

    public RequestDialog setProgressSetting(String content) {
        return setProgressSetting(content, PLEASE_WAIT);
    }

    public RequestDialog setProgressSetting(String content, String cancel) {
        progressContent = content;
        progressCancel = cancel;
        return this;
    }

    public RequestDialog setSuccessSetting(String content) {
        return setSuccessSetting(content, CONFIRM);
    }

    public RequestDialog setSuccessSetting(String content, String confirm) {
        successContent = content;
        successConfirm = confirm;
        return this;
    }

    public RequestDialog setFailureSetting(String content) {
        return setFailureSetting(content, TRY_AGAIN, CANCEL);
    }

    public RequestDialog setFailureSetting(String content, String confirm, String cancel) {
        failureContent = content;
        failureConfirm = confirm;
        failureCancel = cancel;
        return this;
    }

    public void warningDialog(int position) {
        this.position = position;
        dialog = new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE)
                .setTitleText(dialogTitle)
                .setContentText(warningContent)
                .setConfirmText(warningConfirm)
                .setCancelText(warningCancel)
                .setConfirmClickListener(new OnDeleteListener())
                .setCancelClickListener(new OnCancelListener());
        dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0)
                    return true;
                return false;
            }
        });
        dialog.show();
    }

    public void progressDialog() {
        dialog.setTitleText(dialogTitle)
                .setContentText(progressContent)
                .setCancelText(progressCancel)
                .setCancelClickListener(new OnNoActionListener())
                .changeAlertType(SweetAlertDialog.PROGRESS_TYPE);
    }

    public void successDialog() {
        dialog.setTitleText(dialogTitle)
                .setContentText(successContent)
                .setConfirmText(successConfirm)
                .setConfirmClickListener(new OnDeleteSuccessListener())
                .showCancelButton(false)
                .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
    }

    public void failureDialog() {
        dialog.setTitleText(dialogTitle)
                .setContentText(failureContent)
                .setConfirmText(failureConfirm)
                .setCancelText(failureCancel)
                .setConfirmClickListener(new OnDeleteListener())
                .setCancelClickListener(new OnCancelListener())
                .changeAlertType(SweetAlertDialog.ERROR_TYPE);
    }

    public void dismissDelay(long delayMillis) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                dismiss();
            }
        }, delayMillis);
    }

    public void dismiss() {
        dialog.dismissWithAnimation();
        if (listener != null)
            listener.onDismiss();
    }

    class OnNoActionListener implements SweetAlertDialog.OnSweetClickListener {

        @Override
        public void onClick(SweetAlertDialog sweetAlertDialog) {
        }
    }

    class OnCancelListener implements SweetAlertDialog.OnSweetClickListener {

        @Override
        public void onClick(SweetAlertDialog sweetAlertDialog) {
            dialog.dismissWithAnimation();
            if (callback != null)
                callback.onCancel(position);
        }
    }

    class OnDeleteListener implements SweetAlertDialog.OnSweetClickListener {

        @Override
        public void onClick(SweetAlertDialog sweetAlertDialog) {
            progressDialog();
            if (callback != null)
                callback.onRequest(position);
        }
    }

    class OnDeleteSuccessListener implements SweetAlertDialog.OnSweetClickListener {

        @Override
        public void onClick(SweetAlertDialog sweetAlertDialog) {
            dialog.dismissWithAnimation();
            if (callback != null)
                callback.onFinish(position);
        }
    }

    public interface OnRequestCallback {
        void onCancel(int position);

        void onRequest(int position);

        void onFinish(int position);
    }

    public interface OnDismissListener {
        void onDismiss();
    }
}
