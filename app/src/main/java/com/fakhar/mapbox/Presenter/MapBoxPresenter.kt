package com.fakhar.mapbox.Presenter

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.os.AsyncTask
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.fakhar.mapbox.Adapters.CustomInfoWindowGoogleMap
import com.fakhar.mapbox.Adapters.UserTaskAdapter
import com.fakhar.mapbox.Database.AppDB
import com.fakhar.mapbox.Model.UserPlaces
import com.fakhar.mapbox.PinDataActivity
import com.fakhar.mapbox.R
import com.fakhar.mapbox.View.IMapBoxView
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

import org.json.JSONArray
import java.net.URL
import javax.net.ssl.HttpsURLConnection

class MapBoxPresenter (internal var iMapBoxView: IMapBoxView) : IMapBoxPresenter {


    lateinit var mapFragment : SupportMapFragment
    lateinit var googleMap: GoogleMap
    override var mPlacesList = java.util.ArrayList<UserPlaces>()
    lateinit var mContext : Context

    override fun ShowAllPinDataOnMap( context: Context , fragment: FragmentManager) {

//        var myList = ArrayList<UserPlaces>()
        var myList = context.let { AppDB.invoke(it).placesList }


        mapFragment = fragment.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(OnMapReadyCallback {

            googleMap = it

            for (place in myList)
            {
                val location = LatLng(place.latitude,place.longitude)

                googleMap.addMarker(MarkerOptions().position(location).title(place.name)).showInfoWindow()
                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(location , 15f))
            }
        })
    }

    override fun ShowSelectedPinOnMap(context: AppCompatActivity ) {

        val place_name : String? = context.intent.getStringExtra("Place_Name")
        val Place_latitude_Str : String? = context.intent.getStringExtra("Place_latitude")
        val Place_longitude_Str : String? = context.intent.getStringExtra("Place_longitude")
        val Place_Description : String? = context.intent.getStringExtra("Place_Description")

        var latitude = Place_latitude_Str?.toDouble()
        var longitude = Place_longitude_Str?.toDouble()


        mapFragment = context.supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(OnMapReadyCallback {

            googleMap = it


            var adapter = CustomInfoWindowGoogleMap(
                context,
                place_name,
                Place_latitude_Str,
                Place_longitude_Str,
                Place_Description
            )

            val location = latitude?.let { it1 -> longitude?.let { it2 -> LatLng(it1, it2) } }
            googleMap.setInfoWindowAdapter(adapter)
            googleMap.addMarker(location?.let { it1 -> MarkerOptions().position(it1).title(place_name) }).showInfoWindow()
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(location , 15f))
        })


    }

    @SuppressLint("WrongConstant")
    override fun ShowListOfPinData(view: View, context: Context) {

//        var myList = ArrayList<UserPlaces>()
        var myList = context.let { AppDB.invoke(it).placesList }

        var recyclerView  = view.findViewById(R.id.recycler_view) as RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(context, LinearLayout.VERTICAL, false)
        val adapter = UserTaskAdapter(context, myList)
        recyclerView.adapter = adapter

        iMapBoxView.onListShow()

    }


    override fun CheckDataOnDB(context: Context) {

        mContext = context

        Thread{

            AppDB.invoke(context).userDao().readUser().forEach {
                var place = UserPlaces(it.description,it.id,it.latitude,it.longitude,it.name)

                mPlacesList.add(place)

            }

            if(mPlacesList.isEmpty())
            {
                ReadDataFromJSON()
            }
            else
            {
                if(AppDB.invoke(mContext).placesList.isEmpty())
                    AppDB.invoke(mContext).placesList = mPlacesList

                iMapBoxView.onDataFetchingCompleted()
            }


        }.start()
    }


    override fun ReadDataFromJSON() {

        val url = "https://annetog.gotenna.com/development/scripts/get_map_pins.php"
        AsyncTaskHandleJson().execute(url)
    }

    inner class AsyncTaskHandleJson : AsyncTask<String, String, String>() {
        override fun doInBackground(vararg url: String?): String {
            var text : String

            var connection = URL(url[0]).openConnection() as HttpsURLConnection

            try {
                connection.connect()
                text = connection.inputStream.use{it.reader().use{reader -> reader.readText()}}
            }finally {
                connection.disconnect()
            }

            return text

        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)

            mPlacesList = handleJson(result)
            AppDB.invoke(mContext).placesList = mPlacesList

            Thread{
                for(place in mPlacesList)
                {
                    var userPlace = UserPlaces(place.description,place.id,place.latitude,place.longitude,place.name)
                    AppDB.invoke(mContext).userDao().InsertUser(userPlace)
                }
                iMapBoxView.onDataFetchingCompleted()
            }.start()







        }
    }

    private fun handleJson(jsonString: String?): ArrayList<UserPlaces> {

        val jsonArray = JSONArray(jsonString)
        val list = ArrayList<UserPlaces>()

        var x = 0

        while(x < jsonArray.length())
        {
            val jsonObject = jsonArray.getJSONObject(x)

            list.add(UserPlaces(jsonObject.getString("description"),
                jsonObject.getInt("id"),
                jsonObject.getDouble("latitude"),
                jsonObject.getDouble("longitude"),
                jsonObject.getString("name")
            ))

            x++
        }

        return list;

    }
}
