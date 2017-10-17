package com.quangtd.photoeditor.model.local;
import android.util.Log;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmQuery;

/**
 * Todo
 *
 * @author ToanNS
 */

public class RealmUtils {
    private static RealmUtils sInstance;
    private static Realm sRealm;

    public static RealmUtils getInstance() {
        if (sInstance == null) {
            sInstance = new RealmUtils();
            sRealm = Realm.getDefaultInstance();
        }
        return sInstance;
    }

    public Realm getRealm() {
        return sRealm;
    }

    public <E extends RealmObject> List<E> getList(Class<E> eClass) {
        return convertRealmToList(eClass, sRealm.where(eClass).findAll());
    }

    public <E extends RealmObject> void saveData(E obj) {
        beginTransaction();
        sRealm.copyToRealmOrUpdate(obj);
        commitTransaction();
    }

    public <E extends RealmObject> void saveListData(List<E> objs) {
        beginTransaction();
        sRealm.copyToRealmOrUpdate(objs);
        commitTransaction();
    }

    public <E extends RealmObject> RealmQuery getList(Class<E> eClass, Map condition) {
        Log.i("TAG11",condition+"/////");
        RealmQuery realmQuery = sRealm.where(eClass);
        if (condition != null) {
            Iterator entries = condition.entrySet().iterator();
            while (entries.hasNext()) {
                Map.Entry thisEntry = (Map.Entry) entries.next();
                Object key = thisEntry.getKey();
                Object value = thisEntry.getValue();
                if (value instanceof Long) {
                    realmQuery.equalTo((String) key, (Long) value);
                } else if (value instanceof String) {
                    realmQuery.equalTo((String) key, (String) value);
                } else if (value instanceof Integer) {
                    realmQuery.equalTo((String) key, (Integer) value);
                } else if (value instanceof Boolean) {
                    realmQuery.equalTo((String) key, (Boolean) value);
                }
            }
        }
        return realmQuery;
    }

    public <E extends RealmObject> E getListFirst(Class<E> eClass, Map<String, String> condition) {
        return (E) getList(eClass, condition).findFirst();
    }

    public <E extends RealmObject> List<E> getListData(Class<E> eClass, Map<String, String> condition) {
        return convertRealmToList(eClass, getList(eClass, condition).findAll());
    }

    public <E extends RealmObject> void deleteObject(Class<E> eClass, Map<String, String> condition) {
        beginTransaction();
        Log.i("TAG11",getList(eClass, condition).findAll()+"//");
        getList(eClass, condition).findAll().deleteAllFromRealm();
        commitTransaction();
    }

    private <E extends RealmObject> List<E> convertRealmToList(Class<E> eClass, List<E> objSources) {
        return sRealm.copyFromRealm(objSources);
    }

    public void beginTransaction() {
        if (sRealm.isInTransaction()) {
            sRealm.cancelTransaction();
        }
        sRealm.beginTransaction();
    }

    public void commitTransaction() {
        sRealm.commitTransaction();
    }
}