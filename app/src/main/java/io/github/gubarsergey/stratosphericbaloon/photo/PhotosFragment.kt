package io.github.gubarsergey.stratosphericbaloon.photo

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
import io.github.gubarsergey.stratosphericbaloon.constant.BASE_URL
import io.github.gubarsergey.stratosphericbaloon.constant.IMAGE_BASE_URL
import io.github.gubarsergey.stratosphericbaloon.constant.IMAGE_HEIGHT
import io.github.gubarsergey.stratosphericbaloon.constant.IMAGE_WIDTH
import io.github.gubarsergey.stratosphericbaloon.extension.notNullContext
import io.github.gubarsergey.stratosphericbaloon.glide.GlideApp
import io.github.gubarsergey.stratosphericbaloon.helper.SharedPrefHelper
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_photos.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.support.v4.toast
import java.util.*

class PhotosFragment : Fragment(), AnkoLogger {

    private lateinit var disposable: Disposable

    private val photosRepository by lazy {
        PhotosRepository((this@PhotosFragment.activity?.application as App).getRetrofit())
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_photos, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        disposable = photosRepository.getUserPhotos("Bearer ${SharedPrefHelper.getToken(notNullContext)}")
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .doOnSubscribe {
                toast(getString(R.string.downloading_photos))
            }
            .doOnError {
                toast(getString(R.string.error_failed_loading_photos))
            }
            .subscribe({ ids ->
                info("Got photos ids $ids")
                setupPhotos(ids)
            }) { ex ->
                info("Fail $ex")
            }

    }

    private fun setupPhotos(ids: List<String>) {
        val preloadSizeProvider = FixedPreloadSizeProvider<String>(IMAGE_WIDTH, IMAGE_HEIGHT)
        val modelProvider = object : ListPreloader.PreloadModelProvider<String> {
            override fun getPreloadItems(position: Int): MutableList<String> {
                val url = ids[position]
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
        photos_recycler.adapter = PhotosAdapter(this, ids)

    }

    override fun onStop() {
        super.onStop()
        disposable.dispose()
    }
}