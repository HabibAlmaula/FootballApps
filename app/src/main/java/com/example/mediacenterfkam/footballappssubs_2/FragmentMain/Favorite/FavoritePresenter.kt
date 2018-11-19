package com.example.mediacenterfkam.footballappssubs_2.FragmentMain.Favorite

import android.content.Context
import com.example.mediacenterfkam.footballappssubs_2.ApiDB.database
import com.example.mediacenterfkam.footballappssubs_2.Response.MatchItem
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select

class FavoritePresenter(private val mView: FavoriteView) {


    fun getFavoritesAll(context: Context) {
        mView.showLoading()

        val data: MutableList<MatchItem> = mutableListOf()

        context.database.use {
            val favorites = select(MatchItem.TABLE_FAVORITES)
                .parseList(classParser<MatchItem>())

            data.addAll(favorites)
        }

        mView.hideLoading()

        if (data.size > 0) {
            mView.showMatchListFavorite(data)
        } else {
            mView.showEmptyData()
        }
    }
}