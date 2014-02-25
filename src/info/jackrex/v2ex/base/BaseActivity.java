/*
 * Copyright (c) 2014.
 * Jackrex
 */

package info.jackrex.v2ex.base;

import info.jackrex.v2ex.R;
import info.jackrex.v2ex.R.id;
import info.jackrex.v2ex.ui.HeaderView;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

/**
 * Created by Jackrex on 2/18/14.
 */
public abstract class BaseActivity extends Activity {

    protected String TAG = BaseActivity.class.getSimpleName();
    protected String currentActivityName = TAG;
    protected HeaderView headerView;
    protected String headTitle = "";
    
    protected AlertDialog mAlertDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setLayoutAndInitView();
        headerView = (HeaderView) findViewById(R.id.headerview);
        
        headerView.tvHeaderTitle.setText(headTitle);
        headerView.btnLeft.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
    }

    public abstract void setLayoutAndInitView();


    /**
     * 在父类中统一使用友盟统计
     * */
    @Override
    protected void onResume() {
        super.onResume();
      //  MobclickAgent.onResume(this);
    }


    @Override
    protected void onPause() {
        super.onPause();
        //MobclickAgent.onPause(this);

    }


    /**
     *
     * show AlertDialog
     * @param title
     * @param message
     * @return
     */
    protected AlertDialog showAlertDialog(String title, String message) {
        mAlertDialog = new AlertDialog.Builder(this).setTitle(title)
                .setMessage(message).show();
        return mAlertDialog;
    }


    protected AlertDialog showAlertDialog(String pTitle, String pMessage,
                                          DialogInterface.OnClickListener OkClickListener,
                                          DialogInterface.OnClickListener CancelClickListener,
                                          DialogInterface.OnDismissListener DismissListener) {
        mAlertDialog = new AlertDialog.Builder(this)
                .setTitle(pTitle)
                .setMessage(pMessage)
                .setPositiveButton(android.R.string.ok, OkClickListener)
                .setNegativeButton(android.R.string.cancel,
                        CancelClickListener).show();
        if (DismissListener != null) {
            mAlertDialog.setOnDismissListener(DismissListener);
        }
        return mAlertDialog;
    }

    protected AlertDialog showAlertDialog(String pTitle, String pMessage,
                                          String pPositiveButtonLabel, String NegativeButtonLabel,
                                          DialogInterface.OnClickListener OkClickListener,
                                          DialogInterface.OnClickListener CancelClickListener,
                                          DialogInterface.OnDismissListener DismissListener) {
        mAlertDialog = new AlertDialog.Builder(this).setTitle(pTitle)
                .setMessage(pMessage)
                .setPositiveButton(pPositiveButtonLabel, OkClickListener)
                .setNegativeButton(NegativeButtonLabel, CancelClickListener)
                .show();
        if (DismissListener != null) {
            mAlertDialog.setOnDismissListener(DismissListener);
        }
        return mAlertDialog;
    }


    /**
     * 获取一个progressDialog
     * @param title
     * @param message
     * @param cancelClickListener
     * @return
     */
    protected ProgressDialog showProgressDialog(String title, String message,
                                                DialogInterface.OnCancelListener cancelClickListener) {
        mAlertDialog = ProgressDialog.show(this, title, message, true, true);
        mAlertDialog.setOnCancelListener(cancelClickListener);
        return (ProgressDialog) mAlertDialog;
    }


    /**
     * 显示Toast
     * @param ResId
     */
    protected void showShortToast(int ResId) {
        showShortToast(getString(ResId));
    }

    protected void showShortToast(String Msg) {
        Toast.makeText(this, Msg, Toast.LENGTH_SHORT).show();
    }


    protected void showLongToast(String Msg) {
        Toast.makeText(this, Msg, Toast.LENGTH_LONG).show();
    }

    protected void showLongToast(int ResId) {
        Toast.makeText(this, getString(ResId), Toast.LENGTH_LONG).show();
    }


    /**
     *
     * use  certain class to open Activity
     * @param clazz
     */
    protected void openActivity(Class<?> clazz) {
        openActivity(clazz, null);
    }

    protected void openActivity(Class<?> clazz, Bundle pBundle) {
        Intent intent = new Intent(this, clazz);
        if (pBundle != null) {
            intent.putExtras(pBundle);
        }
        startActivity(intent);
    }


    /**
     *
     * use action open activity
     * @param action
     */
    protected void openActivity(String action) {
        openActivity(action, null);
    }

    protected void openActivity(String pAction, Bundle pBundle) {
        Intent intent = new Intent(pAction);
        if (pBundle != null) {
            intent.putExtras(pBundle);
        }
        startActivity(intent);
    }




    /**
     * hide the keyboard
     * @param view
     *
     */
    protected void hideKeyboard(View view) {

        InputMethodManager imm = (InputMethodManager) this
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);
    }






}
