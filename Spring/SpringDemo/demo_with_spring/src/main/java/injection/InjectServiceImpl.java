package injection;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class InjectServiceImpl implements InjectService {

    /**
     * 依赖InjectDAO组件
     * 提供SET方法
     */
    private InjectDAO injectDAO;

    public void setInjectDAO(InjectDAO injectDAO) {
        this.injectDAO = injectDAO;
    }

    /**
     * SET注入String
     */
    private String name;

    public void setName(String name) {
        this.name = name;
    }

    /**
     * SET注入Integer
     */
    private Integer age;

    public void setAge(Integer age) {
        this.age = age;
    }


    /**
     * SET注入Date类型
     */
    private Date date;

    public void setDate(Date date) {
        this.date = date;
    }

    /**
     * 注入数组
     */
    private String[] stringArray;

    public void setStringArray(String[] stringArray) {
        this.stringArray = stringArray;
    }

    private InjectDAO[] injectDAOS;

    public void setInjectDAOS(InjectDAO[] injectDAOS) {
        this.injectDAOS = injectDAOS;
    }

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

    /**
     * 注入Properties
     */
    private Properties properties;

    public void setProperties(Properties properties) {
        this.properties = properties;
    }

    @Override
    public void testInject(String string) {
        System.out.println("Test Injection(InjectService): " + string);
        injectDAO.testInject(string);

        System.out.println("String类型注入一个名字： " + name);
        System.out.println("Integer类型注入年龄:：" + age);
        System.out.println("Date类型注入一个日期：" + date);

        System.out.println();
        System.out.println("遍历数组");
        for (String str : stringArray) {
            System.out.print(str + " ");
        }

        System.out.println();
        for (InjectDAO injectDAO : injectDAOS) {
            System.out.println(injectDAO);
        }

        System.out.println();
        System.out.println("遍历集合");
        habbies.forEach(habby -> System.out.println("habby: " + habby));
        maps.forEach((key, value) -> System.out.println("key: " + key + "; value: " + value));
        System.out.println();
        System.out.println("遍历配置文件Properties");
        properties.forEach((key, value) -> System.out.println("key: " + key + "; value: " + value));
    }
}
