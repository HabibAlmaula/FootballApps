package com.example.mediacenterfkam.footballappssubs_2.Home

import com.example.mediacenterfkam.footballappssubs_2.ApiDB.ApiRepository
import com.example.mediacenterfkam.footballappssubs_2.ApiDB.TheSportsDbApi
import com.example.mediacenterfkam.footballappssubs_2.Response.LeagueResponse
import com.example.mediacenterfkam.footballappssubs_2.Utils.CoroutineContextProvider
import com.google.gson.Gson
import kotlinx.coroutines.*

class HomePresenter(
    private val mView: HomeView,
    private val apiRepository: ApiRepository,
    private val gson: Gson,
    private val context: CoroutineContextProvider = CoroutineContextProvider()
){

    fun getAllLeague(){

        CoroutineScope(Dispatchers.Main).launch {
            val data = async {
                gson.fromJson(apiRepository
                .doRequest(TheSportsDbApi.getAllLeague()),
                LeagueResponse::class.java)}

                mView.showLeagueList(data)
        }
    }


}