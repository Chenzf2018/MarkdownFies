# 购物车类关系

1. Product 产品
2. User 用户
3. Order 订单
4. OrderItem 订单项：购物产品种类、数量等

<div align=center><img src=Pictures\类关系图.png></div>


# 产品模块

## 创建项目

<div align=center><img src=Pictures\创建项目.jpg></div>

## 创建数据库

1. 创建数据库cart
2. 在cart数据库中创建表product
3. 为product准备4条数据

注：插入中文的时候，如果失败,要记得把表的编码方式修改为UTF-8。

```SQL
DROP DATABASE IF EXISTS cart;
CREATE DATABASE cart DEFAULT CHARACTER SET utf8;
USE cart;

DROP TABLE IF EXISTS product;
CREATE TABLE product(
       id INT(11) NOT NULL AUTO_INCREMENT,
       name VARCHAR(50) DEFAULT NULL,
       price FLOAT DEFAULT NULL,
       PRIMARY KEY (id)
)      ENGINE=InnoDB DEFAULT CHARSET=UTF8;

INSERT INTO product VALUES(null, '丝袜', 500);
INSERT INTO product VALUES(null, '娃娃', 2500);
INSERT INTO product VALUES(null, '鞭子', 180);
INSERT INTO product VALUES(null, '蜡烛', 0.2);
```

## Product.java

```java
package bean;

public class Product {
    private int id;
    private String name;
    private float price;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public float getPrice() { return price; }
    public void setPrice() { this.price = price;}
}
```

## ProductDAO

### 为web应用导入jar包

**为web应用导入`mysql-jdbc`的jar包**与为项目导入mysql-jdbc的jar包不同：需要把mysql的jar包放在`WEB-INF/lib`目录下，放在`WEB-INF/lib`下指的是能够web应用中找到对应的class。

### 指定输出目录

<div align=center><img src=Pictures\指定输出目录.jpg></div>

把项目的class文件输出由原来的`out`设置到`web/WEB-INF/classes`下。

tomcat启动之后，在默认情况下，不会去out目录找这些class文件，而是到WEB-INF/classes这个目录下去寻找。

### ProductDAO提供对Product的查询

```java
package dao;

import bean.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO {
    public static void main(String[] args) {
        System.out.println(new ProductDAO().ListProduct().size());
    }

    public List<Product> ListProduct() {
        List<Product> products = new ArrayList<>();

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection(
                    "jdbc:mysql://127.0.0.1:3306/cart?characterEncoding=UTF-8",
                    "root", "admin"
            );
            String sql = "SELECT * FROM product ORDER BY id DESC";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Product product = new Product();

                int id = resultSet.getInt(1);
                String name = resultSet.getString(2);
                float price = resultSet.getFloat(3);

                product.setId(id);
                product.setName(name);
                product.setPrice(price);
                
                products.add(product);
            }

            preparedStatement.close();
            connection.close();

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return products;
    }
}
```

## ProductListServlet

导入`servlet-api.jar`。

ProductListServlet的作用是**通过`ProductDAO`把product从数据库查出来**，然后通过`listProduct.jsp`显示出来。

```java
package servlet;

import bean.Product;
import dao.ProductDAO;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class ProductListServlet extends HttpServlet {
    protected void service(HttpServletRequest request, HttpServletResponse response) {
        try {
            List<Product> products = new ProductDAO().ListProduct();
            
            request.setAttribute("products", products);
            request.getRequestDispatcher("listProduct.jsp").forward(request, response);
            
        } catch (ServletException | IOException e) {
            e.printStackTrace();
        }
    }P
}
```

## listProduct.jsp



`JSTL(JSP Standard Tag Library)`标准标签库，JSTL允许开人员可以像使用HTML标签 那样在JSP中开发Java功能。

为了能够在JSP中使用JSTL，需要两个jar包，分别是`jstl.jar`和`standard.jar`。

`listProduct.jsp`能显示产品名称，价格，并为每种商品提供一个购买按钮，位于`D:\Learning_Java\Java_Code\Cart\web\listProduct.jsp`。

```
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="java.util.*"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<table align='center' border='1' cellspacing='0'>
	<tr>
		<td>id</td>
		<td>名称</td>
		<td>价格</td>
		<td>购买</td>
	</tr>
	<c:forEach items="${products}" var="product" varStatus="st">
		<tr>
			<td>${product.id}</td>
			<td>${product.name}</td>
			<td>${product.price}</td>
			<td>
			
			<form action="addOrderItem" method="post">
			
			数量<input type="text" value="1" name="num">
			<input type="hidden" name="pid" value="${product.id}">
			<input type="submit" value="购买">
			</form>
			 
		</tr>
	</c:forEach>
</table>
```

