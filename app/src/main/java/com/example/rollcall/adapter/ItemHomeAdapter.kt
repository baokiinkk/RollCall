package com.example.rollcall.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.rollcall.data.model.ItemHomeModel
import com.example.rollcall.databinding.ItemHomeBinding

class ItemHomeAdapter(private val onClick: (ItemHomeModel) -> Unit) :
    ListAdapter<ItemHomeModel, ItemHomeAdapter.ViewHolder>(
        ItemHomeDiffUtil()
    ) {
    class ViewHolder(private val binding: ItemHomeBinding) :
        RecyclerView.ViewHolder(binding.root) {
        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val binding =
                    ItemHomeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                return ViewHolder(
                    binding
                )
            }
        }

        fun bind(item: ItemHomeModel, onClick: ((ItemHomeModel) -> Unit)? = null) {
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
}

class ItemHomeDiffUtil : DiffUtil.ItemCallback<ItemHomeModel>() {
    // cung cấp thông tin về cách xác định phần
    override fun areItemsTheSame(
        oldItem: ItemHomeModel,
        newItem: ItemHomeModel
    ): Boolean { // cho máy biết 2 item_detail khi nào giống
        return oldItem.title == newItem.title // dung
    }

    override fun areContentsTheSame(
        oldItem: ItemHomeModel,
        newItem: ItemHomeModel
    ): Boolean { // cho biết item_detail khi nào cùng nội dung
        return oldItem == newItem
    }

}
