package com.mateuszbartos.theguard.models

import com.jacekmarchwicki.universaladapter.BaseAdapterItem

data class DeviceData(val name: String, val value: Double) : BaseAdapterItem {
    override fun adapterId(): Long {
        return hashCode().toLong()
    }

    override fun same(item: BaseAdapterItem): Boolean {
        return equals(item)
    }

    override fun matches(item: BaseAdapterItem): Boolean {
        return item is DeviceData && item.adapterId() == adapterId()
    }
}