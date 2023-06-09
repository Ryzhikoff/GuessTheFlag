package evgeniy.ryzhikov.guesstheflag.utils

import java.util.*

class RoundTimer (val totalTime: Long, val interval: Long = 1000) {
    private var timeLeft: Long = totalTime
    private var isRunning = false
    private var timer: Timer? = null
    private var onTick: ((Long) -> Unit)? = null
    private var onFinish: (() -> Unit)? = null

    fun start() {
        if (isRunning) {
            return
        }
        isRunning = true
        timer = Timer().apply {
            scheduleAtFixedRate(object : TimerTask() {
                override fun run() {
                    timeLeft -= interval
                    if (timeLeft <= 0) {
                        stop()
                        onFinish?.invoke()
                    } else {
                        onTick?.invoke(timeLeft)
                    }
                }
            }, interval, interval)
        }
    }
    fun stop() {
        timer?.cancel()
        timer = null
        isRunning = false
    }
    fun setOnTickListener(listener: ((Long) -> Unit)?) {
        onTick = listener
    }
    fun setOnFinishListener(listener: (() -> Unit)?) {
        onFinish = listener
    }
}
