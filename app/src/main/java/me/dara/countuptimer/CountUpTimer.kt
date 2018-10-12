package me.dara.countuptimer

import android.os.*
import java.lang.ref.WeakReference


/**
 * @author sardor
 */
abstract class CountUpTimer(threadType: Int) {
  companion object {
    const val MAIN_THREAD = 0
    const val WORKER_THREAD = 1
    const val MSG = 2
  }

  val mHandler: CustomHandler
  val mHandlerThread: CustomHandlerThread
  val threadType: Int = threadType
  var baseTime: Long = SystemClock.currentThreadTimeMillis()

  init {
    mHandlerThread = CustomHandlerThread("WorkerThread",this)
    mHandler = CustomHandler(Looper.getMainLooper(),this)
  }

  fun start() {
    baseTime = SystemClock.elapsedRealtime()
    if (threadType == MAIN_THREAD) {
      mHandler.sendMessage(mHandler.obtainMessage(MSG))
    } else {
      mHandlerThread.sendMessage(mHandlerThread.handler.obtainMessage(MSG))
    }
  }

  fun stop() {
    if (threadType == MAIN_THREAD) {
      mHandler.removeMessages(MSG)
    } else {
      mHandlerThread.handler.removeMessages(MSG)
    }
  }

  abstract fun onTick(elapsedTime: Long)

}

class CustomHandler(looper: Looper,
                    countUpTimer: CountUpTimer) : Handler(looper) {
  private val mCountUpTimer: WeakReference<CountUpTimer> = WeakReference(countUpTimer)

  override fun handleMessage(msg: Message?) {
    val baseTime  = mCountUpTimer.get()?.baseTime
    baseTime?.let {
      val elapsedTime = SystemClock.currentThreadTimeMillis() - it
      mCountUpTimer.get()?.onTick(elapsedTime)
      sendMessageDelayed(obtainMessage(), 1000)
    }
  }
}

open class CustomHandlerThread(name: String,countUpTimer: CountUpTimer) : HandlerThread(name) {
  val handler: CustomHandler
  override fun onLooperPrepared() {
    super.onLooperPrepared()
    handler = CustomHandler(looper,countUpTimer)
  }
  fun sendMessage(message: Message) {
    handler.sendMessage(message)
  }
}