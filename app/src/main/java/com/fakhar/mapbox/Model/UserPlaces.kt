package com.fakhar.mapbox.Model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class UserPlaces(

    @ColumnInfo(name = "userplace_description")
    val description: String,

    @PrimaryKey
    val id: Int,

    @ColumnInfo(name = "userplace_latitude")
    val latitude: Double,

    @ColumnInfo(name = "userplace_longitude")
    val longitude: Double,

    @ColumnInfo(name = "userplace_name")
    val name: String
)