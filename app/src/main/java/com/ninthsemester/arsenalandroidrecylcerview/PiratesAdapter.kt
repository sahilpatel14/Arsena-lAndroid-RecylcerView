package com.ninthsemester.arsenalandroidrecylcerview

import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Build
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import java.util.*

/**
 * Created by sahil-mac on 02/04/18.
 */
class PiratesAdapter(private val items : MutableList<Pair<String, String>>,
                     private val showAsList : Boolean = true) : RecyclerView.Adapter<PiratesAdapter.PirateViewHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PirateViewHolder =
            when(showAsList) {
                true -> LayoutInflater.from(parent.context)
                        .inflate(R.layout.rv_item_list,parent, false)
                        .run { PirateViewHolder(this) }

                false-> LayoutInflater.from(parent.context)
                        .inflate(R.layout.rv_item_grid,parent, false)
                        .run { PirateViewHolder(this) }
            }


    override fun onBindViewHolder(holder: PirateViewHolder, position: Int) = holder.bind(items[position])

    override fun getItemCount() : Int = items.size



    inner class PirateViewHolder(
            itemView: View,
            private val imageView : ImageView = itemView.findViewById(R.id.imageView),
            private val tvName: TextView = itemView.findViewById(R.id.tvName),
            private val tvVessel: TextView = itemView.findViewById(R.id.tvVessel)) : RecyclerView.ViewHolder(itemView) {

        fun bind(data : Pair<String, String>) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                val drawable = imageView.background as GradientDrawable
                drawable.color = ColorStateList.valueOf(getRandomColor())
            }

            tvName.text = data.first
            tvVessel.text = data.second
        }
    }

    companion object {

        fun getRandomColor(): Int =
                with(Random()) {
                return Color.argb(255, nextInt(), nextInt(), nextInt())
            }
        }
}