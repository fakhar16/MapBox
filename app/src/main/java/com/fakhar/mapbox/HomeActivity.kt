package com.fakhar.mapbox

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import android.widget.TextView
import androidx.room.Room
import com.fakhar.mapbox.Database.AppDB
import com.fakhar.mapbox.Presenter.IMapBoxPresenter
import com.fakhar.mapbox.Presenter.MapBoxPresenter
import com.fakhar.mapbox.View.IMapBoxView
import kotlinx.android.synthetic.main.activity_pindata.*
import kotlinx.android.synthetic.main.fragment_pindata_list.*

class HomeActivity : AppCompatActivity() , IMapBoxView {
    override fun onListShow() {
        Log.e("Fakhar" , "Showing List Task Completed...")
    }

    override fun onDataFetchingCompleted() {
        mProgressBar.visibility = View.INVISIBLE

        Log.e("Fakhar" , "Data fectching Completed...")

        PindataListFunction()

    }

    val manager = supportFragmentManager

    lateinit var mProgressBar : ProgressBar
    internal lateinit var mapBoxPresenter: IMapBoxPresenter


    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {

                PindataListFunction()
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_dashboard -> {

                PindataOnMapFunction()
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        navView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)

        mProgressBar = findViewById(R.id.progressbar)
        mapBoxPresenter = MapBoxPresenter(this)

        mapBoxPresenter.CheckDataOnDB(this)



    }

    fun PindataListFunction()
    {
        val transaction = manager.beginTransaction()
        val fragment = PindataList()

        transaction.replace(R.id.fragmentHolder , fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    fun PindataOnMapFunction()
    {
        val transaction = manager.beginTransaction()
        val fragment = PindataOnMap()

        transaction.replace(R.id.fragmentHolder , fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }


}

