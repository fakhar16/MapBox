package com.fakhar.mapbox


import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.os.Looper
import android.provider.Settings
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentManager
import com.fakhar.mapbox.Database.AppDB
import com.fakhar.mapbox.Model.UserPlaces
import com.fakhar.mapbox.Presenter.IMapBoxPresenter
import com.fakhar.mapbox.Presenter.MapBoxPresenter
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.activity_pindata.*
import kotlinx.android.synthetic.main.fragment_pindata_on_map.view.*
import java.util.jar.Manifest


class PindataOnMap : Fragment() {


    lateinit var mapFragment : SupportMapFragment
    lateinit var googleMap: GoogleMap

    lateinit var locationRequest : LocationRequest
    lateinit var locationCallback : LocationCallback

    lateinit var fusedLocationProviderClient : FusedLocationProviderClient

    val REQUEST_PERMISSION = 1

     lateinit var mapBoxPresenter: IMapBoxPresenter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        var view:View = inflater.inflate(R.layout.fragment_pindata_on_map , container , false)

        mapBoxPresenter = MapBoxPresenter(context as HomeActivity)
        mapBoxPresenter.ShowAllPinDataOnMap(context as HomeActivity , childFragmentManager)


        var userLocationButton = view.userLocationButton

        userLocationButton.setOnClickListener(View.OnClickListener {

            mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
            mapFragment.getMapAsync(OnMapReadyCallback {
                googleMap = it
            })

            ShowUserLocation()
        })

        return view
    }

    fun ShowUserLocation()
    {
         fusedLocationProviderClient  = context?.let {
            LocationServices.getFusedLocationProviderClient(
                it
            )
        } as FusedLocationProviderClient


         locationRequest = LocationRequest()

        locationCallback = object : LocationCallback()
        {
            override fun onLocationResult(p0: LocationResult?) {

                var location = p0!!.locations.get(p0.locations.size - 1)

                val userLocation = LatLng(location.latitude,location.longitude)
                googleMap.addMarker(MarkerOptions().position(userLocation).title("My Location")).showInfoWindow()
                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(userLocation , 15f))
            }
        }

        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        locationRequest.interval = 5000
        locationRequest.fastestInterval = 3000
        locationRequest.smallestDisplacement = 10f


        if(ContextCompat.checkSelfPermission(context!!,android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
        {
            fusedLocationProviderClient.requestLocationUpdates(locationRequest , locationCallback , Looper.myLooper())
        }

        else
        {
            RequestPermissionForUserLocation()
        }
    }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if(requestCode == 1)
        {
            if(ContextCompat.checkSelfPermission(context!!,android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
            {
                fusedLocationProviderClient.requestLocationUpdates(locationRequest , locationCallback , Looper.myLooper())

            }

        }
    }

    fun RequestPermissionForUserLocation()
    {
        if(shouldShowRequestPermissionRationale(android.Manifest.permission.ACCESS_FINE_LOCATION))
        {
            AlertDialog.Builder(this.context!!)
                .setTitle("Permission needed")
                .setMessage("Permission is required to get the user location")
                .setPositiveButton("Ok"){_, _ ->
                    // Do something when user press the positive button
                    requestPermissions(arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),REQUEST_PERMISSION)


                }
                .setNegativeButton("Cancel"){dialog, _ ->
                    // Do something when user press the positive button
                    dialog.dismiss()
                }
                .create().show()
        }
        else
        {
            requestPermissions(arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),REQUEST_PERMISSION)
        }

    }
}
