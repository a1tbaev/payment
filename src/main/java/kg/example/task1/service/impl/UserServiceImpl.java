package kg.example.task1.service.impl;

import kg.example.task1.entity.User;
import kg.example.task1.repo.UserRepository;
import kg.example.task1.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void save(User user) {
        userRepository.save(user);
    }
}
