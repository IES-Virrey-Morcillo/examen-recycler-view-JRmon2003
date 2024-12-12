package com.example.examen_recycler_joseramon.adaptador

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.example.examen_recycler_joseramon.Montes
import com.example.examen_recycler_joseramon.databinding.ItemMontesBinding

class MontesViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val binding = ItemMontesBinding.bind(view)

    fun render(MonteModel: Montes, onClickDelete:(Int)->Unit) {
        binding.tvNombre.text = "${MonteModel.nombre}"
        binding.tvContienente.text = "Contienente: ${MonteModel.continente}"
        binding.tvAltura.text = "Altura: ${MonteModel.altura}"
        Glide.with(binding.ivMonte.context).load(MonteModel.foto).into(binding.ivMonte)
        binding.btndelete.setOnClickListener{
            onClickDelete(adapterPosition)

            Glide.with(binding.ivMonte.context)
                .asBitmap()
                .load(MonteModel.foto)
                .into(object : CustomTarget<Bitmap>(1280, 720) {
                    override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                        binding.ivMonte.scaleType = ImageView.ScaleType.CENTER_CROP
                        binding.ivMonte.setImageBitmap(resource)
                    }
                    override fun onLoadCleared(placeholder: Drawable?) {}
                })

        }

    }

}