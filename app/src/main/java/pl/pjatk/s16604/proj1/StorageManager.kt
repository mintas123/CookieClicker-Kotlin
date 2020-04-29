package pl.pjatk.s16604.proj1

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import pl.pjatk.s16604.proj1.models.ProjMetadata
import pl.pjatk.s16604.proj1.models.Result
import pl.pjatk.s16604.proj1.models.Upgrade
import java.lang.reflect.Type

class StorageManager {

    private var sharedPreferences: SharedPreferences? = null

    //initial default values in case of lack of SP
    var cookies = 0L
    var timer = MAX_TIME
    var upgrades = initUpgrades()
    var perSecond = 0L
    var highscore = initHighscores()


    fun saveData(md: ProjMetadata) {

        val editor = sharedPreferences!!.edit()
        val gson = Gson()
        var jsonUpgrades = gson.toJson(md.upgrades)
        editor.putLong(COOKIES, md.cookies)
        editor.putLong(TEMPO, md.perSecond)
        editor.putLong(TIMER, md.timer)
        editor.putString(UPGRADES, jsonUpgrades)
        editor.apply()
    }

    fun resetData(scoreList: MutableList<Result>) {
        val editor = sharedPreferences!!.edit()

        //reset game data
        val gson = Gson()
        var jsonUpgrades = gson.toJson(initUpgrades())
        editor.putLong(COOKIES, 0L)
        editor.putString(UPGRADES, jsonUpgrades)
        editor.putLong(TEMPO, 0L)
        editor.putLong(TIMER, MAX_TIME)
        editor.apply()
        saveHighScore(scoreList)

    }

    fun loadData(context: Context): ProjMetadata {
        sharedPreferences = context.getSharedPreferences(PREFS_FILENAME, Context.MODE_PRIVATE)


        cookies = sharedPreferences!!.getLong(COOKIES, 0L)
        timer = sharedPreferences!!.getLong(TIMER, MAX_TIME)
        perSecond = sharedPreferences!!.getLong(TEMPO, 0L)


        val gson = Gson()

        //upgrades
        var jsonUpgrades = sharedPreferences!!.getString(UPGRADES, "")

        upgrades =
            if (jsonUpgrades.equals("") || jsonUpgrades.equals("null")) {
                initUpgrades()
            } else {
                val upgradeListType: Type = object : TypeToken<ArrayList<Upgrade?>?>() {}.type
                gson.fromJson(jsonUpgrades, upgradeListType)
            }

        return ProjMetadata(sharedPreferences!!, cookies, timer, perSecond, upgrades)
    }

    private fun saveHighScore(newScore: MutableList<Result>) {
        val editor = sharedPreferences!!.edit()
        val gson = Gson()
        var jsonHighscore = gson.toJson(newScore)
        editor.putString(HIGHSCORE, jsonHighscore)
        editor.apply()
    }

    fun loadHighScore(context: Context): MutableList<Result> {
        sharedPreferences = context.getSharedPreferences(PREFS_FILENAME, Context.MODE_PRIVATE)
        val gson = Gson()


        var jsonHighScores = sharedPreferences!!.getString(HIGHSCORE, "")

        highscore =
            if (jsonHighScores.equals("") || jsonHighScores.equals("null")) {
                initHighscores()
            } else {
                val highscoreType: Type = object : TypeToken<ArrayList<Result?>?>() {}.type
                gson.fromJson(jsonHighScores, highscoreType)
            }

        highscore.sortByDescending { it.score }

        return highscore
    }


}