package com.mateuszbartos.theguard.adapters

import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import az.plainpie.PieView
import com.jacekmarchwicki.universaladapter.BaseAdapterItem
import com.jacekmarchwicki.universaladapter.ViewHolderManager
import com.jacekmarchwicki.universaladapter.ViewHolderManager.BaseViewHolder
import com.mateuszbartos.theguard.R
import com.mateuszbartos.theguard.models.SensorData
import az.plainpie.animation.PieAngleAnimation



class SensorViewHolderManager() : ViewHolderManager {

    override fun createViewHolder(parent: ViewGroup, inflater: LayoutInflater): BaseViewHolder<SensorData> {
        return ViewHolder(inflater.inflate(R.layout.sensors_item, parent, false))
    }

    /* package */ internal inner class ViewHolder(itemView: View) : BaseViewHolder<SensorData>(itemView) {

        override fun bind(item: SensorData) {
            val pieView = this.itemView.findViewById<PieView>(R.id.pieView)
            val sensorName = this.itemView.findViewById<TextView>(R.id.sensorName)

            pieView.percentage = (item.value * 100).toFloat()
            sensorName.text = item.name

            val colorBackground = ContextCompat.getColor(itemView.context, R.color.colorBackground)
            val colorAccent = ContextCompat.getColor(itemView.context, R.color.colorAccent)
            pieView.setInnerBackgroundColor(colorBackground)
            pieView.setPercentageBackgroundColor(colorAccent)

            val animation = PieAngleAnimation(pieView)
            animation.duration = 1 * 1000
            pieView.startAnimation(animation)
        }
    }

    override fun matches(baseAdapterItem: BaseAdapterItem): Boolean {
        return baseAdapterItem is SensorData
    }
}
