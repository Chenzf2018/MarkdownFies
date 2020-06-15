# Hello Tomcat

Tomcat是常见的免费的**web服务器**。

Tomcat是一种野外的猫科动物，不依赖人类，独立生活。Tomcat的作者，取这个名字的初衷是希望，这一款服务器可以自力更生，自给自足，像Tomcat这样一种野生动物一般，不依赖其他插件，而**可以独立达到提供web服务的效果**。

不使用tomcat也可以打开html页面，但是可以在浏览器的地址里看到 `file:d:/test.html`这样的格式，是通过**打开本地文件**的形式打开的。

使用tomcat后，可以这样`127.0.0.1:8080/test.html`像访问一个网站似的，访问一个html文件了。这是因为tomcat本身是一个web服务器，**`test.html`部署在了这个web服务器上**，所以就可以这样访问了。

## 启动Tomcat

下载解压后，运行批处理文件：`D:\WinSoftware\Tomcat\bin\startup.bat`，最后显示`信息: Server startup in 37 ms`，就表明启动成功了。Tomcat启动之后，**不要关闭**。关闭了就不能访问了。

## 部署网页

部署一个功能完备的web应用有很多种方式，但是如果只是部署一个`test.html`，很简单：

把`test.html`复制到`D:\WinSoftware\Tomcat\webapps`目录下，就可以通过`http://127.0.0.1:8080/test.html`访问了。