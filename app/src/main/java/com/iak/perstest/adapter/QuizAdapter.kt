package com.iak.perstest.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.iak.perstest.databinding.ItemQuizBinding

class QuizAdapter(private var data: List<String>, private var callback: ClickListener) :
    RecyclerView.Adapter<QuizAdapter.VH>() {

    interface ClickListener {
        fun onTrue()
        fun onFalse()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(ItemQuizBinding.inflate(LayoutInflater.from(parent.context)).root)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.binding.textQuestion.text = data[position]
    }

    override fun getItemCount(): Int {
        return data.size
    }

    inner class VH(view: View) : RecyclerView.ViewHolder(view) {
        var binding = ItemQuizBinding.bind(view)

        init {
            binding.buttonTrue.setOnClickListener {
                callback.onTrue()
            }
            binding.buttonFalse.setOnClickListener {
                callback.onFalse()
            }
        }
    }
}