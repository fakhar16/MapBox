
package com.fakhar.mapbox


import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.fakhar.mapbox.Adapters.CustomInfoWindowGoogleMap
import com.fakhar.mapbox.Presenter.IMapBoxPresenter
import com.fakhar.mapbox.Presenter.MapBoxPresenter
import com.fakhar.mapbox.View.IMapBoxView

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.activity_pindata.*


class PinDataActivity : AppCompatActivity(),IMapBoxView{
    override fun onDataFetchingCompleted() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onListShow() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


    internal lateinit var mapBoxPresenter: IMapBoxPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pindata)

        mapBoxPresenter = MapBoxPresenter(this)
        mapBoxPresenter.ShowSelectedPinOnMap(this)



    }




}
