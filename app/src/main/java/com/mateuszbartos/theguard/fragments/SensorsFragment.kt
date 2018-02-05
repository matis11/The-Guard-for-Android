package com.mateuszbartos.theguard.fragments

import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jacekmarchwicki.universaladapter.rx.RxUniversalAdapter
import com.mateuszbartos.theguard.R
import com.mateuszbartos.theguard.adapters.SensorViewHolderManager
import com.mateuszbartos.theguard.presenters.SensorsPresenter
import kotlinx.android.synthetic.main.sensors_fragment.*
import rx.android.schedulers.AndroidSchedulers

class SensorsFragment : BaseFragment() {
    companion object {
        val TAG = SensorsFragment::class.java.simpleName!!

        fun newInstance(): SensorsFragment {
            return SensorsFragment()
        }
    }

    private val COLUMN_COUNT = 2
    private var sensorsPresenter: SensorsPresenter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.sensors_fragment, container, false)
    }


    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sensorsPresenter = SensorsPresenter(context)

        val layoutManager = GridLayoutManager(context, COLUMN_COUNT)
        recyclerView.layoutManager = layoutManager

        val viewHolderManager = SensorViewHolderManager()
        val adapter = RxUniversalAdapter(listOf(viewHolderManager))
        recyclerView.adapter = adapter

        subscription.addAll(
                sensorsPresenter!!.sensorDataLoadedObservable()
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(adapter)
        )
    }
}