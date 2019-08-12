package com.fakhar.mapbox.Database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.fakhar.mapbox.Model.UserPlaces

@Dao
interface User_DAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun InsertUser(user : UserPlaces)

    @Query("select * from UserPlaces")
    fun readUser() : List<UserPlaces>

}