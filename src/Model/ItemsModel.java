/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import javafx.collections.ObservableList;
import javafx.scene.control.Button;

/**
 *
 * @author Administrator
 */
public class ItemsModel {

    private int storage_item_id;
    private String category;
    private String name;
    private float price;
    private int quantity;
    private String images;
    private Button delete;

    public int getStorage_item_id() {
        return storage_item_id;
    }

    public void setStorage_item_id(int storage_item_id) {
        this.storage_item_id = storage_item_id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public Button getDelete() {
        return delete;
    }

    public void setDelete(Button delete) {
        this.delete = delete;
    }

    public ItemsModel(int storage_item_id, String category, String name, float price, int quantity, String images) {
        this.storage_item_id = storage_item_id;
        this.category = category;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.images = images;
    }

    public ItemsModel(String name, float price, int quantity, Button delete) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.delete = delete;
    }

    public ItemsModel(String name) {
        this.name = name;
    }

}
