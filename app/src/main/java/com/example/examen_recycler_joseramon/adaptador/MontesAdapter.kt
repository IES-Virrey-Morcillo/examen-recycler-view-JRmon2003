package com.example.examen_recycler_joseramon.adaptador

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.examen_recycler_joseramon.Montes
import com.example.examen_recycler_joseramon.R

class MontesAdapter(
    private val montesList: MutableList<Montes>,
    private val onClickListener: (Montes) -> Unit,
    private val onClickDelete: (Int) -> Unit
) :
    RecyclerView.Adapter<MontesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MontesViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return MontesViewHolder(layoutInflater.inflate(R.layout.item_montes, parent, false))
    }

    override fun onBindViewHolder(holder: MontesViewHolder, position: Int) {
        val item = montesList[position]
        holder.render(item,onClickDelete)
        holder.itemView.setOnClickListener{onClickListener(item)}

        holder.binding.masInfo.setOnClickListener {

            val urlInfo = item.urlinfo
            Toast.makeText(holder.itemView.context, "URL de información: $urlInfo", Toast.LENGTH_LONG).show()


            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(urlInfo))
            holder.itemView.context.startActivity(intent)
        }

        holder.itemView.setOnClickListener { onClickListener(item) }
    }


    override fun getItemCount(): Int = montesList.size


    fun filterByName(montes: MutableList<Montes>){
        montesList.clear()
        montesList.addAll(montes)
        notifyDataSetChanged()
    }
}