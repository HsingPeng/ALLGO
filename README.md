ALLGO（都来）
=====

一个可以组织活动的app,基于android(客户端)和servlet(服务器)，学习编程之用

##目前已实现功能

* 客户端及服务器完整的构架

* 注册，登录，用户发起活动，拉取活动，活动评论，加入活动，添加活动补充等等。

* 用户登录WebSocket实时接收消息（如活动评论，活动被删除），程序关闭后台Http轮询接收消息

##客户端运行画面

![Screenshot](https://github.com/HsingPeng/ALLGO/blob/master/Screenshot/01%E6%AC%A2%E8%BF%8E%E7%95%8C%E9%9D%A2.png)
![Screenshot](https://github.com/HsingPeng/ALLGO/blob/master/Screenshot/05%E7%BB%84%E7%BB%87%E6%B4%BB%E5%8A%A8%E7%95%8C%E9%9D%A2.png)

![Screenshot](https://github.com/HsingPeng/ALLGO/blob/master/Screenshot/07%E6%B4%BB%E5%8A%A8%E5%88%97%E8%A1%A8%E7%95%8C%E9%9D%A2.png)
![Screenshot](https://github.com/HsingPeng/ALLGO/blob/master/Screenshot/10%E6%B4%BB%E5%8A%A8%E4%B8%BB%E9%A1%B5%E7%95%8C%E9%9D%A2B.png)

![Screenshot](https://github.com/HsingPeng/ALLGO/blob/master/Screenshot/04%E5%AF%BC%E8%88%AA%E8%8F%9C%E5%8D%95%E7%95%8C%E9%9D%A2.png)
![Screenshot](https://github.com/HsingPeng/ALLGO/blob/master/Screenshot/15%E6%88%91%E7%9A%84%E4%B8%BB%E9%A1%B5%E7%95%8C%E9%9D%A2A.png)


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
