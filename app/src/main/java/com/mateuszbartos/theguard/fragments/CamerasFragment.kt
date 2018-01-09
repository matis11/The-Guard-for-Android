package com.mateuszbartos.theguard.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mateuszbartos.theguard.R

class CamerasFragment : BaseFragment() {
    companion object {
        fun newInstance(): CamerasFragment {
            return CamerasFragment()
        }
    }

    val TAG = CamerasFragment::class.java.simpleName

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.cameras_fragment, container, false)
    }

}