package pl.pjatk.s16604.proj1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_entry.*
import kotlinx.android.synthetic.main.activity_shop.startBtn
import kotlin.system.exitProcess

class EntryActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_entry)

        onStartClick()
        onExitClick()

    }

    private fun onExitClick(){
        exitBtn.setOnClickListener {
            moveTaskToBack(true);
            exitProcess(-1)
        }

    }
    private fun onStartClick(){
        startBtn.setOnClickListener {
            animate(this,startBtn)
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}
