package com.mateuszbartos.theguard.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mateuszbartos.theguard.R
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

    val sensorsPresenter = SensorsPresenter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.sensors_fragment, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        val layoutManager = LinearLayoutManager(context)
//        recyclerView.setLayoutManager(layoutManager)

//        val viewHolderManager = RoomsPreviewViewHolderManager(roomsPreviewPresenter.roomSelectedObserver())
//        val adapter = RxUniversalAdapter(listOf<RoomsPreviewViewHolderManager>(viewHolderManager))
//        recyclerView.setAdapter(adapter)

        subscription.addAll(
                sensorsPresenter.sensorDataLoadedObservable()
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe { testText.text = it }
        )
    }
}