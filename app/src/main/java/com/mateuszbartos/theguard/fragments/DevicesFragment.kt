package com.mateuszbartos.theguard.fragments

import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jacekmarchwicki.universaladapter.rx.RxUniversalAdapter
import com.mateuszbartos.theguard.R
import com.mateuszbartos.theguard.adapters.DeviceViewHolderManager
import com.mateuszbartos.theguard.presenters.DevicesPresenter
import kotlinx.android.synthetic.main.cameras_fragment.*
import rx.android.schedulers.AndroidSchedulers
import rx.subjects.BehaviorSubject

class DevicesFragment : BaseFragment() {
    companion object {
        val TAG = DevicesFragment::class.java.simpleName!!

        fun newInstance(): DevicesFragment {
            return DevicesFragment()
        }
    }

    private val COLUMN_COUNT = 1
    private var devicesPresenter: DevicesPresenter? = null
    private val cameraClicked: BehaviorSubject<String> = BehaviorSubject.create()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.cameras_fragment, container, false)
    }


    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        devicesPresenter = DevicesPresenter(context)

        val layoutManager = GridLayoutManager(context, COLUMN_COUNT)
        recyclerView.layoutManager = layoutManager

        val viewHolderManager = DeviceViewHolderManager()
        val adapter = RxUniversalAdapter(listOf(viewHolderManager))
        recyclerView.adapter = adapter

        subscription.addAll(
                devicesPresenter!!.devicesIdsObservable()
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(adapter)
        )
    }

}