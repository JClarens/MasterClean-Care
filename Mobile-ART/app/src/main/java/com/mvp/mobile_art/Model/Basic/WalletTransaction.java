package com.mvp.mobile_art.Model.Basic;

import java.util.Date;

/**
 * Created by Zackzack on 14/07/2017.
 */

public class WalletTransaction {
    private Integer id;
    private User user;
    private Integer amount;
    private Integer trc_type;
    private Date trc_time;

    public Integer getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Integer getTrc_type() {
        return trc_type;
    }

    public void setTrc_type(Integer trc_type) {
        this.trc_type = trc_type;
    }

    public Date getTrc_time() {
        return trc_time;
    }

    public void setTrc_time(Date trc_time) {
        this.trc_time = trc_time;
    }
}
