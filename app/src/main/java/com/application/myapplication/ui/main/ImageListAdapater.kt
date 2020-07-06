package com.application.myapplication.ui.main

import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.application.myapplication.R
import com.application.myapplication.activity.DetailActivity
import com.application.myapplication.network.DataClass
import com.bumptech.glide.Glide
import com.google.gson.Gson


internal class ImageListAdapter internal constructor(context: Context, private val resource: Int, private val itemList: ArrayList<DataClass>) : ArrayAdapter<ImageListAdapter.ItemHolder>(context, resource) {

    override fun getCount(): Int {
        return if (this.itemList != null) this.itemList.size else 0
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var convertView = convertView

        val holder: ItemHolder
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(resource, null)
            holder = ItemHolder()
            holder.layoutItem=convertView!!.findViewById(R.id.layout_item)
            holder.name = convertView.findViewById(R.id.textView)
            holder.icon = convertView.findViewById(R.id.icon)
            convertView.tag = holder
        } else {
            holder = convertView.tag as ItemHolder
        }

        holder.name!!.text = this.itemList[position].title
        var url:String?=""

        //for Loading of image on downloading using Glide
        if(this.itemList[position].images!=null) {
            url = this.itemList[position].images[0].link

            holder.icon?.let {
                Glide.with(context)
                    .load(url)
                    .circleCrop()
                    .placeholder(R.mipmap.ic_launcher)
                    .error(R.mipmap.ic_launcher)
                    .fallback(R.mipmap.ic_launcher)
                    .into(it)
            }
        }

        holder.layoutItem?.setOnClickListener {
            val datapass:String= Gson().toJson(itemList[position])
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra("data",datapass)
            intent.flags =  FLAG_ACTIVITY_NEW_TASK
            context.startActivity(intent)
        }
        return convertView
    }

    internal class ItemHolder {
        var layoutItem: ConstraintLayout?=null
        var name: TextView? = null
        var icon: ImageView? = null
    }
}