## 配置web.xml

进行产品查询Servlet的配置，位于`D:\Learning_Java\Java_Code\Cart\web\WEB-INF\web.xml`：

```xml
<?xml version="1.0" encoding="UTF-8"?>
<web-app>

    <servlet>
        <servlet-name>ProductListServlet</servlet-name>
        <servlet-class>servlet.ProductListServlet</servlet-class>
    </servlet>

    <servlet-mapping>
        <servlet-name>ProductListServlet</servlet-name>
        <url-pattern>/listProduct</url-pattern>
    </servlet-mapping>

</web-app>
```

## 配置Tomcat

点击`run`中的`Edit Configurations`：

<div align=center><img src=Pictures\配置Tomcat.jpg></div>

也许看不到`Tomcat Server`，点击`34items more(irrelevant)`，里面有`Tomcat Server`。

**指定Tomcat路径**：

<div align=center><img src=Pictures\指定Tomcat路径.jpg></div>

**设置部署：**
点击Artifact：
<div align=center><img src=Pictures\设置部署.jpg></div>


## 运行并测试

输入`http://127.0.0.1:8090/listProduct`：

<div align=center><img src=Pictures\产品模块.jpg></div>


# 用户模块

## SQL

```SQL
DROP TABLE IF EXISTS user;
CREATE TABLE user(
     id INT(11) NOT NULL AUTO_INCREMENT,
     name VARCHAR(225),
     password VARCHAR(50),
     PRIMARY KEY (id)
)    ENGINE=InnoDB DEFAULT CHARSET=UTF8;

INSERT INTO user VALUES(null, 'tom', '123');
```

## User

```java
package bean;

public class User {
    private int id;
    private String name;
    private String password;
    
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}
```

## UserDAO

根据name和password查询表user，如果有数据就表示账号密码正确。

```java
package dao;

import bean.User;

import java.sql.*;

public class UserDAO {
    public static void main(String[] args) {
        System.out.println(new UserDAO().getUser("tom", "123").getId());
    }

    public User getUser(String name, String password) {
        User user = null;

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection(
                    "jdbc:mysql://127.0.0.1:3306/cart?characterEncoding=UTF-8",
                    "root", "admin"
            );
            String sql = "SELECT * FROM user WHERE name = ? AND password = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, password);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                user = new User();
                user.setId(resultSet.getInt(1));
                user.setName(name);
                user.setPassword(password);
            }
            
            preparedStatement.close();
            connection.close();
            
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        
        return user;
    }
}
```

## login.jsp

登录页面：

```
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="java.util.*"%>

<!DOCTYPE html>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<form action="login" method="post">
	账号：<input type="text" name="name"> <br>
	密码：<input type="password" name="password"> <br>
	<input type="submit" value="登录">
</form>
```

## UserLoginServlet

登陆Servlet，通过name和password获取user对象。如果对象不为空，就表示账号密码正确，跳转到产品显示界面`/listProduct`；如果对象为空，就跳转到登陆界面，重新登陆。

```java
package servlet;

import bean.User;
import dao.UserDAO;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UserLoginServlet extends HttpServlet {
    protected void service(HttpServletRequest request, HttpServletResponse response) {
        try {
            String name = request.getParameter("name");
            String password = request.getParameter("password");
            User user = new UserDAO().getUser(name, password);
            if (user != null) {
                request.getSession().setAttribute("user", user);
                response.sendRedirect("/listProduct");
            } else {
                response.sendRedirect("login.jsp");
            }
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
```

## 配置web.xml

在web.xml中为路径`/login`加上相关配置：

```xml
<servlet>
    <servlet-name>UserLoginServlet</servlet-name>
    <servlet-class>servlet.UserLoginServlet</servlet-class>
</servlet>

<servlet-mapping>
    <servlet-name>UserLoginServlet</servlet-name>
    <url-pattern>/login</url-pattern>
</servlet-mapping>
```

## listProduct.jsp

修改listProduct.jsp，如果用户登陆了，就显示用户的名字：
```
<c:if test="${!empty user}">
  <div align="center">
	当前用户: ${user.name}
  </div>
</c:if>
```

## 测试

输入：`http://localhost:8090/login`：

<div align=center><img src=Pictures\用户模块.jpg></div>

<div align=center><img src=Pictures\用户模块1.jpg></div>

# 购物车模块

