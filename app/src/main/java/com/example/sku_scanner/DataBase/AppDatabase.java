package com.example.sku_scanner.DataBase;


import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(entities = {ModelDB.class} , version = 1 , exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract DetailDAO detailDAO();
}
