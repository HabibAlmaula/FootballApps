package com.example.mediacenterfkam.footballappssubs_2.FragmentMatch

import com.example.mediacenterfkam.footballappssubs_2.Response.MatchItem

interface MatchView{
    fun showLoading()
    fun hideLoading()
    fun showEmptyData()
    fun showMatchList(data: List<MatchItem>?)
}