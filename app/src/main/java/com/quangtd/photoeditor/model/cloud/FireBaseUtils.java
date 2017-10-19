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
}
