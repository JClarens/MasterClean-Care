package com.TA.MVP.appmobilemember.Model.Basic;

/**
 * Created by Zackzack on 20/07/2017.
 */

public class OfferTask {
    private Integer id;
    private Integer offer_id;
    private Integer task_list_id;
    private Integer status;
    private String created_at;
    private String updated_at;

    public Integer getId() {
        return id;
    }

    public Integer getOffer_id() {
        return offer_id;
    }

    public void setOffer_id(Integer offer_id) {
        this.offer_id = offer_id;
    }

    public Integer getTask_list_id() {
        return task_list_id;
    }

    public void setTask_list_id(Integer task_list_id) {
        this.task_list_id = task_list_id;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }
}
