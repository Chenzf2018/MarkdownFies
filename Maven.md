# Maven介绍

&emsp;&emsp;在了解Maven之前，我们先来看看一个Java项目需要的东西。

首先，我们需要确定引入哪些依赖包。
例如，如果我们需要用到`commons logging`，我们就必须把`commons logging`的jar包放入`classpath`。如果我们还需要`log4j`，就需要把`log4j`相关的jar包都放到`classpath`中。
我们需要引用各种jar包，尤其是比较大的工程，引用的jar包往往有几十个乃至上百个， 每用到一种jar包，都需要手动引入工程目录，而且经常遇到各种让人抓狂的jar包冲突，版本冲突。
这些就是<font color=red>依赖包的管理</font>。

其次，我们要确定项目的<font color=red>目录结构</font>。例如，`src`目录存放Java源码，`resources`目录存放配置文件，`bin`目录存放编译生成的`.class`文件。

此外，我们还需要<font color=red>配置环境</font>，例如JDK的版本，编译打包的流程，当前代码的版本号。

最后，除了使用Eclipse这样的IDE进行<font color=red>编译</font>外，我们还必须能通过命令行工具进行编译，才能够让项目在一个独立的服务器上编译、测试、部署。

这些工作难度不大，但是非常琐碎且耗时。如果每一个项目都自己搞一套配置，肯定会一团糟。我们需要的是一个标准化的Java项目管理和构建工具。

Maven就是是专门为Java项目打造的<font color=red>管理和构建工具</font>，它的主要功能有：
* 提供了一套标准化的项目结构；
* 提供了一套标准化的构建流程（编译，测试，打包，发布……）；
* 提供了一套依赖管理机制。

&emsp;&emsp;Maven是一个项目管理工具，它包含了一个项目对象模型 (POM: Project Object Model)，一组标准集合，一个项目生命周期(Project Lifecycle)，一个依赖管理系统(Dependency Management System)，和用来运行定义在生命周期阶段(phase)中插件(plugin)目标(goal)的逻辑。
<div align=center><img src=MavenImages/maven概念模型图.png></div>

* 项目对象模型(Project Object Model)
每一个Maven工程都有一个`pom.xml`文件，通过`pom.xml`文件定义`项目的坐标、项目依赖、项目信息、插件目标`等。

* 依赖管理系统(Dependency Management System)
通过Maven的依赖管理对项目所依赖的jar包进行统一管理。

* 一个项目生命周期(Project Lifecycle)
使用Maven完成项目的构建，项目构建包括：清理、编译、测试、部署等过程，Maven将这些过程规范为一个生命周期。

* 一组标准集合
Maven将整个项目管理过程定义一组标准，比如：通过Maven构建工程有标准的目录结构，有标准的生命周期阶段、依赖管理有标准的坐标定义等。

* 插件(plugin)目标(goal)
Maven管理项目生命周期过程都是基于插件完成的。

## Mven的两个经典作用
&emsp;&emsp;Maven的一个核心特性就是<font color=red>依赖管理</font>。当我们涉及到多模块的项目（包含成百个模块或者子项目），管理依赖就变成一项困难的任务。传统的Web项目中，我们必须将工程所依赖的jar包复制到工程中，导致了工程的变得很大。

<font color=red>Maven工程中不直接将jar包导入到工程中</font>，而是通过在`pom.xml`文件中添加所需jar包的<font color=red>坐标</font>，这样就很好的避免了jar直接引入进来。在需要用到jar包的时候，只要查找`pom.xml`文件，再通过`pom.xml`文件中的坐标，到一个专门用于“存放jar包的仓库”(Maven仓库)”中根据坐标从而找到这些jar包，再把这些jar包拿去运行。

&emsp;&emsp;项目从编译、测试、运行、打包、安装 ，部署整个过程都交给Maven进行管理，这个过程称为<font color=red>构建</font>。整个构建过程，使用Maven一个命令可以轻松完成整个工作。

Maven规范化构建流程如下：
<div align=center><img src=MavenImages/Maven构建流程.png width=70% height=40%></div>

## Maven项目结构
&emsp;&emsp;一个使用Maven管理的普通的Java项目，它的目录结构默认如下：
<div align=left><img src=MavenImages/Maven目录结构.png width=20%></div>

