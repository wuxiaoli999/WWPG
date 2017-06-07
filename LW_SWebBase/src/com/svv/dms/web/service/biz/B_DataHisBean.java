package com.svv.dms.web.service.biz;

import com.svv.dms.web.dao.SQL;

public class B_DataHisBean extends B_DataBean {

    public String B_DataPublic_his(){
        return this.exeByCmd("");
    }
    public String B_DataPublicChild_his(){
        return this.exeByCmd("");
    }

	protected String saveQueryCondition(){
        return SQL.SP_B_DataHisQueryByC(ao.getTableID(), ao.getTableName(), ao.getDataid()+"", "", this.getUserSession().getSctLevel()+"");
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 6920136402174677699L;

}
