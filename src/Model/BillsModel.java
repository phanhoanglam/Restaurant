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
public class BillsModel {
    private String table;
    private String timein;
    private String timeout;
    private String storage;
    private int quantity;
    private String price;
    private String discount;
    private String bonus;
    private String subtotal;
    private String total;

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public String getTimein() {
        return timein;
    }

    public void setTimein(String timein) {
        this.timein = timein;
    }

    public String getTimeout() {
        return timeout;
    }

    public void setTimeout(String timeout) {
        this.timeout = timeout;
    }

    public String getStorage() {
        return storage;
    }

    public void setStorage(String storage) {
        this.storage = storage;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getBonus() {
        return bonus;
    }

    public void setBonus(String bonus) {
        this.bonus = bonus;
    }

    public String getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(String subtotal) {
        this.subtotal = subtotal;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public BillsModel() {
    }

    public BillsModel(String table, String timein, String timeout, String storage, int quantity, String price, String discount, String bonus, String subtotal, String total) {
        this.table = table;
        this.timein = timein;
        this.timeout = timeout;
        this.storage = storage;
        this.quantity = quantity;
        this.price = price;
        this.discount = discount;
        this.bonus = bonus;
        this.subtotal = subtotal;
        this.total = total;
    }

    public BillsModel(String storage, int quantity) {
        this.storage = storage;
        this.quantity = quantity;
    }

    
    
}
