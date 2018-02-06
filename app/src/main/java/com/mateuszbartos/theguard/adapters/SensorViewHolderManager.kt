package com.mateuszbartos.theguard.adapters

import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import az.plainpie.PieView
import az.plainpie.animation.PieAngleAnimation
import com.jacekmarchwicki.universaladapter.BaseAdapterItem
import com.jacekmarchwicki.universaladapter.ViewHolderManager
import com.jacekmarchwicki.universaladapter.ViewHolderManager.BaseViewHolder
import com.mateuszbartos.theguard.R
import com.mateuszbartos.theguard.models.DeviceData


class SensorViewHolderManager : ViewHolderManager {

    override fun createViewHolder(parent: ViewGroup, inflater: LayoutInflater): BaseViewHolder<DeviceData> {
        return ViewHolder(inflater.inflate(R.layout.sensors_item, parent, false))
    }

    /* package */ internal inner class ViewHolder(itemView: View) : BaseViewHolder<DeviceData>(itemView) {

        override fun bind(item: DeviceData) {
            val pieView = this.itemView.findViewById<PieView>(R.id.pieView)
            val sensorName = this.itemView.findViewById<TextView>(R.id.sensorName)

            val colorBackground = ContextCompat.getColor(itemView.context, R.color.colorBackground)
            val colorAccent = ContextCompat.getColor(itemView.context, R.color.colorAccent)
            val colorOK = ContextCompat.getColor(itemView.context, R.color.colorOK)
            val colorDanger = ContextCompat.getColor(itemView.context, R.color.colorDanger)

            pieView.setInnerBackgroundColor(colorBackground)
            pieView.setPercentageBackgroundColor(colorAccent)

            if (item.value < 1 && item.value > 0) {
                pieView.percentage = (item.value * 100).toFloat()
            } else if (item.value == 0.0) {
                pieView.percentage = 100f
                pieView.setInnerText("OK")
                pieView.setPercentageBackgroundColor(colorOK)
            } else if (item.value == 1.0) {
                pieView.percentage = 100f
                pieView.setInnerText("Uwaga")
                pieView.setPercentageBackgroundColor(colorDanger)
            } else {
                pieView.percentage = item.value.toFloat()
                pieView.setInnerText(String.format("%.1f °C", item.value))
            }
            sensorName.text = item.name

            val animation = PieAngleAnimation(pieView)
            animation.duration = 1 * 1000
            pieView.startAnimation(animation)
        }
    }

    override fun matches(baseAdapterItem: BaseAdapterItem): Boolean {
        return baseAdapterItem is DeviceData
    }


}
