package com.fakhar.mapbox


import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fakhar.mapbox.Adapters.UserTaskAdapter
import com.fakhar.mapbox.Database.AppDB
import com.fakhar.mapbox.Model.UserPlaces
import com.fakhar.mapbox.Presenter.IMapBoxPresenter
import com.fakhar.mapbox.Presenter.MapBoxPresenter


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class PindataList : Fragment()  {

    internal lateinit var mapBoxPresenter: IMapBoxPresenter

    @SuppressLint("WrongConstant")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view:View = inflater.inflate(R.layout.fragment_pindata_list , container , false)

        mapBoxPresenter = MapBoxPresenter(context as HomeActivity)
        mapBoxPresenter.ShowListOfPinData(view, context as HomeActivity)


        return view
    }


}
