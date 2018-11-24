package io.github.gubarsergey.stratosphericbaloon.ui.admin

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.github.gubarsergey.stratosphericbaloon.App
import io.github.gubarsergey.stratosphericbaloon.R
import io.github.gubarsergey.stratosphericbaloon.db.launches.LaunchesRepository
import io.github.gubarsergey.stratosphericbaloon.db.launches.enitity.Launch
import io.github.gubarsergey.stratosphericbaloon.db.launches.realm.LaunchMapper
import io.github.gubarsergey.stratosphericbaloon.extension.notNullContext
import io.github.gubarsergey.stratosphericbaloon.helper.ItemDecoration
import io.github.gubarsergey.stratosphericbaloon.ui.admin.addlaunch.AddLaunchActivity
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.fragment_admin.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.support.v4.intentFor

class AdminFragment : Fragment(), AnkoLogger {

    private val launchesRepository by lazy {
        LaunchesRepository(notNullContext, (activity?.application as App).getRetrofit(), LaunchMapper())
    }

    private val launches = mutableListOf<Launch>()

    private val disposable = CompositeDisposable()

    override fun onResume() {
        super.onResume()
        disposable.add(launchesRepository.getAllLaunches()
            .subscribe({ launches ->
                info("Got launches $launches")
                this.launches.addAll(launches)
                admin_launches_recycler.adapter!!.notifyDataSetChanged()
            }) {
                info("Failed loading launches $it")
            })
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_admin, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        admin_launches_recycler.layoutManager = LinearLayoutManager(notNullContext)
        admin_launches_recycler.adapter = AdminLaunchesAdapter(launches) { id ->
            disposable.add(
                launchesRepository
                    .removeLaunch(id)
                    .subscribe({ response ->
                        info("Remove launch $response")
                        val index = launches.indexOfFirst { it.id == id }
                        launches.removeAt(index)
                        admin_launches_recycler.adapter?.notifyItemRemoved(index)
                    }) {
                        info("Failed removing launch $it")
                    }
            )
        }
        admin_launches_recycler.addItemDecoration(ItemDecoration(resources.getDimensionPixelOffset(R.dimen.default_padding_small)))
        add_launch_fab.setOnClickListener {
            startActivity(intentFor<AddLaunchActivity>())
        }
    }

    override fun onPause() {
        super.onPause()
        disposable.dispose()
    }
}