ALLGO（都来）
=====

一个可以组织活动的app,基于android(客户端)和servlet(服务器)，学习编程之用

##目前已实现功能

* 客户端及服务器完整的构架

* 注册，登录，用户发起活动，拉取活动，活动评论，加入活动，添加活动补充等等。

* 用户登录WebSocket实时接收消息（如活动评论，活动被删除），程序关闭后台Http轮询接收消息

##客户端使用的开源库

* ActionBar-PullToRefresh: https://github.com/chrisbanes/ActionBar-PullToRefresh

* ListViewAnimations: https://github.com/nhaarman/ListViewAnimations

* xUtils: https://github.com/wyouflf/xUtils
 
* FadingActionBar https://github.com/ManuelPeinado/FadingActionBar

##服务器相关

* Tomcat7.0.42容器（JAVA7.0）

* Servlet3.0，HttpServlet及WebSocketServlet

* 数据库使用MySql数据库

* 数据库持久层使用Hibernate4
