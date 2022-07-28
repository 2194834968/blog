# simple-demo

## 博客项目服务端简单实现

具体功能内容参考说明文档https://www.apifox.cn/apidoc/shared-8cc50618-0da6-4d5e-a398-76f3b8f766c5/api-18345145

工程需要创建数据库才能运行，数据库名blog_lee，定义见测试数据

工程有线上地址，http://127.0.0.1:8080/douyin/test

```shell
java -jar "C:\... ...\lzp-0.0.1-SNAPSHOT.jar"
```

### 文档修改说明说明
说明文档https://www.apifox.cn/apidoc/shared-8cc50618-0da6-4d5e-a398-76f3b8f766c5/api-18345145

* 视频播放地址play_url改为文章简介
* 视频封面地址cover_url改为文章内容
* /douyin/publish/action/的data数据类型由file改为string

* 用户数据库额外支持了total_favorited（被赞的总数量）和Favorite_Count（喜欢的视频总数量）两个int

### 功能说明


* 用户数据库中尚未包含用户头像链接avatar，个性签名signature，背景图链接background_image
* 用户数据库中新注册用户，用户昵称为（用户+账号名）
* 如此处理以上数据的原因，是应用并未添加编辑相应数据的接口，以上数据均需要在数据库手动更改
