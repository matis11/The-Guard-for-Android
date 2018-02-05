package com.mateuszbartos.theguard.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import com.jacekmarchwicki.universaladapter.BaseAdapterItem
import com.jacekmarchwicki.universaladapter.ViewHolderManager
import com.jakewharton.rxbinding.view.RxView
import com.mateuszbartos.theguard.R
import com.mateuszbartos.theguard.models.Device
import com.mateuszbartos.theguard.models.DeviceData
import rx.Observer
import rx.subscriptions.CompositeSubscription

class CameraViewHolderManager(val cameraClicked: Observer<String>) : ViewHolderManager {

    private val subscription = CompositeSubscription()

    override fun createViewHolder(parent: ViewGroup, inflater: LayoutInflater): ViewHolderManager.BaseViewHolder<Device> {
        return ViewHolder(inflater.inflate(R.layout.cameras_item, parent, false))
    }

    /* package */ internal inner class ViewHolder(itemView: View) : ViewHolderManager.BaseViewHolder<Device>(itemView) {

        override fun bind(item: Device) {
            val cameraName = this.itemView.findViewById<TextView>(R.id.camera_preview_name)
            val cameraLayout = this.itemView.findViewById<FrameLayout>(R.id.camera_preview_layout)

            cameraName.text = item.name

            subscription.addAll(
                    RxView.clicks(cameraLayout)
                            .map { "http://52.236.165.15:80/hls/${item.serial}.m3u8"}
                            .subscribe { cameraClicked }
            )
        }
    }

    override fun matches(baseAdapterItem: BaseAdapterItem): Boolean {
        return baseAdapterItem is DeviceData
    }

}