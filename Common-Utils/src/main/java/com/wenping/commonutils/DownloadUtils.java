package com.wenping.commonutils;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.NonNull;

/**
 * 需要添加权限： android.permission.WRITE_EXTERNAL_STORAGE
 */
public class DownloadUtils {

    private DownloadUtils() {}

    public static void downloadFile(@NonNull Context context, @NonNull String url, @NonNull String fileName) {
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));

        // set download directory
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, fileName);

        // when downloading music and videos they will be listed in the player
        request.allowScanningByMediaScanner();

        // notify user when download is completed
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);

        // start download
        DownloadManager manager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        if (manager != null) {
            manager.enqueue(request);
        }
    }
}
