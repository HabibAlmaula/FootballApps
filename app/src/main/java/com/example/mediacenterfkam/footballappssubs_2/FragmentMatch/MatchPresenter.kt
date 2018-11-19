package com.example.mediacenterfkam.footballappssubs_2.FragmentMatch

import com.example.mediacenterfkam.footballappssubs_2.ApiDB.ApiRepository
import com.example.mediacenterfkam.footballappssubs_2.ApiDB.TheSportsDbApi
import com.example.mediacenterfkam.footballappssubs_2.Response.MatchResponse
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class MatchPresenter(private val mView: MatchView){
    private val apiRepository = ApiRepository()
    val gson = Gson()

    fun getCurrentMatch(id: String) {
        mView.showLoading()

        CoroutineScope(Dispatchers.Main).launch {
            val data = gson.fromJson(apiRepository
                .doRequest(TheSportsDbApi.getCurrentLeague(id)),
                MatchResponse::class.java
            )


                mView.hideLoading()

                try {
                    mView.showMatchList(data.events)
                } catch (e: NullPointerException) {
                    mView.showEmptyData()
                }

        }
    }

    fun getEventsNext(id: String) {
        mView.showLoading()

        CoroutineScope(Dispatchers.Main).launch {
            val data = gson.fromJson(apiRepository
                .doRequest(TheSportsDbApi.getNextLeague(id)),
                MatchResponse::class.java
            )

                mView.hideLoading()

                try {
                    mView.showMatchList(data.events)
                } catch (e: NullPointerException) {
                    mView.showEmptyData()
                }

        }
    }

}