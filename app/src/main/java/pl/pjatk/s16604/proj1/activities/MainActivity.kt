package pl.pjatk.s16604.proj1.activities

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import pl.pjatk.s16604.proj1.*
import pl.pjatk.s16604.proj1.models.ProjMetadata
import kotlin.random.Random


class MainActivity : AppCompatActivity() {


    private lateinit var sharedPreferences: SharedPreferences
    //initial default values in case of lack of SP
    private var cookies = 0L
    private var timer = MAX_TIME
    private var upgrades = initUpgrades()
    private var perSecond = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loadData()
        onCookieClick()
        onShopClick()
        onFinishClick()
        onScoreBoardClick()
        timer()
    }

    override fun onStart() {
        loadData()
        super.onStart()
    }

    override fun onStop() {
        saveData()
        super.onStop()
    }

    private fun onCookieClick() {
        cookieClick.setOnClickListener {
            animate(this, cookieClick)
            val cursor = upgrades[0]
            if (cursor.amount > 0) {
                cookies += cursor.amount
            } else {
                cookies++
            }
            cookieCounter.text = "Cookies: $cookies"
            saveData()
        }
    }

    private fun onShopClick() {
        shopBtn.setOnClickListener {
            animate(this, shopBtn)
            saveData()
            val intent = Intent(this, ShopActivity::class.java)
            startActivity(intent)
        }
    }

    private fun onScoreBoardClick() {
        scoreBoard.setOnClickListener {
            animate(this, scoreBoard)
            saveData()
            val intent = Intent(this, ScoreboardActivity::class.java)
            startActivity(intent)
        }
    }

    private fun onFinishClick() {
        grannyBtn.setOnClickListener {
            val intent = Intent(this, FinishActivity::class.java)
            startActivity(intent)
        }
    }


    private fun timer() {
        val countdownTimer = object : CountDownTimer(timer, 1000) {
            override fun onTick(millisUntilFinished: Long) {

                perSecond = calcIncome()
                if (perSecond > 0) {
                    cookieCounter.text = "Cookies: $cookies"
                    tempo.text = "Tempo: $perSecond C/s"
                    cookies += perSecond
                }

                var seconds = millisUntilFinished / 1000
                timeLeft.text = "Time: ${timeFormatter(seconds)}"
                timer = millisUntilFinished

                val chance = Random.nextInt(0, 100)
                if (chance < 1) {
                    showMonster()
                }
            }

            override fun onFinish() {
                goToFinish()
            }
        }
        countdownTimer.start()
    }

    private fun goToFinish() {
        val intent = Intent(this, FinishActivity::class.java)
        startActivity(intent)
    }

    private fun calcIncome(): Long {
        var sum = 0L
        upgrades.forEach {
            if (it.amount > 0) {
                sum += it.calcIncome()
            }
        }
        return sum
    }

    private fun saveData() {
        STORAGE.saveData(
            ProjMetadata(
                sharedPreferences,
                cookies,
                timer,
                perSecond,
                upgrades
            )
        )
    }

    private fun loadData() {
        val metadata = STORAGE.loadData(this)

        sharedPreferences = metadata.sharedPreferences
        cookies = metadata.cookies
        perSecond = metadata.perSecond
        upgrades = metadata.upgrades
        timer = metadata.timer

        //set loaded data
        tempo.text = "Tempo: ${calcIncome()} C/s"
        cookieCounter.text = "Cookies: $cookies"
        timeLeft.text = "Time: ${timeFormatter(timer / 1000)}"
    }

    private fun showMonster() {
        if (cookies > 30) {

            val punishment = Random.nextDouble(0.3 * cookies, 0.9 * cookies)

            val countdownTimer = object : CountDownTimer(2000L, 1000L) {
                override fun onFinish() {
                    randomMonster.visibility = View.GONE
                    cookieClick.bringToFront()
                }
                override fun onTick(millisUntilFinished: Long) {
                }
            }
            randomMonster.bringToFront()
            randomMonster.visibility = View.VISIBLE
            countdownTimer.start()
            animateShake(this, randomMonster)

            randomMonster.setOnClickListener {
                animate(this, randomMonster)
                if (cookies > punishment) {
                    cookies -= punishment.toLong()
                } else {
                    cookies = 0L
                }
                cookieCounter.text = "Cookies: $cookies"
                cookieClick.bringToFront()
            }
            saveData()
        }
    }


}
