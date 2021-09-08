package com.nomadev.aplikasigithubuser_submission2.ui.widgets

import android.content.Context
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.os.Binder
import android.widget.RemoteViews
import android.widget.RemoteViewsService
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.nomadev.aplikasigithubuser_submission2.R
import com.nomadev.aplikasigithubuser_submission2.domain.model.ItemsModel
import com.nomadev.aplikasigithubuser_submission2.utils.constant.DatabaseContract
import com.nomadev.aplikasigithubuser_submission2.utils.constant.MappingHelper

internal class StackRemoteViewsFactory(private val mContext: Context) :
    RemoteViewsService.RemoteViewsFactory {

    private var list: List<ItemsModel> = listOf()
    private var cursor: Cursor? = null

    override fun onCreate() {}

    override fun onDataSetChanged() {
        cursor?.close()
        list = listOf()

        val identityToken = Binder.clearCallingIdentity()

        cursor = mContext.contentResolver?.query(
            DatabaseContract.FavoriteUserColumns.CONTENT_URI,
            null,
            null,
            null,
            null
        )

        cursor?.let {
            val listConverted = MappingHelper.mapCursorToArrayList(it)
            list = listConverted
        }

        Binder.restoreCallingIdentity(identityToken)
    }

    override fun onDestroy() {
        cursor?.close()
        list = listOf()
    }

    override fun getCount(): Int = list.size

    override fun getViewAt(position: Int): RemoteViews {
        val rv = RemoteViews(mContext.packageName, R.layout.widget_item)

        if (!list.isNullOrEmpty()) {
            rv.apply {
                list[position].apply {
                    setImageViewBitmap(R.id.iv_widgets, avatarUrl.toBitmap(mContext))
                }
            }
        }
        return rv
    }

    override fun getLoadingView(): RemoteViews? = null

    override fun getViewTypeCount(): Int = 1

    override fun getItemId(position: Int): Long = 0

    override fun hasStableIds(): Boolean = true
}

private fun String.toBitmap(mContext: Context): Bitmap {
    var bitmap: Bitmap =
        BitmapFactory.decodeResource(mContext.resources, R.drawable.placeholder_profile_picture)

    val option = RequestOptions()
        .error(R.drawable.placeholder_profile_picture)
        .diskCacheStrategy(DiskCacheStrategy.ALL)

    try {
        Glide.with(mContext)
            .setDefaultRequestOptions(option)
            .asBitmap()
            .load(this)
            .into(object : CustomTarget<Bitmap>() {
                override fun onLoadCleared(placeholder: Drawable?) {}
                override fun onResourceReady(
                    resource: Bitmap,
                    transition: Transition<in Bitmap>?,
                ) {
                    bitmap = resource
                }
            })
    } catch (e: Exception) {
        e.printStackTrace()
    }

    return bitmap
}
