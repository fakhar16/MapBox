package com.fakhar.mapbox.Presenter

import android.app.Activity
import android.content.Context
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.fakhar.mapbox.Model.UserPlaces
import java.util.ArrayList

public interface IMapBoxPresenter {

    var mPlacesList : ArrayList<UserPlaces>

    fun ReadDataFromJSON ()
    fun CheckDataOnDB(context: Context)
    fun ShowListOfPinData(view : View, context: Context)
    fun ShowAllPinDataOnMap( context: Context , fragment: FragmentManager)
    fun ShowSelectedPinOnMap(context: AppCompatActivity)


}