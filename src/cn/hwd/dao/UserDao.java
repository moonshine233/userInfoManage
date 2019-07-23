package cn.hwd.dao;

import cn.hwd.domain.User;

import java.util.List;
import java.util.Map;

public interface UserDao {
    public List<User> findAll();
    public User findUserByUsernameAndPassword(String username,String password);
    public void add(User user);
    public void del(int id);
    public User findUserById(int id);
    public void update(User user);

    public int findTotalCount(Map<String, String[]> condition);

    List<User> findByPage(int start, int rows, Map<String, String[]> condition);

}
