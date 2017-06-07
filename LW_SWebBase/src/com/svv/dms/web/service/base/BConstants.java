package com.svv.dms.web.service.base;

public class BConstants {
    final public static String SUCCESS = "success";
    final public static String FAILURE = "failure";
    final public static String ERROR = "error";
    final public static String LIST2 = "list2";
    final public static String LIST = "list";
    final public static String TREELIST = "treelist";
    final public static String MESSAGE = "message";
    final public static String MESSAGE_PAGE = "messagePage";
    final public static String XML = "xml";
    final public static String POI = "poi";
    
    final public static String ADDPAGE = "addPage";
    final public static String EDITPAGE = "editPage";
    final public static String COMMITPAGE = "commitPage";
    final public static String QUERYPAGE = "queryPage";
    final public static String DETAIL = "detail";

    public final static int DEF_TABLE_HEIGHT = 585;
    public final static int DEF_PAGE_ROW = 20;
    

    public final static int OPTION_QUERY = 0;
    public final static int OPTION_EDIT_DEL = 1;
    public final static int OPTION_EDIT = 2;
    public final static int OPTION_DEL = 3;
    public final static int OPTION_ALL = 9;
    public static String option_query_string = "<img class=button onclick=\"detail(this,'%s','%s','%s')\" src=../doc/images/query.png title=查看>";
    public static String option_edit_string = "<img class=button onclick=\"edit(this,'%s','%s','%s')\" src=../doc/images/edit.png title=修改>";
    public static String option_copy_string = "<img class=button onclick=\"copy(this,'%s','%s','%s')\" src=../doc/images/copy.png title=复制>";
    public static String option_del_string = "<img class=button onclick=\"del(this,'%s','%s','%s')\" src=../doc/images/delete.png title=删除>";
    public static String option_sql_string = "<img class=button onclick=\"showDDL(this,'%s','%s','%s')\" src=../doc/images/sql.png title=\"View SQL\">";

    public static String optionbar_format_string = "<a id=\"btn%s\" class=\"l-btn-plain\" href=\"javascript:void(0)\" onclick=\"%s()\"><span class=\"l-btn-left\"><span class=\"l-btn-text icon-%s\" style=\"padding-left:20px;\">%s</span></span></a>";
    public static String optionbar_add_string = "<a id=\"btnadd\" class=\"l-btn-plain\" href=\"javascript:void(0)\" onclick=\"add()\"><span class=\"l-btn-left\"><span class=\"l-btn-text icon-add\" style=\"padding-left:20px;\">新增</span></span></a>";
    public static String optionbar_detail_string = "<a id=\"btndetail\" class=\"l-btn-plain l-btn-disabled\" href=\"javascript:void(0)\" onclick=\"if(document.all.focus_flag.value==1){detail()}\"><span class=\"l-btn-left\"><span class=\"l-btn-text icon-search\" style=\"padding-left:20px;\">查看</span></span></a>";
    public static String optionbar_edit_string = "<a id=\"btnedit\" class=\"l-btn-plain l-btn-disabled\" href=\"javascript:void(0)\" onclick=\"if(document.all.focus_flag.value==1){edit()}\"><span class=\"l-btn-left\"><span class=\"l-btn-text icon-edit\" style=\"padding-left:20px;\">编辑</span></span></a>";
    public static String optionbar_copy_string = "<a id=\"btncopy\" class=\"l-btn-plain l-btn-disabled\" href=\"javascript:void(0)\" onclick=\"if(document.all.focus_flag.value==1){copy()}\"><span class=\"l-btn-left\"><span class=\"l-btn-text icon-copy\" style=\"padding-left:20px;\">复制</span></span></a>";
    public static String optionbar_del_string = "<a id=\"btndel\" class=\"l-btn-plain l-btn-disabled\" href=\"javascript:void(0)\" onclick=\"if(document.all.focus_flag.value==1){del()}\"><span class=\"l-btn-left\"><span class=\"l-btn-text icon-remove\" style=\"padding-left:20px;\">删除</span></span></a>";
    public static String optionbar_cmt_string = "<a id=\"btncmt\" class=\"l-btn-plain l-btn-disabled\" href=\"javascript:void(0)\" onclick=\"if(document.all.focus_flag.value==1){cmt()}\"><span class=\"l-btn-left\"><span class=\"l-btn-text icon-cmt\" style=\"padding-left:20px;\">审核</span></span></a>";
}
