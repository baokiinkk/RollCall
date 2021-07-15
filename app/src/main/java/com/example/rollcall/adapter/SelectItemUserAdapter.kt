package com.example.rollcall.adapter

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.rollcall.data.model.User
import com.example.rollcall.databinding.ItemUserBinding

class SelectItemUserAdapter() :
    ListAdapter<User, SelectItemUserAdapter.ViewHolder>(
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

        fun bind(item: User) {
            binding.data = item
            itemView.setBackgroundColor(if (item.selected) Color.GREEN else Color.WHITE)
            itemView.setOnClickListener {
                itemView.setBackgroundColor(if (!item.selected) Color.GREEN else Color.WHITE)
                item.selected = !item.selected
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
        getItem(position)?.let { holder.bind(it) }
    }

    fun filter(newText: CharSequence?, list: MutableList<User>) {
        newText?.let { text ->
            val words = text.split(" ").toMutableList()
            var output = ""
            for (word in words) {
                output += word.lowercase() + " "
            }
            submitList(list.filter {
                Log.d("quocbao",it.id.lowercase()+"-"+it.name.lowercase()+"-"+output.trim()+"\n-"+it.classes.toString())
                it.name.lowercase().contains(output.trim()) ||
                        it.id.lowercase().contains(output.trim()) ||
                        checkClasses(it.classes,output)
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
