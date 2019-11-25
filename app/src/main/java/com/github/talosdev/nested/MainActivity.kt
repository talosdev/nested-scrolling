package com.github.talosdev.nested

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<RecyclerView>(R.id.recycler).adapter = OuterAdapter()
    }
}

class OuterAdapter: RecyclerView.Adapter<MyViewHolder>() {

    private val items : List<ColorCell> = listOf(
            ColorCell.Single(R.color.red_500),
            ColorCell.Single(R.color.green_500),
            ColorCell.Single(R.color.blue_500),
            ColorCell.Multi(arrayOf(
                    R.color.green_050,
                    R.color.green_100,
                    R.color.green_200,
                    R.color.green_300,
                    R.color.green_400,
                    R.color.green_500,
                    R.color.green_600,
                    R.color.green_700,
                    R.color.green_800,
                    R.color.green_900
            ).toIntArray()),
            ColorCell.Single(R.color.red_100),
            ColorCell.Single(R.color.green_100),
            ColorCell.Single(R.color.blue_100),
            ColorCell.Single(R.color.red_300),
            ColorCell.Single(R.color.green_300),
            ColorCell.Single(R.color.blue_300),
            ColorCell.Single(R.color.red_700),
            ColorCell.Single(R.color.green_700),
            ColorCell.Single(R.color.blue_700)
    )


    override fun getItemViewType(position: Int): Int {
        return when (items[position]) {
            is ColorCell.Single -> 0
            is ColorCell.Multi -> 1
        }
    }

    override fun getItemCount()= items.count()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(
                if (viewType == 0) R.layout.single_item else R.layout.multi_item,
                parent, false)
        return if (viewType == 0) ColorViewHolder(v) else MultiColorViewHolder(v)

    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        when (val cell = items[position]) {
            is ColorCell.Single -> (holder as ColorViewHolder).bind(cell.colorRes)
            is ColorCell.Multi -> (holder as MultiColorViewHolder).bind(cell.colorArray)
        }
    }
}



sealed class ColorCell {
    data class Single(val colorRes: Int): ColorCell()
    data class Multi(val colorArray: IntArray): ColorCell()
}

open class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

class ColorViewHolder(itemView: View) : MyViewHolder(itemView) {

    private var cell: View = itemView.findViewById<View>(R.id.cell)

    fun bind(color: Int) {
        cell.setBackgroundColor(ContextCompat.getColor(cell.context, color))

    }
}

class MultiColorViewHolder(itemView: View) : MyViewHolder(itemView) {

    private var recycler: RecyclerView = itemView.findViewById(R.id.multi_recycler)

    fun bind(colors: IntArray) {
        val adapter = InnerAdapter(colors)
        recycler.adapter = adapter
    }

}

class InnerAdapter(private val colors: IntArray): RecyclerView.Adapter<ColorViewHolder>() {

    override fun getItemCount() = colors.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ColorViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.inner_item, parent, false)
        return ColorViewHolder(v)
    }

    override fun onBindViewHolder(holder: ColorViewHolder, position: Int) {
        holder.bind(colors[position])
    }

}