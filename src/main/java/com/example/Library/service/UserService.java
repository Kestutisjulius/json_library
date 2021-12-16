package com.example.Library.service;

import com.example.Library.model.Book;
import com.example.Library.model.SecurityUser;
import com.example.Library.model.User;
import com.example.Library.exseption.*;
import com.example.Library.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    public List<User> findAllUsers() {return userRepository.findAll();}

    public void addUser (User user) {
        user.setEmployeeCode(UUID.randomUUID().toString());
        userRepository.save(user);
        log.info("User was added");
    }
    public void updateUser(User user) {
        if (user.getBooks().size() < 3) {
            userRepository.save(user);
        } else {
            System.out.println("the maximum of three books has been reached");
        }
        userRepository.save(user); }

    public User findUserById(Long id) throws UserNotFoundException{
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User by id " + id + " was not found!"));
    }
    public User getUser(Long id) {
        if (userRepository.findById(id).isPresent()) {
            User user = userRepository.findById(id).get();
            User user1 = new User();
            user1.setName(user.getName());
            user1.setBooks(getBookList(user));
            user1.setEmployeeCode(user.getEmployeeCode());
            user1.setAuthority(user.getAuthority());
            user1.setEmail(user.getEmail());
            user1.setPassword(user.getPassword());
            user1.setImageURL(user.getImageURL());
            return user1;
        } else return null;
    }

    public List<User> getUsers() {
        List<User> userList = userRepository.findAll();
        if (userList.size() > 0) {
            List<User> users = new ArrayList<>();
            for (User user: userList
                 ) {
                User user1 = new User();
                user1.setName(user.getName());
                user1.setBooks(getBookList(user));
                user1.setEmployeeCode(user.getEmployeeCode());
                user1.setAuthority(user.getAuthority());
                user1.setEmail(user.getEmail());
                user1.setPassword(user.getPassword());
                user1.setImageURL(user.getImageURL());
                users.add(user1);
            } return users;
        } else return new ArrayList<User>();
    }

    private List<Book> getBookList(User user) {
        List<Book> bookList = new ArrayList<>();
        for (int i = 0; i < user.getBooks().size(); i++) {
            Book book = new Book();
            book.setName(user.getBooks().get(i).getName());
            bookList.add(book);
        } return bookList;
    }

    public void deleteUserById(Long id) {
        userRepository.deleteUserById(id);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<User> user = userRepository.findByEmail(username);
        if (user.size() == 0) {
            throw new UsernameNotFoundException("User details not found for the user : " + username);
        }
        return new SecurityUser(user.get(0));
    }


}
