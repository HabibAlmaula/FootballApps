package com.example.mediacenterfkam.footballappssubs_2.Detail

import android.content.Context
import android.database.sqlite.SQLiteConstraintException
import com.example.mediacenterfkam.footballappssubs_2.ApiDB.ApiRepository
import com.example.mediacenterfkam.footballappssubs_2.ApiDB.TheSportsDbApi
import com.example.mediacenterfkam.footballappssubs_2.Response.MatchItem
import com.example.mediacenterfkam.footballappssubs_2.Response.TeamsResponse
import com.example.mediacenterfkam.footballappssubs_2.ApiDB.database
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.toast
import org.jetbrains.anko.uiThread

class DetailMatchPresenter(
    private val mView: DetailMatchView,
    private val apiRepository: ApiRepository,
    private val gson: Gson){

    fun getTeamDetails(idHomeTeam: String, idAwayTeam: String) {
        mView.showLoading()

        CoroutineScope(Dispatchers.Main).launch {
            val homeTeam = gson.fromJson(apiRepository
                .doRequest(TheSportsDbApi.getDetailTeams(idHomeTeam)),
                TeamsResponse::class.java
            )

            val awayTeam = gson.fromJson(apiRepository
                .doRequest(TheSportsDbApi.getDetailTeams(idAwayTeam)),
                TeamsResponse::class.java
            )

                mView.hideLoading()
                mView.showTeamDetails(homeTeam.teams, awayTeam.teams)

        }
    }

    fun isFavorite(context: Context, matchdata: MatchItem): Boolean{
        var favBOOl = false

        context.database.use {
            val favorites = select(MatchItem.TABLE_FAVORITES)
                .whereArgs(MatchItem.ID_EVENT + "= {id}",
                    "id" to matchdata.idEvent.toString())
                .parseList(classParser<MatchItem>())

            favBOOl = !favorites.isEmpty()
        }

        return favBOOl

    }

   fun addFavorite(context : Context, matchData : MatchItem){
        try {
            context.database.use {
                insert(MatchItem.TABLE_FAVORITES,
                    MatchItem.ID_EVENT to matchData.idEvent,
                    MatchItem.DATE to matchData.dateEvent,
                    // home team
                    MatchItem.HOME_ID to matchData.idHomeTeam,
                    MatchItem.HOME_TEAM to matchData.strHomeTeam,
                    MatchItem.HOME_SCORE to matchData.intHomeScore,
                    MatchItem.HOME_FORMATION to matchData.strHomeFormation,
                    MatchItem.HOME_GOAL_DETAILS to matchData.strHomeGoalDetails,
                    MatchItem.HOME_SHOTS to matchData.intHomeShots,
                    MatchItem.HOME_LINEUP_GOALKEEPER to matchData.strHomeLineupGoalkeeper,
                    MatchItem.HOME_LINEUP_DEFENSE to matchData.strHomeLineupDefense,
                    MatchItem.HOME_LINEUP_MIDFIELD to matchData.strHomeLineupMidfield,
                    MatchItem.HOME_LINEUP_FORWARD to matchData.strHomeLineupForward,
                    MatchItem.HOME_LINEUP_SUBSTITUTES to matchData.strHomeLineupSubstitutes,

                    // away team
                    MatchItem.AWAY_ID to matchData.idAwayTeam,
                    MatchItem.AWAY_TEAM to matchData.strAwayTeam,
                    MatchItem.AWAY_SCORE to matchData.intAwayScore,
                    MatchItem.AWAY_FORMATION to matchData.strAwayFormation,
                    MatchItem.AWAY_GOAL_DETAILS to matchData.strAwayGoalDetails,
                    MatchItem.AWAY_SHOTS to matchData.intAwayShots,
                    MatchItem.AWAY_LINEUP_GOALKEEPER to matchData.strAwayLineupGoalkeeper,
                    MatchItem.AWAY_LINEUP_DEFENSE to matchData.strAwayLineupDefense,
                    MatchItem.AWAY_LINEUP_MIDFIELD to matchData.strAwayLineupMidfield,
                    MatchItem.AWAY_LINEUP_FORWARD to matchData.strAwayLineupForward,
                    MatchItem.AWAY_LINEUP_SUBSTITUTES to matchData.strAwayLineupSubstitutes

                )
            }
        }catch (e: SQLiteConstraintException){
            context.toast("${e.message}")
        }
    }

     fun removeFavorite(context: Context, matchdata: MatchItem){
        try {
            context.database.use {
                delete(MatchItem.TABLE_FAVORITES,
                    MatchItem.ID_EVENT + " = {id}",
                    "id" to matchdata.idEvent.toString())
            }
        }catch (e:SQLiteConstraintException){
            context.toast("Error : ${e.message}")
        }
    }
}