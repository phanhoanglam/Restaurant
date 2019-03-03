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
public class CustomerModel {

    private String customer_name;
    private int customer_phone;
    private String customer_address;
    private int customer_table;
    private String datetime;

    public String getCustomer_name() {
        return customer_name;
    }

    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
    }

    public int getCustomer_phone() {
        return customer_phone;
    }

    public void setCustomer_phone(int customer_phone) {
        this.customer_phone = customer_phone;
    }

    public String getCustomer_address() {
        return customer_address;
    }

    public void setCustomer_address(String customer_address) {
        this.customer_address = customer_address;
    }

    public int getCustomer_table() {
        return customer_table;
    }

    public void setCustomer_table(int customer_table) {
        this.customer_table = customer_table;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public CustomerModel() {
    }

    public CustomerModel(String customer_name, int customer_phone, String customer_address, int customer_table, String datetime) {
        this.customer_name = customer_name;
        this.customer_phone = customer_phone;
        this.customer_address = customer_address;
        this.customer_table = customer_table;
        this.datetime = datetime;
    }

}
