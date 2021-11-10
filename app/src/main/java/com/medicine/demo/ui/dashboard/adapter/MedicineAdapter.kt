package com.medicine.demo.ui.dashboard.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.medicine.demo.R
import com.medicine.demo.data.remote.AssociatedDrug
import kotlinx.android.synthetic.main.lay_item.view.*

class MedicineAdapter(
    private var alData: ArrayList<AssociatedDrug> = ArrayList()
) : RecyclerView.Adapter<MedicineAdapter.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.lay_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        holder.bindView()
    }

    override fun getItemCount(): Int {
        return alData.size
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bindView() {
            val item = alData[adapterPosition]
            itemView.tvName.text = item.name
            itemView.tvStrength.text = item.strength
            itemView.tvDose.text = if(item.dose.isEmpty()) "--" else  item.dose
        }
    }

    fun addItem(alData: ArrayList<AssociatedDrug>) {
        this.alData = alData
        notifyDataSetChanged()
    }
}