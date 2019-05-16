package br.com.briefer.briefer.model;

import java.io.Serializable;
import java.util.Date;

public class Budget implements Serializable {
    private Date time_goal;
    private double cost;

    public Date getTime_goal() {
        return time_goal;
    }

    public void setTime_goal(Date time_goal) {
        this.time_goal = time_goal;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }
}
