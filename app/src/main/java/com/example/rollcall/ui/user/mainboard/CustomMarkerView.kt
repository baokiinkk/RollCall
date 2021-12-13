package com.example.rollcall.ui.user.mainboard

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.widget.TextView
import com.example.rollcall.R
import com.github.mikephil.charting.components.MarkerView
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.highlight.Highlight

class CustomMarkerView(context: Context?, layoutResource: Int, private val listLabel : MutableList<String>, private val widthCard:Int, private val heightCard:Int) :
    MarkerView(context, layoutResource) {
    private val tvContent: TextView = findViewById(R.id.tvContent)

    // callbacks everytime the MarkerView is redrawn, can be used to update the
    // content (user-interface)
    @SuppressLint("SetTextI18n")
    override fun refreshContent(
        e: Entry,
        highlight: Highlight?
    ) {
        //Log.d("tncnhan", e.describeContents().toString())
        tvContent.text = "Kì " + listLabel[e.x.toInt()] // set the entry-value as the display text
        super.refreshContent(e, highlight)
    }

    //    override fun getOffset(): MPPointF? {
//        return MPPointF((-(width / 2)).toFloat(), (-height).toFloat())
//    }
    override fun draw(canvas: Canvas, x: Float, y: Float) {
        // Check marker position and update offsets.
        var posx = x
        var posy = y
        val w = width
        val h = height
        if (posx + w / 2 > widthCard) posx -= w
        else if (posx >= w / 2) posx -= w / 2

        if (posy + height > heightCard) posy -= 1.5f * h

        // translate to the correct position and draw
        canvas.translate(posx, posy)
        draw(canvas)
        canvas.translate(-posx, -posy)
    }
}
