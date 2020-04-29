package pl.pjatk.s16604.proj1.activities

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_finish.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_shop.*
import pl.pjatk.s16604.proj1.*
import pl.pjatk.s16604.proj1.R
import pl.pjatk.s16604.proj1.initHighscores
import pl.pjatk.s16604.proj1.initUpgrades
import pl.pjatk.s16604.proj1.models.Result
import pl.pjatk.s16604.proj1.models.Upgrade
import java.lang.reflect.Type
import java.time.LocalDateTime

class FinishActivity : AppCompatActivity() {

    private var sharedPreferences: SharedPreferences? = null


    //initial default values in case of lack of SP
    private var cookies = 0L
    private var upgrades = initUpgrades()
    private var highscores = initHighscores()
    private var score = 0L


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_finish)
        loadData()
        onSaveClick()

        var sum = cookies
        upgrades.forEach {
            sum += it.calcPoints().toLong()
        }
        player_score.text = "Your score: $sum"

    }


    private fun onSaveClick() {
        saveBtn.setOnClickListener {
            val result = createResult()
            highscores.add(result)


            animate(this, saveBtn)
            saveData()
            goToScore()
        }
    }

    private fun createResult(): Result {
        val name = editText.text.toString()
        val date = LocalDateTime.now().toString()

        var sum = 0L
        upgrades.forEach {
            sum += it.calcPoints().toLong()
        }

        val score = sum + cookies

        return Result(score, name, date)

    }

    private fun goToScore() {
        finish()
        val intent = Intent(this, ScoreboardActivity::class.java)
        startActivity(intent)
    }


    private fun saveData() {
        val editor = sharedPreferences!!.edit()

        //reset game data
        val gson = Gson()
        var jsonUpgrades = gson.toJson(initUpgrades())
        editor.putLong(COOKIES, 0L)
        editor.putString(UPGRADES, jsonUpgrades)
        editor.putLong(TEMPO, 0L)
        editor.putLong(TIMER, MAX_TIME)

        //save highscore
        var jsonResults = gson.toJson(highscores)
        editor.putString(HIGHSCORE, jsonResults)

        editor.apply()
    }

    private fun loadData() {
        sharedPreferences = this.getSharedPreferences(PREFS_FILENAME, Context.MODE_PRIVATE)

        cookies = sharedPreferences!!.getLong(COOKIES, 0L)
        val gson = Gson()

        var jsonUpgrades = sharedPreferences!!.getString(UPGRADES, gson.toJson(initUpgrades()))
        val upgradeListType: Type = object : TypeToken<ArrayList<Upgrade?>?>() {}.type
        upgrades = gson.fromJson(jsonUpgrades, upgradeListType)

        var jsonHighScores = sharedPreferences!!.getString(HIGHSCORE, gson.toJson(initHighscores()))

        highscores =
            if (jsonHighScores.equals("") || jsonHighScores.equals("null")) {
                initHighscores()
            } else {
                val resultListType: Type = object : TypeToken<ArrayList<Result?>?>() {}.type
                gson.fromJson(jsonHighScores, resultListType)
            }


    }
}
