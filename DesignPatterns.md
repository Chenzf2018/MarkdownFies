# 设计模式

参考资料：
1. [设计模式|菜鸟教程](https://www.runoob.com/design-pattern/design-pattern-tutorial.html)

2. [Dive into Design patterns](https://refactoring.guru/design-patterns/book)

3. [图说设计模式](https://design-patterns.readthedocs.io/zh_CN/latest/index.html)

使用设计模式是为了重用代码、让代码更容易被他人理解、保证代码可靠性。

## 设计模式的类型
总共有23种设计模式。这些模式可以分为三大类：创建型模式（Creational）、结构型模式（Structural）、行为型模式（Behavioral）。

### 创建型模式
创建型模式(Creational Pattern)对类的实例化过程进行了抽象，能够<font color=red>将软件模块中对象的创建和对象的使用分离</font>。为了使软件的结构更加清晰，<font color=red>外界对于这些对象只需要知道它们共同的接口，而不清楚其具体的实现细节</font>，使整个系统的设计更加符合单一职责原则。

创建型模式在<font color=red>创建什么(What)，由谁创建(Who)，何时创建(When)</font>等方面都为软件设计者提供了尽可能大的灵活性。创建型模式隐藏了类的实例的创建细节，通过隐藏对象如何被创建和组合在一起达到使整个系统独立的目的。

- 简单工厂模式（Simple Factory）
- 工厂方法模式（Factory Method）：复杂度：1；流行度：3
- 抽象工厂模式（Abstract Factory）：复杂度：2；流行度：3
- 建造者模式（Builder）：复杂度：2；流行度：3
- 原型模式（Prototype）：复杂度：1；流行度：2
- 单例模式（Singleton）：复杂度：1；流行度：2

<div align=center><img src=DesignPatterns/the-relationship-between-design-patterns.jpg width=90%></div>

### 工厂方法模式

追MM少不了请吃饭了，麦当劳的鸡翅和肯德基的鸡翅都是MM爱吃的东西，虽然口味有所不同，但不管你带MM去麦当劳或肯德基，只管向服务员说“来四个鸡翅”就行了。麦当劳和肯德基就是生产鸡翅的Factory工厂模式：<font color=red>客户类和工厂类分开</font>。消费者任何时候需要某种产品，只需向工厂请求即可。消费者无须修改就可以接纳新产品。缺点是当产品修改时，工厂类也要做相应的修改。

它提供了一种创建对象的最佳方式。在工厂模式中，我们<font color=red>在创建对象时不会对客户端暴露创建逻辑，并且是通过使用一个共同的接口来指向新创建的对象</font>。

**Factory Method** is a creational design pattern that provides an interface for creating objects in a superclass, but allows subclasses to alter the type of objects that will be created.

Factory Method defines a method, which should be used for creating objects instead of direct constructor call (`new` operator). Subclasses can override this method to change the class of objects that will be created.

Factory methods can be recognized by creation methods, which create objects from concrete classes, but return them as objects of abstract type or interface.

#### 问题引入
<div align=center><img src=DesignPatterns/factory-method.png width=80%></div>

Imagine that you’re creating <font color=red>a logistics management application</font>. The first version of your app can only <font color=red>handle transportation by trucks</font>, so the bulk of your code lives inside the `Truck` class.

After a while, your app becomes pretty popular. Each day you receive dozens of requests from sea transportation companies to incorporate <font color=red>sea logistics</font> into the app.

<div align=center><img src=DesignPatterns/factory-method2.png width=70%></div>

Adding a new class to the program isn’t that simple if the rest of the code is already coupled to existing classes.

At present, most of your code is coupled to the `Truck` class. Adding `Ships` into the app would require making changes to the entire codebase. Moreover, if later you decide to add another type of transportation to the app, you will probably need to make all of these changes again.

As a result, you will end up with pretty nasty code, riddled with conditionals that switch the app’s behavior depending on the class of transportation objects.

#### 解决办法
The Factory Method pattern suggests that you <font color=red>replace direct object construction calls (using the `new` operator) with calls to a special factory method</font>. Don’t worry: the objects are still created via the `new` operator, but it’s being called from within the factory method. <font color=red>Objects returned by a factory method are often referred to as “products”</font>.

<div align=center><img src=DesignPatterns/factory-method3.png width=90%></div>

At first glance, this change may look pointless: we just moved the constructor call from one part of the program to another. However, consider this: <font color=red>now you can override the factory method in a subclass and change the class of products being created by the method</font>.

There’s a slight limitation though: subclasses may return different types of products only if these products have a common base class or interface. Also, the factory method in the base class should have its return type declared as this interface.

<div align=center><img src=DesignPatterns/factory-method4.png width=70%></div>

All products must follow the same interface.

For example, both `Truck` and `Ship` classes should implement the `Transport` interface, which declares a method called `deliver`. Each class implements this method differently: trucks deliver cargo by land, ships deliver cargo by sea. <font color=red>The factory method in the `RoadLogistics` class returns truck objects, whereas the factory method in the `SeaLogistics` class returns ships</font>.

<div align=center><img src=DesignPatterns/factory-method5.png width=80%></div>

As long as all product classes implement a common interface, you can pass their objects to the client code without breaking it.

The code that uses the factory method (often called the `client code`) doesn’t see a difference between the actual products returned by various subclasses. The client treats all the products as abstract `Transport`. <font color=red>The client knows that all transport objects are supposed to have the deliver method, but exactly how it works isn’t important to the client</font>.

#### Structure

<div align=center><img src=DesignPatterns/factory-method-structure.png></div>



#### 实例一：Production of cross-platform GUI elements
<div align=center><img src=DesignPatterns/factory-method6.png width=90%></div>

In this example, Buttons play a product role and dialogs act as creators.

Different types of dialogs require their own types of elements. That’s why we create a subclass for each dialog type and override their factory methods.

Now, each dialog type will instantiate proper button classes. Base dialog works with products using their common interface, that’s why its code remains functional after all changes.


<div align=center><img src=DesignPatterns/factory-method7.png width=40%></div>

##### Button.java
```java
package Factory_Method.buttons;

/**
 * Common interface for all buttons.
 */

public interface Button
{
    void render();
    void onClick();
}
```

##### HtmlButton.java
```java
package Factory_Method.buttons;

/**
 * HTML button implementation.
 * Concrete product
 */

public class HtmlButton implements Button
{
    @Override
    public void onClick()
    {
        System.out.println("Click! Button says - 'Hello World!'");
    }

    @Override
    public void render()
    {
        System.out.println("<button>Test Button</button>");
        onClick();
    }
}
```

##### WindowsButton.java
```java
package Factory_Method.buttons;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Windows button implementation.
 */

public class WindowsButton implements Button
{
    JPanel panel = new JPanel();
    JFrame frame = new JFrame();
    JButton button;

    @Override
    public void onClick()
    {
        button = new JButton("Exit");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);
                System.exit(0);
            }
        });
    }

    public void render()
    {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel label = new JLabel("Hello World !");
        label.setOpaque(true);
        label.setBackground(new Color(235, 233, 126));
        label.setFont(new Font("Dialog", Font.BOLD, 44));
        label.setHorizontalAlignment(SwingConstants.CENTER);

        panel.setLayout(new FlowLayout(FlowLayout.CENTER));
        frame.getContentPane().add(panel);
        panel.add(label);
        onClick();
        panel.add(button);

        frame.setSize(320, 200);
        frame.setVisible(true);
        onClick();
    }
}
```

##### Dialog.java
```java
package Factory_Method.factory;

import Factory_Method.buttons.Button;

/**
 * Base creator
 * Base factory class. Note that "factory" is merely a role for the class.
 * It should have some core business logic which needs different products to be created.
 */

public abstract class Dialog
{
    public void renderWindow()
    {
        // ... other code ...

        Button okButton = createButton();
        okButton.render();
    }

    /**
     * Subclasses will override this method in order to create specific button objects.
     */
    public abstract Button createButton();
}
```

##### HtmlDialog.java
```java
package Factory_Method.factory;

import Factory_Method.buttons.Button;
import Factory_Method.buttons.HtmlButton;

/**
 * Concrete creator
 * HTML Dialog will produce HTML buttons.
 */

public class HtmlDialog extends Dialog
{
    @Override
    public Button createButton()
    {
        return new HtmlButton();
    }
}
```

##### WindowsDialog.java
```java
package Factory_Method.factory;

import Factory_Method.buttons.Button;
import Factory_Method.buttons.WindowsButton;

/**
 * Factory_Method
 */

public class WindowsDialog extends Dialog
{
    @Override
    public Button createButton()
    {
        return new WindowsButton();
    }
}
```

##### Client.java
```java
package Factory_Method;

import Factory_Method.factory.Dialog;
import Factory_Method.factory.HtmlDialog;
import Factory_Method.factory.WindowsDialog;

/**
 * Demo class. Everything comes together here.
 */

public class Client
{
    private static Dialog dialog;

    public static void main(String[] args)
    {
        configure();
        runBusinessLogic();
    }

    /**
     * The concrete factory is usually chosen depending on configuration or environment options.
     */
    static void configure()
    {
        if (System.getProperty("os.name").equals("Windows 9"))
            dialog = new WindowsDialog();
        else
            dialog = new HtmlDialog();
    }

    /**
     * All of the client code should work with factories and products through
     * abstract interfaces. This way it does not care which factory it works
     * with and what kind of product it returns.
     */
    static void runBusinessLogic()
    {
        dialog.renderWindow();
    }
}
```

#### 实例二：工厂类 ShapeFactory
<div align=center><img src=DesignPatterns/factory-method8.png width=80%></div>

`FactoryPatternDemo`使用`ShapeFactory`来获取`Shape`对象。它将向`ShapeFactory`传递信息（`CIRCLE, RECTANGLE, SQUARE`），以便获取它所需对象的类型。

<div align=center><img src=DesignPatterns/factory-method9.png width=30%></div>

```java
// Shape.java
package Factory_Method2.shapeInterface;

public interface Shape
{
    void draw();
}


// Rectangle.java
package Factory_Method2.shapeInterface;

public class Rectangle implements Shape
{
    @Override
    public void draw()
    {
        System.out.println("Inside Rectangle::draw() method.");
    }
}

// Square.java
package Factory_Method2.shapeInterface;

public class Square implements Shape
{
    @Override
    public void draw()
    {
        System.out.println("Inside Square::draw() method.");
    }
}

// Circle.java
package Factory_Method2.shapeInterface;

public class Circle implements Shape
{
    @Override
    public void draw()
    {
        System.out.println("Inside Square::draw() method.");
    }
}

// ShapeFactory.java
package Factory_Method2.shapeFactory;

import Factory_Method2.shapeInterface.Rectangle;
import Factory_Method2.shapeInterface.Shape;
import Factory_Method2.shapeInterface.Circle;
import Factory_Method2.shapeInterface.Square;

/**
 * 创建一个工厂，生成基于给定信息的实体类的对象。
 */

public class ShapeFactory
{
    //使用 getShape 方法获取形状类型的对象
    public Shape getShape(String shapeType)
    {
        if(shapeType.equalsIgnoreCase("CIRCLE"))
            return new Circle();
        else if(shapeType.equalsIgnoreCase("RECTANGLE"))
            return new Rectangle();
        else if(shapeType.equalsIgnoreCase("SQUARE"))
            return new Square();
        return null;
    }
}

// Client.java
package Factory_Method2;

import Factory_Method2.shapeFactory.ShapeFactory;
import Factory_Method2.shapeInterface.Shape;

/**
 * 使用工厂，通过传递类型信息来获取实体类的对象
 */

public class Client
{
    public static void main(String[] args) {
        ShapeFactory shapeFactory = new ShapeFactory();

        //获取 Circle 的对象，并调用它的 draw 方法
        Shape shape1 = shapeFactory.getShape("CIRCLE");
        //调用 Circle 的 draw 方法
        shape1.draw();

        //获取 Rectangle 的对象，并调用它的 draw 方法
        Shape shape2 = shapeFactory.getShape("RECTANGLE");
        //调用 Rectangle 的 draw 方法
        shape2.draw();

        //获取 Square 的对象，并调用它的 draw 方法
        Shape shape3 = shapeFactory.getShape("SQUARE");
        //调用 Square 的 draw 方法
        shape3.draw();
    }
}

```

#### 总结

**目的**：定义一个创建对象的接口，<font color=red>让其子类自己决定实例化哪一个工厂类</font>，工厂模式使其创建过程延迟到子类进行。

**主要解决**：主要解决接口选择的问题。

**何时使用**：计划不同条件下创建不同实例时。

**如何解决**：<font color=red>让其子类实现工厂接口，返回的也是一个抽象的产品</font>。

**关键代码**：创建过程在其子类执行。

**应用实例**： 1、您需要一辆汽车，<font color=red>可以直接从工厂里面提货，而不用去管这辆汽车是怎么做出来的，以及这个汽车里面的具体实现</font>。 2、Hibernate 换数据库只需换方言和驱动就可以。

**优点**： 1、一个调用者想创建一个对象，只要知道其名称就可以了。 2、扩展性高，如果想增加一个产品，只要扩展一个工厂类就可以。 3、屏蔽产品的具体实现，调用者只关心产品的接口。

The Factory Method separates product construction code from the code that actually uses the product. Therefore it’s easier to extend the product construction code independently from the rest of the code.

For example, to add a new product type to the app, you’ll only need to create a new creator subclass and override the factory method in it.

**缺点**：<font color=red>每次增加一个产品时，都需要增加一个具体类和对象实现工厂</font>，使得系统中类的个数成倍增加，在一定程度上增加了系统的复杂度，同时也增加了系统具体类的依赖。这并不是什么好事。

**使用场景**： 1、日志记录器：记录可能记录到本地硬盘、系统事件、远程服务器等，用户可以选择记录日志到什么地方。 2、数据库访问，当用户不知道最后系统采用哪一类数据库，以及数据库可能有变化时。 3、设计一个连接服务器的框架，需要三个协议，"POP3"、"IMAP"、"HTTP"，可以把这三个作为产品类，共同实现一个接口。

**注意事项**：作为一种创建类模式，在任何需要生成复杂对象的地方，都可以使用工厂方法模式。有一点需要注意的地方就是复杂对象适合使用工厂模式，而简单对象，特别是只需要通过`new`就可以完成创建的对象，无需使用工厂模式。如果使用工厂模式，就需要引入一个`工厂类`，会增加系统的复杂度。


### 抽象工厂模式
请MM去麦当劳吃汉堡，不同的MM有不同的口味，要每个都记住是一件烦人的事情，一般采用抽象工厂模式，带着MM到服务员那儿，说“要一个汉堡”，具体要什么样的汉堡呢，让MM直接跟服务员说就行了。<font color=red>核心工厂类不再负责所有产品的创建，而是将具体创建的工作交给子类去做，成为一个抽象工厂角色，仅负责给出具体工厂类必须实现的接口，而不接触哪一个产品类应当被实例化这种细节</font>。

抽象工厂模式（Abstract Factory Pattern）是围绕一个超级工厂创建其他工厂。这种类型的设计模式属于创建型模式，它提供了一种创建对象的最佳方式。

在抽象工厂模式中，接口是负责创建一个相关对象的工厂，不需要显式指定它们的类。每个生成的工厂都能按照工厂模式提供对象。

Abstract Factory is a creational design pattern that lets you produce families of related objects without specifying their concrete classes.

<div align=center><img src=DesignPatterns/abstract-factory.png width=90%></div>

#### 问题引入

Imagine that you’re creating `a furniture shop simulator`. Your code consists of classes that represent:

1. A family of related products, say: `Chair + Sofa + CoffeeTable`.

2. Several variants of this family. For example, products `Chair + Sofa + CoffeeTable` are available in these variants: `Modern, Victorian, ArtDeco`.

<div align=center><img src=DesignPatterns/abstract-factory1.png width=60%></div>

Product families and their variants.

You need a way to create individual furniture objects so that they match other objects of the same family. Customers get quite mad when they receive non-matching furniture.

<div align=center><img src=DesignPatterns/abstract-factory2.png width=80%></div>

A Modern-style sofa doesn’t match Victorian-style chairs.

Also, you don’t want to change existing code when adding new products or families of products to the program. Furniture vendors update their catalogs very often, and you wouldn’t want to change the core code each time it happens.

#### 解决方案

The first thing the Abstract Factory pattern suggests is to explicitly <font color=red>declare interfaces for each distinct product of the product family</font> (e.g., chair, sofa or coffee table). Then you can <font color=red>make all variants of products follow those interfaces</font>. For example, all chair variants can implement the `Chair interface`; all coffee table variants can implement the `CoffeeTable interface`, and so on.

<div align=center><img src=DesignPatterns/abstract-factory3.png width=50%></div>

All variants of the same object must be moved to a single class hierarchy.

The next move is to <font color=red>declare the Abstract Factory — an interface with a list of creation methods for all products that are part of the product family</font> (for example, `createChair, createSofa` and `createCoffeeTable`). These methods must return abstract product types represented by the interfaces we extracted previously: Chair, Sofa, CoffeeTable and so on.

<div align=center><img src=DesignPatterns/abstract-factory4.png width=90%></div>

Each concrete factory corresponds to a specific product variant.

Now, how about the product variants? For each variant of a product family, we create a separate factory class based on the `AbstractFactory interface`. <font color=red>A factory is a class that returns products of a particular kind</font>. For example, the `ModernFurnitureFactory` can only create `ModernChair`, `ModernSofa` and `ModernCoffeeTable` objects.

The client code has to work with both factories and products via their respective abstract interfaces. This lets you change the type of a factory that you pass to the client code, as well as the product variant that the client code receives, without breaking the actual client code.

<div align=center><img src=DesignPatterns/abstract-factory5.png width=80%></div>

The client shouldn’t care about the concrete class of the factory it works with.

<font color=red>Say the client wants a factory to produce a chair. The client doesn’t have to be aware of the factory’s class, nor does it matter what kind of chair it gets. Whether it’s a Modern model or a Victorian-style chair, the client must treat all chairs in the same manner, using the abstract `Chair interface`</font>. With this approach, the only thing that the client knows about the chair is that it implements the `sitOn method` in some way. Also, whichever variant of the chair is returned, it’ll always match the type of sofa or coffee table produced by the same factory object.

There’s one more thing left to clarify: if the client is only exposed to the abstract interfaces, what creates the actual factory objects? Usually, the application creates a concrete factory object at the initialization stage. Just before that, the app must select the factory type depending on the configuration or the environment settings.

#### Structure
<div align=center><img src=DesignPatterns/abstract-factory6.png></div>

#### 实例一：Families of cross-platform GUI components and their production

<div align=center><img src=DesignPatterns/abstract-factory7.png width=90%></div>

In this example, buttons and checkboxes will act as products. They have two variants: macOS and Windows.

<div align=center><img src=DesignPatterns/abstract-factory8.png width=30%></div>

##### GUIFactory.java
```java
package AbstractFactory.factories;

import AbstractFactory.buttons.Button;
import AbstractFactory.checkboxes.Checkbox;

/**
 * Abstract factory knows about all (abstract) product types.
 */

public interface GUIFactory
{
    Button createButton();
    Checkbox createCheckbox();
}
```

##### MacOSFactory.java
```java
package AbstractFactory.factories;

import AbstractFactory.buttons.Button;
import AbstractFactory.buttons.MacOSButton;
import AbstractFactory.checkboxes.Checkbox;
import AbstractFactory.checkboxes.MacOSCheckbox;

/**
 * Each concrete factory extends basic factory and responsible for creating
 * products of a single variety.
 */

public class MacOSFactory implements GUIFactory
{
    @Override
    public Button createButton()
    {
        return new MacOSButton();
    }

    @Override
    public Checkbox createCheckbox()
    {
        return new MacOSCheckbox();
    }
}
```

##### WindowsFactory.java
```java
package AbstractFactory.factories;

import AbstractFactory.buttons.Button;
import AbstractFactory.buttons.WindowsButton;
import AbstractFactory.checkboxes.Checkbox;
import AbstractFactory.checkboxes.WindowsCheckbox;

/**
 * Each concrete factory extends basic factory and responsible for creating
 * products of a single variety.
 */

public class WindowsFactory implements GUIFactory
{
    @Override
    public Button createButton()
    {
        return new WindowsButton();
    }

    @Override
    public Checkbox createCheckbox()
    {
        return new WindowsCheckbox();
    }
}
```

##### Button.java
```java
package AbstractFactory.buttons;

/**
 * buttons: First product hierarchy
 *
 * Abstract Factory assumes that you have several families of products,
 * structured into separate class hierarchies (Button/Checkbox).
 * All products of the same family have the common interface.
 *
 * This is the common interface for buttons family.
 */

public interface Button
{
    void paint();
}
```

##### MacOSButton.java
```java
package AbstractFactory.buttons;

/**
 * All products families have the same varieties (MacOS/Windows).
 *
 * This is a MacOS variant of a button.
 */

public class MacOSButton implements Button
{
    @Override
    public void paint()
    {
        System.out.println("You have created MacOSButton.");
    }
}
```

##### WindowsButton.java
```java
package AbstractFactory.buttons;

/**
 * All products families have the same varieties (MacOS/Windows).
 *
 * This is another variant of a button.
 */

public class WindowsButton implements Button
{
    @Override
    public void paint()
    {
        System.out.println("You have created WindowsButton.");
    }
}
```

##### Checkbox.java
```java
package AbstractFactory.checkboxes;

/**
 * Second product hierarchy
 *
 * Checkboxes is the second product family. It has the same variants as buttons.
 */

public interface Checkbox
{
    void paint();
}
```

##### MacOSCheckbox.java
```java
package AbstractFactory.checkboxes;

/**
 * All products families have the same varieties (MacOS/Windows).
 *
 * This is a variant of a checkbox.
 */

public class MacOSCheckbox implements Checkbox
{
    @Override
    public void paint()
    {
        System.out.println("You have created MacOSCheckbox.");
    }
}
```

##### WindowsCheckbox.java
```java
package AbstractFactory.checkboxes;

/**
 * All products families have the same varieties (MacOS/Windows).
 *
 * This is another variant of a checkbox.
 */

public class WindowsCheckbox implements Checkbox
{
    @Override
    public void paint()
    {
        System.out.println("You have created WindowsCheckbox.");
    }
}
```

##### Application.java
```java
package AbstractFactory.app;

import AbstractFactory.buttons.Button;
import AbstractFactory.checkboxes.Checkbox;
import AbstractFactory.factories.GUIFactory;

/**
 * Factory users don't care which concrete factory they use since they work with
 * factories and products through abstract interfaces.
 */

public class Application
{
    private Button button;
    private Checkbox checkbox;

    public Application(GUIFactory factory)
    {
        button = factory.createButton();
        checkbox = factory.createCheckbox();
    }

    public void paint()
    {
        button.paint();
        checkbox.paint();
    }
}
```

##### Demo.java
```java
package AbstractFactory;

import AbstractFactory.app.Application;
import AbstractFactory.factories.GUIFactory;
import AbstractFactory.factories.MacOSFactory;
import AbstractFactory.factories.WindowsFactory;

public class Demo
{
    /**
     * Application picks the factory type and creates it in run time (usually at
     * initialization stage), depending on the configuration or environment
     * variables.
     */
    private static Application configureApplication()
    {
        Application application;
        GUIFactory factory;
        String osName = System.getProperty("os.name").toLowerCase();
        if (osName.contains("mac"))
        {
            factory = new MacOSFactory();
            application = new Application(factory);
        }
        else
        {
            factory = new WindowsFactory();
            application = new Application(factory);
        }

        return application;
    }

    public static void main(String[] args)
    {
        Application application = configureApplication();
        application.paint();
    }
}
```

### 创建者/生成器(Bulider)模式
生成器模式是一种创建型设计模式，<font color=red>能够分步骤创建复杂对象。该模式可使用相同的创建代码生成不同类型和形式的对象</font>。

<div align=center><img src=DesignPatterns/builder.png width=70%></div>

#### 问题引入
假设有这样一个复杂对象，在对其进行构造时需要对诸多成员变量和嵌套对象进行繁复的初始化工作。这些初始化代码通常深藏于一个包含众多参数且让人基本看不懂的构造函数中；甚至还有更糟糕的情况，那就是这些代码散落在客户端代码的多个位置。

<div align=center><img src=DesignPatterns/builder1.png width=70%></div>

如果为每种可能的对象都创建一个子类，这可能会导致程序变得过于复杂。

例如，我们来思考如何创建一个房屋`House对象`。

建造一栋简单的房屋，首先你需要建造四面墙和地板，安装房门和一套窗户，然后再建造一个屋顶。但是如果你想要一栋更宽敞更明亮的房屋，还要有院子和其他设施 （例如暖气、排水和供电设备），那又该怎么办呢？

<font color=red>最简单的方法是`扩展房屋基类`，然后创建一系列涵盖所有参数组合的子类。但最终你将面对相当数量的子类</font>。任何新增的参数（例如门廊类型）都会让这个层次结构更加复杂。

另一种方法则**无需生成子类**。你<font color=red>可以在`房屋基类`中创建一个包括所有可能参数的`超级构造函数`，并用它来控制房屋对象</font>。这种方法确实可以避免生成子类，但它却会造成另外一个问题。

<div align=center><img src=DesignPatterns/builder2.png width=60%></div>

拥有大量输入参数的构造函数也有缺陷：<font color=red>这些参数也不是每次都要全部用上的</font>。

通常情况下，绝大部分的参数都没有使用，这使得对于构造函数的调用十分不简洁。 例如，只有很少的房子有游泳池，因此与游泳池相关的参数十之八九是毫无用处的。

#### 解决方案

生成器模式建议<font color=red>将对象构造代码从产品类中抽取出来，并将其放在一个名为生成器的独立对象中</font>。

<div align=center><img src=DesignPatterns/builder3.png width=40%></div>

生成器模式让你能够分步骤创建复杂对象。生成器不允许其他对象访问正在创建中的产品。

该模式会将对象构造过程划分为一组步骤，比如`build­Walls`创建墙壁和 `build­Door`创建房门创建房门等。每次创建对象时，你都需要通过生成器对象执行一系列步骤。重点在于你无需调用所有步骤，而只需调用创建特定对象配置所需的那些步骤即可。

当你需要<font color=red>创建不同形式的产品</font>时，其中的一些构造步骤可能需要不同的实现。例如，<font color=red>木屋的房门可能需要使用木头制造，而城堡的房门则必须使用石头制造。在这种情况下，你可以创建多个不同的生成器，用不同方式实现一组相同的创建步骤。然后你就可以在创建过程中使用这些生成器（例如按顺序调用多个构造步骤）来生成不同类型的对象</font>。

<div align=center><img src=DesignPatterns/builder4.png width=60%></div>

不同生成器以不同方式执行相同的任务。

例如，假设第一个建造者使用木头和玻璃制造房屋，第二个建造者使用石头和钢铁，而第三个建造者使用黄金和钻石。在调用同一组步骤后，第一个建造者会给你一栋普通房屋，第二个会给你一座小城堡，而第三个则会给你一座宫殿。但是，只有在调用构造步骤的客户端代码可以通过通用接口与建造者进行交互时，这样的调用才能返回需要的房屋。

**主管(Director)**

你可以进一步将用于创建产品的一系列生成器步骤调用抽取成为单独的主管类。主管类可定义创建步骤的执行顺序，而生成器则提供这些步骤的实现。主管知道需要哪些创建步骤才能获得可正常使用的产品。

严格来说，程序中并不一定需要主管类。客户端代码可直接以特定顺序调用创建步骤。不过，主管类中非常适合放入各种例行构造流程，以便在程序中反复使用。

此外，对于客户端代码来说，主管类完全隐藏了产品构造细节。客户端只需要将一个生成器与主管类关联，然后使用主管类来构造产品，就能从生成器处获得构造结果了。

#### 生成器模式结构
<div align=center><img src=DesignPatterns/builder5.png width=100%></div>

#### 实例：Step-by-step car production
下面关于生成器模式的例子演示了你可以<font color=red>如何复用相同的对象构造代码来生成不同类型的产品——例如汽车（Car）——及其相应的使用手册（Manual）</font>。


在本例中，生成器模式允许你分步骤地制造不同型号的汽车。

示例还展示了生成器如何使用相同的生产过程制造不同类型的产品（汽车手册）。

主管控制着构造顺序。它知道制造各种汽车型号需要调用的生产步骤。它仅与汽车的通用接口进行交互。这样就能将不同类型的生成器传递给主管了。

最终结果将从生成器对象中获得，因为主管不知道最终产品的类型。只有生成器对象知道自己生成的产品是什么。

##### 伪代码
汽车是一个复杂对象，有数百种不同的制造方法。我们没有在`汽车类`中塞入一个巨型构造函数，而是<font color=red>将汽车组装代码抽取到单独的汽车生成器类中</font>。该类中有一组方法可用来配置汽车的各种部件。

如果**客户端代码**需要组装一辆与众不同、精心调教的汽车，它可以<font color=red>直接调用生成器</font>。或者，客户端可以<font color=red>将组装工作委托给主管类</font>，因为主管类知道如何使用生成器制造最受欢迎的几种型号汽车。

每辆汽车都需要一本使用手册，使用手册会介绍汽车的每一项功能，因此<font color=red>不同型号的汽车，其使用手册内容也不一样</font>。 因此，<font color=red>可以复用现有流程来制造实际的汽车及其对应的手册</font>。当然，编写手册和制造汽车不是一回事，所以我们<font color=red>需要另外一个生成器对象来专门编写使用手册</font>。该类与其制造汽车的兄弟类都实现了相同的制造方法，但是其功能不是制造汽车部件，而是描述每个部件。将这些生成器传递给相同的主管对象，我们就能够生成一辆汽车或是一本使用手册了。

最后一个部分是获取结果对象。尽管金属汽车和纸质手册存在关联，但它们却是完全不同的东西。我们无法在主管类和具体产品类不发生耦合的情况下，在主管类中提供获取结果对象的方法。因此，我们只能通过负责制造过程的生成器来获取结果对象。

```java
// 只有当产品较为复杂且需要详细配置时，使用生成器模式才有意义。
// 下面的两个产品尽管没有同样的接口，但却相互关联。
class Car is
    // 一辆汽车可能配备有GPS设备、行车电脑和几个座位。
    // 不同型号的汽车（运动型轿车、SUV和敞篷车）可能会安装或启用不同的功能。

class Manual is
    // 用户使用手册应该根据汽车配置进行编制，并介绍汽车的所有功能。


// 生成器接口声明了创建产品对象不同部件的方法。
interface Builder is
    method reset()
    method setSeats(...)
    method setEngine(...)
    method setTripComputer(...)
    method setGPS(...)

// 具体生成器类将遵循生成器接口并提供生成步骤的具体实现。
// 你的程序中可能会有多个以不同方式实现的生成器变体。
class CarBuilder implements Builder is
    private field car:Car

    // 一个新的生成器实例必须包含一个在后续组装过程中使用的空产品对象。
    constructor CarBuilder() is
        this.reset()

    // reset（重置）方法可清除正在生成的对象。
    method reset() is
        this.car = new Car()

    // 所有生成步骤都会与同一个产品实例进行交互。
    method setSeats(...) is
        // 设置汽车座位的数量。

    method setEngine(...) is
        // 安装指定的引擎。

    method setTripComputer(...) is
        // 安装行车电脑。

    method setGPS(...) is
        // 安装全球定位系统。

    // 具体生成器需要自行提供获取结果的方法。这是因为不同类型的生成器可能
    // 会创建不遵循相同接口的、完全不同的产品。所以也就无法在生成器接口中
    // 声明这些方法（至少在静态类型的编程语言中是这样的）。
    //
    // 通常在生成器实例将结果返回给客户端后，它们应该做好生成另一个产品的
    // 准备。因此生成器实例通常会在 `getProduct（获取产品）`方法主体末尾
    // 调用重置方法。但是该行为并不是必需的，你也可让生成器等待客户端明确
    // 调用重置方法后再去处理之前的结果。
    method getProduct():Car is
        product = this.car
        this.reset()
        return product

// 生成器与其他创建型模式的不同之处在于：它让你能创建不遵循相同接口的产品。
class CarManualBuilder implements Builder is
    private field manual:Manual

    constructor CarManualBuilder() is
        this.reset()

    method reset() is
        this.manual = new Manual()

    method setSeats(...) is
        // 添加关于汽车座椅功能的文档。

    method setEngine(...) is
        // 添加关于引擎的介绍。

    method setTripComputer(...) is
        // 添加关于行车电脑的介绍。

    method setGPS(...) is
        // 添加关于 GPS 的介绍。

    method getProduct():Manual is
        // 返回使用手册并重置生成器。


// 主管只负责按照特定顺序执行生成步骤。其在根据特定步骤或配置来生成产品时
// 会很有帮助。由于客户端可以直接控制生成器，所以严格意义上来说，主管类并
// 不是必需的。
class Director is
    private field builder:Builder

    // 主管可同由客户端代码传递给自身的任何生成器实例进行交互。客户端可通
    // 过这种方式改变最新组装完毕的产品的最终类型。
    method setBuilder(builder:Builder)
        this.builder = builder

    // 主管可使用同样的生成步骤创建多个产品变体。
    method constructSportsCar(builder: Builder) is
        builder.reset()
        builder.setSeats(2)
        builder.setEngine(new SportEngine())
        builder.setTripComputer(true)
        builder.setGPS(true)

    method constructSUV(builder: Builder) is
        // ...


// 客户端代码会创建生成器对象并将其传递给主管，然后执行构造过程。最终结果
// 将需要从生成器对象中获取。
class Application is

    method makeCar() is
        director = new Director()

        CarBuilder builder = new CarBuilder()
        director.constructSportsCar(builder)
        Car car = builder.getProduct()

        CarManualBuilder builder = new CarManualBuilder()
        director.constructSportsCar(builder)

        // 最终产品通常需要从生成器对象中获取，因为主管不知晓具体生成器和
        // 产品的存在，也不会对其产生依赖。
        Manual manual = builder.getProduct()
```

<div align=center><img src=DesignPatterns/builder6.png width=40%></div>

##### Engine.java
```java
package Builder.components;

public class Engine
{
    private final double volume;
    private double mileage;
    private boolean started;

    public Engine(double volume, double mileage)
    {
        this.volume = volume;
        this.mileage = mileage;
    }

    public void on(){started = true;}
    public void off(){started = false;}
    public boolean isStarted(){return started;}

    public double getVolume(){return volume;}
    public double getMileage(){return mileage;}

    public void go(double mileage)
    {
        if (started)
            this.mileage += mileage;
        else
            System.err.println("Cannot go(), you must start engine first!");
    }
}
```

##### GPSNavigator.java
```java
package Builder.components;

public class GPSNavigator
{
    private String route;

    public GPSNavigator()
    {
        this.route = "221b, Baker Street, London  to Scotland Yard, 8-10 Broadway, London";
    }

    public GPSNavigator(String manualRoute)
    {
        this.route = manualRoute;
    }

    public String getRoute()
    {
        return route;
    }
}
```

##### Transmission.java
```java
package Builder.components;

public enum  Transmission
{
    SINGLE_SPEED, MANUAL, AUTOMATIC, SEMI_AUTOMATIC
}
```

##### TripComputer.java
```java
package Builder.components;

import Builder.cars.Car;

public class TripComputer
{
    private Car car;

    public void setCar(Car car)
    {
        this.car = car;
    }

    public void showFuelLevel()
    {
        System.out.println("Fuel level: " + car.getFuel());
    }

    public void showStatus()
    {
        if (this.car.getEngine().isStarted())
            System.out.println("Car is started.");
        else
            System.out.println("Car isn't started.");
    }
}
```

##### Builder.java
```java
package Builder.builders;

import Builder.cars.Type;
import Builder.components.Engine;
import Builder.components.GPSNavigator;
import Builder.components.Transmission;
import Builder.components.TripComputer;

/**
 * 通用生成器接口
 * Builder interface defines all possible ways to configure a product.
 */

public interface Builder
{
    void setType(Type type);
    void setSeats(int seats);
    void setEngine(Engine engine);
    void setTransmission(Transmission transmission);
    void setTripComputer(TripComputer tripComputer);
    void setGPSNavigator(GPSNavigator gpsNavigator);
}
```

##### CarBuilder.java
```java
package Builder.builders;

import Builder.cars.Car;
import Builder.cars.Type;
import Builder.components.Engine;
import Builder.components.GPSNavigator;
import Builder.components.Transmission;
import Builder.components.TripComputer;

/**
 * 汽车生成器
 * Concrete builders implement steps defined in the common interface.
 */

public class CarBuilder implements Builder
{
    private Type type;
    private int seats;
    private Engine engine;
    private Transmission transmission;
    private TripComputer tripComputer;
    private GPSNavigator gpsNavigator;

    @Override
    public void setType(Type type){this.type = type;}

    @Override
    public void setSeats(int seats){this.seats = seats;}

    @Override
    public void setEngine(Engine engine){this.engine = engine;}

    @Override
    public void setTransmission(Transmission transmission){this.transmission = transmission;}

    @Override
    public void setTripComputer(TripComputer tripComputer){this.tripComputer = tripComputer;}

    @Override
    public void setGPSNavigator(GPSNavigator gpsNavigator){this.gpsNavigator = gpsNavigator;}

    public Car getResult()
    {
        return new Car(type, seats, engine, transmission, tripComputer,gpsNavigator);
    }
}
```

##### CarManualBuilder.java
```java
package Builder.builders;

import Builder.cars.Manual;
import Builder.cars.Type;
import Builder.components.Engine;
import Builder.components.GPSNavigator;
import Builder.components.Transmission;
import Builder.components.TripComputer;

/**
 * Unlike other creational patterns, Builder can construct unrelated products,
 * which don't have the common interface.
 *
 * In this case we build a user manual for a car, using the same steps as we
 * built a car. This allows to produce manuals for specific car models,
 * configured with different features.
 */

public class CarManualBuilder implements Builder
{
    private Type type;
    private int seats;
    private Engine engine;
    private Transmission transmission;
    private TripComputer tripComputer;
    private GPSNavigator gpsNavigator;

    @Override
    public void setType(Type type){this.type = type;}

    @Override
    public void setSeats(int seats){this.seats = seats;}

    @Override
    public void setEngine(Engine engine){this.engine = engine;}

    @Override
    public void setTransmission(Transmission transmission){this.transmission = transmission;}

    @Override
    public void setTripComputer(TripComputer tripComputer){this.tripComputer = tripComputer;}

    @Override
    public void setGPSNavigator(GPSNavigator gpsNavigator){this.gpsNavigator = gpsNavigator;}

    public Manual getResult()
    {
        return new Manual(type, seats, engine, transmission, tripComputer,gpsNavigator);
    }
}
```

##### Car.java
```java
package Builder.cars;

import Builder.components.Engine;
import Builder.components.GPSNavigator;
import Builder.components.Transmission;
import Builder.components.TripComputer;

/**
 * Car is a product class.
 */

public class Car
{
    private final Type type;
    private final int seats;
    private final Engine engine;
    private final Transmission transmission;
    private final TripComputer tripComputer;
    private final GPSNavigator gpsNavigator;
    private double fuel = 0;

    public Car(Type type, int seats, Engine engine, Transmission transmission, TripComputer tripComputer, GPSNavigator gpsNavigator)
    {
        this.type = type;
        this.seats = seats;
        this.engine = engine;
        this.transmission = transmission;
        this.tripComputer = tripComputer;
        this.tripComputer.setCar(this);
        this.gpsNavigator = gpsNavigator;
    }

    public Type getType(){return type;}
    public int getSeats(){return seats;}
    public Engine getEngine(){return engine;}
    public Transmission getTransmission(){return transmission;}
    public TripComputer getTripComputer(){return tripComputer;}
    public GPSNavigator getGpsNavigator(){return gpsNavigator;}
    public double getFuel(){return fuel;}
    public void setFuel(double fuel){this.fuel = fuel;}
}
```

##### Manual.java
```java
package Builder.cars;

import Builder.components.Engine;
import Builder.components.GPSNavigator;
import Builder.components.Transmission;
import Builder.components.TripComputer;

/**
 * Car manual is another product. Note that it does not have the same ancestor as a Car. They are not related.
 */

public class Manual
{
    private final Type type;
    private final int seats;
    private final Engine engine;
    private final Transmission transmission;
    private final TripComputer tripComputer;
    private final GPSNavigator gpsNavigator;

    public Manual(Type type, int seats, Engine engine, Transmission transmission,
                          TripComputer tripComputer, GPSNavigator gpsNavigator)
    {
        this.type = type;
        this.seats = seats;
        this.engine = engine;
        this.transmission = transmission;
        this.tripComputer = tripComputer;
        this.gpsNavigator = gpsNavigator;
    }

    public String print()
    {
        String info = "";
        info += "Type of car: " + type + "\n";
        info += "Count of seats: " + seats + "\n";
        info += "Engine: volume - " + engine.getVolume() + "; mileage - " + engine.getMileage() + "\n";
        info += "Transmission: " + transmission + "\n";

        if (this.tripComputer != null)
            info += "Trip Computer: Functional" + "\n";
        else
            info += "Trip Computer: N/A" + "\n";

        if (this.gpsNavigator != null)
            info += "GPS Navigator: Functional" + "\n";
        else
            info += "GPS Navigator: N/A" + "\n";

        return info;
    }
}
```

##### Type.java
```java
package Builder.cars;

public enum Type
{
    CITY_CAR, SPORTS_CAR, SUV
}
```

##### Director.java
```java
package Builder.director;

import Builder.builders.Builder;
import Builder.cars.Type;
import Builder.components.Engine;
import Builder.components.GPSNavigator;
import Builder.components.Transmission;
import Builder.components.TripComputer;

/**
 * 主管控制生成器
 * Director defines the order of building steps. It works with a builder object through common Builder interface.
 * Therefore it may not know what product is being built.
 */

public class Director
{
    public void constructSportsCar(Builder builder)
    {
        builder.setType(Type.SPORTS_CAR);
        builder.setSeats(2);
        builder.setEngine(new Engine(3.0, 0));
        builder.setTransmission(Transmission.SEMI_AUTOMATIC);
        builder.setTripComputer(new TripComputer());
        builder.setGPSNavigator(new GPSNavigator());
    }

    public void constructCityCar(Builder builder)
    {
        builder.setType(Type.CITY_CAR);
        builder.setSeats(2);
        builder.setEngine(new Engine(1.2, 0));
        builder.setTransmission(Transmission.AUTOMATIC);
        builder.setTripComputer(new TripComputer());
        builder.setGPSNavigator(new GPSNavigator());
    }

    public void constructSUV(Builder builder)
    {
        builder.setType(Type.SUV);
        builder.setSeats(4);
        builder.setEngine(new Engine(2.5, 0));
        builder.setTransmission(Transmission.MANUAL);
        builder.setTripComputer(new TripComputer());
        builder.setGPSNavigator(new GPSNavigator());
    }
}
```

##### Client.java
```java
package Builder;

import Builder.builders.CarBuilder;
import Builder.builders.CarManualBuilder;
import Builder.cars.Car;
import Builder.cars.Manual;
import Builder.director.Director;

public class Client
{
    public static void main(String[] args)
    {
        Director director = new Director();

        // Director gets the concrete builder object from the client (application code).
        // That's because application knows better which builder to use to get a specific product.
        CarBuilder carBuilder = new CarBuilder();
        director.constructSportsCar(carBuilder);
        // The final product is often retrieved from a builder object, since
        // Director is not aware and not dependent on concrete builders and  products.
        Car car = carBuilder.getResult();
        System.out.println("Car built:\n" + car.getType());


        CarManualBuilder carManualBuilder = new CarManualBuilder();
        // Director may know several building recipes.
        director.constructSportsCar(carManualBuilder);
        Manual manual = carManualBuilder.getResult();
        System.out.println("\nCar manual built:\n" + manual.print());
    }
}
```


### 原型模式(Prototype)
原型模式(Clone, Prototype)是一种创建型设计模式，使你<font color=red>能够复制已有对象，而又无需使代码依赖它们所属的类</font>。

#### 问题引入
<font color=red>如果你有一个对象，并希望生成与其完全相同的一个复制品</font>，你该如何实现呢？

首先，你必须新建一个属于相同类的对象。然后，你必须遍历原始对象的所有成员变量，并将成员变量值复制到新对象中。但有个小问题：<font color=red>并非所有对象都能通过这种方式进行复制，因为有些对象可能拥有私有成员变量</font>，它们在对象本身以外是不可见的。

直接复制还有另外一个问题。 因为你必须知道对象所属的类才能创建复制品， 所以代码必须依赖该类。 即使你可以接受额外的依赖性， 那还有另外一个问题： 有时你只知道对象所实现的接口， 而不知道其所属的具体类， 比如可向方法的某个参数传入实现了某个接口的任何对象。

#### 解决方案
原型模式将克隆过程委派给被克隆的实际对象。<font color=red>模式为所有支持克隆的对象声明了一个通用接口，该接口让你能够克隆对象，同时又无需将代码和对象所属类耦合</font>。通常情况下，这样的接口中仅包含一个`克隆方法`。

所有的类对`克隆方法`的实现都非常相似。<font color=red>该方法会创建一个当前类的对象，然后将原始对象所有的成员变量值复制到新建的类中。你甚至可以复制私有成员变量，因为绝大部分编程语言都允许对象访问其同类对象的私有成员变量</font>。

<font color=red>支持克隆的对象即为原型</font>。当你的对象有几十个成员变量和几百种类型时，对其进行克隆甚至可以代替子类的构造。

其运作方式如下：创建一系列不同类型的对象并不同的方式对其进行配置。如果所需对象与预先配置的对象相同，那么你只需克隆原型即可，无需新建一个对象。

#### 原型模式结构
基本实现：
<div align=center><img src=DesignPatterns/prototype.png width=100%></div>

原型注册表实现：
<div align=center><img src=DesignPatterns/prototype1.png width=100%></div>

#### 实例：复制图形
克隆一系列位于同一类层次结构中的对象：
<div align=center><img src=DesignPatterns/prototype2.png width=70%></div>

在不使用标准`Cloneable`接口的情况下实现原型模式：
<div align=center><img src=DesignPatterns/prototype3.png width=30%></div>

##### Shape.java
```java
package Prototype.shapes;

import java.util.Objects;

public abstract class Shape
{
    public int x;
    public int y;
    public String color;

    public Shape(){}

    public Shape(Shape target)
    {
        if (target != null)
        {
            this.x = target.x;
            this.y = target.y;
            this.color = target.color;
        }
    }

    public abstract Shape clone();

    @Override
    public boolean equals(Object object)
    {
        if (!(object instanceof Shape))
            return false;

        Shape shape = (Shape) object;
        return shape.x == x && shape.y == y && Objects.equals(shape.color, color);
    }
}
```

##### Circle.java
```java
package Prototype.shapes;

public class Circle extends Shape
{
    public int radius;

    public Circle(){}

    public Circle(Circle target)
    {
        super(target);
        if (target != null)
            this.radius = target.radius;
    }

    @Override
    public Shape clone()
    {
        return new Circle(this);
    }

    @Override
    public boolean equals(Object object)
    {
        if (!(object instanceof Circle) || !super.equals(object))
            return false;

        Circle circle = (Circle) object;
        return circle.radius == radius;
    }
}
```

##### Rectangle.java
```java
package Prototype.shapes;

public class Rectangle extends Shape
{
    public int width;
    public int height;

    public Rectangle(){}

    public Rectangle(Rectangle target)
    {
        super(target);
        if (target != null)
        {
            this.width = target.width;
            this.height = target.height;
        }
    }

    @Override
    public Shape clone()
    {
        return new Rectangle(this);
    }

    @Override
    public boolean equals(Object object)
    {
        if (!(object instanceof Rectangle) || !super.equals(object))
            return false;

        Rectangle rectangle = (Rectangle) object;
        return rectangle.width == width && rectangle.height == height;
    }
}
```

##### Demo.java
```java
package Prototype;

import Prototype.shapes.Circle;
import Prototype.shapes.Rectangle;
import Prototype.shapes.Shape;

import java.util.ArrayList;
import java.util.List;

public class Demo
{
    public static void main(String[] args)
    {
        List<Shape> shapes = new ArrayList<>();
        List<Shape> shapesCopy = new ArrayList<>();

        Circle circle = new Circle();
        circle.x = 10;
        circle.y = 20;
        circle.radius = 15;
        circle.color = "red";
        shapes.add(circle);

        Circle circle1 = (Circle) circle.clone();
        shapes.add(circle1);

        Rectangle rectangle = new Rectangle();
        rectangle.width = 10;
        rectangle.height = 20;
        rectangle.color = "blue";
        shapes.add(rectangle);

        cloneAndCompare(shapes, shapesCopy);
    }

    private static void cloneAndCompare(List<Shape> shapes, List<Shape> shapesCopy)
    {
        for (Shape shape : shapes)
            shapesCopy.add(shape.clone());

        for (int i = 0; i < shapes.size(); i++)
        {
            if (shapes.get(i) != shapesCopy.get(i))
            {
                System.out.println(i + ": Shapes are different objects !");

                if (shapes.get(i).equals(shapesCopy.get(i)))
                    System.out.println(i + ": And they are identical");
                else
                    System.out.println(i + ": But they are not identical");
            }
            else
                System.out.println(i + ": Shape objects are the same.");
        }
    }
}
```

**原型注册站**

你可以实现中心化的原型注册站（或工厂），其中包含一系列预定义的原型对象。这样一来，你就可以通过传递对象名称或其他参数的方式从工厂处获得新的对象。工厂将搜索合适的原型，然后对其进行克隆复制，最后将副本返回给你。

##### BundledShapeCache.java
```java
package Prototype.cache;

import Prototype.shapes.Circle;
import Prototype.shapes.Rectangle;
import Prototype.shapes.Shape;

import java.util.HashMap;
import java.util.Map;

public class BundledShapeCache
{
    private Map<String, Shape> cache = new HashMap<>();

    public BundledShapeCache()
    {
        Circle circle = new Circle();
        circle.x = 5;
        circle.y = 7;
        circle.radius = 45;
        circle.color = "Green";

        Rectangle rectangle = new Rectangle();
        rectangle.x = 6;
        rectangle.y = 9;
        rectangle.width = 8;
        rectangle.height = 10;
        rectangle.color = "Blue";

        cache.put("Big green circle", circle);
        cache.put("Medium blue rectangle", rectangle);
    }

    public Shape put(String key, Shape shape)
    {
        cache.put(key, shape);
        return shape;
    }

    public Shape get(String key)
    {
        return cache.get(key).clone();
    }
}
```

##### Demo1.java
```java
package Prototype;

import Prototype.cache.BundledShapeCache;
import Prototype.shapes.Shape;

public class Demo1
{
    public static void main(String[] args)
    {
        BundledShapeCache cache = new BundledShapeCache();

        Shape shape1 = cache.get("Big green circle");
        Shape shape2 = cache.get("Medium blue rectangle");
        Shape shape3 = cache.get("Medium blue rectangle");

        if (shape1 != shape2 && !shape1.equals(shape2))
            System.out.println("Big green circle != Medium blue rectangle (yay!)");
        else
            System.out.println("Big green circle == Medium blue rectangle (yay!)");

        if (shape2 != shape3)
        {
            System.out.println("Medium blue rectangles are two different objects (yay!)");
            if (shape2.equals(shape3))
                System.out.println("And they are identical (yay!)");
            else
                System.out.println("But they are not identical (booo!)");
        }
        else
            System.out.println("Rectangle objects are the same (booo!)");
    }
}
```

### 单例模式(Singleton)
单例模式是一种创建型设计模式，让你<font color=red>能够保证一个类只有一个实例，并提供一个访问该实例的全局节点</font>。

#### 问题引入
单例模式同时解决了两个问题， 所以违反了单一职责原则(Single Responsibility Principle)：

1. <font color=red>保证一个类只有一个实例</font>。 为什么会有人想要控制一个类所拥有的实例数量？ 最常见的原因是控制某些共享资源 （例如数据库或文件） 的访问权限。它的运作方式是这样的：如果你创建了一个对象，同时过一会儿后你决定再创建一个新对象，此时你会获得之前已创建的对象，而不是一个新对象。注意，普通构造函数无法实现上述行为，因为构造函数的设计决定了它必须总是返回一个新对象。

2. <font color=red>为该实例提供一个全局访问节点</font>。 对象的全局变量在使用上十分方便，但同时也非常不安全，因为任何代码都有可能覆盖掉那些变量的内容，从而引发程序崩溃。和全局变量一样，单例模式也允许在程序的任何地方访问特定对象。但是它可以保护该实例不被其他代码覆盖。还有一点：你不会希望解决同一个问题的代码分散在程序各处的。因此更好的方式是将其放在同一个类中，特别是当其他代码已经依赖这个类时更应该如此。

如今，单例模式已经变得非常流行，以至于人们会将只解决上文描述中任意一个问题的东西称为单例。

#### 解决方案
所有单例的实现都包含以下两个相同的步骤：

- 将默认构造函数设为私有，防止其他对象使用单例类的`new`运算符。

- 新建一个静态构建方法作为构造函数。该函数会 “偷偷” 调用私有构造函数来创建对象，并将其保存在一个静态成员变量中。此后所有对于该函数的调用都将返回这一缓存对象。

如果你的代码能够访问单例类，那它就能调用单例类的静态方法。无论何时调用该方法，它总是会返回相同的对象。

#### 单例模式结构
<div align=center><img src=DesignPatterns/Singleton.png width=100%></div>

#### 实例一：单线程
##### Singleton.java
```java
package Singleton.SingleThread;

public final class Singleton
{
    private static Singleton instance;
    public String value;

    private Singleton(String value)
    {
        // The following code emulates slow initialization.
        try
        {
            Thread.sleep(1000);
        }
        catch (InterruptedException ex)
        {
            ex.printStackTrace();
        }

        this.value = value;
    }

    public static Singleton getInstance(String value)
    {
        if (instance == null)
            instance = new Singleton(value);

        return instance;
    }
}
```

##### Client.java
```java
package Singleton.SingleThread;

public class Client
{
    public static void main(String[] args)
    {
        System.out.println("If you see the same value, then singleton was reused" + "\n" +
                "If you see different values, then 2 singletons were created" + "\n\n" +
                "RESULT:" + "\n");
        Singleton singleton = Singleton.getInstance("FOO");
        Singleton singleton1 = Singleton.getInstance("BAR");
        System.out.println(singleton.value);
        System.out.println(singleton1.value);
    }
}
```

#### 实例二：多线程
`Singleto.java`与前相同！
##### Client.java
```java
package Singleton.MultiThread;

public class Client
{
    public static void main(String[] args)
    {
        System.out.println("If you see the same value, then singleton was reused (yay!)" + "\n" +
                "If you see different values, then 2 singletons were created (booo!!)" + "\n\n" +
                "RESULT:" + "\n");
        Thread threadFoo = new Thread(new ThreadFoo());
        Thread threadBar = new Thread(new ThreadBar());
        threadFoo.start();
        threadBar.start();
    }

    static class ThreadFoo implements Runnable
    {
        @Override
        public void run()
        {
            Singleton singleton = Singleton.getInstance("FOO");
            System.out.println(singleton.value);
        }
    }

    static class ThreadBar implements Runnable
    {
        @Override
        public void run()
        {
            Singleton singleton = Singleton.getInstance("BAR");
            System.out.println(singleton.value);
        }
    }
}
```

#### 实例三：采用延迟加载的线程安全单例
##### Singleton.java
```java
package Singleton.ThreadSafe;

public final class Singleton
{
    // The field must be declared volatile so that double check lock would work
    // correctly.
    private static volatile Singleton instance;

    public String value;

    private Singleton(String value)
    {
        this.value = value;
    }

    public static Singleton getInstance(String value)
    {
        // The approach taken here is called double-checked locking (DCL). It
        // exists to prevent race condition between multiple threads that may
        // attempt to get singleton instance at the same time, creating separate
        // instances as a result.
        //
        // It may seem that having the `result` variable here is completely
        // pointless. There is, however, a very important caveat when
        // implementing double-checked locking in Java, which is solved by
        // introducing this local variable.
        //
        // You can read more info DCL issues in Java here:
        // https://refactoring.guru/java-dcl-issue
        Singleton result = instance;
        if (result != null)
        {
            return result;
        }
        synchronized(Singleton.class)
        {
            if (instance == null)
            {
                instance = new Singleton(value);
            }
            return instance;
        }
    }
}
```

##### Client.java
```java
package Singleton.ThreadSafe;

public class Client
{
    public static void main(String[] args)
    {
        System.out.println("If you see the same value, then singleton was reused (yay!)" + "\n" +
                "If you see different values, then 2 singletons were created (booo!!)" + "\n\n" +
                "RESULT:" + "\n");
        Thread threadFoo = new Thread(new ThreadFoo());
        Thread threadBar = new Thread(new ThreadBar());
        threadFoo.start();
        threadBar.start();
    }

    static class ThreadFoo implements Runnable
    {
        @Override
        public void run()
        {
            Singleton singleton = Singleton.getInstance("FOO");
            System.out.println(singleton.value);
        }
    }

    static class ThreadBar implements Runnable
    {
        @Override
        public void run()
        {
            Singleton singleton = Singleton.getInstance("BAR");
            System.out.println(singleton.value);
        }
    }
}
```