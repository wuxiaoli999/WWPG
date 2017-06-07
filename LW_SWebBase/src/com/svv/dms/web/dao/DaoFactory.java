package com.svv.dms.web.dao;

import com.gs.db.dao.DaoJDBCManager;
import com.gs.db.dao.DaoManager;

public class DaoFactory {

    public static final int DATABASE_MODE_JDBC = 0;
    ////public static final int DATABASE_MODE_TSVR = 1;

    public static DaoManager getDaoManager(){     
        return getDaoManager(DATABASE_MODE_JDBC);
    }
    
    public static DaoManager getDaoManager(int type){
        
        if(DATABASE_MODE_JDBC == type)
            return new DaoJDBCManager();
        return new DaoJDBCManager();
    }
    
}
