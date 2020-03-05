package com.example.coroutineui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        합성 프로퍼티에 의한 리소스의 접근
        countTask(tv_count, btn_start, btn_stop)

    }
}

fun countTask(count: TextView, start: Button, end: Button) {
//    Dispatchers.Main -> UI 문맥에서 코루틴 실행, 늦은 실행을 위한 LAZY 지정
    val job = GlobalScope.launch(Dispatchers.Main, start = CoroutineStart.LAZY) {
        for (i in 10 downTo 1) {
            count.text = "Countdown $i ..."
            delay(1000)
        }
        count.text = "Done!"
    }
    start.setOnClickListener { job.start() }
    end.setOnClickListener { job.cancel() }
}
