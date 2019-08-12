package com.fakhar.mapbox.Database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.fakhar.mapbox.Model.UserPlaces

@Database (entities = [(UserPlaces::class)], version =  1)
abstract class AppDB : RoomDatabase() {

    companion object {
            @Volatile private var dbInstance:AppDB? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = dbInstance?: synchronized(LOCK){

            dbInstance?: buildDataBase(context).also { dbInstance = it }
        }

        private fun buildDataBase(context: Context)=
            Room.databaseBuilder(context.applicationContext,
                AppDB::class.java,"UserPlacesDB")
                .build()
    }
    abstract fun userDao() : User_DAO
    var placesList = ArrayList<UserPlaces>()


}