package com.example.mediacenterfkam.footballappssubs_2.FragmentMatch


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.mediacenterfkam.footballappssubs_2.*
import com.example.mediacenterfkam.footballappssubs_2.Adapter.MatchAdapter
import com.example.mediacenterfkam.footballappssubs_2.Response.MatchItem
import com.example.mediacenterfkam.footballappssubs_2.Utils.DataTransfer.RxBus
import com.example.mediacenterfkam.footballappssubs_2.Utils.DataTransfer.RxEvent
import com.example.mediacenterfkam.footballappssubs_2.Utils.invisible
import com.example.mediacenterfkam.footballappssubs_2.Utils.visible
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.next_match.*
import org.jetbrains.anko.support.v4.onRefresh


class NextMatch : Fragment(), MatchView {
    private lateinit var presenter: MatchPresenter
    private lateinit var leagueDisposable: Disposable


    private var events : MutableList<MatchItem> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.next_match,container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        var id = ""


        presenter = MatchPresenter(this)

        leagueDisposable = RxBus.listen(RxEvent.EventAddLeague::class.java).subscribe {
            id = it.leagueName
            presenter.getEventsNext(id)
        }

        refreshSwipeN.onRefresh {
            presenter.getEventsNext(id)
            progressNext.invisible()
        }
    }


    override fun showLoading() {
        progressNext.visible()
        rv_next.invisible()
        noDataNext.invisible()
    }

    override fun hideLoading() {
        progressNext.invisible()
        rv_next.visible()
        noDataNext.invisible()
    }

    override fun showEmptyData() {
        refreshSwipeN.isRefreshing = false
        progressNext.invisible()
        rv_next.invisible()
        noDataNext.visible()

    }

    override fun showMatchList(data: List<MatchItem>?) {
        refreshSwipeN.isRefreshing = false
        events.clear()
        data?.let { events.addAll(it) }

        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        rv_next.layoutManager = layoutManager

        val adapter = MatchAdapter(events, context)
        rv_next.adapter = adapter

        adapter.notifyDataSetChanged()
        rv_next.scrollToPosition(0)
    }

    override fun onDestroy() {
        super.onDestroy()
        if (!leagueDisposable.isDisposed) leagueDisposable.dispose()
    }


}
