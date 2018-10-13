package me.dara.countuptimer

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    val thread = Thread(Runnable {
      run {
        val timer = object : CountUpTimer() {
          override fun onTick(elapsedTime: Long) {
            Log.i("View", elapsedTime.toString())
          }
        }
        timer.start()
      }
    })
    thread.start()

  }
}
