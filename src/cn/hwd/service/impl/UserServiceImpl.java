package cn.hwd.service.impl;

import cn.hwd.dao.UserDao;
import cn.hwd.dao.impl.UserDaoImpl;
import cn.hwd.domain.PageBean;
import cn.hwd.domain.User;
import cn.hwd.service.UserService;

import java.util.List;

public class UserServiceImpl implements UserService {
    private UserDao dao=new UserDaoImpl();
    @Override
    public List<User> FindAll() {
        return dao.findAll();
    }

    @Override
    public User login(User loginUser) {
        return dao.findUserByUsernameAndPassword(loginUser.getUsername(),loginUser.getPassword());
    }

    @Override
    public void add(User user) {
        dao.add(user);
    }

    @Override
    public void del(int id) {
        dao.del(id);
    }

    @Override
    public User findUserById(int id) {
        User user = dao.findUserById(id);
        return user;
    }

    @Override
    public void update(User user) {
        dao.update(user);
    }

    @Override
    public void delSelectedUser(String[] ids) {
        if(ids!=null&&ids.length>0){
            for (String id: ids){
                dao.del(Integer.parseInt(id));
            };
        }

    }

    @Override
    public PageBean<User> findUserByPage(String _currentPage, String _rows) {

        int currentPage=Integer.parseInt(_currentPage);
        int rows=Integer.parseInt(_rows);

        PageBean<User> pb=new PageBean<User>();
        pb.setCurrentPage(currentPage);
        pb.setRows(rows);

        int totalCount = dao.findTotalCount();
        pb.setTotalCount(totalCount);

        int start=(currentPage-1)*rows;
        List<User> list=dao.findByPage(start,rows);
        pb.setList(list);

        int totalPage=totalCount%rows==0 ? totalCount/rows:totalCount/rows+1;
        pb.setTotalPage(totalPage);

        return pb;

    }


}
