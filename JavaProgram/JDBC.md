# 概念

JDBC (Java DataBase Connection) 是通过JAVA访问数据库。

# Hello JDBC

## 为项目导入mysql-jdbc的jar包

访问MySQL数据库需要用到第三方的类，这些第三方的类，都被压缩在一个叫做Jar的文件里。

为了代码能够使用第三方的类，需要为项目导入mysql的专用Jar包`mysql-connector-java-5.0.8-bin.jar`。通常都会把项目用到的jar包统一放在项目的lib目录下，然后在IDEA中导入这个jar包。

## 初始化驱动

通过`Class.forName("com.mysql.jdbc.Driver");`初始化驱动类`com.mysql.jdbc.Driver`，就在`mysql-connector-java-5.0.8-bin.jar`中。

如果忘记了第一个步骤的导包，就会抛出`ClassNotFoundException`。

`Class.forName`是把这个类加载到JVM中，加载的时候，就会执行其中的静态初始化块，完成驱动的初始化的相关工作。

```java
public class InitialJDBC {
    public static void main(String[] args) {
        //初始化驱动
        try {
            //驱动类com.mysql.jdbc.Driver
            //就在 mysql-connector-java-5.0.8-bin.jar中
            //如果忘记了第一个步骤的导包，就会抛出ClassNotFoundException
            Class.forName("com.mysql.jdbc.Driver");

            System.out.println("数据库驱动加载成功 ！");

        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
```

## 建立与数据库的连接

建立与数据库的Connection连接，这里需要提供：
- 数据库所处于的ip：127.0.0.1 (本机)
- 数据库的端口号：3306 （mysql专用端口号）
- 数据库名称：learingjdbc
- 编码方式：UTF-8
- 账号：root；密码：admin

注： 这一步要成功执行，必须建立在mysql中有数据库`learingjdbc`的基础上。

```SQL
mysql> CREATE DATABASE learingjdbc;  # 创建数据库
Query OK, 1 row affected (0.01 sec)

mysql> USE learningjdbc;  # 切换数据库
Database changed
```

```java
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionJDBC {
    public static void main(String[] args) {
        try {
            /**
             * 通过Class.forName("com.mysql.jdbc.Driver"); 初始化驱动类com.mysql.jdbc.Driver
             */
            Class.forName("com.mysql.jdbc.Driver");

            /**
             * 建立与数据库的Connection连接
             *  需要提供：
             *  数据库所处于的ip：127.0.0.1 (本机)
             *  据库的端口号：3306（mysql专用端口号）
             *  数据库名称：learningjdbc
             *  编码方式：UTF-8
             *  账号：root；密码：admin
             */
            Connection connection = DriverManager
                    .getConnection(
                            "jdbc:mysql://127.0.0.1:3306/learningjdbc?characterEncoding=UTF-8",
                            "root", "admin");

            System.out.println("连接成功，获取连接对象： " + connection);

        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
/*
连接成功，获取连接对象： com.mysql.jdbc.Connection@6e8cf4c6
*/
```


## 创建Statement

Statement是用于执行SQL语句的，比如增加，删除。

```java
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class CreateStatement {
    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.jdbc.Driver");

            Connection connection = DriverManager
                    .getConnection(
                            "jdbc:mysql://127.0.0.1:3306/learningjdbc?characterEncoding=UTF-8",
                            "root", "admin");

            // 注意：使用的是 java.sql.Statement
            // 不要不小心使用到： com.mysql.jdbc.Statement;
            Statement statement = connection.createStatement();

            System.out.println("获取 Statement对象：" + statement);

        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
/*
获取 Statement对象：com.mysql.jdbc.Statement@6e8cf4c6
 */
```

## 执行SQL语句

执行SQL语句之前要确保数据库中有表hero的存在，如果没有，需要事先创建表。

```SQL
USE learningjdbc;

CREATE TABLE hero(
	id INT(11) AUTO_INCREMENT,
	name VARCHAR(225),
	hp FLOAT,
	damage INT(11),
	PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
```

```
mysql> SHOW TABLES;
+------------------------+
| Tables_in_learningjdbc |
+------------------------+
| hero                   |
+------------------------+

mysql> DESC hero;
+--------+--------------+------+-----+---------+----------------+
| Field  | Type         | Null | Key | Default | Extra          |
+--------+--------------+------+-----+---------+----------------+
| id     | int          | NO   | PRI | NULL    | auto_increment |
| name   | varchar(225) | YES  |     | NULL    |                |
| hp     | float        | YES  |     | NULL    |                |
| damage | int          | YES  |     | NULL    |                |
+--------+--------------+------+-----+---------+----------------+
```

