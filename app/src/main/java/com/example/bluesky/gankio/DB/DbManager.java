package com.example.bluesky.gankio.DB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.bluesky.gankio.DateBean.DaoMaster;
import com.example.bluesky.gankio.DateBean.DaoSession;
import com.example.bluesky.gankio.DateBean.LoveDate;
import com.example.bluesky.gankio.DateBean.LoveDateDao;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

/**
 *
 *
 *  单例模式 使用单例
 * Created by localhost on 2016/11/23.
 */

public class DbManager {
    private static final String DBName="Love_Db";
    private  Context mcontext;
    private static DbManager mInstance=null;
    private DaoMaster.DevOpenHelper openHelper;
    private DbManager (Context context){
        mcontext=context;
        openHelper=new DaoMaster.DevOpenHelper(context,DBName,null);
    }
    public static DbManager getInstance(Context context){
        if(mInstance==null){
            synchronized (DbManager.class){
                if(mInstance==null)
                    mInstance=new DbManager(context);
            }
        }
        return mInstance;
    }

    /**
     * get Datebase
     * @param type for the kind of SqliteBatabase
     * @return
     */
    private SQLiteDatabase getDatabase(String type){
        SQLiteDatabase m=null;
        if(openHelper==null){
            openHelper=new DaoMaster.DevOpenHelper(mcontext,DBName,null);
        }
        if(type.equals("R")||type.equals("r")){
            m=openHelper.getReadableDatabase();
        }

        if(type.equals("W")||type.equals("w")){
            m=openHelper.getWritableDatabase();
        }
        return m;
    }

    /**
     *
     *insert a into the Batabase
     * @param a  for LoveDate
     */
    public void IntoData(LoveDate a){
        DaoMaster master=new DaoMaster(getDatabase("W"));
        DaoSession mDaoSession=master.newSession();
        LoveDateDao dateDao=mDaoSession.getLoveDateDao();
        dateDao.insert(a);
    }

    /**
     * insert for List<LoveDate>
     * @param l
     */
    public void IntoDatea(List<LoveDate> l){
        if(l==null||l.isEmpty())
            return;
        DaoMaster daoMaster = new DaoMaster(getDatabase("W"));
        DaoSession daoSession = daoMaster.newSession();
        LoveDateDao dateDao = daoSession.getLoveDateDao();
        dateDao.insertInTx(l);
    }


    //delate
    public void deleteData(LoveDate a) {
        DaoMaster daoMaster = new DaoMaster(getDatabase("W"));
        DaoSession daoSession = daoMaster.newSession();
        LoveDateDao dateDao = daoSession.getLoveDateDao();
        dateDao.delete(a);
    }

    public void updateData(LoveDate a) {
        DaoMaster daoMaster = new DaoMaster(getDatabase("w"));
        DaoSession daoSession = daoMaster.newSession();
        LoveDateDao dateDao = daoSession.getLoveDateDao();
        dateDao.update(a);
    }
    //查询所有列表
    public List<LoveDate> queryAllList() {
        DaoMaster daoMaster = new DaoMaster(getDatabase("r"));
        DaoSession daoSession = daoMaster.newSession();
        LoveDateDao dateDao = daoSession.getLoveDateDao();
        QueryBuilder<LoveDate> qb = dateDao.queryBuilder();
        List<LoveDate> list = qb.list();
        return list;
    }

    /**
     * 便于分享
     *
     * @return
     */
     public String queryAllString(){
         StringBuilder a=new StringBuilder();
         List<LoveDate> d=queryAllList();
         for(LoveDate k:d){
             a.append(k.getUrl()+"\n");
         }
         return a.toString();
     }

    /**
     * 查询某个列表
     */
    public List<LoveDate> queryList(String s ) {
        DaoMaster daoMaster = new DaoMaster(getDatabase("r"));
        DaoSession daoSession = daoMaster.newSession();
        LoveDateDao dateDao = daoSession.getLoveDateDao();
        QueryBuilder<LoveDate> qb = dateDao.queryBuilder();
        qb.where(LoveDateDao.Properties.Url.eq(s)).orderAsc(LoveDateDao.Properties.Url);
        List<LoveDate> list = qb.list();
        return list;
    }
}
