package com.example.mediacenterfkam.footballappssubs_2.Home

import com.example.mediacenterfkam.footballappssubs_2.Response.LeagueResponse
import kotlinx.coroutines.Deferred

interface HomeView{
    fun showLeagueList(data: Deferred<LeagueResponse>)

}