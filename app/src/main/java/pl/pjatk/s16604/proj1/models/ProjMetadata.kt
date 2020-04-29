package pl.pjatk.s16604.proj1.models

import android.content.SharedPreferences

data class ProjMetadata(
    val sharedPreferences: SharedPreferences,
    var cookies: Long,
    val timer: Long,
    var perSecond: Long,
    var upgrades: MutableList<Upgrade>
)