从开发者的角度来看，**购买行为**就是**创建一条一条的订单项**。而显示购物车，也就是把这些订单项显示在页面上。

在这个阶段，订单项都会保存在session中，直到最后生成订单的时候，才会把这些订单项保存在数据库中。

## OrderItem

OrderItem使用属性Product类型的product，而非int类型的pid，因为在后续显示购物车的时候，可以很简单的通过el表达式就显示商品名称和价格了。

```java
package bean;

public class OrderItem { 
    private int id;
    private Product product;
    private int num;
    
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public Product getProduct() { return product; }
    public void setProduct(Product product) { this.product = product; }
    
    public int getNum() { return num; }
    public void setNum(int num) { this.num = num; }
}
```

## ProductDAO

因为购买的时候，提交到服务器的是pid，而OrderItem类的product属性是一个Product类型。所以ProductDAO需要根据id获取Product对象。

```java
package dao;

import bean.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO {
    public static void main(String[] args) {
        // System.out.println(new ProductDAO().ListProduct().size());
        System.out.println(new ProductDAO().getProduct(1).getName());
    }

    public Product getProduct(int id) {
        Product product = null;

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection(
                    "jdbc:mysql://127.0.0.1:3306/cart?characterEncoding=UTF-8",
                    "root", "admin"
            );
            String sql = "SELECT * FROM product WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                product = new Product();
                product.setId(id);

                String name = resultSet.getString(2);
                float price = resultSet.getFloat(3);
                
                product.setName(name);
                product.setPrice(price);
            }
            
            preparedStatement.close();
            connection.close();

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        
        return product;
    }

    public List<Product> ListProduct() {
        List<Product> products = new ArrayList<>();

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection(
                    "jdbc:mysql://127.0.0.1:3306/cart?characterEncoding=UTF-8",
                    "root", "admin"
            );
            String sql = "SELECT * FROM product ORDER BY id DESC";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Product product = new Product();

                int id = resultSet.getInt(1);
                String name = resultSet.getString(2);
                float price = resultSet.getFloat(3);

                product.setId(id);
                product.setName(name);
                product.setPrice(price);

                products.add(product);
            }

            preparedStatement.close();
            connection.close();

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return products;
    }
}
```

## 购买商品OrderItemAddServlet

购买行为本身就是**创建一个OrderItem对象**。在负责购买商品的OrderItemAddServlet中，进行如下流程
1. 获取购买数量
2. 获取购买商品的id
3. 根据id获取商品对象
4. 创建一个新的OrderItem对象
5. 从session中取出一个List，这个List里面存放陆续购买的商品。
    如果是第一次从session中获取该List，那么它会是空的，需要创建一个ArrayList。
6. 把新创建的OrderItem对象放入该List中
7. 跳转到显示购物车的listOrderItem
   
```java
package servlet;

import bean.OrderItem;
import bean.Product;
import dao.ProductDAO;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class OrderItemAddServlet extends HttpServlet {
    protected void service(HttpServletRequest request, HttpServletResponse response) {
        try {
            int num = Integer.parseInt(request.getParameter("num"));
            int pid = Integer.parseInt(request.getParameter("pid"));

            Product product = new ProductDAO().getProduct(pid);
            OrderItem orderItem = new OrderItem();
            orderItem.setNum(num);
            orderItem.setProduct(product);

            List<OrderItem> orderItems = (List<OrderItem>) request.getSession().getAttribute("orderItems");
            if (orderItems == null) {
                orderItems = new ArrayList<>();
                request.getSession().setAttribute("orderItems", orderItems);
            }
            
            orderItems.add(orderItem);
            
            response.sendRedirect("/listOrderItem");
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
```

## 显示购物车内容

显示购物车的`OrderItemListServlet`其实什么也没做，因为数据已经在session准备好了，直接服务端跳转到`listOrderItem.jsp`。

在listOrderItem.jsp中，从session中遍历出所有的OrderItem。因为保存在OrderItem上的是一个Product对象，所以很容易就可以通过EL表达式遍历出商品的名称和价格。

```java
package servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class OrderItemListServlet extends HttpServlet {
    protected void service(HttpServletRequest request, HttpServletResponse response) {
        try {
            request.getRequestDispatcher("listOrderItem.jsp").forward(request, response);
        } catch (ServletException | IOException e) {
            e.printStackTrace();
        }
    }
}
```

