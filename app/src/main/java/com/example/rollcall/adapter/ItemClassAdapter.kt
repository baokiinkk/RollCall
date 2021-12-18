package com.example.rollcall.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.rollcall.data.model.DataClass
import com.example.rollcall.data.model.SubjectResponse
import com.example.rollcall.databinding.ItemClassBinding
import com.example.rollcall.utils.Utils

class ItemClassAdapter(private val onClick: (SubjectResponse) -> Unit) :
    ListAdapter<SubjectResponse, ItemClassAdapter.ViewHolder>(
        ItemClassDiffUtil()
    ) {
    class ViewHolder(private val binding: ItemClassBinding) :
        RecyclerView.ViewHolder(binding.root) {
        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val binding =
                    ItemClassBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                return ViewHolder(
                    binding
                )
            }
        }

        fun bind(item: SubjectResponse, onClick: ((SubjectResponse) -> Unit)? = null) {
                binding.data = item
                itemView.setOnClickListener {
                    if (onClick != null) {
                        onClick(item)
                    }
                }
                binding.executePendingBindings()
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(
            parent
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it, onClick) }
    }
    fun filter(newText: CharSequence?, list: MutableList<SubjectResponse>) {
        newText?.let { text ->
            val words = text.split(" ").toMutableList()
            var output = ""
            for (word in words) {
                output += word.lowercase() + " "
            }
            submitList(list.filter {
                it.name.lowercase().contains(output.trim()) ||
                        it.subjectId.lowercase().contains(output.trim())
            })
            notifyDataSetChanged()
        }
    }
}

class ItemClassDiffUtil : DiffUtil.ItemCallback<SubjectResponse>() {
    // cung cấp thông tin về cách xác định phần
    override fun areItemsTheSame(
        oldItem: SubjectResponse,
        newItem: SubjectResponse
    ): Boolean { // cho máy biết 2 item_detail khi nào giống
        return oldItem.subjectId == newItem.subjectId // dung
    }

    override fun areContentsTheSame(
        oldItem: SubjectResponse,
        newItem: SubjectResponse
    ): Boolean { // cho biết item_detail khi nào cùng nội dung
        return oldItem == newItem
    }

}
