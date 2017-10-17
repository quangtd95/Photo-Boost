package com.quangtd.photoeditor.model.cloud;

import android.app.Activity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.quangtd.photoeditor.utils.LogUtils;

/**
 * QuangTD on 10/17/2017.
 */

public class FireBaseUtils {
    private final String TAG = getClass().getSimpleName();
    private static FireBaseUtils instance;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private final FirebaseStorage fireBaseStorage;
    private final StorageReference storageReference;

    public synchronized static FireBaseUtils getInstance() {
        if (instance == null) instance = new FireBaseUtils();
        return instance;
    }

    public FireBaseUtils() {
        fireBaseStorage = FirebaseStorage.getInstance();
        storageReference = fireBaseStorage.getReference().child(ServerConst.APP_NAME);
        mAuth = FirebaseAuth.getInstance();
    }

    public void signIn(Activity activity) {
        LogUtils.e(TAG, "signing...");
        mAuth.signInWithEmailAndPassword(ServerConst.USER_NAME, ServerConst.PASSWORD).addOnCompleteListener(activity, task -> {
            if (task.isSuccessful()) {
                LogUtils.e(TAG, "signInWithEmail:success");
                mUser = mAuth.getCurrentUser();
            } else {
                LogUtils.e(TAG, "signInWithEmail:failure", task.getException());
                mUser = null;
            }
        });
    }

    public void addListener(Activity activity) {
        if (mAuthListener == null) {
            mAuthListener = firebaseAuth -> {
                mUser = firebaseAuth.getCurrentUser();
                if (mUser == null) {
                    signIn(activity);
                } else {
                    //TODO:
                    LogUtils.e(TAG, "signed in " + mUser.getEmail() + "..." + mUser.getUid());

                }
            };
        }
        if (mAuthListener != null) {
            mAuth.addAuthStateListener(mAuthListener);
        }
    }

    public void removeListener() {
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }
}
