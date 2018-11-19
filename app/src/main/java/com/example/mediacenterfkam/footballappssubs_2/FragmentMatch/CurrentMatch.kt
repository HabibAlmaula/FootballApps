package com.example.mediacenterfkam.footballappssubs_2.FragmentMatch


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.mediacenterfkam.footballappssubs_2.*
import com.example.mediacenterfkam.footballappssubs_2.Adapter.MatchAdapter
import com.example.mediacenterfkam.footballappssubs_2.Response.MatchItem
import com.example.mediacenterfkam.footballappssubs_2.Utils.DataTransfer.RxBus
import com.example.mediacenterfkam.footballappssubs_2.Utils.DataTransfer.RxEvent
import com.example.mediacenterfkam.footballappssubs_2.Utils.invisible
import com.example.mediacenterfkam.footballappssubs_2.Utils.visible
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.current_match.*
import org.jetbrains.anko.support.v4.onRefresh


class CurrentMatch : Fragment(), MatchView {

    private lateinit var presenter: MatchPresenter
    private lateinit var leagueDisposable: Disposable

    private var events: MutableList<MatchItem> = mutableListOf()


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        var id = ""


        presenter = MatchPresenter(this)
        leagueDisposable = RxBus.listen(RxEvent.EventAddLeague::class.java).subscribe {
            id = it.leagueName
            presenter.getCurrentMatch(id)
        }

        refreshSwipe.onRefresh {
           presenter.getCurrentMatch(id)
            progressCurrent.invisible()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.current_match,container,false)
        }

    override fun showLoading() {
        progressCurrent.visible()
        rv_current.invisible()
        noData.invisible()
    }

    override fun hideLoading() {
        progressCurrent.invisible()
        rv_current.visible()
        noData.invisible()
    }

    override fun showEmptyData() {
        refreshSwipe.isRefreshing = false
        progressCurrent.invisible()
        rv_current.invisible()
        noData.visible()
    }

    override fun showMatchList(data: List<MatchItem>?) {
        refreshSwipe.isRefreshing = false
        events.clear()
        data?.let { events.addAll(it) }
        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false)
        rv_current.layoutManager = layoutManager
        val adapter = MatchAdapter(events,context)
        rv_current.adapter = adapter

        adapter.notifyDataSetChanged()
        rv_current.scrollToPosition(0)

    }

    override fun onDestroy() {
        super.onDestroy()
        if (!leagueDisposable.isDisposed) leagueDisposable.dispose()
        Toast.makeText(context,"onDestroy", Toast.LENGTH_SHORT).show()
    }

}