**listOrderItem.jsp**
```
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="java.util.*" isELIgnored="false"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<h1 align="center" >购物车</h1>
<table align='center' border='1' cellspacing='0'>
	<tr>
		<td>商品名称</td>
		<td>单价</td>
		<td>数量</td>
		<td>小计</td>
	</tr>

	<c:forEach items="${orderItems}" var="orderItem" varStatus="st">
		<tr>
			<td>${orderItem.product.name}</td>
			<td>${orderItem.product.price}</td>
			<td>${orderItem.num}</td>
			<td>${orderItem.product.price*orderItem.num}</td>
		</tr>
	</c:forEach>
</table>
```

## 购买相同商品

遍历session中所有的OrderItem，如果找到对应的product.id一样的条目，就调整其数量
如果没有找到，就新增加一条。

```java {.line-numbers highlight=31}
package servlet;

import bean.OrderItem;
import bean.Product;
import dao.ProductDAO;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class OrderItemAddServlet extends HttpServlet {
    protected void service(HttpServletRequest request, HttpServletResponse response) {
        try {
            int num = Integer.parseInt(request.getParameter("num"));
            int pid = Integer.parseInt(request.getParameter("pid"));

            Product product = new ProductDAO().getProduct(pid);
            OrderItem orderItem = new OrderItem();
            orderItem.setNum(num);
            orderItem.setProduct(product);

            List<OrderItem> orderItems = (List<OrderItem>) request.getSession().getAttribute("orderItems");
            if (orderItems == null) {
                orderItems = new ArrayList<>();
                request.getSession().setAttribute("orderItems", orderItems);
            }

            boolean found = false;
            for (OrderItem orderItem1 : orderItems) {
                if (orderItem1.getProduct().getId() == orderItem.getProduct().getId()) {
                    orderItem1.setNum(orderItem1.getNum() + orderItem.getNum());
                    found = true;
                    break;
                }
            }

            if (!found)
                orderItems.add(orderItem);

            response.sendRedirect("/listOrderItem");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
```

## 配置web.xml

```xml
<servlet>
    <servlet-name>OrderItemAddServlet</servlet-name>
    <servlet-class>servlet.OrderItemAddServlet</servlet-class>
</servlet>

<servlet-mapping>
    <servlet-name>OrderItemAddServlet</servlet-name>
    <url-pattern>/addOrderItem</url-pattern>
</servlet-mapping>
    
<servlet>
    <servlet-name>OrderItemListServlet</servlet-name>
    <servlet-class>servlet.OrderItemListServlet</servlet-class>
</servlet>
    
<servlet-mapping>
    <servlet-name>OrderItemListServlet</servlet-name>
    <url-pattern>/listOrderItem</url-pattern>
</servlet-mapping>
```

## 测试

从**登陆页面**开始，登陆后到产品**显示页面**，然后**修改**购买数量，最后点击购买：`http://127.0.0.1:8090/login`

显示页面：
<div align=center><img src=Pictures\购物车模块.jpg></div>

购物车：
<div align=center><img src=Pictures\购物车模块1.jpg></div>
<div align=center><img src=Pictures\购物车模块2.jpg></div>


# 订单模块

## SQL

创建order表，里面有一个uid字段用于表明该订单属于哪个用户。order是关键字，不能直接用作表名，通常的做法是加一个下划线`order_`。

创建orderitem，表里有id、pid、num、oid等字段，分别表示主键，商品对应的id，购买数量以及订单id。

```SQL
USE cart;

CREATE TABLE order_(
       id INT(11) AUTO_INCREMENT,
       uid INT(11),
       PRIMARY KEY (id)
)      ENGINE=InnoDB DEFAULT CHARSET=UTF8;

CREATE TABLE orderItem(
       id INT(11) AUTO_INCREMENT,
       pid INT(11),
       num INT(11),
       oid INT(11),
       PRIMARY KEY (id)
)      ENGINE=InnoDB DEFAULT CHARSET=UTF8;
```

## Order

与OrderItem类似的，会有一个User属性，而不是使用int类型的uid。

```java
package bean;

public class Order {
    int id;
    User user;
    
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
}
```

## OrderItem

OrderItem在原来的基础上，增加一个Order属性：

```java {.line-numbers highlight=7-10}
package bean;

public class OrderItem {
    private int id;
    private Product product;
    private int num;
    private Order order;

    public Order getOrder() { return order; }
    public void setOrder(Order order) { this.order = order; }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public Product getProduct() { return product; }
    public void setProduct(Product product) { this.product = product; }

    public int getNum() { return num; }
    public void setNum(int num) { this.num = num; }
}
```

## OrderDAO

OrderDAO把订单对象保存到数据库中。

