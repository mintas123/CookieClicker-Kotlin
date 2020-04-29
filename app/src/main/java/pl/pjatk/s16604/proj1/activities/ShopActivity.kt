package pl.pjatk.s16604.proj1.activities

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_shop.*
import pl.pjatk.s16604.proj1.*
import pl.pjatk.s16604.proj1.models.ProjMetadata
import pl.pjatk.s16604.proj1.recyclerUtils.UpgradeViewHolder

class ShopActivity : AppCompatActivity() {


    private lateinit var sharedPreferences: SharedPreferences
    //initial default values in case of lack of SP
    private var cookies = 0L
    private var timer = MAX_TIME
    private var upgrades = initUpgrades()
    private var perSecond = 0L


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shop)

        initRecyclerView()
        loadData()
        onHomeClick()
    }


    private fun initRecyclerView() {

        val recyclerAdapter = UpgradeViewHolder.RecyclerAdapterMenu(this)

        recycler_view.apply {
            layoutManager = LinearLayoutManager(this@ShopActivity)
            val topSpacingDecorator =
                TopSpacingItemDecoration(30)
            addItemDecoration(topSpacingDecorator)
            adapter = recyclerAdapter
        }
    }

    override fun onStart() {
        loadData()
        super.onStart()
    }

    override fun onStop() {
        saveData()
        super.onStop()
    }

    private fun onHomeClick() {
        startBtn.setOnClickListener {
            animate(this, startBtn)
            finish()
        }
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
    }
}
