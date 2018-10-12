package me.dara.countuptimer

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast

class MainActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    val timer = object : CountUpTimer(CountUpTimer.MAIN_THREAD){
      override fun onTick(elapsedTime: Long) {
        Toast.makeText(applicationContext,elapsedTime.toString(),Toast.LENGTH_SHORT).show()
      }
    }
    timer.start()
  }
}
