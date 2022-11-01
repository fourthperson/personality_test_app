package com.iak.perstest.presentation.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.iak.perstest.databinding.ItemHistoryBinding
import com.iak.perstest.domain.entity.PastTest

class HistoryAdapter(private val data: List<PastTest>) : RecyclerView.Adapter<HistoryAdapter.VH>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(ItemHistoryBinding.inflate(LayoutInflater.from(parent.context)).root)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int {
        return data.size
    }

    inner class VH(view: View) : RecyclerView.ViewHolder(view) {
        val layout = ItemHistoryBinding.bind(view)

        fun bind(test: PastTest) {
            layout.date.text = test.date
            layout.outcome.text = test.outcome
        }
    }
}