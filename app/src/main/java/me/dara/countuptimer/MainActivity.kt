package me.dara.countuptimer

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    val timer = object : CountUpTimer(100){
      override fun onTick(elapsedTime: Long) {
        textView.text = elapsedTime.toString()
      }

    }
    timer.start()

  }


}
