package com.example.recyclerview

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.data_list_item.view.*

class DataAdapter(val items: ArrayList<String>, val context: Context) : RecyclerView.Adapter<ViewHolder>() {
//    뷰를 띄움 : 만들어진 뷰가 없는 경우 xml 파일을 inflate하여 ViewHolder를 생성한다.
//    inflate : xml에 써져 있는 view의 정의를 실제 view 객체로 만드는 역할
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(
            R.layout.data_list_item,
            parent,
            false
        ))
    }

//    목록 개수를 반환 : RecyclerView()로 만들어지는 item의 총 개수를 반환한다.
    override fun getItemCount(): Int {
        return items.size
    }

//    ArrayList의 각 데이터를 바인드 : onCreateVIewHolder()에서 만든 뷰와 실제 입력되는 각각의 데이터를 연결한다.
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        holder.tvDataType.text = items[position]
    }
}

// 데이터를 로드해 보여주기 위한 뷰홀더
class ViewHolder (view: View) : RecyclerView.ViewHolder(view) {
//    val tvDataType = view.tv_data_type!!
}

