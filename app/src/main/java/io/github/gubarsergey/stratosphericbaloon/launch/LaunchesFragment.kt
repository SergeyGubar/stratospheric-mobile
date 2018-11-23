package io.github.gubarsergey.stratosphericbaloon.launch

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.github.gubarsergey.stratosphericbaloon.App
import io.github.gubarsergey.stratosphericbaloon.R
import io.github.gubarsergey.stratosphericbaloon.extension.notNullContext
import io.github.gubarsergey.stratosphericbaloon.helper.SharedPrefHelper
import io.github.gubarsergey.stratosphericbaloon.retrofit.api.launch.LaunchesApi
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info

class LaunchesFragment : Fragment(), AnkoLogger {

    private lateinit var disposable: Disposable

    private val retrofit by lazy {
        (activity?.application as App).getRetrofit()
    }

    private val launchesRepository by lazy {
        LaunchesRepository(notNullContext, retrofit)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_launches, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        disposable = launchesRepository.getAllLaunches()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({
                info("Got launches $it")
            }) {
                info("Fail $it")
            }
    }

    override fun onStop() {
        super.onStop()
        disposable.dispose()
    }
}