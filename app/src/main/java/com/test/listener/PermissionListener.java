package com.test.listener;

import java.util.List;

/**
 * @author 简言
 * @description: 权限申请回调的接口
 */
public interface PermissionListener {

    void onGranted();

    void onDenied(List<String> deniedPermissions);
}
