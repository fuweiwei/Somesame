package com.somesame.somesame.db.dao;

import com.somesame.somesame.db.entity.User;
import com.somesame.somesame.db.greendao.UserDao;

import java.util.List;

/**
 * 用户信息表操作
 * @author Veer
 * @email 276412667@qq.com
 * @date 18/8/16
 */

public class DbUserDao extends DbDao {
    private volatile static  DbUserDao mDbUserDao;
    private  UserDao mUserDao;

    private DbUserDao() {
        super();
        mUserDao = getDaoSession().getUserDao();
    }

    public static DbUserDao getInstance(){
        if(mDbUserDao == null){
            synchronized (DbUserDao.class){
                mDbUserDao = new DbUserDao();
            }
        }
        return mDbUserDao;
    }

    /**
     * 添加用户信息
     * @param user
     */
    public void addUser(User user){
        List<User> users =  mUserDao.loadAll();
        if(users!=null&& !users.isEmpty()){
            mUserDao.deleteAll();
        }
        mUserDao.insertOrReplace(user);
    }
    /**
     * 获取用户信息
     */
    public User getUser(){
       List<User> users =  mUserDao.loadAll();
       if(users!=null&& !users.isEmpty()){
          return users.get(0);
       }
       return  new User();
    }
    /**
     * 删除用户用户信息
     * @param user
     */
    public void deleteUser(User user){
        mUserDao.delete(user);
    }


}
