//package com.example.four_pic.room
//
//import Stata
//import android.content.Context
//import androidx.room.Database
//import androidx.room.Room
//import androidx.room.RoomDatabase
//
//@Database(
//    entities = [
//        Stata::class
//    ],
//    version = 1
//)
//
//abstract class AppDatabase : RoomDatabase() {
//    abstract fun inputData(): InputDao
//
//    companion object {
//        var database: AppDatabase? = null
//        fun init(context: Context) {
//            database = Room.databaseBuilder(
//                context.applicationContext, AppDatabase::class.java, "Stata"
//            )
//                .allowMainThreadQueries()
//                .build()
//        }
//    }
//}