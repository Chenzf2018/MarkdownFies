package service;

// 原始业务对象——目标对象

public class EmpServiceImpl implements EmpService {
    @Override
    public void save(String name) {
        System.out.println("EmpServiceImpl处理业务逻辑（调用saveDAO）：" + name);
    }

    @Override
    public String find(String name) {
        System.out.println("EmpServiceImpl处理业务逻辑（调用findDAO）：" + name);
        return name;
    }
}
