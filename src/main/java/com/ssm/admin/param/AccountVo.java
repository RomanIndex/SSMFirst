package com.ssm.admin.param;

import com.ssm.admin.entity.SsmAccount;

import java.util.List;

public class AccountVo {
    private SsmAccount account;
    private List<String> others;

    public SsmAccount getAccount() {
        return account;
    }

    public void setAccount(SsmAccount account) {
        this.account = account;
    }

    public List<String> getOthers() {
        return others;
    }

    public void setOthers(List<String> others) {
        this.others = others;
    }
}
