package com.samwrotethecode.marspics.utils

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import androidx.core.net.toUri

fun openImgIntent(context: Context, url: String, onError: (Exception) -> Unit = {}) {
    val uri = url.toUri()

    val intent = Intent(Intent.ACTION_VIEW).apply {
        setDataAndType(uri, "image/*")
    }

    try {
        context.startActivity(intent)
    } catch (e: ActivityNotFoundException) {
        onError(e)
    }
}