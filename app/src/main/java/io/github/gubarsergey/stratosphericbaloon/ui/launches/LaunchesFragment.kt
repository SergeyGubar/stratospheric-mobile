package io.github.gubarsergey.stratosphericbaloon.ui.launches

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.github.gubarsergey.stratosphericbaloon.App
import io.github.gubarsergey.stratosphericbaloon.R
import io.github.gubarsergey.stratosphericbaloon.extension.notNullContext
import io.github.gubarsergey.stratosphericbaloon.helper.ItemDecoration
import io.github.gubarsergey.stratosphericbaloon.db.launches.LaunchesRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_launches.*
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
            .subscribe({ launches ->
                info("Got launches $launches")
                val adapter = LaunchesAdapter(launches)
                launches_recycler.adapter = adapter
                launches_recycler.layoutManager = LinearLayoutManager(notNullContext)
                launches_recycler.addItemDecoration(ItemDecoration(resources.getDimensionPixelOffset(R.dimen.default_padding_small)))
            }) {
                info("Failed getting launches! $it")
            }
    }

    override fun onStop() {
        super.onStop()
        disposable.dispose()
    }
}