package ru.itmentor.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.itmentor.spring.boot_security.demo.model.User;
import ru.itmentor.spring.boot_security.demo.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getUsers(){
        return userRepository.findAll();
    }

    public Optional<User> findUserByName(String name){
        return userRepository.findByName(name);
    }

    public void create(User user) {
//        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    public User read(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("User with " + id + " not found"));
    }

    public void updateUser(User user) {
        Optional<User> optionalUser = userRepository.findById(user.getId());
        if (optionalUser.isPresent()) {
            User dbUser = optionalUser.get();
            dbUser.setName(user.getName());
            dbUser.setEmail(user.getEmail());
            dbUser.setPassword(user.getPassword());
            dbUser.setRoles(user.getRoles());
            userRepository.save(dbUser);
        } else {
            // handle the case where the user is not found
        }
    }


//    public void update(User user) {
//        userRepository.updateUser(user);
//    }

    public void delete(User user) {
        userRepository.delete(user);
    }
    public void deleteById(Long id) {
        userRepository.deleteUserById(id);
    }
}
