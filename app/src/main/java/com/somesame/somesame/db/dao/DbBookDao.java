package com.somesame.somesame.db.dao;

import com.somesame.somesame.db.entity.Book;
import com.somesame.somesame.db.greendao.BookDao;

/**
 * 书本信息表 操作
 * @author Veer
 * @email 276412667@qq.com
 * @date 18/8/16
 */

public class DbBookDao extends DbDao {
    private static DbBookDao mDbBookDao;
    private BookDao mBookDao;

    private DbBookDao(){
        super();
        mBookDao = getDaoSession().getBookDao();

    }
    public static DbBookDao getInstance(){
        if(mDbBookDao == null){
            synchronized (DbBookDao.class){
                mDbBookDao = new DbBookDao();
            }

        }
        return mDbBookDao;
    }
    /**
     * 添加书本信息
     * @param book
     */
    public void addBook(Book book){
        mBookDao.insert(book);
    }
}
