package com.example.ankolayouts

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.delay
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.sdk27.coroutines.onSeekBarChangeListener

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)

//        DSL을 이용한 레이아웃 작성
//        이 코드에서는 onClick 블록에서 코루틴을 지원하기 때문에 async()를 사용하지 않고도 지연 함수나 람다식을 이용할 수 있다.
        verticalLayout { // vertical 속성을 가진 LinearLayout
            val name = editText() { hint = "Name" } // EditText의 객체
            button("Say Hello") { // Button 뷰
                onClick { toast("Hello, ${name.text}!") } // 클릭 이벤트 처리
            }
        }

//        findByViewId()와 동일하지만 더 간단하게
//        val helloWorld = find<TextView>(R.id.tv_hello_world)
//        helloWorld.hint = "Hello World"
//        helloWorld.onClick { /* do Something */ }

        MainUI().setContentView(this)
    }
}

// 분리된 레이아웃을 위한 클래스
class MainUI : AnkoComponent<MainActivity> {
//    with 함수는 인자로 받는 객체를 이어지는 블록에 전달하며 블록의 결괏값을 반환한다.
//    여기서 DSL의 레이아웃 코드가 사용된다. 이것을 setContentView(this)에 의해 MainActivity에 화면을 구성하게 된다.
    override fun createView(ui: AnkoContext<MainActivity>): View = with(ui) {
//        DSL 형태의 UI 작성
        verticalLayout { // vertical 속성을 가진 LinearLayout
            val name = editText() { hint = "Name" } // EditText의 객체
            checkBox {
                text = "checkBox"
                onClick {
                    delay(2000) // UI 문맥에 있으므로 지연 함수 적용 가능
                    isChecked = true // true, false로 체크 설정 가능
                }
                setChecked(true) // isChecked = true와 같은 표현
            }
            val percent = textView() { text = "Seek!" }
            seekBar {
                onSeekBarChangeListener { // 필요한 메서드 이외의 오버라이딩해야 하는 메서드 생략 가능
                    onProgressChanged { seekBar, progress, fromUser ->
                        percent.text = progress.toString()
                    }
                }
            }
            button("Say Hello") { // Button 뷰
//                일반 코루틴 핸들러 코드를 구현할 땐 launch() { ... } 블록을 넣어줘야 한다.
//                DSL 코드에서는 onClick { ... }을 사용할 때 기본적으로 Dispatchers.Main을 사용한다.
                onClick { toast("Hello, ${name.text}!") } // 클릭 이벤트 처리
            }.lparams(width = wrapContent) { // width와 height에 wrapContent, matchParent 설정 가능
                horizontalMargin = dip(5) // 왼쪽과 오른쪽 margin 설정
                topMargin = dip(10) // margin을 사용하면 4개의 모든 margin 설정
            }
//            button("Login") {
//                onClick(yourContext // 사용자가 정의한 특정 문맥이 있을 경우) {
//                    val result = longWork().await()
//                    showResult(result)
//                }
//            }
        }
    }
}
