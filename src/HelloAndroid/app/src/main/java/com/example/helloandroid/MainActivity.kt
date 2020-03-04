package com.example.helloandroid

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        기존 id 사용법
//        val tvTitle = findViewById<TextView>(R.id.tv_title)
//        tvTitle.text = "Hello Kotlin!"

//        합성 프로퍼티 사용하기
//        xml에 선언된 id와 변수 이름이 동일해야 한다.
//        코틀린 코드에서 뷰 인스턴스에 접근할 수 있도록 지원하기 위해 코틀린 안드로이드 확장은 클래스 안에 뷰 id 이름으로 된 가상의 프로퍼티를 생성한다.
//        이렇게 코틀린 코드 외부의 요소와의 조합을 통해 만들어진 프로퍼티를 합성 프로퍼티라고 부른다.
        tv_title.text = "Hello Kotlin!"

        titleOn()
    }
}

// 확장 함수 형태로 접근하기
// 리소스의 id를 매번 읽어올 경우, 읽을 때마다 리소스 id를 변환하는 것보다 최초에 읽은 내용을 캐시에 두고 이후 읽게 되면 훨씬 빨리 처리할 수 있다.
// 캐시된 내용을 읽으려면 확장 함수와 같은 기법을 사용해 리소스를 지정해 둔다.
fun MainActivity.titleOn() {
    tv_title.text = "Hi There!"
    tv_title.visibility = View.VISIBLE
}
