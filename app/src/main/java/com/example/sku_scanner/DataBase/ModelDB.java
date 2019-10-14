package com.example.sku_scanner.DataBase;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class ModelDB {

    @PrimaryKey(autoGenerate = true)
    private int id;


    @ColumnInfo(name = "shop_id")
    private String shop_id;

    @ColumnInfo(name = "category_id")
    private String category_id;

    @ColumnInfo(name = "barcode")
    private String barcode;

    @ColumnInfo(name = "image")
    private String image;


    public ModelDB(String shop_id, String category_id, String barcode, String image) {
        this.id = id;
        this.shop_id = shop_id;
        this.category_id = category_id;
        this.barcode = barcode;
        this.image = image;
    }

    public ModelDB() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getShop_id() {
        return shop_id;
    }

    public void setShop_id(String shop_id) {
        this.shop_id = shop_id;
    }

    public String getCategory_id() {
        return category_id;
    }

    public void setCategory_id(String category_id) {
        this.category_id = category_id;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
