package com.example.ankocommons

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.*
import org.jetbrains.anko.design.snackbar

// 로그 기록을 위해 AnkoLogger 추가 선언
class MainActivity : AppCompatActivity(), AnkoLogger {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        info("onCreate() - info message") // 일반 정보
//        info { "onCreate()" + "info message" } // lazy 표현(람다식)
        debug(5) // .toString()이 호출된 문자열로 바뀜
        warn(null) // null로 출력

//        다음과 같이 AnkoLogger를 일반 객체 스타일로 작성할 수도 있다.
//        private val log = AnkoLogger<MainActivity>(this)
//        private val logWithASpecificTag = AnkoLogger("my_tag")
//
//        private fun someMethod() {
//            log.warning("Warning!")
//        }

//        기존의 Toast 코드
//        btn_toast.setOnClickListener {
//            Toast.makeText(
//                this,
//                "안녕하세요!",
//                Toast.LENGTH_SHORT
//            ).show()
//        }

//        Anko의 Toast 코드
        btn_toast.setOnClickListener {
//            toast("안녕하세요!") // 짧은 길이의 Toast
            toast(R.string.message) // 리소스의 메시지 직접 사용하기
//            longToast("메시지가 긴 길이를 가지고 있을 때 사용")
        }

        btn_snackbar.setOnClickListener {
//            it.longSnackbar("메시지가 긴 길이를 가지고 있을 때 사용")
            it.snackbar("실행할까요?", "실행") {
//                클릭 이벤트의 처리 내용
                toast("실행합니다!")
            }
        }

        btn_alert.setOnClickListener {
            alert("반려견이 있나요?") {
                yesButton { toast("좋아요.") }
                noButton {  }
            }.show()
        }

//        Selector는 여러 개의 목록 중 선택할 수 있는 다이얼로그다.
        val countries = listOf("한국", "미국", "영국", "프랑스")

        selector("어디서 오셨나요?", countries) { dialog, i ->
            toast("그럼 ${countries[i]}에서 살고 있겠군요.")
        }

        btn_progress.setOnClickListener {
            val dialog = horizontalProgressBar()
            dialog.progress = 50
        }

        btn_sub.setOnClickListener {
//            일반적인 액티비티의 시작
//            val intent = Intent(this, SubActivity::class.java)
//            intent.putExtra("id", 5)
//            intent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
//            startActivity(intent)

//            Anko의 액티비티 시작
            startActivity(intentFor<SubActivity>("id" to 5).singleTop())

//            Anko가 제공하는 인텐트 래퍼 (대괄호는 생략 가능한 옵션)
//            전화 걸기 : makeCall(number)
//            문자 메시지 보내기 : sendSMS(number, [text])
//            웹 탐색하기 : browse(url)
//            텍스트 공유하기 : share(text, [subject])
//            이메일 보내기 : email(email, [subject], [text])
        }
    }
}
