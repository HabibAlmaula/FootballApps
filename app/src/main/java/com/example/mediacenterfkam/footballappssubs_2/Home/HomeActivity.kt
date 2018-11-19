package com.example.mediacenterfkam.footballappssubs_2.Home

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.example.mediacenterfkam.footballappssubs_2.ApiDB.ApiRepository
import com.example.mediacenterfkam.footballappssubs_2.FragmentMain.Favorite.MainFavorite
import com.example.mediacenterfkam.footballappssubs_2.FragmentMain.MainMatch
import com.example.mediacenterfkam.footballappssubs_2.FragmentMain.Teams.MainTeams
import com.example.mediacenterfkam.footballappssubs_2.R
import com.example.mediacenterfkam.footballappssubs_2.Response.LeagueResponse
import com.example.mediacenterfkam.footballappssubs_2.Response.LeaguesItem
import com.example.mediacenterfkam.footballappssubs_2.Utils.DataTransfer.RxBus
import com.example.mediacenterfkam.footballappssubs_2.Utils.DataTransfer.RxEvent
import com.google.gson.Gson
import kotlinx.coroutines.Deferred

class HomeActivity : AppCompatActivity(), HomeView {

    private lateinit var presenter: HomePresenter
    lateinit var leaguesItem: LeaguesItem

    private val fragment1 = MainMatch()
    private val fragment2 = MainTeams()
    private val fragment3 = MainFavorite()
    private var active : Fragment = fragment1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)


        val apiRepository = ApiRepository()
        val gson = Gson()

        presenter = HomePresenter(this, apiRepository, gson)
        presenter.getAllLeague()


        supportFragmentManager
            .beginTransaction()
            .add(R.id.main_content, fragment1, MainMatch::class.java.simpleName)
            .commit()

        supportFragmentManager
            .beginTransaction()
            .add(R.id.main_content, fragment2, MainTeams::class.java.simpleName)
            .hide(fragment2)
            .commit()

        supportFragmentManager
            .beginTransaction()
            .add(R.id.main_content, fragment3, MainFavorite::class.java.simpleName)
            .hide(fragment3)
            .commit()




        bottomNavigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

    }


    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_macthes -> {
                homeSpinner.visible()
                supportFragmentManager.beginTransaction().hide(active).detach(fragment3).show(fragment1).commit()
                active = fragment1

                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_teams -> {
                homeSpinner.visible()
                supportFragmentManager.beginTransaction().hide(active).detach(fragment3).show(fragment2).commit()
                active = fragment2

                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_favorite -> {
                homeSpinner.visibility = View.GONE

                supportFragmentManager.beginTransaction().hide(active).attach(fragment3).show(fragment3).commit()
                active = fragment3
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }


    override fun showLeagueList(data: Deferred<LeagueResponse>) {
        homeSpinner.adapter = ArrayAdapter(ctx, android.R.layout.simple_spinner_dropdown_item, data.leagues)
        homeSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                leaguesItem = homeSpinner.selectedItem as LeaguesItem

                val idLeague = leaguesItem.idLeague!!.toString()
                RxBus.publish(RxEvent.EventAddLeague(idLeague))

            }
        }
    }



}