项目的根目录`a-maven-project`是<font color=red>项目名</font>，它有一个<font color=red>项目描述文件`pom.xml`</font>，存放<font color=red>Java源码</font>的目录是`src/main/java`，存放<font color=red>资源文件</font>的目录是`src/main/resources`，存放<font color=red>测试源码</font>的目录是`src/test/java`，存放<font color=red>测试资源</font>的目录是`src/test/resources`，最后，<font color=red>所有编译、打包生成的文件都放在`target`目录里</font>。这些就是一个Maven项目的标准目录结构。

<font color=red>所有的目录结构都是约定好的标准结构</font>，我们千万不要随意修改目录结构。使用标准结构不需要做任何配置，Maven就可以正常使用。

### pom.xml
&emsp;&emsp;`pom.xml`是Maven项目的核心配置文件，位于每个工程的根目录，它的内容如下所示：
```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>2.2.4.RELEASE</version>
        <relativePath/> <!-- lookup parent from repository -->
    </parent>

    <groupId>com.example</groupId>
    <artifactId>hello</artifactId>
    <version>0.0.1-SNAPSHOT</version>
    <name>hello</name>
    <description>Demo project for Spring Boot</description>

    <properties>
        <java.version>13</java.version>
    </properties>

    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-devtools</artifactId>
            <scope>runtime</scope>
            <optional>true</optional>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
            <exclusions>
                <exclusion>
                    <groupId>org.junit.vintage</groupId>
                    <artifactId>junit-vintage-engine</artifactId>
                </exclusion>
            </exclusions>
        </dependency>
    </dependencies>

    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
                <configuration><fork>true</fork></configuration>
            </plugin>
        </plugins>
    </build>

</project>
```
其中，<font color=red>*groupId* 类似于Java的包名</font>，通常是公司或组织名称，<font color=red>*artifactId* 类似于Java的类名</font>，通常是项目名称，再加上version，<font color=red>一个Maven工程就是由groupId，artifactId和version作为唯一标识</font>。我们在引用其他第三方库的时候，也是通过这3个变量确定。例如，依赖：*org.springframework.boo*
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>
```
使用<dependency>声明一个依赖后，Maven就会自动下载这个依赖包并把它放到classpath中。

&emsp;&emsp;`pom.xml`的基本配置如下：
* \<project>: 文件的根节点；
* \<modelVersion>: `pom.xml`使用的对象模型版本；
* \<groupId>：项目名称，一般写项目的域名；
* \<artifactId>：模块名称，子项目名或模块名称;
* \<version>：产品的版本号；
* \<packaging>：打包类型，一般有`jar、war、pom`等；
* \<name>：项目的显示名，常用于 Maven生成的文档；
* \<description>：项目描述，常用于 Maven生成的文档；
* \<dependencies>：项目依赖构件配置，配置项目依赖构件的<font color=red>坐标</font>；
* \<build>：项目构建配置，配置编译、运行插件等（打包插件）。

#### `pom.xml`结构
&emsp;&emsp;`pom.xml`文件主要描述了<font color=red>项目包的依赖和项目构建时的配置</font>，在默认的`pom.xml`包中分为四大块。
第一部分为项目的描述信息：
```xml
<groupId>com.example</groupId>
<artifactId>hello</artifactId>
<version>0.0.1-SNAPSHOT</version>
<name>hello</name>
<description>Demo project for Spring Boot</description>
```
第二部分为项目的依赖配置信息：
```xml
<parent>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-parent</artifactId>
    <version>2.2.4.RELEASE</version>
    <relativePath/> <!-- lookup parent from repository -->
</parent>

<dependencies>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-test</artifactId>
        <scope>test</scope>
        <exclusions>
            <exclusion>
                <groupId>org.junit.vintage</groupId>
                <artifactId>junit-vintage-engine</artifactId>
            </exclusion>
        </exclusions>
    </dependency>
