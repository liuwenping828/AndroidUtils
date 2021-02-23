自动更新原理
 1. apk安装包文件下载
 2. 利用 Notification 通知用户进度等消息
 3. 文件下载成功后调用系统安装程序进行apk 安装

使用
   Intent intent = new Intent(MainActivity.this, UpdateService.class);
   intent.putExtra("apkUrl", "http://121.42.53.175:8080/hello_project/resources/upload/TianQiBao201605231.apk");
   startService(intent);