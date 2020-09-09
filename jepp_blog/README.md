jedp_blog 俊哥的博客。使用springboot开发的Java博客，前端使用Bootstrap。支持移动端自适应，配有完备的前台和后台管理功能。    

# 功能简介

- **多种编辑器**：支持wangEditor和Markdown两种富文本编辑器，可以自行选择
- **自动申请友情链接**：在线申请友情链接，无需站长手动配置，只需申请方添加完站长的连接后自行申请即可
- **百度推送**：支持百度推送功能，加速百度搜索引擎收录博文
- **评论系统**：自研的评论系统，支持显示用户地址、浏览器和os信息，后台可审核评论、开启匿名评论、回复和邮件通知评论
- **权限管理**：后台配备完善的权限管理
- **SEO**：自带robots、sitemap等seo模板，实现自动生成robots和sitemap
- **实时通讯**：管理员可向在线的用户发送实时消息（需用户授权 - 基于websocket实现，具体参考[DBlog建站之Websocket的使用](https://www.zhyd.me/article/111)）
- **系统配置支持快速配置**：可通过后台手动修改诸如域名信息、SEO优化、赞赏码、七牛云以及更新维护通知等
- **多种文件存储**：集成七牛云、阿里云OSS，实现文件云存储，同时支持本地文件存储
- **文件搬运工**：集成[blog-hunter](https://gitee.com/yadong.zhang/blog-hunter)实现“文章搬运工”功能，支持一键同步imooc、csdn、iteye或者cnblogs上的文章，可抓取列表和单个文章
- **第三方授权登录**：集成[JustAuth](https://gitee.com/yadong.zhang/JustAuth)实现第三方授权登录


# 模块划分

| 模块  | 释义 | 备注 |
| :------------: | :------------: | :------------: |
| blog-core | 核心业务类模块，提供基本的数据操作、工具处理等 | 该模块只是作为核心依赖包存在 |
| blog-admin | 后台管理模块 | 该模块作为单独项目打包部署 |
| blog-web | 前台模块 | 该模块作为单独项目打包部署 |
| blog-file | 文件存储功能模块 | 支持local、七牛云和阿里云OSS |
| ~~blog-spider~~ | 爬虫相关代码模块 | 已使用[blog-hunter](https://gitee.com/yadong.zhang/blog-hunter)插件替代 |


# 技术栈

- Springboot 2.0.8
- Apache Shiro 1.2.2
- Logback
- Redis
- Lombok
- Websocket
- MySQL、Mybatis、Mapper、Pagehelper
- Freemarker
- Bootstrap 3.3.0
- wangEditor
- jQuery 1.11.1、jQuery Lazyload 1.9.7、fancybox、iCheck
- 阿里云OSS
- kaptcha
- Qiniu
- webMagic
- ...


# Demo 演示

[前台demo](http://dblog-web.zhyd.me/)

[后台demo(root,123456)](http://dblog-admin.zhyd.me)

![admin端首页](https://images.gitee.com/uploads/images/2019/0129/191117_221c6064_784199.png "admin-index.png")
![admin端文章列表也](https://images.gitee.com/uploads/images/2019/0129/191135_21e4f61c_784199.png "admin-article.png")
![admin端发布文章页](https://images.gitee.com/uploads/images/2019/0129/191150_0d28d51a_784199.png "admin-publish-article.png")
![admin端系统配置页](https://images.gitee.com/uploads/images/2019/0129/191203_cc6941e4_784199.png "admin-config.png")
![admin端文章搬运工](https://images.gitee.com/uploads/images/2019/0129/191214_5e8f3c34_784199.png "admin-spider.png")
![admin端文章搬运工](https://images.gitee.com/uploads/images/2019/0129/191237_d015fcda_784199.png "admin-spider2.png")
![web端首页-pc](https://images.gitee.com/uploads/images/2019/0129/191409_d2604f7d_784199.png "web-index-pc.png")
![web端首页-mobile](https://images.gitee.com/uploads/images/2019/0129/191428_c76317e8_784199.png "web-index.png")
![web端文章详情页](https://images.gitee.com/uploads/images/2019/0129/191448_a2777443_784199.png "web-article-detail.png")

----



----

# 请关注：

  1. [我的博客](https://www.zhyd.me)
  2. [我的微博](http://weibo.com/211230415)
  3. [我的头条号](http://www.toutiao.com/c/user/3286958681/)

|  微信(备注:加群)  |  公众号  |
| :------------: | :------------: |
| <img src="https://gitee.com/yadong.zhang/static/raw/master/wx/wx.png" width="170"/> | <img src="https://gitee.com/yadong.zhang/static/raw/master/wx/wechat_account.jpg" width="200" /> |

