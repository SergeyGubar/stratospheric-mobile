package io.github.gubarsergey.stratosphericbaloon.ui.photo

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.ListPreloader
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.integration.recyclerview.RecyclerViewPreloader
import com.bumptech.glide.util.FixedPreloadSizeProvider
import io.github.gubarsergey.stratosphericbaloon.App
import io.github.gubarsergey.stratosphericbaloon.R
import io.github.gubarsergey.stratosphericbaloon.extension.notNullContext
import io.github.gubarsergey.stratosphericbaloon.helper.SharedPrefHelper
import io.github.gubarsergey.stratosphericbaloon.db.photo.PhotosRepository
import io.github.gubarsergey.stratosphericbaloon.helper.GlideApp
import io.github.gubarsergey.stratosphericbaloon.helper.IMAGE_HEIGHT
import io.github.gubarsergey.stratosphericbaloon.helper.IMAGE_WIDTH
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.fragment_photos.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.support.v4.toast
import java.util.*

class PhotosFragment : Fragment(), AnkoLogger {

    private val disposable = CompositeDisposable()

    private val photosRepository by lazy {
        PhotosRepository(notNullContext, (this@PhotosFragment.activity?.application as App).getRetrofit())
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_photos, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        disposable.add(photosRepository
            .getUserPhotos("Bearer ${SharedPrefHelper.getToken(notNullContext)}")
            .doOnSubscribe {
                toast(getString(R.string.downloading))
            }
            .doOnError {
                toast(getString(R.string.error_network_generic))
            }
            .subscribe({ urlsToIds ->
                info("Got photos urlsToIds $urlsToIds")
                setupPhotos(urlsToIds.map { it.first }.toMutableList(),
                    urlsToIds.map { it.second }.toMutableList())
            }) { ex ->
                info("Fail $ex")
            })

    }

    private fun setupPhotos(urls: MutableList<String>, ids: MutableList<String>) {
        val preloadSizeProvider = FixedPreloadSizeProvider<String>(IMAGE_WIDTH, IMAGE_HEIGHT)
        val modelProvider = object : ListPreloader.PreloadModelProvider<String> {
            override fun getPreloadItems(position: Int): MutableList<String> {
                val url = urls[position]
                if (TextUtils.isEmpty(url)) {
                    return Collections.emptyList()
                }
                return Collections.singletonList(url)
            }

            override fun getPreloadRequestBuilder(url: String): RequestBuilder<*>? {
                return GlideApp.with(this@PhotosFragment)
                    .load(url)
                    .override(IMAGE_WIDTH, IMAGE_HEIGHT)
            }
        }
        val preloader = RecyclerViewPreloader<String>(
            Glide.with(this),
            modelProvider,
            preloadSizeProvider,
            10
        )

        photos_recycler.addOnScrollListener(preloader)
        photos_recycler.layoutManager = LinearLayoutManager(notNullContext)
        photos_recycler.adapter = PhotosAdapter(this, urls, ids) { id ->
            photosRepository.removePhoto(id)
                .subscribe({ result  ->
                    val index = ids.indexOf(id)
                    urls.removeAt(index)
                    ids.removeAt(index)
                    photos_recycler.adapter?.notifyItemRemoved(index)
                    info("Remove photo $result")
                }) { ex ->
                    info("Fail $ex")
                }
        }

    }

    override fun onStop() {
        super.onStop()
        disposable.dispose()
    }
}