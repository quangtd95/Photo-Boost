package com.quangtd.photoeditor.view.fragment;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.quangtd.photoeditor.R;
import com.quangtd.photoeditor.presenter.PresenterBase;
import com.quangtd.photoeditor.view.iface.IViewBase;

import java.lang.reflect.ParameterizedType;

/**
 * QuangTD on 10/5/2017.
 */

public class FragmentBase<P extends PresenterBase> extends Fragment {
    private P viewPresenter;

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
        } catch (InstantiationException var4) {
            var4.printStackTrace();
        } catch (IllegalAccessException var5) {
            var5.printStackTrace();
        } catch (ClassNotFoundException var6) {
            var6.printStackTrace();
        } catch (java.lang.InstantiationException var7) {
            var7.printStackTrace();
        }
        return this.viewPresenter;
    }

    public void startActivity(Class<?> cls) {
        Intent intent = new Intent(getContext(), cls);
        startActivity(intent);
    }

    public void replaceFragment(FragmentBase fragment, boolean addToBackStack) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        if (addToBackStack) {
            transaction.addToBackStack(fragment.getClass().getName());
        }
        transaction.replace(R.id.content, fragment);
        transaction.commit();
    }

    public void addFragment(FragmentBase fragment, boolean addToBackStack) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        if (addToBackStack) {
            transaction.addToBackStack(fragment.getClass().getName());
        }
        transaction.add(R.id.content, fragment);
        transaction.commit();
    }

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
        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
                    return popFragment();
                }
                return false;
            }
        });
    }

}