</dependencies>
```
*	`parent`，标签内配置Spring Boot父级版本`spring-boot-starter-parent`，Maven支持项目的父子结构，引入父级后会默认继承父级的配置。这个特殊的`starter`可以用来提供相关的Maven默认依赖，使用它之后，常用的包依赖可以省去version标签；
*	`dependencies`，标签内配置项目所需要的依赖包，Spring Boot体系内的依赖组件不需要填写具体版本号，`spring-boot-starter-parent`维护了体系内所有依赖包的版本信息。
* `<scope>test</scope>`表示依赖的组件仅仅参与测试相关的工作，包括测试代码的编译和执行，不会被打包包含进去；
* `spring-boot-starter-test`是Spring Boot提供项目测试的工具包，内置了多种测试工具，方便我们在项目中做单元测试、集成测试。

第三部分为构建时需要的公共变量：
```xml
<properties>
    <java.version>13</java.version>
</properties>
```
第四部分为构建配置：
```xml
<build>
    <plugins>
        <plugin>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-maven-plugin</artifactId>
            <configuration><fork>true</fork></configuration>
        </plugin>
    </plugins>
</build>
```
使用Maven构建Spring Boot项目必须依赖于`spring-boot-maven-plugin`组件，`spring-boot-maven-plugin`能够以Maven的方式为应用提供Spring Boot的支持，即为Spring Boot应用提供了执行Maven操作的可能。`spring-boot-maven-plugin`能够将Spring Boot应用打包为可执行的`jar`或`war`文件，然后以简单的方式运行Spring Boot应用。默认使用了`spring-boot-maven-plugin`，配合`spring-boot-starter-parent`就可以把Spring Boot应用打包成`JAR`来直接运行。

#### 坐标定义
&emsp;&emsp;在`pom.xml`中定义坐标，内容包括：`groupId, artifactId, version, packaging`，详细内容如下：
```xml
<!--项目名称，定义为组织名+项目名，类似包名-->
<groupId>org.springframework.boot</groupId>
<!-- 模块名称 -->
<artifactId>spring-boot-starter-parent</artifactId>
<!-- 当前项目版本号，snapshot为快照版本即非正式版本，release为正式发布版本 -->
<version>2.2.4.RELEASE</version>
<packaging>jar</packaging>
```

## 安装Maven
&emsp;&emsp;要安装Maven，可以从Maven官网下载最新的Maven 3.6.x(建议使用3.3.9)，然后在本地解压，安装Maven的前提是完成Java环境安装，Maven依赖于Java环境。Maven为绿色软件解压后即可使用。解压后需要设置环境变量：
<div align=center><img src = MavenImages/设置环境变量.png width = 70%></div>

然后，打开命令行窗口，输入mvn -version，应该看到Maven的版本信息：
<div align=center><img src=MavenImages/验证Maven是否正确安装.png></div>

建议在安装Java1.8的基础上安装Maven3.3.9：
<div align=center><img src=MavenImages/验证Maven是否安装正确.png></div>

### settings.xml 设置
&emsp;&emsp;Maven解压后目录下会有一个`settings.xml`文件，位置`/conf/settings.xml`，用来<font color=red>配置Maven的仓库和本地Jar包存储地址</font>。Maven仓库地址代表从哪里去下载项目中的依赖包Jar包；Maven会将所有的Jar包统一存储到一个地址下，方便各个项目复用。默认本地仓库位置在`C:\Users\Chenzf\.m2\repository`。

<font color=red>*localRepository* 设置本地存放Jar包地址</font>，可以根据自己的情况改动：
```xml
<!-- localRepository
 | The path to the local repository maven will use to store artifacts.
 |
 | Default: ${user.home}/.m2/repository
<localRepository>/path/to/local/repo</localRepository>
-->
  <localRepository>D:\WinSoftware\Maven\repository</localRepository>
```
<span id="mirrors">`mirrors`</span><font color=red>为仓库列表配置的下载镜像列表</font>：
```xml
<!-- mirrors
 | This is a list of mirrors to be used in downloading artifacts from remote repositories.
 |
 | It works like this: a POM may declare a repository to use in resolving certain artifacts.
 | However, this repository may have problems with heavy traffic at times, so people have mirrored
 | it to several places.
 |
 | That repository definition will have a unique id, so we can create a mirror reference for that
 | repository, to be used as an alternate download site. The mirror site will be the preferred
 | server for that repository.
 |-->
