/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author Administrator
 */
public class OutcomeModel {

    private int amount_id;
    private float amount;
    private String amountReason;
    private float income;
    private String user;
    private String time;
    private float total;

    public int getAmount_id() {
        return amount_id;
    }

    public void setAmount_id(int amount_id) {
        this.amount_id = amount_id;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public String getAmountReason() {
        return amountReason;
    }

    public void setAmountReason(String amountReason) {
        this.amountReason = amountReason;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public OutcomeModel() {
    }

    public float getIncome() {
        return income;
    }

    public void setIncome(float income) {
        this.income = income;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    public OutcomeModel(int amount_id, float amount, String amountReason, String user, String time) {
        this.amount_id = amount_id;
        this.amount = amount;
        this.amountReason = amountReason;
        this.user = user;
        this.time = time;
    }

    public OutcomeModel(float amount, float income, float total) {
        this.amount = amount;
        this.income = income;
        this.total = total;
    }

}
