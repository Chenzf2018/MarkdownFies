# 参考资料

[快速入门Spring](https://www.bilibili.com/video/BV12k4y167jK?p=1)

# 1 概述

Spring框架是一个集众多**`设计模式`**于一身的`开源的`、`轻量级`的**`项目管理框架`**。致力于Java EE轻量级解决方案。

Spring框架用来`整合、管理`项目中的组件（单层框架），由于Spring框架可以帮我们生产项目中组件对象，因此也习惯称Spring是一个**工厂、容器（存放已创建的组件对象）**。

- **`组件`:** 项目中的`service`、`dao`、`action`，都是项目中的组件 
- Spring框架**不必通过`new`来创建组件对象**，而是通过其他方式来负责项目中**组件对象的创建、使用和销毁**
- Spring框架不负责**`entity`组件**的创建、使用和销毁

# 2 项目搭建

## 2.1 示例项目

该项目展示了，不使用`Spring`时，该如何创建、使用和销毁组件！

1. 创建模块

在`D:\MarkdownFiles\Spring\SpringDemo`路径下创建工程：

`GroupId`为`com.chenzf`；`AritfactId`为`demo_without_spring`：

<img src="Spring.assets/image-20201005140414543.png" alt="image-20201005140414543" style="zoom:70%;" />

设置`Maven`路径：

<img src="Spring.assets/image-20201005140726105.png" alt="image-20201005140726105" style="zoom:70%;" />

2. 完善目录结构

在`main`目录下新建`java`和`resources`并分别设置`Mark Directory as`：`Sources Root`和`Resources Root`。

3. 在`main.java`下新建`init`目录

   - 编写`UserDAO`接口：

   ```java
   package init;
   
   public interface UserDAO {
       void save(String name);
   }
   ```

   - 编写`USerDAOImpl`实现类

   ```java
   package init;
   
   public class UserDAOImpl implements UserDAO {
       
       @Override
       public void save(String name) {
           System.out.println("name = " + name);
       }
   }
   ```

   - 编写`TestUserDAO`类，**创建组件对象并使用**

   ```java
   package init;
   
   public class TestUserDAO {
       public static void main(String[] args) {
           // 创建组件对象
           UserDAOImpl user = new UserDAOImpl();
   
           // 使用组件
           user.save("chenzufeng");
   
           // 由JVM来销毁对象
       }
   }
   ```

## 2.2 第一个Spring程序

[Spring官网](https://spring.io/projects/spring-framework#learn)

### 2.2.1 创建模块

![image-20201005154702902](Spring.assets/image-20201005154702902.png)

### 2.2.1 引入项目依赖

在`pom.xml`中[引入Spring核心和依赖模块](https://mvnrepository.com/search?q=Spring)，并点击`Import Changes`：

```xml
<dependency>
      <groupId>org.springframework</groupId>
      <artifactId>spring-core</artifactId>
      <version>4.3.2.RELEASE</version>
  </dependency>
  <dependency>
      <groupId>org.springframework</groupId>
      <artifactId>spring-beans</artifactId>
      <version>4.3.2.RELEASE</version>
  </dependency>
  <dependency>
      <groupId>org.springframework</groupId>
      <artifactId>spring-web</artifactId>
      <version>4.3.2.RELEASE</version>
  </dependency>
  <dependency>
      <groupId>org.springframework</groupId>
      <artifactId>spring-expression</artifactId>
      <version>4.3.2.RELEASE</version>
  </dependency>
  <dependency>
      <groupId>org.springframework</groupId>
      <artifactId>spring-context</artifactId>
      <version>4.3.2.RELEASE</version>
  </dependency>
  <dependency>
      <groupId>org.springframework</groupId>
      <artifactId>spring-context-support</artifactId>
      <version>4.3.2.RELEASE</version>
  </dependency>
  <dependency>
      <groupId>org.springframework</groupId>
      <artifactId>spring-aspects</artifactId>
      <version>4.3.2.RELEASE</version>
  </dependency>
  <dependency>
      <groupId>org.springframework</groupId>
      <artifactId>spring-jdbc</artifactId>
      <version>4.3.2.RELEASE</version>
  </dependency>
  <dependency>
      <groupId>org.springframework</groupId>
      <artifactId>spring-aop</artifactId>
      <version>4.3.2.RELEASE</version>
  </dependency>
```

### 2.2.2 引入Spring框架配置文件

- 配置文件名称：任意名称
  - `applicationContext.xml`或`Spring.xml`
- 配置文件位置：项目中根下任意位置
  - `resources.init`目录下
- 配置文件内容

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">

</beans>
```

### 2.2.3 创建组件

- 在`main.java.init`目录下创建`UserDAO`接口：

```java
package init;

public interface UserDAO {
    void save(String name);
}
```

- 在`main.java.init`目录下创建`UserDAOImpl`实现类：

```java
package init;

public class UserDAOImpl implements UserDAO {

    @Override
    public void save(String name) {
        System.out.println("name = " + name);
    }
}
```

### 2.2.4 工厂管理

工厂管理比手动（`new`）创建要轻量：工厂创建对象默认是`单例模式`，无论从工厂中获取多少个对象，获得的都是`同一个对象`；而通过`new`创建对象，每`new`一次，就在`JVM`中创建了一个新的对象！

通过`spring.xml`配置文件来管理组件：**使用`bean`标签来创建组件对象**

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">

        <!--通过Spring管理组件
            bean: 用来管理组件对象的创建
            class: 用来指定管理组件对象的全限定名 包.类
 				   肯定是实现类，只有实现类才能创建对象
            id: 用来指定Spring框架创建的当前组件在Spring框架（容器、工厂）中的（全局）唯一标识，
                方便获取Spring框架中已经创建好的对象
				推荐使用当前实现类的接口首字母小写userDAO-->
        <bean class="init.UserDAOImpl" id="userDAO"></bean>

</beans>
```

### 2.2.5 测试

启动工厂，获取对象，进行测试：

在`main.java.init`下创建`TestSpring.java`

```java
package init;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestSpring {
    public static void main(String[] args) {
        // 启动工厂
        // 读取配置文件，根据指定的类创建组件对象，并为其取了唯一的标识
        ApplicationContext context = new ClassPathXmlApplicationContext("/init/spring.xml");

        // 获取对象
        // 参数：获取工厂中指定的唯一标识
        // context.getBean("UserDAO") 返回Object，需要强制转换
        UserDAO userDAO = (UserDAO) context.getBean("userDAO");
		System.out.println(userDAO);
        userDAO.save("chenzf");
    }
}
```

输出：

```markdown
init.UserDAOImpl@12108b5
name = chenzf
```

# 3 Spring框架的核心思想

## 3.1 IOC(Inversion of Control)

控制反转（**控制权力反转**）：将对象的创建由原来(`new`)的方式（在任意地方随意创建）转移到`配置文件`中，交给`Spring工厂`来创建对象。在此基础上，还需通过`DI`的方式维护组件与组件之间的调用关系！

## 3.2 DI(Dependency Injection)

Spring不仅要`创建对象`，还要在创建对象时`维护组件与组件的依赖关系`！

依赖注入：解决**`组件之间的调用`**关系问题；为组件中成员变量完成赋值过程。

语法：

- 组件对象中需要哪个组件，就将该组件声明为成员变量并提供公开的`SET`方法
- 在Spring的配置文件里对应的`组件标签`内使用`property`完成属性的`赋值操作`

### 3.2.1 控制反转过程

- 在`java`下新建`di`目录，然后创建`DeptDAO`接口并实现

  - `DeptDAO`接口

    ```java
    package di;
    
    public interface DeptDAO {
        void save(String name);
    }
    ```

  - `DeptDAOImpl`实现类

    ```java
    package di;
    
    public class DeptDAOImpl implements DeptDAO {
        @Override
        public void save(String name) {
            System.out.println("验证依赖注入：name = " + name);
        }
    }
    ```

- 在`resources`下新建`di`目录，然后创建`springDI.xml`，管理`DAO`组件

  ```java
  <?xml version="1.0" encoding="UTF-8"?>
  <beans xmlns="http://www.springframework.org/schema/beans"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">
  
      <!--通过Spring管理组件
          bean: 用来管理组件对象的创建
          class: 用来指定管理组件对象的全限定名 包.类
          id: 用来指定Spring框架创建的当前组件在Spring框架（容器、工厂）中的（全局）唯一标识，
              方便获取Spring框架中已经创建好的对象
              推荐使用当前实现类的接口首字母小写userDAO-->
      <bean class="di.DeptDAOImpl" id="deptDAO"></bean>
  
  </beans>
  ```

- 在`java.di`下创建`TestSpringDI.java`

  ```java
  package di;
  
  import org.springframework.context.ApplicationContext;
  import org.springframework.context.support.ClassPathXmlApplicationContext;
  
  public class TestSpringDI {
      public static void main(String[] args) {
          // 启动工厂
          // 读取配置文件，根据指定的类创建组件对象，并为其取了唯一的标识
          ApplicationContext context = new ClassPathXmlApplicationContext("/di/springDI.xml");
  
          // 获取对象
          // 参数：获取工厂中指定的唯一标识
          // context.getBean("DeptDAO") 返回Object，需要强制转换
          DeptDAO deptDAO = (DeptDAO) context.getBean("deptDAO");
          System.out.println(deptDAO);
          deptDAO.save("chenzf");
  
      }
  }
  ```

- 运行输出：

  ```
  di.DeptDAOImpl@12108b5
  验证依赖注入(DeptDAOImpl)：name = chenzf
  ```

### 3.2.2 基于控制反转的依赖注入

- 在`java.di`下创建`DeptService`接口并实现

  - `DeptService`接口

    ```java
    package di;
    
    public interface DeptService {
        void save(String name);
    }
    ```

  - `DeptServiceImpl`实现类

    ```java
    package di;
    
    public class DeptServiceImpl implements DeptService {
        @Override
        public void save(String name) {
            System.out.println("验证依赖注入(DeptServiceImpl)：name = " + name);
        }
    }
    ```

- 修改`springDI.xml`，管理`Service`组件

  ```xml
  <?xml version="1.0" encoding="UTF-8"?>
  <beans xmlns="http://www.springframework.org/schema/beans"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">
  
      <!--管理DAO组件-->
      <bean class="di.DeptDAOImpl" id="deptDAO"></bean>
  
      <!--管理Service组件-->
      <bean class="di.DeptServiceImpl" id="deptService"></bean>
  
  </beans>
  ```

- 修改`TestSpringDI.java`

  ```java
  package di;
  
  import org.springframework.context.ApplicationContext;
  import org.springframework.context.support.ClassPathXmlApplicationContext;
  
  public class TestSpringDI {
      public static void main(String[] args) {
          // 启动工厂
          ApplicationContext context = new ClassPathXmlApplicationContext("/di/springDI.xml");
  
          // 获取service组件
          DeptService deptService = (DeptService) context.getBean("deptService");
          deptService.save("chenzufeng");
      }
  }
  ```

- 运行输出：`验证依赖注入(DeptServiceImpl)：name = chenzufeng`

#### `Service`组件调用`DAO`组件

- 在`di.DeptServiceImpl`中注入依赖——事务处理最终需要调取`DAO`

  - 原始方式：在`di.DeptServiceImpl`中创建一个对象：==第6行和第11行==

    ```java
    package di;
    
    public class DeptServiceImpl implements DeptService {
    
        // 依赖DAO组件
        private DeptDAO deptDAO = new DeptDAOImpl();
    
        @Override
        public void save(String name) {
            System.out.println("验证依赖注入(DeptServiceImpl)：name = " + name);
            deptDAO.save(name);
        }
    }
    ```

  - 运行`TestSpringDI.java`输出：

    ```
    验证依赖注入(DeptServiceImpl)：name = chenzufeng
    验证依赖注入(DeptDAOImpl)：name = chenzufeng
    ```

- 工厂中已经有了`DeptDAO`对象，无需再使用`new`方式创建，`依赖注入即向需要的对象赋值`

  - 组件对象中需要哪个组件，就将该组件声明为成员变量并提供公开的`SET`方法（右键->`Generate`->`Setter`）

    ```java
    // 依赖DAO组件
    private DeptDAO deptDAO;
    
    // 公开的Set方法
    public void setDeptDAO(DeptDAO deptDAO) {
        this.deptDAO = deptDAO;
    }
    ```

    此时运行会出现`空指针`（`private DeptDAO deptDAO;`）！

  - 在Spring的配置文件里对应的`组件标签`内完成属性的`赋值操作`：==第9-15行==

    ```xml
    <?xml version="1.0" encoding="UTF-8"?>
    <beans xmlns="http://www.springframework.org/schema/beans"
           xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
           xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">
    
        <!--管理DAO组件-->
        <bean class="di.DeptDAOImpl" id="deptDAO"></bean>
    
        <!--管理Service组件，该组件依赖DAO组件-->
        <bean class="di.DeptServiceImpl" id="deptService">
            <!--依赖的注入
                property: 用来给组件中的属性进行赋值
                name: 用来指定给组件中哪个属性名进行赋值
                ref: 用来指定赋值对象在工厂中的唯一标识，即DAO组件bean的id-->
            <property name="deptDAO" ref="deptDAO"/>
        </bean>
    
    </beans>
    ```

  - 运行结果

    ```markdown
    验证依赖注入(DeptServiceImpl)：name = chenzufeng
    验证依赖注入(DeptDAOImpl)：name = chenzufeng
    ```

# 4 Spring注入方式

- SET注入——重点
  - 使用成员变量SET方法进行赋值
- 构造注入
  - 使用构造方法的形式进行属性赋值
- 自动注入
  - 通过在配置文件中完成类中属性自动赋值

## 4.1 SET注入语法

### 4.1.1 基本类型+String类型+日期类型的注入

`八种基本类型`、`String类型`与`Date`注入时，使用`value`属性进行赋值，其中`Date`的格式为`yyyy/MM/dd HH:mm:ss`

新建`injection`文件夹，添加`InjectDAO`、`InjectService`、`applicationContext`等文件，完成基本测试环境的搭建！

1. 注入`String`类型的名称

   在`InjectServiceImpl`中进行修改：添加==第12-16行，第23行==

   ```java
   package injection;
   
   public class InjectServiceImpl implements InjectService {
   
       // 依赖InjectDAO组件
       private InjectDAO injectDAO;
       // 提供SET方法
       public void setInjectDAO(InjectDAO injectDAO) {
           this.injectDAO = injectDAO;
       }
   
       private String name;
   
       public void setName(String name) {
           this.name = name;
       }
   
       @Override
       public void testInject(String string) {
           System.out.println("Test Injection(InjectService): " + string);
           injectDAO.testInject(string);
   
           System.out.println("String类型注入一个名字: " + name);
       }
   }
   ```

   在`applicationContext.xml`中添加：

   ```xml
   <!--注入String-->
   <property name="name" value="chenzufeng"/>
   ```

   输出：`String类型注入一个名字: chenzufeng`

2. 注入`Integer`类型

   在`InjectServiceImpl`中添加

   ```java
   /**
   * SET注入Integer
   */
   private Integer age;
   
   public void setAge(Integer age) {
       this.age = age;
   }
   ```

   在`applicationContext.xml`中添加：

   ```xml
   <!--注入Integer-->
   <property name="age" value="27"/>
   ```

   输出：`Integer类型注入年龄: 27`

3. 注入`Date`类型

   在`InjectServiceImpl`中添加

   ```java
   /**
   * SET注入Date类型
   */
   private Date date;
   
   public void setDate(Date date) {
       this.date = date;
   }
   ```

   在`applicationContext.xml`中添加

   ```xml
   <!--注入Date，日期格式为yyyy/MM/dd HH:mm:ss-->
   <property name="date" value="1993/11/10"/>
   ```

   输出：`Date类型注入一个日期：Wed Nov 10 00:00:00 CST 1993`

### 4.1.2 数组类型注入

在`InjectServiceImpl`中添加

```java
/**
* 注入数组
*/
private String[] stringArray;

public void setStringArray(String[] stringArray) {
    his.stringArray = stringArray;
}

private InjectDAO[] injectDAOS;

public void setInjectDAOS(InjectDAO[] injectDAOS) {
    this.injectDAOS = injectDAOS;
}

System.out.println("遍历数组");
for (String str : stringArray) {
    System.out.print(str + " ");
}
```

在`applicationContext.xml`中添加

```xml
<!--注入数组-->
<property name="stringArray">
    <array>
        <value>chen</value>
        <value>zu</value>
        <value>feng</value>
    </array>
</property>

<property name="injectDAOS">
    <array>
        <ref bean="injectDAO"/>
        <ref bean="injectDAO"/>
        <ref bean="injectDAO"/>
    </array>
</property>
```

输出：

```
遍历数组
chen zu feng 
injection.InjectDAOImpl@1536d79
injection.InjectDAOImpl@1536d79
injection.InjectDAOImpl@1536d79
```

由结果可知，[工厂是使用单例模式创建对象的](# 2.2.4-工厂管理)！

### 4.1.3 引用类型注入

使用`ref`进行注入

在`InjectServiceImpl`中添加

```java
/**
* 依赖InjectDAO组件
* 提供SET方法
*/
private InjectDAO injectDAO;

public void setInjectDAO(InjectDAO injectDAO) {
    this.injectDAO = injectDAO;
}
```

在`applicationContext.xml`中添加

```xml
<property name="injectDAO" ref="injectDAO"/>
```

### 4.1.4 集合类型注入

- 注入`List、Map`

  - 在`InjectServiceImpl`中添加

  ```java
  /**
  * 注入集合
  */
  private List<String> habbies;
  
  public void setHabbies(List<String> habbies) {
      this.habbies = habbies;
  }
  
  private Map<String, String> maps;
  
  public void setMaps(Map<String, String> maps) {
      this.maps = maps;
  }
  
  System.out.println("遍历集合");
  habbies.forEach(habby -> System.out.println("habby: " + habby));
  
  maps.forEach((key, value) -> System.out.println("key: " + key + "; value: " + value));
  ```

  第11行使用了`Lambda`表达式，`IDEA`报错：`Lambda expressions are not supported at language level '7'`。因此需要在`Project Structure`中做以下修改：

  ![image-20201005220411000](Spring.assets/image-20201005220411000.png)

  - 在`applicationContext.xml`中添加

  ```xml
  <!--注入集合-->
  <property name="habbies">
      <list>
          <value>看书</value>
          <value>打球</value>
      </list>
  </property>
  
  <!--key; key-ref-->
  <property name="maps">
      <map>
          <entry key="1" value="chen"/>
          <entry key="2" value="zufeng"/>
      </map>
  </property>
  ```

  - 输出：

  ```
  遍历集合
  habby: 看书
  habby: 打球
  key: 1; value: chen
  key: 2; value: zufeng
  ```

- 注入`Properties`——特殊的`Map`，只能放文本字符串

  - 在`InjectServiceImpl`中添加

  ```java
  /**
  * 注入Properties
  */
  private Properties properties;
  
  public void setProperties(Properties properties) {
      this.properties = properties;
  }
  
  System.out.println("遍历配置文件Properties");
  properties.forEach((key, value) -> System.out.println("配置文件key: " + key + "; value: " + value));
  ```

  - 在`applicationContext.xml`中添加

  ```xml
  <!--注入Properties：特殊的map，只能放文本字符串-->
  <property name="properties">
      <props>
          <prop key="driver">com.mysql.jdbc</prop>
          <prop key="url">jdbc:mysql://localhost:3306/test</prop>
          <prop key="username">root</prop>
          <prop key="password">admin</prop>
      </props>
  </property>
  ```

  - 运行结果

  ```
  遍历配置文件Properties
  key: username; value: root
  key: driver; value: com.mysql.jdbc
  key: url; value: jdbc:mysql://localhost:3306/test
  key: password; value: admin
  ```

  

  