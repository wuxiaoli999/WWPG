package com.svv.dms.web.common;

public class ParamClass {
    final public static int CLASS_PROCESS_TYPE = 100;   //流程分类
    final public static int CLASS_PROCESS_ACTOR_MODE = 101;   //流程经手模式
    final public static int CLASS_PROCESS_ACTOR_SELECT_RULE = 102;   //流程选人过滤规则
    final public static int CLASS_PROCESS_ACTOR_AUTOSELECT_RULE = 103;   //流程自动选人规则

    final public static int CLASS_ORGAN_TYPE = 200;   //机构类型
    final public static int CLASS_LEVEL = 201;   //等级：一级 二级 三级 四级
    final public static int CLASS_DATA_TYPE = 202;   //数据类型：1-NUMBER 2-VARCHAR2 3-DATE 4-CHAR 5-TEXT 6-DATATIME
    final public static int CLASS_FILE_TYPE = 204;   //文件类型：1-图片 2-音频 3-视频 4-文本
    final public static int CLASS_LANGUAGE = 205;   //系统语言
    final public static int CLASS_GROUPFUNC = 206;   //分组函数

    final public static int CLASS_DATA_STATUS = 801;   //数据状态：0-等待校对 1-等待审核入库 2-已入库，等待发布 3-已发布 9-已冻结
    final public static int CLASS_SCT_LEVEL = 802;   //密级：绝密 机密 秘密 内部 公开
    final public static int CLASS_VALUE_SCOPE_TYPE = 803;   //值域表：1-数据应用字典 2-编成体系 3-作战数据
    final public static int CLASS_RESOURCE_TABLE_TYPE = 804;   //数据来源：1-内部资料 2-现场采集 3-文献资料 4-网络资源
    final public static int CLASS_FORMATION_TYPE = 805;   //体系类别：1-编成体系 2-装备体系 3-交通运输体系 4-战备工程体系
    final public static int CLASS_DATASHOW_TYPE = 806;   //态势显示类型：1-防化力量 2-防化仓库 3-预备役防化力量 4-军工企业 5-核生化危险源 6-核生化武器

    /*********************************************************************************************/

    final public static int VALUE_PROCESS_DEALLINE_DEAL_TYPE_RETURN = 100001;   //流程期限处理方式：退回上一级
    final public static int VALUE_PROCESS_DEALLINE_DEAL_TYPE_PASS = 100002;   //流程期限处理方式：默认处理
    final public static int VALUE_PROCESS_ACTOR_MODE_11 = 102001;   //流程经手模式：一人名单一人处理
    final public static int VALUE_PROCESS_ACTOR_MODE_21 = 102002;   //流程经手模式：多人名单一人处理
    final public static int VALUE_PROCESS_ACTOR_MODE_22 = 102003;   //流程经手模式：多人名单多人并行处理
    
    final public static int VALUE_LEVEL_ZERO = 201000;   //等级：0级
    final public static int VALUE_LEVEL_ONE = 201001;   //等级：一级
    final public static int VALUE_LEVEL_TWO = 201002;   //等级：二级
    final public static int VALUE_LEVEL_THREE = 201003;   //等级：三级
    final public static int VALUE_LEVEL_FOUR = 201004;   //等级：四级
    
    final public static int VALUE_LANGUAGE_ONE = 205001;   //语言1
    final public static int VALUE_LANGUAGE_TWO = 205002;   //语言2
    final public static int VALUE_LANGUAGE_THREE = 205003;   //语言3
    final public static int VALUE_LANGUAGE_FOUR = 205004;   //语言4
    final public static int VALUE_LANGUAGE_FIVE = 205005;   //语言5

    final public static int VALUE_GROUPFUNC_GROUPBY = 206000;   //分组函数：groupby
    final public static int VALUE_GROUPFUNC_SUM = 206001;   //分组函数：sum
    final public static int VALUE_GROUPFUNC_MAX = 206002;   //分组函数：max
    final public static int VALUE_GROUPFUNC_MIN = 206003;   //分组函数：min
    final public static int VALUE_GROUPFUNC_COUNT = 206004;   //分组函数：count

