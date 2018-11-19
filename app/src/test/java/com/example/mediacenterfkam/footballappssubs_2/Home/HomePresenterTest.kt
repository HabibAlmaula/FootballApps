package com.example.mediacenterfkam.footballappssubs_2.Home

import com.example.mediacenterfkam.footballappssubs_2.ApiDB.ApiRepository
import com.example.mediacenterfkam.footballappssubs_2.ApiDB.TheSportsDbApi
import com.example.mediacenterfkam.footballappssubs_2.Response.LeagueResponse
import com.example.mediacenterfkam.footballappssubs_2.Response.LeaguesItem
import com.example.mediacenterfkam.footballappssubs_2.Utils.TestContextProvider
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.junit.Test

import org.junit.Before
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

class HomePresenterTest {

    @Mock
    lateinit var mView: HomeView

    @Mock
    lateinit var gson: Gson

    @Mock
    lateinit var apiRepository: ApiRepository

    private lateinit var presenter: HomePresenter

    @Before
    fun setUp(){
        MockitoAnnotations.initMocks(this)
        presenter = HomePresenter(mView, apiRepository, gson, TestContextProvider())
    }

    @Test
    fun getAllLeague() {

        val league: MutableList<LeaguesItem> = mutableListOf()
        val response = LeagueResponse(league)

        CoroutineScope(Dispatchers.Main).launch {
            `when`(
                gson.fromJson(
                    apiRepository
                        .doRequest(TheSportsDbApi.getAllLeague()).await(),
                    LeagueResponse::class.java
                )
            ).thenReturn(response)

            presenter.getAllLeague()

            verify(mView).showLeagueList(response)
        }

    }
}