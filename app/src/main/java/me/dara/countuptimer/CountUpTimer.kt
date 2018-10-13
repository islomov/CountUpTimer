package me.dara.countuptimer

import android.os.*
import me.dara.countuptimer.CountUpTimer.Companion.MSG
import java.lang.Exception
import java.lang.ref.WeakReference

/**
 * @author sardor
 */
abstract class CountUpTimer{
  companion object {
    const val MSG = 2
  }
  var mHandler: CustomHandler? = null
  val baseTime: Long by lazy {
    System.currentTimeMillis()
  }

  var looper : Looper? = null
  var isStarted = false

  fun start() {
    if (isStarted){
      throw Exception("Timer is already started you can't start it." +
          "In order to start you have to stop timer first")
    }
    looper = Looper.myLooper()
    isStarted = true
    if (looper == null){
      Looper.prepare()
      looper = Looper.myLooper()
    }
    baseTime
    mHandler = CustomHandler(looper!!,this)
    mHandler?.sendMessage(mHandler?.obtainMessage(MSG))
    if (looper == null)
      Looper.loop()
  }

  fun stop() {
    if (!isStarted){
      throw Exception("Timer is not started.You have to start it first")
    }
    mHandler?.removeMessages(MSG)
    looper?.quit()
  }

  abstract fun onTick(elapsedTime: Long)

}

class CustomHandler(looper: Looper,
                    countUpTimer: CountUpTimer) : Handler(looper) {

  private val mCountUpTimer = WeakReference(countUpTimer)
  override fun handleMessage(msg: Message?) {
    val baseTime = mCountUpTimer.get()?.baseTime
    baseTime?.let {
      val elapsedTime = System.currentTimeMillis() - it
      mCountUpTimer.get()?.onTick(elapsedTime)
      sendMessageDelayed(obtainMessage(), 1000)
    }
  }
}