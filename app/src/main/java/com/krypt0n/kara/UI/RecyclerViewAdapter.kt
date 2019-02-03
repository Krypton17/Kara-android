package com.krypt0n.kara.UI

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import com.krypt0n.kara.*
import java.io.File
import java.io.FileInputStream
import java.io.ObjectInputStream
import java.util.regex.Pattern

class RecyclerViewAdapter(val list: ArrayList<Note>) : RecyclerView.Adapter<RecyclerViewAdapter.CustomViewHolder>() {
    override fun onBindViewHolder(holder : CustomViewHolder, position : Int) {
        val title = list[position].title
        var text = list[position].text
        holder.title_field.text = title
        val pattern = Pattern.compile("(^([\\S]+)([ ])([\\S]+)([ ])([\\w\\d]+))")
        val matcher = pattern.matcher(text)
        text = if (matcher.find())
            matcher.group()
        else {
            if (text.length > 41) text.substring(0, 40).plus(" ...") else text
        }
        holder.desc.text = text
    }
    override fun getItemCount(): Int {
        return list.size
    }
    override fun onCreateViewHolder(parent : ViewGroup, viewType: Int): CustomViewHolder {
        val v =  LayoutInflater.from(parent.context).inflate(R.layout.notes_list,parent,false)
        return CustomViewHolder(v)
    }
    fun removeItem(position: Int){
        list.removeAt(position)
        notifyItemRemoved(position)
    }
    fun restoreItem(note: Note,position: Int){
        list.add(position,note)
        notifyItemInserted(position)
    }
    class CustomViewHolder(view : View) : RecyclerView.ViewHolder(view){
        val title_field = view.findViewById(R.id.note_title) as TextView
        val desc   = view.findViewById(R.id.note_desc) as TextView
        val view_foreground = view.findViewById(R.id.view_foreground) as RelativeLayout
        val view_background= view.findViewById(R.id.view_background) as RelativeLayout
        init {
            view.setOnClickListener {
                selected_item = adapterPosition
                view.context.startActivity(Intent(view.context, NoteActivity()::class.java))
            }
        }
    }
}