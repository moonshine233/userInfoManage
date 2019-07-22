package cn.hwd.service;

import cn.hwd.domain.PageBean;
import cn.hwd.domain.User;

import java.util.List;

public interface UserService {

    public List<User> FindAll();
    public User login(User loginUser);
    public void add(User user);
    public void del(int id);
    public User findUserById(int id);
    public void update(User user);
    public void delSelectedUser(String[] ids);

    PageBean<User> findUserByPage(String currentPage, String rows);
}
