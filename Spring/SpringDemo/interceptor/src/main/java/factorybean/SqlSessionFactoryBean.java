package factorybean;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.springframework.beans.factory.FactoryBean;

import java.io.InputStream;

/**
 * 自定义创建SqlSessionFactory复杂对象
 */
public class SqlSessionFactoryBean implements FactoryBean<SqlSessionFactory> {

    /**
     * mybatis-config.xml位置属性注入
     */
    private String configLocation;

    public void setConfigLocation(String configLocation) {
        this.configLocation = configLocation;
    }

    @Override
    public SqlSessionFactory getObject() throws Exception {
        // import org.apache.ibatis.io.Resources;
        InputStream inputStream = Resources.getResourceAsStream(configLocation);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        return sqlSessionFactory;
    }

    @Override
    public Class<?> getObjectType() {
        return SqlSessionFactory.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
}
