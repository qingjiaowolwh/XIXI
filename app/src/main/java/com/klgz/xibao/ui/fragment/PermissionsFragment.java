package com.klgz.xibao.ui.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lwh on 2017/11/21.
 */

public class PermissionsFragment extends Fragment {
    private final static String TAG = PermissionsFragment.class.getSimpleName();

    private static final int PERMISSIONS_REQUEST_CODE = 55;

    private Map<String, PermissionsCallback> listeners = new HashMap<>();

    public static PermissionsFragment getInstance(Activity activity){
        PermissionsFragment permissionsFragment = (PermissionsFragment) activity.getFragmentManager().findFragmentByTag(TAG);
        boolean isNewInstance = permissionsFragment == null;
        if (isNewInstance) {
            permissionsFragment = new PermissionsFragment();
            FragmentManager fragmentManager = activity.getFragmentManager();
            fragmentManager
                    .beginTransaction()
                    .add(permissionsFragment, TAG)
                    .commit();
            fragmentManager.executePendingTransactions();
        }
        return permissionsFragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    public void requestPermissions(@NonNull PermissionsCallback listener, @NonNull String... permissions) {
        if (!isM()){
            listener.requestSuccess();
            return;
        }
        if (!checkPermissions(permissions)){
            addListener(listener,permissions);
            requestPermissions(permissions, PERMISSIONS_REQUEST_CODE);
        }else{
            listener.requestSuccess();
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode != PERMISSIONS_REQUEST_CODE) return;

        onRequestPermissionsResult(permissions, grantResults);
    }

    public void onRequestPermissionsResult(String permissions[], int[] grantResults) {
        String permissionStr=getPermissionStr(permissions);
        PermissionsCallback listener = listeners.get(permissionStr);
        listeners.remove(permissionStr);
        if (listener != null) {
            if (isSuccess(grantResults)){
                listener.requestSuccess();
            }else{
                listener.requestFail();
            }

        }
    }

    private boolean isSuccess(int grantResults[]) {
        for (int i = 0, size = grantResults.length; i < size; i++) {
            boolean granted = grantResults[i] == PackageManager.PERMISSION_GRANTED;
            if (!granted) {
                return false;
            }
        }
        return true;
    }

    private boolean checkPermissions(String... permissions) {
        for (int i = 0; i < permissions.length; i++) {
            if (!isGranted(permissions[i])) {
                return false;
            }
        }
        return true;
    }

    private boolean isM() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
    }

    private boolean isGranted(String permission) {
        return getActivity().checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED;
    }

    public boolean isRevoked(String permission) {
        return getActivity().getPackageManager().isPermissionRevokedByPolicy(permission, getActivity().getPackageName());
    }


    private void addListener(@NonNull PermissionsCallback listener, @NonNull String... permissions) {
        listeners.put(getPermissionStr(permissions), listener);
    }

    private String getPermissionStr(String... permissions) {
        StringBuilder builder = new StringBuilder();
        for (String permission : permissions) {
            builder.append(permission);
        }
        return builder.toString();
    }


    public interface PermissionsCallback {
        void requestSuccess();
        void requestFail();
    }

}
