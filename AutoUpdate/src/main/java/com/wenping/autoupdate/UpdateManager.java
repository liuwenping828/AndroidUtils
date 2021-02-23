package com.wenping.autoupdate;

import java.io.File;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 文件下载 Manager
 */
public class UpdateManager {

    private static UpdateManager updateManager;
    private ThreadPoolExecutor threadPoolExecutor;
    private UpdateDownloadRequest request;

    static {
        updateManager = new UpdateManager();
    }

    private UpdateManager() {
        // 线程池的初始化
        threadPoolExecutor = (ThreadPoolExecutor) Executors.newCachedThreadPool();
    }

    public static UpdateManager getInstance() {
        return updateManager;
    }

    public void startDownloads(String downloadUrl, String localPath, UpdateDownloadListener listener) {
        if (request != null) {
            return;
        }
        checkLocalFilePath(localPath);
        request = new UpdateDownloadRequest(downloadUrl, localPath, listener);
        threadPoolExecutor.submit(request);
    }

    /**
     * 检查文件路径是否存在
     */
    private void checkLocalFilePath(String path) {
        File dir = new File(path.substring(0, path.lastIndexOf("/") + 1));
        if (!dir.exists()) {
            dir.mkdir();
        }
        File file = new File(path);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
