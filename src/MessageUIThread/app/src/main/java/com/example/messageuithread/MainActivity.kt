package com.example.messageuithread

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Message
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    lateinit var mHandler: Handler // 핸들러 선언
    lateinit var mThread: Thread
    private val START = 100 // 메시지 종류를 나타내기 위한 구분값
    private val COUNT = 101

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        메인 스레드는 화면상에 UI를 그린느 작업을 한다. 따라서 메인 스레드에서 실행되는 코드는 최소한의 일을 해야 한다.
//        특히 onCreate()나 onResume()같은 안드로이드의 초기 생명주기에 속한 콜백함수는 가능한 한 적은 일을 수행해야 한다.
//        따라서 시간이 걸리는 작업은 일반 스레드에서 처리하며, 프로그레스바 등을 통해 작업의 진행 과정을 보여준다.

//        일반 스레드에서 메시지를 보내 UI를 갱신할 수 있다.
//        1) 메시지, 루퍼, 핸들러 사용
//        2) AsyncTask 사용
//        3) runOnUiThread() 사용

//        메인 스레드 : UI 스레드라고도 불리며 UI의 내용을 그리거나 갱신하여 사용자와 직접 상호작용하는 역할을 한다.
//        일반 스레드 : UI를 직접 그리지 못하며 대부분 연산이나 시간이 오래 걸리는 백그라운드 작업을 한다.
//        메시지 큐 : 일반 스레드로부터 들어온 메시지를 담는 공간이다.
//        루퍼 : 담겨진 메시지를 감시하며 하나씩 꺼내 오는 역할을 한다.
//        핸들러 : 메시지를 보내거나 받아서 처리하는 루틴을 구성한다.

        progressBar.max = 100

        start_progress.setOnClickListener {
            if (!mThread.isAlive) {
                mHandler.sendEmptyMessage(START) // 카운트 시작 메시지 보내기
            }
        }

        mThread = Thread(Runnable {
            for (i in 0..100) {
                Thread.sleep(100)
                val message = Message()
                message.what = COUNT
                message.arg1 = i

                mHandler.sendMessage(message)
            }
        })
    }

    override fun onResume() {
        super.onResume()
        mHandler = MyHandler(this)
    }

    companion object {
        class MyHandler(private val activity: MainActivity) : Handler() {
            override fun handleMessage(msg: Message) {
                super.handleMessage(msg)
                if (msg.what == activity.START) {
                    if (activity.mThread.state == Thread.State.NEW)
                        activity.mThread.start()
                } else if (msg.what == activity.COUNT) {
                    activity.progressBar.progress = msg.arg1
                    activity.tv_count.text = "Count " + msg.arg1
                }
            }
        }
    }
}
