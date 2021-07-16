package com.example.rollcall.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.rollcall.data.model.User
import com.example.rollcall.databinding.ItemUserBinding

class ItemUserAdapter(private val onClick: (User) -> Unit) :
    ListAdapter<User, ItemUserAdapter.ViewHolder>(
        ItemUserDiffUtil()
    ) {
    class ViewHolder(private val binding: ItemUserBinding) :
        RecyclerView.ViewHolder(binding.root) {
        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val binding =
                    ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                return ViewHolder(
                    binding
                )
            }
        }

        fun bind(item: User, onClick: ((User) -> Unit)? = null) {
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

    fun filter(newText: CharSequence?, list: MutableList<User>) {
        newText?.let { text ->
            val words = text.split(" ").toMutableList()
            var output = ""
            for (word in words) {
                output += word.lowercase() + " "
            }
            submitList(list.filter {
                it.name.lowercase().contains(output.trim()) ||
                        it.id.lowercase().contains(output.trim()) ||
                        checkClasses(it.classes, output)
            })
            notifyDataSetChanged()
        }
    }

    fun checkClasses(data: MutableList<String>?, output: String): Boolean {
        if (data == null) return false
        else {
            data.forEach {
                if (it.lowercase().contains(output.trim()))
                    return true
            }
        }
        return false
    }
}

class ItemUserDiffUtil : DiffUtil.ItemCallback<User>() {
    // cung cấp thông tin về cách xác định phần
    override fun areItemsTheSame(
        oldItem: User,
        newItem: User
    ): Boolean { // cho máy biết 2 item_detail khi nào giống
        return oldItem.id == newItem.id // dung
    }

    override fun areContentsTheSame(
        oldItem: User,
        newItem: User
    ): Boolean { // cho biết item_detail khi nào cùng nội dung
        return oldItem == newItem
    }

}
