package com.fakhar.mapbox.Adapters

import android.app.Activity
import android.content.Context
import android.view.View
import com.fakhar.mapbox.R
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.Marker
import kotlinx.android.synthetic.main.row_layout.view.*

class CustomInfoWindowGoogleMap(val context: Context , val placeName : String , val placeLatitude : String , val placeLongitude : String, val placeDescription : String) : GoogleMap.InfoWindowAdapter {

    override fun getInfoContents(p0: Marker?): View {

        var mInfoView = (context as Activity).layoutInflater.inflate(R.layout.marker_info_window, null)


        mInfoView.place_name.text =  placeName
        mInfoView.place_latitude.text = "Latitude: " +placeLatitude
        mInfoView.place_longitude.text ="Longitude: " + placeLongitude
        mInfoView.place_description.text = placeDescription


        return mInfoView
    }

    override fun getInfoWindow(p0: Marker?): View? {
        return null
    }
}