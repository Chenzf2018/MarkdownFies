# 内部类

内部类分为四种：非静态内部类；静态内部类；匿名类；本地类。

## 非静态内部类

非静态内部类可以直接**在一个类里面定义**。

示例：
- 战斗成绩只有在一个英雄对象存在的时候才有意义。所以实例化`BattleScore`的时候，必须建立在一个存在的英雄的基础上。即**非静态内部类，只有一个外部类对象存在的时候，才有意义**。
- 语法: `new 外部类().new 内部类()`；(`BattleScore score = hero.new BattleScore();`)
- 作为`Hero`的非静态内部类，是可以**直接访问外部类的private实例属性name**的。

`HeroInnerClass.java`

```java
package Basic.charactor;

public class HeroInnerClass {
    private String name;
    private float hp;  // 血量
    private float armor;  // 护甲
    private int moveSpeed;

    // 非静态内部类，只有一个外部类对象存在的时候，才有意义
    // 战斗成绩只有在一个英雄对象存在的时候才有意义

    class BattleScore{
        int kill, die, assist;

        public void legendary(){
            if (kill >= 8)
                System.out.println(name + "超神");  // 可以直接访问外部类的private实例属性name的
            else
                System.out.println(name + "尚未超神！");
        }
    }

    public static void main(String[] args){
        HeroInnerClass hero = new HeroInnerClass();
        hero.name = "盖伦";

        // 实例化内部类
        // BattleScore对象只有在一个英雄对象存在的时候才有意义
        // 所以其实例化必须建立在一个外部类对象的基础之上
        BattleScore score = hero.new BattleScore();
        score.kill = 9;
        score.legendary();
    }
}
```

## 静态内部类

在一个类里面声明一个静态内部类。

比如敌方水晶，当敌方水晶没有血的时候，己方所有英雄都取得胜利，而不只是某一个具体的英雄取得胜利。

与非静态内部类不同，**静态内部类的实例化不需要一个外部类的实例为基础**，可以直接实例化；

语法：`new 外部类.静态内部类();`

因为**没有一个外部类的实例**，所以在静态内部类里面**不可以访问外部类的实例属性和方法**。除了**可以访问外部类的私有静态成员**外，静态内部类和普通类没什么大的区别。

`HeroStaticInner.java`

```java
package Basic.charactor;

public class HeroStaticInner {
    private static String name;
    protected float hp;

    private static void battleWin(){
        System.out.println("Battle win");
    }

    // 静态内部类
    // 敌方水晶
    static class EnemyCrystal{
        int hp = 0;

        public void checkIfVictory(){
            if (hp == 0){
                HeroStaticInner.battleWin();

                //静态内部类不能直接访问外部类的对象属性
                // 无法从静态上下文中引用非静态 变量 name // public String name;
                System.out.println(name + " wins the game!");
            }
        }
    }

    public static void main(String[] args){
        //实例化静态内部类
        HeroStaticInner.EnemyCrystal crystal = new HeroStaticInner.EnemyCrystal();
        crystal.checkIfVictory();
    }
}
```

```
Battle win
null wins the game!
```

## 匿名类

匿名类指的是**在声明一个类的同时实例化它**，使代码更加简洁精练。通常情况下，要使用一个接口或者抽象类，都必须创建一个子类。有的时候，为了快速使用，**直接实例化一个抽象类**，**并“当场”实现其抽象方法**。**既然实现了抽象方法，那么就是一个新的类*8，只是这个类，**没有命名**。这样的类，叫做匿名类。

`HeroAnonymous.java`

```java
package Basic.charactor;

public abstract class HeroAnonymous {
    String name;
    float hp, armor;

    public abstract void attack();

    public static void main(String[] args){
        HeroAnonymous hero = new HeroAnonymous() {
            @Override
            public void attack() {
                System.out.println("进行攻击！");
            }
        };

        hero.attack();

        //通过打印hero，可以看到hero这个对象属于HeroAnonymous$1这么一个系统自动分配的类名
        System.out.println(hero);
    }
}
```

```
进行攻击！
Basic.charactor.HeroAnonymous$1@880ec60
```

## 本地类

本地类可以理解为**有名字的匿名类**。

内部类与匿名类不一样的是，内部类必须声明在成员的位置，即**与属性和方法平等的位置**。

本地类和匿名类一样，**直接声明在代码块里面**，可以是主方法，`for`循环里等等地方。

`HeroLocal.java`

```java
package Basic.charactor;

public abstract class HeroLocal {
    String name;

    public abstract void attack();

    public static void main(String[] args){
        //与匿名类的区别在于，本地类有了自定义的类名
        class SomeHero extends HeroLocal{
            @Override
            public void attack() {
                System.out.println(name + "进行攻击！");
            }
        }

        SomeHero hero = new SomeHero();
        hero.name = "Chenzf";
        hero.attack();
    }
}
```

```
Chenzf进行攻击！
```

## 在匿名类中使用外部的局部变量

在匿名类中使用外部的局部变量，**外部的局部变量必须修饰为`final`**。

为什么要声明为final，其机制比较复杂，请参考第二个Hero代码中的解释。

注：在jdk8中，已经不需要强制修饰成final了，如果没有写final，不会报错，因为编译器偷偷的帮你加上了看不见的final。

```java
package Basic.charactor;

public abstract class Hero1 {
    public abstract void attack();

    public static void main(String[] args){
        //在匿名类中使用外部的局部变量，外部的局部变量必须修饰为final
        final int damage = 5;

        Hero1 hero = new Hero1() {
            @Override
            public void attack() {
                System.out.printf("进攻，造成%d点伤害！", damage);
            }
        };
        
        hero.attack();
    }
}
```

```java{.line-numbers highlight=30}
package Basic.charactor;

public abstract class Hero2 {
    public abstract void attack();

    public static void main(String[] args){
        //在匿名类中使用外部的局部变量damage必须修饰为final
        int damage = 5;

        //这里使用本地类AnonymousHero来模拟匿名类的隐藏属性机制

        //事实上的匿名类，会在匿名类里声明一个damage属性，并且使用构造方法初始化该属性的值
        //在attack中使用的damage，真正使用的是这个内部damage，而非外部damage

        //假设外部属性不需要声明为final
        //那么在attack中修改damage的值，就会被暗示为修改了外部变量damage的值

        //但是他们俩是不同的变量，是不可能修改外部变量damage的
        //所以为了避免产生误导，外部的damage必须声明为final,"看上去"就不能修改了

        class AnonymousHero extends Hero2{
            int damage;

            public AnonymousHero(int damage){
                this.damage = damage;
            }

            @Override
            public void attack(){
                damage = 10;  // 注释掉这句，在int damage = 5;前加上final，“看起来”就不能修改了
                System.out.printf("进攻，造成了%d点伤害！", damage);
            }
        }

        Hero2 hero = new AnonymousHero(damage);
        hero.attack();
    }
}
/*
进攻，造成了10点伤害！
*/
```