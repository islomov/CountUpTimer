package me.dara.countuptimer

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.concurrent.timer

class MainActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    val timer = object : CountUpTimer() {
      override fun onTick(elapsedTime: Long) {
        textView.text = elapsedTime.toString()
        if (elapsedTime>= 60000)
          this.stop()
      }
    }
    timer.start()
  }


}
