package com.example.mediacenterfkam.footballappssubs_2.Detail

import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.mediacenterfkam.footballappssubs_2.ApiDB.ApiRepository
import com.example.mediacenterfkam.footballappssubs_2.FragmentMain.Favorite.MainFavorite
import com.example.mediacenterfkam.footballappssubs_2.R
import com.example.mediacenterfkam.footballappssubs_2.Response.MatchItem
import com.example.mediacenterfkam.footballappssubs_2.Response.TeamsItem
import com.example.mediacenterfkam.footballappssubs_2.Utils.ConvertDate
import com.example.mediacenterfkam.footballappssubs_2.Utils.invisible
import com.example.mediacenterfkam.footballappssubs_2.Utils.visible
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_detail_match.*
import org.jetbrains.anko.ctx
import org.jetbrains.anko.toast


class DetailMatch : AppCompatActivity(), DetailMatchView {

    private lateinit var presenter: DetailMatchPresenter
    private lateinit var matchData : MatchItem

    private var menuItem: Menu? = null
    private var isFavorite: Boolean = false
    private val fragment3 = MainFavorite()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_match)

        supportFragmentManager.beginTransaction().detach(fragment3).commit()

        matchData = intent.getParcelableExtra("MATCH")
        setupData(matchData)


        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = matchData.strHomeTeam +" vs "+ matchData.strAwayTeam



    }

    private fun setupData(item: MatchItem) {
        txtDetail_date.text = item.dateEvent!!.let { ConvertDate.newDate(it) }
        tv_HomeName.text = item.strHomeTeam
        tv_AwayName.text = item.strAwayTeam
        tv_HomeScore.text = item.intHomeScore
        tv_AwayScore.text = item.intAwayScore
        tv_FormationHome.text = item.strHomeFormation
        tv_FormationAway.text = item.strAwayFormation
        tv_HomeGoals.text = item.strHomeGoalDetails
        tv_AwayGoals.text = item.strAwayGoalDetails
        tv_HomeShots.text = item.intHomeShots
        tv_AwayShots.text = item.intAwayShots
        tv_HomeGoalKeeper.text = item.strHomeLineupGoalkeeper
        tv_AwayGoalKeeper.text = item.strAwayLineupGoalkeeper
        tv_HomeDefense.text = item.strHomeLineupDefense
        tv_AwayDefens.text = item.strAwayLineupDefense
        tv_HomeMidfield.text = item.strHomeLineupMidfield
        tv_AwayMidfield.text = item.strAwayLineupMidfield
        tv_HomeForward.text = item.strHomeLineupForward
        tv_AwayForward.text = item.strAwayLineupForward
        tv_HomeSubtitutes.text = item.strHomeLineupSubstitutes
        tv_AwaySubtitutes.text = item.strAwayLineupSubstitutes


        presenter = DetailMatchPresenter(this, ApiRepository(), Gson())
        matchData.idAwayTeam?.let { matchData.idHomeTeam?.let { it1 -> presenter.getTeamDetails(it1, it) } }
        isFavorite = presenter.isFavorite(ctx, item)

    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.favorite_menu, menu)
        menuItem = menu
        setFavorite()
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            android.R.id.home -> {
                supportFragmentManager.beginTransaction().attach(fragment3).commit()
                finish()
                true
            }
            
            R.id.add_to_favorite ->{
                if (isFavorite) {
                    val dialog = AlertDialog.Builder(this)

                    dialog.setMessage("Are you sure want to delete this event from favorite?")
                        .setCancelable(false)
                        .setPositiveButton("YES") {
                                dialog, which ->finish()
                                presenter.removeFavorite(ctx, matchData)
                                toast("Swipe down to take an effect")

                        }
                        .setNegativeButton("Cancel") { dialog, which ->
                            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, R.drawable.ic_added_to_favorites)
                            isFavorite = true
                            dialog.cancel()
                        }

                    val aler = dialog.create()
                    aler.setTitle("Action Remove")
                    aler.show()

                } else{
                    presenter.addFavorite(ctx, matchData)
                }
                isFavorite = !isFavorite
                setFavorite()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }



    override fun showLoading() {
        progressDetail.visible()
        dataView.invisible()
    }

    override fun hideLoading() {
        progressDetail.invisible()
        dataView.visible()
    }

    override fun showTeamDetails(dataHomeTeam: List<TeamsItem>?, dataAwayTeam: List<TeamsItem>?) {
        Glide.with(applicationContext)
            .load(dataHomeTeam?.get(0)?.strTeamBadge)
            .apply(RequestOptions().placeholder(R.drawable.ic_no_data))
            .into(iv_BadgeHome)

        Glide.with(applicationContext)
            .load(dataAwayTeam?.get(0)?.strTeamBadge)
            .apply(RequestOptions().placeholder(R.drawable.ic_no_data))
            .into(iv_BadgeAway)

    }

    private fun setFavorite(){
        if (isFavorite)
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, R.drawable.ic_added_to_favorites)
        else
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, R.drawable.ic_add_to_favorites)


    }
    

    

}
