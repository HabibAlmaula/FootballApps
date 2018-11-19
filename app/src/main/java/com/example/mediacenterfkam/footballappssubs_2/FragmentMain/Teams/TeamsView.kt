package com.example.mediacenterfkam.footballappssubs_2.FragmentMain.Teams

import com.example.mediacenterfkam.footballappssubs_2.Response.TeamsItem

interface TeamsView{
    fun showLoading()
    fun hideLoading()
    fun showEmptyData()
    fun showTeams (data: List<TeamsItem>?)
}