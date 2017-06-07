package com.svv.dms.web.service.biz;

import com.svv.dms.web.dao.SQL;

public class B_DataChildBean extends B_DataBean {

    public String B_DataPublicChild(){
        return this.exeByCmd("");
    }
    public String B_DataPublicQueryChild(){
        return this.exeByCmd("");
    }
    public String B_ProcessChild(){
        return this.exeByCmd("");
    }
	protected String saveQueryCondition(){
        return SQL.SP_B_DataQueryByC(ao.getTableID(), ao.getTableName(), ao.getParentDataid()+"", "", this.getUserSession().getSctLevel()+"", getKeywordColNames(), c_keyword);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 6920136402174677699L;

	private int afp = 0;

	public int getAfp() {
		return afp;
	}
	public void setAfp(int afp) {
		this.afp = afp;
	}
}
