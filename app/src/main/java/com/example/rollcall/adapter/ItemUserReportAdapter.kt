package com.example.rollcall.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.rollcall.data.model.Content
import com.example.rollcall.data.model.User
import com.example.rollcall.databinding.ItemUserBinding
import com.example.rollcall.databinding.ItemUserReportBinding

class ItemUserReportAdapter :
    ListAdapter<Content, ItemUserReportAdapter.ViewHolder>(
        ItemUserReportDiffUtil()
    ) {
    class ViewHolder(private val binding: ItemUserReportBinding) :
        RecyclerView.ViewHolder(binding.root) {
        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val binding =
                    ItemUserReportBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                return ViewHolder(
                    binding
                )
            }
        }

        fun bind(item: Content) {
            binding.data = item
            itemView.setBackgroundColor(if (item.status == "ontime") Color.GREEN else if (item.status == "late") Color.YELLOW else Color.WHITE)
            binding.executePendingBindings()

        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(
            parent
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    fun filter(newText: CharSequence?, list: MutableList<Content>) {
        newText?.let { text ->
            val words = text.split(" ").toMutableList()
            var output = ""
            for (word in words) {
                output += word.lowercase() + " "
            }
            submitList(list.filter {
                it.user.name.lowercase().contains(output.trim()) ||
                        it.user.id.lowercase().contains(output.trim()) ||
                        checkClasses(it.user.classes, output)
            })
            notifyDataSetChanged()
        }
    }

    fun checkClasses(data: MutableList<String>?, output: String): Boolean {
        if (data == null) return false
        else {
            data.forEach {
                if (it.lowercase() == (output.trim()))
                    return true
            }
        }
        return false
    }
}

class ItemUserReportDiffUtil : DiffUtil.ItemCallback<Content>() {
    // cung cấp thông tin về cách xác định phần
    override fun areItemsTheSame(
        oldItem: Content,
        newItem: Content
    ): Boolean { // cho máy biết 2 item_detail khi nào giống
        return oldItem.status == newItem.status // dung
    }

    override fun areContentsTheSame(
        oldItem: Content,
        newItem: Content
    ): Boolean { // cho biết item_detail khi nào cùng nội dung
        return oldItem.user.id == newItem.user.id
    }

}

