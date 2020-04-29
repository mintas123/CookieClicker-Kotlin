package pl.pjatk.s16604.proj1.activities

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_scoreboard.*
import kotlinx.android.synthetic.main.activity_shop.startBtn
import pl.pjatk.s16604.proj1.*
import pl.pjatk.s16604.proj1.models.Result
import pl.pjatk.s16604.proj1.models.Upgrade
import pl.pjatk.s16604.proj1.recyclerAdapters.ResultRecyclerAdapter
import java.lang.reflect.Type
import java.time.LocalDateTime
import java.time.Month
import java.util.*
import java.util.stream.Collector
import java.util.stream.Collectors
import kotlin.collections.ArrayList

class ScoreboardActivity : AppCompatActivity() {


    private var sharedPreferences: SharedPreferences? = null
    private lateinit var resultAdapter: ResultRecyclerAdapter

    private var highscores = initHighscores()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scoreboard)
        initRecyclerView()
        loadData()

        onHomeClick()
    }

    private fun initRecyclerView() {
        recycler.apply {
            layoutManager = LinearLayoutManager(this@ScoreboardActivity)
            val topSpacingDecorator =
                TopSpacingItemDecoration(10)
            addItemDecoration(topSpacingDecorator)
            resultAdapter =
                ResultRecyclerAdapter()
            adapter = resultAdapter
        }
    }

    private fun onHomeClick() {
        startBtn.setOnClickListener {
            animate(this, startBtn)
            finish()
        }
    }

    private fun loadData() {
        sharedPreferences = this.getSharedPreferences(PREFS_FILENAME, Context.MODE_PRIVATE)

        val gson = Gson()
        var jsonHighScores = sharedPreferences!!.getString(HIGHSCORE, "")
        if (jsonHighScores.equals("") || jsonHighScores.equals("null")) {
            resultAdapter.submitList(highscores)
        } else {
            val resultListType: Type = object : TypeToken<ArrayList<Result?>?>() {}.type
            highscores = gson.fromJson(jsonHighScores, resultListType)
            highscores.sortByDescending {
                it.score
            }
            val topTen = highscores.stream().limit(TOP_LIMIT).collect(Collectors.toList())
            resultAdapter.submitList(topTen)
        }

    }


}
