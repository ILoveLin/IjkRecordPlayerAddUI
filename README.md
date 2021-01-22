# 此Demo： 支持录像,截图功能(只支持直播噢!),支持RTSP,RTMP,HTTP等等在线直播(支持所有CPU架构)，您可以做二次开发添加其他功能！！添加了rtsp和rtmp推流（仅推音频,实现直播语音通话）,并且添加了播放器UI控制

*
#### 如果帮助的到了您，请您不要吝啬你的Star，谢谢您的点赞（Star），3Q
#### 如果帮助的到了您，请您不要吝啬你的Star，谢谢您的点赞（Star），3Q
*
*
####这个项目是在ijkplayer_Record_Player的基础上添加了播放器控制器(暂停播放，截图，录像，跳转相册，连麦等等功能！)

#### 项目使用
* 请参考我的ijkplayer_Record_Player使用方式(其实就是截图和录像三个api使用，不懂得请跳转下面github链接下载demo自己看)
*https://github.com/ILoveLin/ijkplayer_Record_Player
*
#### 简单说明

* 网上有很多修改底层C的代码，实现录像和截图，但是好多都会程序奔溃和闪退，这个Demo做了优化

* 修改底层C代码增加了三个native的API：
* 我们使用的其实都是底层调用这三个方法：
* public native int startRecord(String var1);           //开始录像
* public native int stopRecord();                       //结束录像
* public native boolean getCurrentFrame(Bitmap var1);   //截图


*详情请参考~~(其实就是截图和录像三个api使用，不懂得请跳转下面github链接下载demo自己看)
*https://github.com/ILoveLin/ijkplayer_Record_Player



