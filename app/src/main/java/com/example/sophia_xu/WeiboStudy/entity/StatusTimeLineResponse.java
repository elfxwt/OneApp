package com.example.sophia_xu.WeiboStudy.entity;

import java.util.ArrayList;

/**
 * Created by Sophia_Xu on 2015/8/11.
 */
public class StatusTimeLineResponse {
    public ArrayList<Status> getStatuses() {
        return statuses;
    }

    public void setStatuses(ArrayList<Status> statuses) {
        this.statuses = statuses;
    }

    public int getTotal_number() {
        return total_number;
    }

    public void setTotal_number(int total_number) {
        this.total_number = total_number;
    }

    private ArrayList<Status> statuses;
    private int total_number;

}