```java
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class InsertJDBC {
    public static void main(String[] args) {

        try {
            Class.forName("com.mysql.jdbc.Driver");

            Connection connection = DriverManager
                    .getConnection(
                            "jdbc:mysql://127.0.0.1:3306/learningjdbc?characterEncoding=UTF-8",
                            "root", "admin");

            Statement statement = connection.createStatement();

            // 准备sql语句
            // 注意：字符串要用单引号'
            String sql = "insert into hero values(null, "+"'提莫'"+", "+313.0f+", "+50+")";
            statement.execute(sql);

            System.out.println("执行插入语句成功");

        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
}
```

<div align=center><img src=JDBC\执行插入语句后表内容.png width=50%></div>


## 关闭连接

数据库的连接是有限资源，相关操作结束后，养成关闭数据库的好习惯。先关闭Statement，后关闭Connection。

```java
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class CloseJDBC {
    public static void main(String[] args) {

        Connection connection = null;
        Statement statement = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");

            connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/learningjdbc?characterEncoding=UTF-8", "root",
                    "admin");

            statement = connection.createStatement();

            String sql = "insert into hero values(null, " + "'chenzf'" + ", " + 313.0f + ", " + 50 + ")";

            statement.execute(sql);

        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            // 数据库的连接时有限资源，相关操作结束后，养成关闭数据库的好习惯
            // 先关闭Statement
            if (statement != null)
                try {
                    statement.close();
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            // 后关闭Connection
            if (connection != null)
                try {
                    connection.close();
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

        }

    }
}
```

## 使用try-with-resource的方式自动关闭连接

如果觉得上一步的关闭连接的方式很麻烦，可以参考**关闭流**的方式，使用`try-with-resource`的方式**自动关闭连接**，因为Connection和Statement都实现了`AutoCloseable`接口。

所有的流，都实现了一个接口叫做`AutoCloseable`，任何类实现了这个接口，都可以在`try()`中进行实例化。 并且在`try, catch, finally`结束的时候自动关闭，回收相关资源。

```java
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class CloseStream {
    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try (
                Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/learningjdbc?characterEncoding=UTF-8",
                        "root", "admin");
                Statement statement = connection.createStatement();
        )
        {
            String sql = "INSERT INTO hero(name, hp, damage) VALUES('zufeng', 313.1, 21)";
            statement.execute(sql);

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
```

# 增删改

CRUD是最常见的数据库操作，即增删改查：
- C 增加(Create)
- R 读取查询(Retrieve)
- U 更新(Update)
- D 删除(Delete)

在JDBC中增加，删除，修改的操作都很类似，只是传递不同的SQL语句就行了。

## 增

```java {.line-numbers highlight=20}
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class CloseStream {
    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try (
                Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/learningjdbc?characterEncoding=UTF-8",
                        "root", "admin");
                Statement statement = connection.createStatement();
        )
        {
            String sql = "INSERT INTO hero(name, hp, damage) VALUES('zufeng', 313.1, 21)";
            statement.execute(sql);

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
```

## 删除

```java
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Delete {
    public static void main(String[] args) {
        try{
            Class.forName("com.mysql.jdbc.Driver");
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }

        try(
                Connection connection = DriverManager.getConnection(
                        "jdbc:mysql://127.0.0.1:3306/learningjdbc?" +
                                "characterEncoding=UTF-8",
                        "root", "admin"
                );
                Statement statement = connection.createStatement();
                ){
            String sql = "DELETE FROM hero WHERE id = 4";
            statement.execute(sql);
            System.out.println("删除成功！");
        }catch (SQLException e){
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
```

## 改

```java
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Update {
    public static void main(String[] args) {

        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try (
                Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/learningjdbc?characterEncoding=UTF-8",
                        "root", "admin");
                Statement statement = connection.createStatement();
                ) {
            String sql = "UPDATE hero SET name = 'zufeng' WHERE id = 2";
            statement.execute(sql);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
```

# 查询

# 预编译Statement

# execute与executeUpdate

# 特殊操作

# 事务

# ORM

# DAO

DAO(Data Access Object)：数据访问对象。

