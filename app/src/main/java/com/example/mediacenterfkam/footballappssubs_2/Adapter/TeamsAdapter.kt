package com.example.mediacenterfkam.footballappssubs_2.Adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.mediacenterfkam.footballappssubs_2.R
import com.example.mediacenterfkam.footballappssubs_2.Response.TeamsItem
import kotlinx.android.synthetic.main.teams_model.view.*

class TeamsAdapter (private val teamList : List<TeamsItem>, val context: Context?):
    RecyclerView.Adapter<TeamsAdapter.TeamsViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamsViewHolder {
        return TeamsViewHolder(LayoutInflater.from(context).inflate(R.layout.teams_model, parent, false))

    }

    override fun getItemCount(): Int = teamList.size

    override fun onBindViewHolder(holder: TeamsViewHolder, position: Int) {
        holder.bind(teamList[position])
    }


    class TeamsViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        fun bind (team: TeamsItem){
            itemView.tv_Teams.text = team.strTeam
            Glide.with(itemView.context)
                .load(team.strTeamBadge)
                .into(itemView.iv_Teams)
            itemView.setOnClickListener {
                //itemView.context.startActivity<DetailTeams>("TEAMS" to team)
            }

        }

    }
}