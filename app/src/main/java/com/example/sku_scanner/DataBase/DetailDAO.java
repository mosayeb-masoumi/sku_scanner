package com.example.sku_scanner.DataBase;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface DetailDAO {
    //get all list
    @Query("SELECT * FROM modeldb")
    List<ModelDB> getAllMyModelSaveDB();


    //add
    @Insert
    void insertAll(ModelDB modelDB);


//    //search
//    @Query("SELECT * FROM modeldb WHERE barcode = :name")
//    List<ModelDB> findProduct(String name);


    // delete each item (use in adapter)
    @Delete
    void deleteItem(ModelDB modelDB);

    //delete all
    @Query("DELETE FROM modeldb")
    void deleteAll();


    @Update
    void updateItem(ModelDB myModelSaveDB);
}
