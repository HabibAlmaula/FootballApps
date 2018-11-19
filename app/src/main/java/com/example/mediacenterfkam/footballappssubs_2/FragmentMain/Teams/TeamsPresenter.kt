package com.example.mediacenterfkam.footballappssubs_2.FragmentMain.Teams

import com.example.mediacenterfkam.footballappssubs_2.ApiDB.ApiRepository
import com.example.mediacenterfkam.footballappssubs_2.ApiDB.TheSportsDbApi
import com.example.mediacenterfkam.footballappssubs_2.Response.TeamsResponse
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class TeamsPresenter(private val mView : TeamsView){
    private val apiRepository = ApiRepository()
    val gson = Gson()


    fun getTeams(id: String?){
        mView.showLoading()
        CoroutineScope(Dispatchers.Main).launch{
            val data = gson.fromJson(
                id?.let { TheSportsDbApi.getTeams(it) }?.let { apiRepository.doRequest(it) },
                TeamsResponse::class.java)

                mView.hideLoading()

                try {
                    mView.showTeams(data.teams)
                } catch (e: NullPointerException) {
                    mView.showEmptyData()
                }
        }

    }
}