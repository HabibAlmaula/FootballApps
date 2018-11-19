package com.example.mediacenterfkam.footballappssubs_2.FragmentMain.Teams


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.mediacenterfkam.footballappssubs_2.Adapter.TeamsAdapter

import com.example.mediacenterfkam.footballappssubs_2.R
import com.example.mediacenterfkam.footballappssubs_2.Response.TeamsItem
import com.example.mediacenterfkam.footballappssubs_2.Utils.DataTransfer.RxBus
import com.example.mediacenterfkam.footballappssubs_2.Utils.DataTransfer.RxEvent
import com.example.mediacenterfkam.footballappssubs_2.Utils.invisible
import com.example.mediacenterfkam.footballappssubs_2.Utils.visible
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.fragment_main_teams.*
import org.jetbrains.anko.support.v4.onRefresh

class MainTeams : Fragment(), TeamsView {

    private lateinit var presenter: TeamsPresenter
    private lateinit var teamDisposable: Disposable

    private var teams : MutableList<TeamsItem> = mutableListOf()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main_teams, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var id =""

        presenter = TeamsPresenter(this)

        teamDisposable = RxBus.listen(RxEvent.EventAddLeague::class.java).subscribe {
            id = it.leagueName
            presenter.getTeams(id)
        }

        swipeTeam.onRefresh {
            presenter.getTeams(id)
            progressTeams.invisible()
        }
    }

    override fun showLoading() {
        progressTeams.visible()
        rv_Teams.invisible()
        noDataTeams.invisible()
    }

    override fun hideLoading() {
        progressTeams.invisible()
        rv_Teams.visible()
        noDataTeams.invisible()
    }

    override fun showEmptyData() {
        progressTeams.invisible()
        swipeTeam.isRefreshing = false
        rv_Teams.invisible()
        noDataTeams.visible()
    }

    override fun showTeams(data: List<TeamsItem>?) {
        swipeTeam.isRefreshing = false
        teams.clear()
        teams.addAll(data!!)

        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        rv_Teams.layoutManager = layoutManager

        val adapter = TeamsAdapter(teams, context)
        rv_Teams.adapter = adapter

        adapter.notifyDataSetChanged()
        rv_Teams.scrollToPosition(0)
    }

    override fun onDestroy() {
        super.onDestroy()
        if (!teamDisposable.isDisposed) teamDisposable.dispose()
        Toast.makeText(context,"onDestroy", Toast.LENGTH_SHORT).show()
    }


}
