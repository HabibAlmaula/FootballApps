package com.example.mediacenterfkam.footballappssubs_2.Detail

import com.example.mediacenterfkam.footballappssubs_2.Response.TeamsItem

interface DetailMatchView{
    fun showLoading()
    fun hideLoading()
    fun showTeamDetails(dataHomeTeam: List<TeamsItem>?, dataAwayTeam: List<TeamsItem>?)
}