<mirrors>
  <!-- mirror
   | Specifies a repository mirror site to use instead of a given repository. The repository that
   | this mirror serves has an ID that matches the mirrorOf element of this mirror. IDs are used
   | for inheritance and direct lookup purposes, and must be unique across the set of mirrors.
   |
  <mirror>
    <id>mirrorId</id>
    <mirrorOf>repositoryId</mirrorOf>
    <name>Human Readable Name for this Mirror.</name>
    <url>http://my.repository.com/repo/path</url>
  </mirror>
   -->

    <mirror>
        <id>alimaven</id>
        <mirrorOf>central</mirrorOf>
        <name>aliyun maven</name>
        <url>http://maven.aliyun.com/nexus/content/groups/public/</url>
    </mirror>

    <mirror>
        <id>repo2</id>
        <mirrorOf>central</mirrorOf>
        <name>spring2.0 for this Mirror.</name>
        <url>https://repo.spring.io/libs-milestone</url>
    </mirror>

    <mirror>
        <id>jboss-public-repository-group</id>
        <mirrorOf>central</mirrorOf>
        <name>JBoss Public Repository Group</name>
        <url>http://repository.jboss.org/nexus/content/groups/public</url>
    </mirror>

</mirrors>
```
### 全局setting与用户setting
&emsp;&emsp;Maven仓库地址、私服等配置信息需要在`setting.xml`文件中配置，分为`全局配置`和`用户配置`。在Maven安装目录下的有`conf/setting.xml`文件，此`setting.xml`文件用于Maven的所有project项目，它作为Maven的`全局配置`。如需要`个性配置`则需要在用户配置中设置，用户配置的setting.xml文件默认的位置在：`C:\Users\Chenzf\.m2\repository`。

<font color=red>Maven会先找用户配置，如果找到则以用户配置文件为准，否则使用全局配置文件。</font>
<div align=center><img src=MavenImages/全局配置与用户配置.png width=50%></div>

# 依赖管理
&emsp;&emsp;如果我们的项目依赖第三方的jar包，例如commons logging，那么问题来了：<u>commons logging发布的jar包在哪下载</u>？如果我们还希望依赖log4j，那么<u>使用log4j需要哪些jar包</u>？类似的依赖还包括：JUnit，JavaMail，MySQL驱动等等。一个可行的方法是通过搜索引擎搜索到项目的官网，然后<font color=red>手动下载zip包，解压，放入classpath</font>。但是，这个过程非常繁琐。

&emsp;&emsp;<font color=red>Maven解决了依赖管理问题</font>。例如，我们的项目依赖abc这个jar包，而abc又依赖xyz这个jar包：
<div align=center><img src=MavenImages/jar包依赖关系.png></div>

<u>当我们声明了abc的依赖时，Maven自动把abc和xyz都加入了我们的项目依赖，不需要我们自己去研究abc是否需要依赖xyz</u>。因此，`Maven的第一个作用就是解决依赖管理`。我们声明了自己的项目需要abc，Maven会自动导入abc的jar包，再判断出abc需要xyz，又会自动导入xyz的jar包，这样，最终我们的项目会依赖abc和xyz两个jar包。

&emsp;&emsp;我们来看一个复杂依赖示例：
```xml
<groupId>org.springframework.boot</groupId>
<artifactId>spring-boot-starter-parent</artifactId>
<version>2.2.4.RELEASE</version>
```
当我们声明一个`spring-boot-starter-web`依赖时，Maven会自动解析并判断最终需要大概二三十个其他依赖：
<div align=center><img src=MavenImages/Maven自动解析依赖关系.png></div>
如果我们自己去手动管理这些依赖是非常费时费力的，而且出错的概率很大。

## 依赖关系
&emsp;&emsp;Maven通过解析依赖关系确定项目所需的jar包，Maven定义了几种依赖关系，分别是`compile`、`test`、`runtime`和`provided`：
<div align=center><img src=MavenImages/依赖关系.png></div>

其中，默认的`compile`是最常用的，Maven会把这种类型的依赖直接放入classpath。

`test依赖`表示仅在测试时使用，正常运行时并不需要。最常用的test依赖就是JUnit：
```xml
<dependency>
    <groupId>org.junit.jupiter</groupId>
    <artifactId>junit-jupiter-api</artifactId>
    <version>5.3.2</version>
    <scope>test</scope>