    final public static int VALUE_DATA_TYPE_NUMBER = 202001;    //数据类型：NUMBER
    final public static int VALUE_DATA_TYPE_VARCHAR2 = 202002;  //数据类型：VARCHAR2
    final public static int VALUE_DATA_TYPE_DATE = 202003;      //数据类型：DATE
    final public static int VALUE_DATA_TYPE_CHAR = 202004;      //数据类型：CHAR
    final public static int VALUE_DATA_TYPE_TEXT = 202005;      //数据类型：TEXT
    final public static int VALUE_DATA_TYPE_DATETIME = 202006;  //数据类型：DATATIME
    final public static int VALUE_DATA_TYPE_FILE = 202007;      //数据类型：FILE
    final public static int VALUE_DATA_TYPE_IMGFILE = 202008;   //数据类型：IMGFILE
    final public static int VALUE_DATA_TYPE_IMGLINK = 202009;   //数据类型：IMGLINK
    final public static int VALUE_DATA_TYPE_MD5 = 202010;       //数据类型：MD5加密文
    final public static int VALUE_DATA_TYPE_UGID = 202011;       //数据类型：MD5加密文
    final public static int VALUE_DATA_TYPE_RADIO = 202012;     //数据类型：单选框
    final public static int VALUE_DATA_TYPE_CHECKBOX = 202013;  //数据类型：多选框

    final public static int VALUE_FILE_TYPE_PIC = 204001;    //文件类型：1-图片 2-音频 3-视频 4-文本
    final public static int VALUE_FILE_TYPE_AUDIO = 204002;
    final public static int VALUE_FILE_TYPE_VIDIO = 204003;
    final public static int VALUE_FILE_TYPE_FILE = 204004;
    
    final public static int VALUE_DATA_STATUS_BEGIN = 801000;   //数据状态：0-初始
    final public static int VALUE_DATA_STATUS_WAITING_CHECK = 801001;   //数据状态：1-初始
    final public static int VALUE_DATA_STATUS_WAITING_CMT = 801003;   //数据状态：3-等待审核入库
    final public static int VALUE_DATA_STATUS_WAITING_PUBLISH = 801004;   //数据状态：4-已入库，等待发布
    final public static int VALUE_DATA_STATUS_DONGJIE = 801005;   //数据状态：5-已冻结
    final public static int VALUE_DATA_STATUS_PUBLISHED = 801009;   //数据状态：9-已发布

    final public static int VALUE_SCT_LEVEL_PUBLIC = 802001;   //密级：1-公开
    final public static int VALUE_SCT_LEVEL_JUEMI = 802005;   //密级：5-绝密

    final public static int VALUE_VALUE_SCOPE_TYPE_SYS_DATAPARAM = 803001;   //值域表：1-数据应用字典
    final public static int VALUE_VALUE_SCOPE_TYPE_DEF_USERINFO = 803002;   //值域表：2-默认值:当前登录信息
    final public static int VALUE_VALUE_SCOPE_TYPE_DEF_CURDATE = 803003;   //值域表：4-默认值:当前日期
    final public static int VALUE_VALUE_SCOPE_TYPE_DEF_NEXTDATE = 803004;   //值域表：4-默认值:明日日期
    final public static int VALUE_VALUE_SCOPE_TYPE_DEF_DEFINE = 803008;   //值域表：4-默认值:自定义
    final public static int VALUE_VALUE_SCOPE_TYPE_BIZ_DATATABLE = 803009;   //值域表：9-业务数据
    final public static int VALUE_VALUE_SCOPE_TYPE_BIZ_DATATABLE_COL = 803010;   //值域表：10-业务数据字段
    final public static int VALUE_VALUE_SCOPE_TYPE_DES_FILE = 803011;   //值域表：11-加密文件
    final public static int VALUE_VALUE_SCOPE_TYPE_DES_FILENAME = 803012;   //值域表：12-加密文件名

    final public static int VALUE_RESOURCE_TABLE_TYPE_001 = 804001;
    final public static int VALUE_RESOURCE_TABLE_TYPE_002 = 804002;
    final public static int VALUE_RESOURCE_TABLE_TYPE_003 = 804003;
    final public static int VALUE_RESOURCE_TABLE_TYPE_004 = 804004;
    
    final public static int VALUE_FORMATION_TYPE_DEPARTMENT = 805001;   //体系类别：1-部门体系
    final public static int VALUE_FORMATION_TYPE_EMP = 805002;          //体系类别：2-人员体系

    
    
    final public static int VALUE_SUPER_ADMIN_POWER = 9;   //超级用户
}
