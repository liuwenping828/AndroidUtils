package com.wenping.autoupdate;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.IBinder;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.FileProvider;

import java.io.File;

/**
 * 更新下载后台进程
 */
public class UpdateService extends Service {

    private String apkUrl;
    private String filePath;
    private NotificationManager notificationManager;

    @Override
    public void onCreate() {
        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        // 指定文件下载路径
        filePath = Environment.getExternalStorageDirectory() + "/AppUpdate/release.apk";
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent == null) {
            notifyUser(getString(R.string.update_download_failed), getString(R.string.update_download_failed), 0);
            stopSelf();
        }
        if (intent != null) {
            apkUrl = intent.getStringExtra("apkUrl");
        }
        notifyUser(getString(R.string.update_download_start), getString(R.string.update_download_start), 0);
        startDownload();
        return super.onStartCommand(intent, flags, startId);
    }

    private void startDownload() {
        UpdateManager.getInstance().startDownloads(apkUrl, filePath, new UpdateDownloadListener() {
            @Override
            public void onStarted() {

            }

            @Override
            public void onProgressChanged(int progress, String downloadUrl) {
                notifyUser(getString(R.string.update_download_progressing),
                        getString(R.string.update_download_progressing), progress);
            }

            @Override
            public void onFinished(float completeSize, String downloadUrl) {
                notifyUser(getString(R.string.update_download_finish),
                        getString(R.string.update_download_finish), 100);
                stopSelf();
            }

            @Override
            public void onFailure() {
                notifyUser(getString(R.string.update_download_failed),
                        getString(R.string.update_download_failed), 0);
                stopSelf();
            }
        });
    }

    /**
     * 更新notification
     */
    private void notifyUser(String result, String msg, int progress) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        // todo  替换icon
        builder.setSmallIcon(R.drawable.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher))
                .setContentTitle(getString(R.string.app_name));
        if (progress > 0 && progress <= 100) {
            builder.setProgress(100, progress, false);
        } else {
            builder.setProgress(0, 0, false);
        }
        builder.setAutoCancel(true);
        builder.setWhen(System.currentTimeMillis());
        builder.setTicker(result);
        builder.setContentIntent(progress >= 100 ? getContentIntent() :
                PendingIntent.getActivity(this, 0, new Intent(), PendingIntent.FLAG_UPDATE_CURRENT));
        Notification notification = builder.build();
        notificationManager.notify(0, notification);

    }

    /**
     * 进入apk安装程序
     */
    private PendingIntent getContentIntent() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            File apkFile = new File(filePath);
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setDataAndType(Uri.fromFile(apkFile),
                    "application/vnd.android.package-archive");
            PendingIntent pendingIntent = PendingIntent.getActivity(this,
                    0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            startActivity(intent);
            return pendingIntent;
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                boolean hasInstallPermission = getPackageManager().canRequestPackageInstalls();
                if (!hasInstallPermission) {
                    startInstallPermissionSettingActivity(this);
                    return null;
                }
            }
            File apkFile = new File(filePath);
            Uri apkUri = FileProvider.getUriForFile(this, getPackageName() + ".fileprovider", apkFile);
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.setDataAndType(apkUri, "application/vnd.android.package-archive");
            PendingIntent pendingIntent = PendingIntent.getActivity(this,
                    0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            startActivity(intent);
            return pendingIntent;
        }
    }


//    public static void installAPK(File file, Context context) {
//        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
//            Intent intent = new Intent(Intent.ACTION_VIEW);
//            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            intent.setDataAndType(Uri.fromFile(file),
//                    "application/vnd.android.package-archive");
//            context.startActivity(intent);
//        } else {
//            // 兼容8.0
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                boolean hasInstallPermission = context.getPackageManager().canRequestPackageInstalls();
//                if (!hasInstallPermission) {
//                    startInstallPermissionSettingActivity(context);
//                    return;
//                }
//            }
//            Uri apkUri = FileProvider.getUriForFile(context, "com.mxt.min.fileprovider", file);
//            Intent intent = new Intent(Intent.ACTION_VIEW);
//            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//            intent.setDataAndType(apkUri, "application/vnd.android.package-archive");
//
//            context.startActivity(intent);
//        }
//    }

    /**
     * 跳转到设置-允许安装未知来源-页面
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void startInstallPermissionSettingActivity(Context context) {
        //后面跟上包名，可以直接跳转到对应APP的未知来源权限设置界面。使用startActivityForResult
        // 是为了在关闭设置界面之后，获取用户的操作结果，然后根据结果做其他处理
        Intent intent = new Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES, Uri.parse("package:" + getPackageName()));
//        context.startActivityForResult(intent, 1);
        context.startActivity(intent);
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
