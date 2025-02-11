package com.example.helper_for_conf.utils

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.helper_for_conf.R

object ImageUtils {

    /**
     * Загружает изображение по URL и отображает его в ImageView.
     *
     * @param context Контекст (например, Activity или Fragment).
     * @param imageUrl URL изображения.
     * @param imageView ImageView, в которое нужно загрузить изображение.
     * @param placeholderResId Ресурс для placeholder (заглушки), пока изображение загружается.
     * @param errorResId Ресурс для отображения в случае ошибки загрузки.
     */
    fun loadImage(
        context: Context,
        imageUrl: String?,
        imageView: ImageView,
        placeholderResId: Int = R.drawable.placeholder_image,
        errorResId: Int = R.drawable.error_image
    ) {
        Glide.with(context)
            .load(imageUrl)
            .apply(
                RequestOptions()
                    .placeholder(placeholderResId) // Заглушка
                    .error(errorResId) // Изображение при ошибке
                    .diskCacheStrategy(DiskCacheStrategy.ALL) // Кэширование
            )
            .into(imageView)
    }

    /**
     * Загружает изображение из ресурсов и отображает его в ImageView.
     *
     * @param context Контекст (например, Activity или Fragment).
     * @param imageResId Ресурс изображения.
     * @param imageView ImageView, в которое нужно загрузить изображение.
     */
    fun loadImageFromResources(
        context: Context,
        imageResId: Int,
        imageView: ImageView
    ) {
        Glide.with(context)
            .load(imageResId)
            .into(imageView)
    }

    /**
     * Очищает кэш Glide.
     *
     * @param context Контекст (например, Activity или Fragment).
     */
    fun clearCache(context: Context) {
        Glide.get(context).clearMemory() // Очистка памяти
        Thread {
            Glide.get(context).clearDiskCache() // Очистка дискового кэша
        }.start()
    }
}