这里需要注意的是，Order对象保存到数据库中后，该对象就会有对应的id，这个id，在后续保存OrderItem的时候，是作为order id存在的。所以在保存的数据库的时候，要获取自增长id：
```java
ResultSet rs = ps.getGeneratedKeys();
if (rs.next()) {
    int id = rs.getInt(1);
    o.setId(id);
}
```

```java
package dao;

import bean.Order;

import java.sql.*;

public class OrderDAO {
    public void insert(Order order) {
        try {
            Class.forName("com.mysql.jdbc.Driver");

            Connection connection = DriverManager.getConnection(
                    "jdbc:mysql://127.0.0.1:3306/cart?characterEncoding=UTF-8",
                    "root", "admin"
            );
            
            String sql = "INSERT INTO order_ VALUES(null, ?)";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, order.getUser().getId());
            preparedStatement.execute();

            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                int id = resultSet.getInt(1);
                order.setId(id);
            }
            
            preparedStatement.close();
            connection.close();
            
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
}
```

## OrderItemDAO

将OrderItem保存到数据库中：

```java
package dao;

import bean.OrderItem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class OrderItemDAO {
    public void insert(OrderItem orderItem) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection(
                    "jdbc:mysql://127.0.0.1:3306/cart?characterEncoding=UTF-8",
                    "root", "admin"
            );
            String sql = "INSERT INTO orderitem VALUES(null, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, orderItem.getProduct().getId());
            preparedStatement.setInt(2, orderItem.getNum());
            preparedStatement.setInt(3, orderItem.getOrder().getId());

            preparedStatement.execute();
            preparedStatement.close();
            connection.close();

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
}
```

## listOrderItem.jsp

在listOrderItem.jsp页面新增加一个"生成订单"的链接：
```
<c:if test="${!empty ois}">
		<tr>
			<td colspan="4" align="right">
				<a href="/createOrder">生成订单</a>
			</td>
		</tr>
</c:if>
```

```
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="java.util.*" isELIgnored="false"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<h1 align="center" >购物车</h1>
<table align='center' border='1' cellspacing='0'>
	<tr>
		<td>商品名称</td>
		<td>单价</td>
		<td>数量</td>
		<td>小计</td>
	</tr>

	<c:forEach items="${orderItems}" var="orderItem" varStatus="st">
		<tr>
			<td>${orderItem.product.name}</td>
			<td>${orderItem.product.price}</td>
			<td>${orderItem.num}</td>
			<td>${orderItem.product.price*orderItem.num}</td>
		</tr>
	</c:forEach>

	<c:if test="${!empty orderItems}">
        <tr>
            <td colspan="4" align="right">
                <a href="createOrder">生成订单</a>
            </td>
        </tr>
    </c:if>
</table>
```

## OrderCreateServlet

OrderCreateServlet创建订单的Servlet：
1. 首选判断用户是否登陆，如果没有登陆跳转到登陆页面
2. 创建一个订单对象，并设置其所属用户
3. 把该订单对象保存到数据库中
4. 遍历session中所有的订单项，设置他们的Order，然后保存到数据库中。
5. 清空session中的订单项
6. 最后打印订单创建成功
   
```java
package servlet;

import bean.Order;
import bean.OrderItem;
import bean.User;
import dao.OrderDAO;
import dao.OrderItemDAO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class OrderCreateServlet {
    protected void service(HttpServletRequest request, HttpServletResponse response) {
        try {
            User user = (User) request.getSession().getAttribute("user");
            if (user == null) {
                response.sendRedirect("/login.jsp");
            }

            Order order = new Order();
            order.setUser(user);

            new OrderDAO().insert(order);

            List<OrderItem> orderItems = (List<OrderItem>) request.getSession().getAttribute("orderItems");
            for (OrderItem orderItem : orderItems) {
                orderItem.setOrder(order);
                new OrderItemDAO().insert(orderItem);
            }

            orderItems.clear();

            response.setContentType("text/html; charset=UTF-8");
            response.getWriter().println("订单创建成功");
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    } 
}
```

## 配置web.xml

```xml
<servlet>
    <servlet-name>OrderCreateServlet</servlet-name>
    <servlet-class>servlet.OrderCreateServlet</servlet-class>
</servlet>

<servlet-mapping>
    <servlet-name>OrderCreateServlet</servlet-name>
    <url-pattern>/createOrder</url-pattern>
</servlet-mapping>
```

## 测试

登录：`http://127.0.0.1:8090/login`

点击创建订单后，在数据库中的orderitem表观察到插入的数据：
<div align=center><img src=Pictures\订单模块.jpg></div>
