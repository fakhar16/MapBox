package com.fakhar.mapbox.Adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater

import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView

import androidx.recyclerview.widget.RecyclerView
import com.fakhar.mapbox.Model.UserPlaces
import com.fakhar.mapbox.PinDataActivity
import com.fakhar.mapbox.R

import java.util.ArrayList

class UserTaskAdapter(var mContext : Context, var placeList: ArrayList<UserPlaces>)  : RecyclerView.Adapter<UserTaskAdapter.ViewHolder>()
     {
         override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
             val v = LayoutInflater.from(parent.context).inflate(R.layout.row_layout, parent , false)

             return ViewHolder(v)
         }

         override fun getItemCount(): Int {
             return placeList.size
         }

         override fun onBindViewHolder(holder: ViewHolder, position: Int) {

             val userPlaces : UserPlaces = placeList[position]

             holder.placeId.text = userPlaces.id.toString()
             holder.placename.text = userPlaces.name
             holder.placelatitude.text = userPlaces.latitude.toString()
             holder.placelongitude.text = userPlaces.longitude.toString()
             holder.placedescription.text = userPlaces.description

             holder.itemView.setOnClickListener(View.OnClickListener {
                 var intent = Intent(mContext, PinDataActivity:: class.java)

                 intent.putExtra("Place_Name" , holder.placename.text.toString())
                 intent.putExtra("Place_latitude" , holder.placelatitude.text.toString())
                 intent.putExtra("Place_longitude" , holder.placelongitude.text.toString())
                 intent.putExtra("Place_Description" , holder.placedescription.text.toString())

                 mContext.startActivity(intent)
             })
         }


         class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

             val placeId = itemView.findViewById(R.id.place_id) as AppCompatTextView
             val placename = itemView.findViewById(R.id.place_name) as AppCompatTextView
             val placelatitude = itemView.findViewById(R.id.place_latitude) as AppCompatTextView
             val placelongitude = itemView.findViewById(R.id.place_longitude) as AppCompatTextView
             val placedescription = itemView.findViewById(R.id.place_description) as AppCompatTextView


    }
}