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

    private int table;
    private String name;
    private String timein;
    private String timeout;
    private String storage;
    private int quantity;
    private float price;
    private float discount;
    private float bonus;
    private float subtotal;
    private float total;

    public int getTable() {
        return table;
    }

    public void setTable(int table) {
        this.table = table;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public float getDiscount() {
        return discount;
    }

    public void setDiscount(float discount) {
        this.discount = discount;
    }

    public float getBonus() {
        return bonus;
    }

    public void setBonus(float bonus) {
        this.bonus = bonus;
    }

    public float getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(float subtotal) {
        this.subtotal = subtotal;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    public BillsModel(int table, String name, String timein, String timeout, String storage, int quantity, float price, float discount, float bonus, float subtotal, float total) {
        this.table = table;
        this.name = name;
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

    public BillsModel(String name, int quantity) {
        this.name = name;
        this.quantity = quantity;
    }

    public BillsModel(int table, String timein, String timeout, float discount, float bonus, float subtotal, float total) {
        this.table = table;
        this.timein = timein;
        this.timeout = timeout;
        this.discount = discount;
        this.bonus = bonus;
        this.subtotal = subtotal;
        this.total = total;
    }
    
    

}
