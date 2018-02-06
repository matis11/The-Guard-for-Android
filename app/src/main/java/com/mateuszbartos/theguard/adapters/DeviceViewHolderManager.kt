package com.mateuszbartos.theguard.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.jacekmarchwicki.universaladapter.BaseAdapterItem
import com.jacekmarchwicki.universaladapter.ViewHolderManager
import com.mateuszbartos.theguard.R
import com.mateuszbartos.theguard.models.Device

class DeviceViewHolderManager() : ViewHolderManager {

    override fun createViewHolder(parent: ViewGroup, inflater: LayoutInflater): ViewHolderManager.BaseViewHolder<Device> {
        return ViewHolder(inflater.inflate(R.layout.devices_item, parent, false))
    }

    /* package */ internal inner class ViewHolder(itemView: View) : ViewHolderManager.BaseViewHolder<Device>(itemView) {

        override fun bind(item: Device) {
            val deviceName = this.itemView.findViewById<TextView>(R.id.device_name)
            val deviceSerial = this.itemView.findViewById<TextView>(R.id.device_serial)

            deviceName.text = item.name
            deviceSerial.text = item.serial
        }
    }

    override fun matches(baseAdapterItem: BaseAdapterItem): Boolean {
        return baseAdapterItem is Device
    }

}