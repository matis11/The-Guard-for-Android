package com.mateuszbartos.theguard.fragments

import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jacekmarchwicki.universaladapter.rx.RxUniversalAdapter
import com.mateuszbartos.theguard.R
import com.mateuszbartos.theguard.activities.PlayerActivity
import com.mateuszbartos.theguard.adapters.CameraViewHolderManager
import com.mateuszbartos.theguard.presenters.CamerasPresenter
import kotlinx.android.synthetic.main.sensors_fragment.*
import rx.android.schedulers.AndroidSchedulers
import rx.subjects.BehaviorSubject


class CamerasFragment : BaseFragment() {
    companion object {
        val TAG = CamerasFragment::class.java.simpleName!!

        fun newInstance(): CamerasFragment {
            return CamerasFragment()
        }
    }

    private val COLUMN_COUNT = 1
    private var camerasPresenter: CamerasPresenter? = null
    private val cameraClicked: BehaviorSubject<String> = BehaviorSubject.create()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.cameras_fragment, container, false)
    }


    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        camerasPresenter = CamerasPresenter(context)

        val layoutManager = GridLayoutManager(context, COLUMN_COUNT)
        recyclerView.layoutManager = layoutManager

        val viewHolderManager = CameraViewHolderManager(cameraClicked)
        val adapter = RxUniversalAdapter(listOf(viewHolderManager))
        recyclerView.adapter = adapter

        subscription.addAll(
                camerasPresenter!!.devicesIdsObservable()
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(adapter),

                cameraClicked
                        .doOnNext{println(it)}
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe {
                            startActivity(PlayerActivity.newIntent(context, it))
                        }
        )
    }

}