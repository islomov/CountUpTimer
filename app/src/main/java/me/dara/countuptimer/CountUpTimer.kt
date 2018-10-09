package me.dara.countuptimer

import android.os.Handler
import android.os.HandlerThread

/**
 * @author sardor
 */
abstract class CountUpTimer(threadType: Int) {

  companion object {
    const val MAIN_THREAD = 0
    const val WORKER_THREAD = 1
  }

  val mHandler: Handler
  val mHandlerThread: HandlerThread
  val threadType: Int = threadType

  init {
    mHandler = Handler()
    mHandlerThread = HandlerThread("WorkerThread")
  }

  fun start() {
    val startTime = System.currentTimeMillis()
    if (threadType == MAIN_THREAD){
      mHandler.postDelayed({

      }, 50)
    }else{
      mHandlerThread.start()
    }

  }


}