</dependency>
```
`runtime依赖`表示编译时不需要，但运行时需要。最典型的runtime依赖是JDBC驱动，例如MySQL驱动：
```xml
<dependency>
    <groupId>mysql</groupId>
    <artifactId>mysql-connector-java</artifactId>
    <version>5.1.48</version>
    <scope>runtime</scope>
</dependency>
```
`provided依赖`表示编译时需要，但运行时不需要。最典型的provided依赖是Servlet API，编译的时候需要，但是运行时，Servlet服务器内置了相关的jar，所以运行期不需要：
```xml
<dependency>
    <groupId>javax.servlet</groupId>
    <artifactId>javax.servlet-api</artifactId>
    <version>4.0.0</version>
    <scope>provided</scope>
</dependency>
```

&emsp;&emsp;<u>Maven如何知道从何处下载所需的依赖</u>？也就是相关的jar包？答案是Maven维护了一个[中央仓库](https://repo1.maven.org/maven2)，<font color=red>所有第三方库将自身的jar以及相关信息上传至中央仓库</font>，Maven就可以从中央仓库把所需依赖下载到本地。

Maven并不会每次都从中央仓库下载jar包。<font color=red>一个jar包一旦被下载过，就会被Maven自动缓存在本地目录（用户主目录的.m2目录）</font>。所以，除了第一次编译时因为下载需要时间会比较慢，后续过程因为有本地缓存，并不会重复下载相同的jar包。

## 唯一ID
&emsp;&emsp;对于某个依赖，Maven只需要3个变量即可唯一确定某个jar包：
* groupId：属于组织的名称，类似Java的包名；
* artifactId：该jar包自身的名称，类似Java的类名；
* version：该jar包的版本。

通过上述3个变量，即可唯一确定某个jar包。Maven通过对jar包进行PGP签名确保<u>任何一个jar包一经发布就无法修改</u>。修改已发布jar包的唯一方法是发布一个新版本。因此，某个jar包一旦被Maven下载过，即可永久地安全缓存在本地。

注：只有以`SNAPSHOT-`开头的版本号会被Maven视为开发版本，开发版本每次都会重复下载，<font color=red>这种SNAPSHOT版本只能用于内部私有的Maven repo，公开发布的版本不允许出现SNAPSHOT</font>。

## Maven镜像
&emsp;&emsp;除了可以从Maven的中央仓库下载外，还可以从Maven的镜像仓库下载。如果访问Maven的中央仓库非常慢，我们可以选择一个速度较快的Maven的镜像仓库，Maven镜像仓库定期从中央仓库同步。如何设置`mirrors`可参见[“settings.xml中设置mirrors”](#mirrors)[^1]。
[^1]:文内链接#mirrors不能含中文

## 搜索第三方组件
&emsp;&emsp;如果我们要引用一个第三方组件，比如`okhttp`，<u>如何确切地获得它的groupId、artifactId和version</u>？方法是通过[search.maven.org](https://search.maven.org/)搜索关键字，找到对应的组件后，直接复制：
<div align=center><img src=MavenImages/引用第三方组件.png></div>

# 构建流程
&emsp;&emsp;Maven不但有标准化的项目结构，而且还有一套标准化的构建流程，可以<font color=red>自动化实现编译，打包，发布等</font>。

## Lifecycle和Phase
&emsp;&emsp;使用Maven时，首先要了解什么是Maven的生命周期（lifecycle）。<font color=red>Maven的<u>生命周期</u>由一系列<u>阶段</u>（phase）构成</font>，以<u><font color=red>内置的生命周期`default`</font></u>为例，它包含以下phase：
`validate, initialize, generate-sources, process-sources, generate-resources, process-resources, compile, process-classes, generate-test-sources, process-test-sources, generate-test-resources, process-test-resources, test-compile, process-test-classes, test, prepare-package, package, pre-integration-test, integration-test, post-integration-test, verify, install, deploy`。

如果我们运行`mvn package`，Maven就会执行`default`生命周期，它会从开始一直运行到`package`这个phase为止：`validate,...package`。

如果我们运行`mvn compile`，Maven也会执行`default`生命周期，但这次它只会运行到`compile`，即以下几个phase：`validate,...compile`。

<u><font color=red>Maven另一个常用的生命周期是`clean`</font></u>，它会执行3个phase：`pre-clean,
clean`[^2]`, post-clean`。
[^2]:注意这个clean不是lifecycle而是phase

所以，使用`mvn`这个命令时，后面的参数是phase，Maven自动根据生命周期运行到指定的phase。

&emsp;&emsp;更复杂的例子是<font color=red>指定多个phase</font>，例如，运行`mvn clean package`，Maven先执行`clean生命周期`并运行到`clean这个phase`，然后执行`default生命周期`并运行到`package这个phase`，实际执行的phase如下：`pre-clean, clean, validate,..., package`。

&emsp;&emsp;在实际开发过程中，经常使用的命令有：
* `mvn clean`：清理所有生成的class和jar；
* `mvn clean compile`：先清理，再执行到`compile`；
* `mvn clean test`：先清理，再执行到`test`，因为<font color=red>执行`test`前必须执行`compile`</font>，所以这里不必指定`compile`；
* `mvn clean package`：先清理，再执行到`package`。

大多数phase在执行过程中，<font color=red>因为我们通常没有在`pom.xml`中配置相关的设置，所以这些phase什么事情都不做</font>。经常用到的phase其实只有几个：
* `mvn clean`：清理
* `mvn compile`：编译
* `mvn test`：运行测试
* `mvn package`：打包

## Goal
&emsp;&emsp;执行一个phase又会触发一个或多个goal(goal的命名总是`abc:xyz`这种形式)：
<div align=left><img src=MavenImages/Phase_Goal.png width=70%></div>

## 类比理解
&emsp;&emsp;为了便于理解上述概念，举一个如下类比：
* `lifecycle`相当于Java的`package`，它包含一个或多个phase；
* `phase`相当于Java的`class`，它包含一个或多个goal；
* `goal`相当于class的`method`，它其实才是真正干活的。

Maven通过lifecycle、phase和goal来提供标准的构建流程。通常情况，总是执行phase默认绑定的`goal`，因此<u>不必指定`goal`</u>。

# 使用插件
&emsp;&emsp;在前面介绍了Maven的lifecycle，phase和goal：<font color=red>使用Maven构建项目就是执行lifecycle，执行到指定的phase为止。每个phase会执行自己默认的一个或多个goal。goal是最小任务单元。</font>

&emsp;&emsp;以`compile`这个phase为例，如果执行：`mvn compile`，Maven将执行`compile`这个phase，这个phase会调用`compiler插件`执行关联的`compiler:compile`这个goal。

实际上，<font color=red>执行每个phase，都是通过某个插件（plugin）来执行的</font>，Maven本身其实并不知道如何执行`compile`，它只是负责找到对应的`compiler`插件，然后执行默认的`compiler:compile`这个goal来完成编译。

所以，使用Maven，实际上就是<font color=red>配置好需要使用的插件，然后通过phase调用它们。</font>

Maven已经内置了一些常用的标准插件：
<div align=left><img src=MavenImages/标准插件.png width=70%></div>

如果标准插件无法满足需求，我们还可以使用<font color=red>自定义插件</font>。使用自定义插件的时候，<font color=red>需要声明</font>。例如，使用`maven-shade-plugin`可以创建一个可执行的jar，要使用这个插件，需要在`pom.xml`中<font color=red>声明</font>它：
```xml {.line-numbers highlight=5}
<build>
    <plugins>
        <plugin>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-maven-plugin</artifactId>
            <configuration><fork>true</fork></configuration>
        </plugin>
    </plugins>
</build>
```
自定义插件往往需要一些<font color=red>配置</font>，例如，`maven-shade-plugin`需要指定Java程序的入口，它的配置是：
```xml {.line-numbers, highlight=1}
<configuration>
    <transformers>
        <transformer implementation="org.apache.maven.plugins.shade.resource.ManifestResourceTransformer">
            <mainClass>com.itranswarp.learnjava.Main</mainClass>
        </transformer>
    </transformers>
</configuration>
```
Maven通过自定义插件可以执行项目构建时需要的额外功能，<font color=red>使用自定义插件必须在`pom.xml`中声明插件及配置；插件会在某个phase被执行时执行</font>；插件的配置和用法需参考插件的官方文档。
