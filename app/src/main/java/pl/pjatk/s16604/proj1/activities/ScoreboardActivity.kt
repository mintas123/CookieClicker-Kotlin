package pl.pjatk.s16604.proj1.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_scoreboard.*
import kotlinx.android.synthetic.main.activity_shop.startBtn
import pl.pjatk.s16604.proj1.*
import pl.pjatk.s16604.proj1.recyclerUtils.ResultRecyclerAdapter
import java.util.stream.Collectors

class ScoreboardActivity : AppCompatActivity() {

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
        highscores = STORAGE.loadHighScore(this)

        val top = highscores.stream().limit(TOP_LIMIT).collect(Collectors.toList())
        resultAdapter.submitList(top)
    }


}
