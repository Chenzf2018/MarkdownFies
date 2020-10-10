package factorybean;

import org.springframework.beans.factory.FactoryBean;

import java.util.Calendar;

/**
 * 在工厂中创建复杂对象
 */
public class CalendarFactoryBean implements FactoryBean<Calendar> {

    /**
     * 复杂对象的创建方式
     * @return 复杂对象
     */
    @Override
    public Calendar getObject() {
        return Calendar.getInstance();
    }

    /**
     * 指定所创建的复杂对象的类型
     * @return
     */
    @Override
    public Class<?> getObjectType() {
        return Calendar.class;
    }

    /**
     * 用来指定创建对象的模式：单例或多例
     * @return true:单例；false:多例
     */
    @Override
    public boolean isSingleton() {
        return true;
    }
}
