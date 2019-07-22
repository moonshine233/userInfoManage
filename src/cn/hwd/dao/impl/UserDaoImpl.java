package cn.hwd.dao.impl;

import cn.hwd.dao.UserDao;
import cn.hwd.domain.User;
import cn.hwd.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class UserDaoImpl implements UserDao {

    private JdbcTemplate template=new JdbcTemplate(JDBCUtils.getDataSource());
    @Override
    public List<User> findAll() {

        String sql="select * from user";
        List<User> users = template.query(sql, new BeanPropertyRowMapper<User>(User.class));

        return users;
    }

    @Override
    public User findUserByUsernameAndPassword(String username, String password) {
        try{
            String sql="select * from user where username=? and password=?";
            User user = template.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), username, password);
            return user;
        }catch (Exception e){
            return null;
        }

    }
    @Override
    public void add(User user) {
        String sql="insert into user(name,gender,age,address,qq,email) values('"+user.getName()+"','"+user.getGender()+"',"+user.getAge()+",'"+user.getAddress()+"','"+user.getQq()+"','"+user.getEmail()+"')";
        template.update(sql);
    }

    @Override
    public void del(int id) {
        String sql="DELETE FROM user WHERE id = ?";
        template.update(sql,id);
    }

    @Override
    public User findUserById(int id) {
        try{
            String sql="select * from user where id=?";
            User user = template.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), id);
            return user;
        }catch (Exception e){
            return null;
        }
    }

    @Override
    public void update(User user) {
        String sql="update user set name=? , gender=?,age=?,address=?,qq=?,email=?where id="+user.getId();
        template.update(sql,user.getName(),user.getGender(),user.getAge(),user.getAddress(),user.getQq(),user.getEmail());
    }

    @Override
    public int findTotalCount() {
        String sql="select count(*) from user";

        return template.queryForObject(sql,Integer.class);
    }

    @Override
    public List<User> findByPage(int start, int rows) {

        String sql="select * from user limit ? ,?";

        return template.query(sql,new BeanPropertyRowMapper<User>(User.class),start,rows);
    }
}
