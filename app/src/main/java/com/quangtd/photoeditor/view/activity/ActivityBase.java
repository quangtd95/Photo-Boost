package com.quangtd.photoeditor.view.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import com.quangtd.photoeditor.R;
import com.quangtd.photoeditor.presenter.PresenterBase;
import com.quangtd.photoeditor.view.fragment.FragmentBase;
import com.quangtd.photoeditor.view.iface.IViewBase;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;

import java.lang.reflect.ParameterizedType;

/**
 * QuangTD on 10/5/2017.
 */

@EActivity
public abstract class ActivityBase<P extends PresenterBase> extends AppCompatActivity {

    private P viewPresenter;

    public P getPresenter(IViewBase iViewBase) {
        try {
            if (this.viewPresenter == null) {
                String e = ((Class) ((ParameterizedType) this.getClass().getSuperclass().getGenericSuperclass()).getActualTypeArguments()[0]).getName();
                Log.e("TAGG", e);
                Class classDefinition = Class.forName(e);
                this.viewPresenter = (P) classDefinition.newInstance();
                this.viewPresenter.setIFace(iViewBase);
                this.viewPresenter.onInit();
                return this.viewPresenter;
            }
        } catch (InstantiationException var4) {
            var4.printStackTrace();
        } catch (IllegalAccessException var5) {
            var5.printStackTrace();
        } catch (ClassNotFoundException var6) {
            var6.printStackTrace();
        }

        return this.viewPresenter;
    }

    @AfterViews
    protected void init() {
        hideStatusAndNavigationBar();
        uiChangeListener();
    }

    ;

    public void startActivity(Class<?> cls) {
        Intent intent = new Intent(this, cls);
        startActivity(intent);
    }

    public void hideFragment(FragmentBase fragment, boolean addToBackStack) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (addToBackStack) {
            transaction.addToBackStack(fragment.getClass().getName());
        }
        transaction.hide(fragment);
        transaction.commit();
    }

    public void showFragment(FragmentBase fragment, boolean addToBackStack) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (addToBackStack) {
            transaction.addToBackStack(fragment.getClass().getName());
        }
        transaction.show(fragment);
        transaction.commit();
    }

    /*public void replaceFragment(FragmentBase fragment, boolean addToBackStack) {
        String TAB = fragment.getClass().getName();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (addToBackStack) {
            transaction.addToBackStack(TAB);
        }
        transaction.replace(R.id.content, fragment, TAB);
        transaction.commit();
        getSupportFragmentManager().executePendingTransactions();
    }

    public void addFragment(FragmentBase fragment, boolean addToBackStack) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (addToBackStack) {
            transaction.addToBackStack(fragment.getClass().getName());
        }
        transaction.add(R.id.content, fragment);
        transaction.commit();
    }*/

    public void showDialogNotify(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Notify");
        builder.setMessage(message);
        builder.setNegativeButton("OK", null);
        builder.show();
    }

    private ProgressDialog mProgressDialog;

    private void hideStatusAndNavigationBar() {
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                | View.SYSTEM_UI_FLAG_FULLSCREEN;

        if (Build.VERSION.SDK_INT >= 19) {
            uiOptions = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                    | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                    | View.SYSTEM_UI_FLAG_IMMERSIVE;
        }
        decorView.setSystemUiVisibility(uiOptions);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            hideStatusAndNavigationBar();
        }
    }

    public void uiChangeListener() {
        final View decorView = getWindow().getDecorView();
        decorView.setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {
            @Override
            public void onSystemUiVisibilityChange(int visibility) {
                if ((visibility & View.SYSTEM_UI_FLAG_FULLSCREEN) == 0) {
                    hideStatusAndNavigationBar();
                }
            }
        });
    }

    public void showProgressDialog() {
        if (null == mProgressDialog) {
            final int version = Build.VERSION.SDK_INT;
            if (version > 17) {
                mProgressDialog = new ProgressDialog(this, R.style.DialogStyleTransparent);
            } else {
                mProgressDialog = new ProgressDialog(this);
            }
            mProgressDialog.setCancelable(false);
            mProgressDialog.setCanceledOnTouchOutside(false);
        }
        if (!mProgressDialog.isShowing())
            mProgressDialog.show();
    }

    public void dismissProgressDialog() {
        if (null != mProgressDialog && mProgressDialog.isShowing())
            mProgressDialog.dismiss();
    }
}
