package com.chenzf.service;

import com.chenzf.dao.UserDAO;
import com.chenzf.entity.User;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.util.List;
import java.util.UUID;

public class UserServiceImpl implements UserService {

    /**
     * 依赖注入
     */
    private UserDAO userDAO;

    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    /**
     * 注入事务管理器依赖
     */
    private PlatformTransactionManager platformTransactionManager;

    public void setPlatformTransactionManager(PlatformTransactionManager platformTransactionManager) {
        this.platformTransactionManager = platformTransactionManager;
    }

    @Override
    public List<User> findAllUser() {
        return userDAO.findAllUser();
    }

    @Override
    public void saveUser(User user) {
        // 控制事务

        // 为了获取事务状态，需要创建事务配置对象
        TransactionDefinition transactionDefinition = new DefaultTransactionDefinition();
        // 获取当前事务状态
        TransactionStatus transactionStatus = platformTransactionManager.getTransaction(transactionDefinition);

        try {
            // 处理业务
            user.setId(UUID.randomUUID().toString());

            // 调用业务
            userDAO.saveUser(user);

            // 抛出异常
            // throw new RuntimeException("出错了，验证是否可以回滚！");
            System.out.println("1/0");
            int i = 1 / 0;

            // 提交需要传入事务状态
            platformTransactionManager.commit(transactionStatus);

        } catch (Exception e) {
            e.printStackTrace();
            // 回滚需要传入事务状态
            platformTransactionManager.rollback(transactionStatus);
        }
    }
}
