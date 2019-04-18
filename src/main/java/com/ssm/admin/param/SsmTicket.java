package com.ssm.admin.param;

/**
 * SSM 专属 权限票据 类
 */
public class SsmTicket {
    private String code;

    private String moduleId;

    private String operate;

    //正常 不应该 只存 一个ID，应该存 判断出 结果的
    private String validDateId;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getModuleId() {
        return moduleId;
    }

    public void setModuleId(String moduleId) {
        this.moduleId = moduleId;
    }

    public String getOperate() {
        return operate;
    }

    public void setOperate(String operate) {
        this.operate = operate;
    }

    public String getValidDateId() {
        return validDateId;
    }

    public void setValidDateId(String validDateId) {
        this.validDateId = validDateId;
    }
}
