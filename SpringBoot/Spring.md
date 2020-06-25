# IOC/DI

Spring是一个基于`IOC`和`AOP`的结构J2EE系统的框架。

`IOC(Inversion Of Control)反转控制`是Spring的基础，简单说就是创建对象由以前的程序员自己`new构造方法`来调用，变成了交由Spring创建对象。对象的生命周期由Spring来管理，直接从Spring那里去获取一个对象。就像控制权从本来在自己手里，交给了Spring。

- 传统方式：相当于你自己去菜市场`new`了一只鸡，不过是生鸡，要自己拔毛，去内脏，再上花椒，酱油，烤制，经过各种工序之后，才可以食用。

- `IOC`相当于去馆子(Spring)点了一只鸡，交到你手上的时候，已经五味俱全，你就只管吃就行了。

`DI(Dependency Inject)依赖注入`. 简单地说就是拿到的对象的属性，已经被注入好相关值了，直接使用即可。

`Category.java`

```java
package com.how2java.pojo;
 
public class Category {
 
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    private int id;
    private String name;
}
```

在`src`目录下新建`applicationContext.xml`文件。`applicationContext.xml`是Spring的核心配置文件，通过关键字`c`即可获取Category对象，该对象获取的时候，即被注入了字符串`category 1`到`name`属性中。通过Spring拿到的Category对象的name属性。

`applicationContext.xml`

```XML
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xmlns:aop="http://www.springframework.org/schema/aop"
    xmlns:tx="http://www.springframework.org/schema/tx"
    xmlns:context="http://www.springframework.org/schema/context"
    xsi:schemaLocation="
   http://www.springframework.org/schema/beans 
   http://www.springframework.org/schema/beans/spring-beans-3.0.xsd
   http://www.springframework.org/schema/aop 
   http://www.springframework.org/schema/aop/spring-aop-3.0.xsd
   http://www.springframework.org/schema/tx 
   http://www.springframework.org/schema/tx/spring-tx-3.0.xsd
   http://www.springframework.org/schema/context      
   http://www.springframework.org/schema/context/spring-context-3.0.xsd">
 
    <bean name="c" class="com.how2java.pojo.Category">
        <property name="name" value="category 1" />
    </bean>
 
</beans>
```

`TestSpring.java`

```java
package com.how2java.test;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.how2java.pojo.Category;

public class TestSpring {

	public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext(new String[] { "applicationContext.xml" });
        
        Category c = (Category) context.getBean("c");
        System.out.println(c.getName());
    }
}
```

# 注入对象

在上例中，**对`Category`的`name`属性注入了`category 1`字符串**。在本例中，对`Product`对象，注入一个`Category`对象。

`Product.java`

```java
package com.how2java.pojo;

public class Product {
    private int id;
    private String name;
    private Category category;

    public int getId(){
        return id;
    }
    public void setId(int id){
        this.id = id;
    }

    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name = name;
    }

    public Category getCategory(){
        return category;
    }
    public void setCategory(Category category){
        this.category = category;
    }
}
```

在创建Product的时候注入一个Category对象。注意，这里要使用`ref`来注入另一个对象。

`applicationContext.xml`

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xmlns:aop="http://www.springframework.org/schema/aop"
    xmlns:tx="http://www.springframework.org/schema/tx"
    xmlns:context="http://www.springframework.org/schema/context"
    xsi:schemaLocation="
   http://www.springframework.org/schema/beans 
   http://www.springframework.org/schema/beans/spring-beans-3.0.xsd
   http://www.springframework.org/schema/aop 
   http://www.springframework.org/schema/aop/spring-aop-3.0.xsd
   http://www.springframework.org/schema/tx 
   http://www.springframework.org/schema/tx/spring-tx-3.0.xsd
   http://www.springframework.org/schema/context      
   http://www.springframework.org/schema/context/spring-context-3.0.xsd">
 
    <bean name = "c" class = "com.how2java.pojo.Category">
        <property name = "name" value = "category 1" />
    </bean>

    <bean name = 'p' class = "com.how2java.pojo.Product">
        <property name = "name" value = "product 1"/>
        <property name = "category" ref = "c"/>
    </bean>
 
</beans>
```

通过Spring拿到的Product对象已经**被注入了Category对象**了：

```java
package com.how2java.test;

import com.how2java.pojo.Product;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestSpring { 
    public static void main(String[] args) { 
        ApplicationContext context = new ClassPathXmlApplicationContext(new String[] { "applicationContext.xml" });

		Product product = (Product) context.getBean("p");

        System.out.println(product.getName());
        System.out.println(product.getCategory().getName());
	}
}
/*
product 1
category 1
 */
```

