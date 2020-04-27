package pl.pjatk.s16604.proj1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_shop.*

class ShopActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shop)
        initUpgrades()


        onHomeClick()

    }
    private fun initUpgrades(){
        var upgrades = listOf(
            Upgrade("Clicker", 0, 50),
            Upgrade("Granny", 0,  200)
        )
        recycleViewUpgrades.apply {

            // set the custom adapter to the RecyclerView
        }
    }

    private fun onHomeClick(){
        startBtn.setOnClickListener{
            animate(this, startBtn)
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}
