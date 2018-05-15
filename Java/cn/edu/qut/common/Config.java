package cn.edu.qut.common;

import java.text.SimpleDateFormat;

public class Config {
    /**
     * 默认部门
     */
    public static final Integer depablock = 1000;

    /**
     * 默认代表团
     */
    public static final Integer depublock = 10000;

    /**
     * 管理员权限
     */
    public static final String rootClass = "root";

    /**
     * 部门联络员权限
     */
    public static final String masterClass = "master";

    /**
     * 普通用户权限
     */
    public static final String userClass = "user";

    /**
     * 团长权限
     */
    public static final String adminClass = "user";

    /**
     * 分管校长权限
     */
    public static final String manageClass = "manager";

    /**
     * 删除标志位 true是存在 false是被删除
     */
    public static final String flag = "true";

    public static final SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * 会议的prop_Type
     */
    public static final String meetting = "会议";

    /**
     * 等待部署处理的状态
     */
    public static final String dealState = "部署中";// deployState

    /**
     * 等待团长审核的状态
     */
    public static final String checkState = "待审核";

    /**
     * 部署实施中的状态
     */
    public static final String deployState = "实施中";// dealState

    /**
     * 等待进行分类的提案
     */
    public static final String passState = "通过";

    /**
     * 得到审核要求的最少附议人数
     */
    public static final int minSupprotNum = 3;


    /**
     * 初始化状态,谁也没有进行握手
     */
    public static final Integer noShake = 0;

    /**
     * 部门第一次返回结果
     */
    public static final Integer deptShake = 1;

    /**
     * 提出人觉得不满意 进行交涉
     */
    public static final Integer memShake = 2;

    /**
     * 部门针对提出的交涉,再次修改意见
     */
    public static final Integer deptReShake = 3;

    /**
     * 分类的类型是立案
     */
    public static final String passType = "立案";
}
