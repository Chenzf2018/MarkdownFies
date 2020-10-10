package afterreturningadvice;

public class AfterAdviceServiceImpl implements AfterAdviceService {
    @Override
    public void save(String name) {
        System.out.println("测试后置通知(AfterAdviceServiceImpl)：" + name);
        throw new RuntimeException("save方法抛出异常");
    }
}
