package com.example.mediacenterfkam.footballappssubs_2.Response

data class LeaguesItem(val idLeague: String?, val strLeague: String?) {

    override fun toString(): String {
        return strLeague.toString()
    }
}
