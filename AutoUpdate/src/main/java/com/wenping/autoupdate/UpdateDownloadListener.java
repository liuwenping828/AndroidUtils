package com.wenping.autoupdate;

/**
 * 下载监听回调接口
 */
public interface UpdateDownloadListener {

    void onStarted();

    void onProgressChanged(int progress, String downloadUrl);

    void onFinished(float completeSize, String downloadUrl);

    void onFailure();
}
