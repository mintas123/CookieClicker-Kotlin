package pl.pjatk.s16604.proj1

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_scoreboard.*
import kotlinx.android.synthetic.main.activity_shop.startBtn
import java.lang.reflect.Type
import java.time.LocalDateTime
import java.time.Month

class ScoreboardActivity : AppCompatActivity() {


    private var sharedPreferences: SharedPreferences? = null
    private lateinit var resultAdapter: ResultRecyclerAdapter

    val HIGHSCORE = "HIGHSCORE"
    private var highscores = mutableListOf(
        Result(
            500,
            "Kuba",
            LocalDateTime.of(
                2020, Month.APRIL, 27, 13, 37
            )
        )
    )


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scoreboard)
        initRecyclerView()
        saveData()
        loadData()
        onHomeClick()
    }

    private fun initRecyclerView(){
        recycler.apply {
            layoutManager = LinearLayoutManager(this@ScoreboardActivity)
            resultAdapter = ResultRecyclerAdapter()
            adapter = resultAdapter
        }
    }

    private fun onHomeClick() {
        startBtn.setOnClickListener {
            animate(this, startBtn)
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    private fun saveData() {
        val editor = sharedPreferences!!.edit()
        val gson = Gson()
        var jsonHighScores = gson.toJson(highscores)
        editor.putString(HIGHSCORE, jsonHighScores)
        editor.apply()
    }

    private fun loadData() {
        sharedPreferences = this.getSharedPreferences(PREFS_FILENAME, Context.MODE_PRIVATE)

        val gson = Gson()
        var jsonHighScores = sharedPreferences!!.getString(UPGRADES, "")
        val resultListType: Type = object : TypeToken<ArrayList<Result?>?>() {}.type
        highscores = gson.fromJson(jsonHighScores, resultListType)

        resultAdapter.submitList(highscores)
    }


}
