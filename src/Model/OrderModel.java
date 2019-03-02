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
public class OrderModel {
    private int order_id;
    private int bill_id;
    private int table_id;
    private String time_in;

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public int getBill_id() {
        return bill_id;
    }

    public void setBill_id(int bill_id) {
        this.bill_id = bill_id;
    }

    public int getTable_id() {
        return table_id;
    }

    public void setTable_id(int table_id) {
        this.table_id = table_id;
    }

    public String getTime_in() {
        return time_in;
    }

    public void setTime_in(String time_in) {
        this.time_in = time_in;
    }

    public OrderModel() {
    }

    public OrderModel(int order_id, int bill_id, int table_id, String time_in) {
        this.order_id = order_id;
        this.bill_id = bill_id;
        this.table_id = table_id;
        this.time_in = time_in;
    }
    
    
}
