package com.quangtd.photoeditor.view.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.View;

import com.quangtd.photoeditor.R;
import com.quangtd.photoeditor.presenter.PresenterBase;
import com.quangtd.photoeditor.view.iface.IViewBase;

import java.lang.reflect.ParameterizedType;

/**
 * QuangTD on 10/5/2017.
 */

public class FragmentBase<P extends PresenterBase> extends Fragment {
    public String TAG = getClass().getSimpleName();
    private P viewPresenter;
    private ProgressDialog mProgressDialog;

    public FragmentBase() {
    }

    protected P getPresenter(IViewBase iface) {
        try {
            if (this.viewPresenter == null) {
                String e = ((Class) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0]).getName();
                Class classDefinition = Class.forName(e);
                this.viewPresenter = (P) classDefinition.newInstance();
                this.viewPresenter.setIFace(iface);
                this.viewPresenter.onInit();
                return this.viewPresenter;
            }
        } catch (InstantiationException | IllegalAccessException | java.lang.InstantiationException | ClassNotFoundException var4) {
            var4.printStackTrace();
        }
        return this.viewPresenter;
    }

    public void startActivity(Class<?> cls) {
        Intent intent = new Intent(getContext(), cls);
        startActivity(intent);
    }

   /* public void replaceFragment(FragmentBase fragment, boolean addToBackStack) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        if (addToBackStack) {
            transaction.addToBackStack(fragment.getClass().getName());
        }
        transaction.replace(R.id.content, fragment);
        transaction.commit();
    }*/

    /*public void addFragment(FragmentBase fragment, boolean addToBackStack) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        if (addToBackStack) {
            transaction.addToBackStack(fragment.getClass().getName());
        }
        transaction.add(R.id.content, fragment);
        transaction.commit();
    }*/

    public boolean popFragment() {
        if (getFragmentManager().getBackStackEntryCount() >= 0) {
            getFragmentManager().popBackStackImmediate();
            return true;
        }
        return false;
    }

    public void handleBackPress(View view) {
        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener((v, keyCode, event) -> keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP && popFragment());
    }

    public void showProgressDialog() {
        if (null == mProgressDialog) {
            final int version = Build.VERSION.SDK_INT;
            if (version > 17) {
                mProgressDialog = new ProgressDialog(this.getActivity(), R.style.DialogStyleTransparent);
            } else {
                mProgressDialog = new ProgressDialog(this.getActivity());
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
