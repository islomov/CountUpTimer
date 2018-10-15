package me.dara.countuptimer

import android.os.*
import java.lang.Exception
import java.lang.ref.WeakReference

/**
 * @author sardor
 */
abstract class CountUpTimer(val millisInterval:Long) {
  companion object {
    const val MSG = 2
  }

  private var mHandler: WeakReference<Handler>? = null
  private val baseTime: Long by lazy {
    SystemClock.elapsedRealtime()
  }

  private var looper: Looper? = null
  private var isStarted = false
  private var isStoped = false

  fun start() {
    if (isStarted) {
      throw Exception("Timer is already started you can't start it." +
          "In order to start you have to stop timer first")
    }
    looper = Looper.myLooper()
    isStarted = true
    isStoped = false
    if (looper == null) {
      Looper.prepare()
      looper = Looper.myLooper()
    }
    baseTime
    mHandler = WeakReference(CustomHandler(looper!!, this))
    mHandler?.get()?.sendMessage(mHandler?.get()?.obtainMessage(MSG))
    if (looper == null)
      Looper.loop()
  }

  fun stop() {
    if (!isStarted) {
      throw Exception("Timer is not started.You have to start it first")
    }
    isStarted = false
    isStoped = true
    mHandler?.get()?.removeMessages(MSG)
    if (looper != Looper.getMainLooper())
      looper?.quit()
  }

  abstract fun onTick(elapsedTime: Long)

}

class CustomHandler(looper: Looper,
                    private val countUpTimer: CountUpTimer) : Handler(looper) {

  override fun handleMessage(msg: Message?) {
    if (countUpTimer.isStoped)
      return
    val interval = countUpTimer.millisInterval
    val lastTickStart = SystemClock.elapsedRealtime()
    val elapsedTime = SystemClock.elapsedRealtime() - countUpTimer.baseTime
    countUpTimer.onTick(elapsedTime)
    val lastTickDuration = SystemClock.elapsedRealtime() - lastTickStart
    val delay = interval - lastTickDuration
    sendMessageDelayed(obtainMessage(), delay)
  }
}