package com.example.recyclerview

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.data_list_item.*

class ExtensionDataAdapter(val items: ArrayList<Pet>, val context: Context, val itemSelect: (Pet) -> Unit) : RecyclerView.Adapter<ExtensionDataAdapter.ExtensionViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExtensionViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.data_list_item, parent, false)
        return ExtensionViewHolder(view, itemSelect)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ExtensionViewHolder, position: Int) {
        holder.bind(items[position], context)
    }

    // LayoutContainer 인터페이스에서 하위 뷰들을 찾을 수 있는 뷰를 제공한다.
    // 이 인터페이스는 안드로이드 코틀린 확장을 통해 제공되며, 어떤 클래스든 캐시 메커니즘을 사용할 수 있게 만들어 준다.
    // 즉, containerView는 LayoutContainer로부터 오버라이딩된 뷰 객체로 ViewHolder가 잡고 있는 뷰가 된다.
    // 또한 ExtensionViewHolder 클래스의 모든 메서드에서, 리소스로부터 변환된 뷰에 직접 접근할 수 있게 된다.
    // 내부적으로는 _findCashedViewById()를 사용해 호출되므로 리소스를 불러들이기 위한 속도도 저하되지 않는다.
    inner class ExtensionViewHolder(override val containerView: View, itemSelect: (Pet) -> Unit) : RecyclerView.ViewHolder(containerView),
        LayoutContainer {
        fun bind(pet: Pet, context: Context) {
            if (pet.photo != "") {
                val resourceId = context.resources.getIdentifier(
                    pet.photo,
                    "drawable",
                    context.packageName
                )
                img_pet?.setImageResource(resourceId)
            } else {
//                없으면 기본 아이콘으로
                img_pet?.setImageResource(R.mipmap.ic_launcher_round)
            }
            tv_breed.text = pet.breed
            tv_age.text = pet.age
            tv_gender.text = pet.gender

            itemView.setOnClickListener { itemSelect(pet) }

        }
    }
}

