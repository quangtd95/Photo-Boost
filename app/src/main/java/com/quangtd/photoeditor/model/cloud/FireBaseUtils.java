package com.quangtd.photoeditor.model.cloud;

import android.app.Activity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.quangtd.photoeditor.global.GlobalDefine;
import com.quangtd.photoeditor.model.net.DataCallBack;
import com.quangtd.photoeditor.utils.LogUtils;

import java.io.File;

/**
 * QuangTD on 10/17/2017.
 */

public class FireBaseUtils {
    private final String TAG = getClass().getSimpleName();
    private static FireBaseUtils instance;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private final StorageReference storageReference;

    public synchronized static FireBaseUtils getInstance() {
        if (instance == null) instance = new FireBaseUtils();
        return instance;
    }

    private FireBaseUtils() {
        FirebaseStorage fireBaseStorage = FirebaseStorage.getInstance();
        storageReference = fireBaseStorage.getReference().child(GlobalDefine.APP_NAME);
        mAuth = FirebaseAuth.getInstance();
    }

    private void signIn(Activity activity) {
        LogUtils.e(TAG, "signing...");
        mAuth.signInWithEmailAndPassword(GlobalDefine.FIREBASE_USER_NAME, GlobalDefine.FIREBASE_PASSWORD).addOnCompleteListener(activity, task -> {
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

    public StorageReference getStorageReference(String path) {
        return storageReference.child(path);
    }

    public void downloadSticker(StorageReference storageReference, DataCallBack<String> callBack) {
        File stickerDir = new File(GlobalDefine.STICKER_FOLDER_LOCAL + storageReference.getParent().getName());
        if (!stickerDir.exists()) {
            stickerDir.mkdirs();
        }
        String localPath = GlobalDefine.STICKER_FOLDER_LOCAL + storageReference.getParent().getName() + File.separator + storageReference.getName();
        File stickerLocalFile = new File(localPath);
        if (stickerLocalFile.exists()) {
            callBack.onSuccess(localPath);
            return;
        }
        storageReference.getFile(stickerLocalFile)
                .addOnSuccessListener(taskSnapshot -> callBack.onSuccess(localPath))
                .addOnFailureListener(e -> callBack.onError(e.toString()));
    }

    public void downloadEffect(StorageReference storageReference, DataCallBack<String> callBack) {
        File effectDir = new File(GlobalDefine.EFFECT_FOLDER_LOCAL);
        if (!effectDir.exists()) {
            effectDir.mkdirs();
        }
        String localPath = GlobalDefine.EFFECT_FOLDER_LOCAL + storageReference.getName();
        File effectLocalFile = new File(localPath);
        if (effectLocalFile.exists()) {
            callBack.onSuccess(localPath);
            return;
        }
        storageReference.getFile(effectLocalFile)
                .addOnSuccessListener(taskSnapshot -> callBack.onSuccess(localPath))
                .addOnFailureListener(e -> callBack.onError(e.getMessage()));
    }
}
