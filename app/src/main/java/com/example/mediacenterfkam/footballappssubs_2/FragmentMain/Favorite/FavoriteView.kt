package com.example.mediacenterfkam.footballappssubs_2.FragmentMain.Favorite

import com.example.mediacenterfkam.footballappssubs_2.Response.MatchItem

interface FavoriteView{
    fun showLoading()
    fun hideLoading()
    fun showEmptyData()
    fun showMatchListFavorite(data: List<MatchItem>)
}