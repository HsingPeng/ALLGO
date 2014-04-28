ALLGO（都来）
=====

一个可以组织活动的app,基于android(客户端)和servlet(服务器)，学习编程之用

##目前已实现功能

* 客户端及服务器完整的构架

* 注册，登录，用户发起活动，拉取活动，活动评论，加入活动，添加活动补充等等。

* 用户登录WebSocket实时接收消息（如活动评论，活动被删除），程序关闭后台Http轮询接收消息

##软件展示

![Screenshot](https://github.com/HsingPeng/ALLGO/raw/master/header_graphic_00.png)

![Screenshot](https://github.com/HsingPeng/ALLGO/raw/master/header_graphic_01.png)

![Screenshot](https://github.com/HsingPeng/ALLGO/raw/master/header_graphic_02.png)

查看完整客户端截图 https://github.com/HsingPeng/ALLGO/tree/master/Screenshot

##客户端使用的开源库

* ActionBar-PullToRefresh: https://github.com/chrisbanes/ActionBar-PullToRefresh

* ListViewAnimations: https://github.com/nhaarman/ListViewAnimations

* xUtils: https://github.com/wyouflf/xUtils
 
* FadingActionBar: https://github.com/ManuelPeinado/FadingActionBar

##服务器相关

* Tomcat7.0.42容器（JAVA7.0）

* Servlet3.0，HttpServlet及WebSocketServlet

* 数据库使用MySql数据库

* 数据库持久层使用Hibernate4

##版权申明

* 本项目代码所有权归本团队所有

* 仅供个人学习使用，请勿商用

* 本项目部分图片资源来源于网络