把数据库相关的操作都封装在这个类里面，其他地方看不到JDBC的代码。

## DAO接口

```java
public class Hero {
    public int id;
    public String name;
    public float hp;
    public int damage;
}


import java.util.List;

public interface HeroDAO {
    //增加
    public void add(Hero hero);
    //修改
    public void update(Hero hero);
    //删除
    public void delete(int id);
    //获取
    public Hero get(int id);
    //查询
    public List<Hero> list();
    //分页查询
    public List<Hero> list(int start, int count);    
}
```

## HeroDAOClass

设计类HeroDAOClass，实现接口HeroDAO：

1. 把驱动的初始化放在了构造方法HeroDAOClass里：
    ```
    public HeroDAOClass() {
	try {
        Class.forName("com.mysql.jdbc.Driver");
	} catch (ClassNotFoundException e) {
		e.printStackTrace();
	    }
    }
    ```
    因为驱动初始化只需要执行一次，所以放在这里更合适，其他方法里也不需要写了，代码更简洁。

2. 提供了一个`getConnection`方法返回连接
    所有的数据库操作都需要事先拿到一个数据库连接Connection，以前的做法每个方法里都会写一个，如果要改动密码，那么每个地方都需要修改。通过这种方式，只需要修改这一个地方就可以了。代码变得更容易维护，而且也更加简洁。

```java
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.ArrayList;
import java.util.List;


public class HeroDAOClass implements HeroDAO{

    public HeroDAOClass() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/learningjdbc?characterEncoding=UTF-8",
                "root", "admin");
    }

    public int getTotal() {
        int total = 0;
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement();) {

            String sql = "SELECT COUNT(*) FROM hero";  // 查询所有列的行数

            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                total = resultSet.getInt(1);
            }

            System.out.println("total:" + total);

        } catch (SQLException e) {

            e.printStackTrace();
        }
        return total;
    }

    public void add(Hero hero) {

        String sql = "INSERT INTO hero VALUES (null, ?, ?, ?)";
        try (Connection connection = getConnection();
             PreparedStatement ps = connection.prepareStatement(sql);) {

            ps.setString(1, hero.name);
            ps.setFloat(2, hero.hp);
            ps.setInt(3, hero.damage);

            ps.execute();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                int id = rs.getInt(1);
                hero.id = id;
            }
        } catch (SQLException e) {

            e.printStackTrace();
        }
    }

    public void update(Hero hero) {

        String sql = "UPDATE hero SET name = ?, hp = ?, damage = ? WHERE id = ?";
        try (Connection connection = getConnection();
             PreparedStatement ps = connection.prepareStatement(sql);) {

            ps.setString(1, hero.name);
            ps.setFloat(2, hero.hp);
            ps.setInt(3, hero.damage);
            ps.setInt(4, hero.id);

            ps.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void delete(int id) {

        try (Connection connection = getConnection();
             Statement statement = connection.createStatement();) {

            String sql = "DELETE FROM hero WHERE id = " + id;

            statement.execute(sql);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Hero get(int id) {
        Hero hero = null;

        try (Connection connection = getConnection();
             Statement statement = connection.createStatement();) {

            String sql = "SELECT * FROM hero WHERE id = " + id;

            ResultSet rs = statement.executeQuery(sql);

            if (rs.next()) {
                hero = new Hero();
                String name = rs.getString(2);
                float hp = rs.getFloat("hp");
                int damage = rs.getInt(4);
                hero.name = name;
                hero.hp = hp;
                hero.damage = damage;
                hero.id = id;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return hero;
    }

    public List<Hero> list() {
        return list(0, Short.MAX_VALUE);
    }

    public List<Hero> list(int start, int count) {
        List<Hero> heroes = new ArrayList<Hero>();

        String sql = "SELECT * FROM hero ORDER BY id DESC limit ?, ? ";

        try (Connection connection = getConnection();
             PreparedStatement ps = connection.prepareStatement(sql);) {

            ps.setInt(1, start);
            ps.setInt(2, count);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Hero hero = new Hero();
                int id = rs.getInt(1);
                String name = rs.getString(2);
                float hp = rs.getFloat("hp");
                int damage = rs.getInt(4);
                hero.id = id;
                hero.name = name;
                hero.hp = hp;
                hero.damage = damage;
                heroes.add(hero);
            }
        } catch (SQLException e) {

            e.printStackTrace();
        }
        return heroes;
    }

}
```


# 数据库连接池