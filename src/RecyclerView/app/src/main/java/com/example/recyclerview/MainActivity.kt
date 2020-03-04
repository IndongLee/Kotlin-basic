package com.example.recyclerview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private val petList = arrayListOf<Pet>(
        Pet("Samoyed", "Female", "4", "samoyed"),
        Pet("Jindo", "Male", "8", "jindo"),
        Pet("Pomeranian", "Male", "9", "pomeranian"),
        Pet("Shiba", "Female", "2", "shiba"),
        Pet("Golden Retriever", "Female", "5", "golden_retriever")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        addDataArray()

//        RecyclerVIew를 구성하기 위한 세 부분
//        1) 화면 레이아웃을 관리하는 레이아웃 매니저
//        2) RecyclerView에 뷰를 놓을 뷰홀더
//        3) 데이터를 연결하는 어댑터. 어댑터는 새로운 항목을 생성하고 뷰홀더에 구성해 띄우는 역할을 한다.

        rv_data_list.layoutManager = LinearLayoutManager(this) as RecyclerView.LayoutManager?
//        rv_data_list.adapter = DataAdapter(dataArray, this)
        rv_data_list.setHasFixedSize(true)
//        람다식 매개변수를 통한 이벤트 핸들러 처리
        rv_data_list.adapter = ExtensionDataAdapter(petList, this) {
            Toast.makeText(
                this,
                "Breed: ${it.breed}, Age: ${it.age}",
                Toast.LENGTH_SHORT
            ).show()
        }

    }
//
//    private fun addDataArray() {
//        dataArray.add("오리")
//        dataArray.add("호랑이")
//        dataArray.add("여우")
//        dataArray.add("늑대")
//        dataArray.add("오소리")
//        dataArray.add("원숭이")
//        dataArray.add("펭귄")
//        dataArray.add("강아지")
//        dataArray.add("고양이")
//        dataArray.add("미어캣")
//        dataArray.add("타조")
//    }
}

