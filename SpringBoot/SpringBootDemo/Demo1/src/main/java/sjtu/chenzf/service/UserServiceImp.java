package sjtu.chenzf.service;

import org.springframework.stereotype.Service;

@Service
public class UserServiceImp implements UserService {
    @Override
    public void save(String name) {
        System.out.println("name = " + name);
    }
}
