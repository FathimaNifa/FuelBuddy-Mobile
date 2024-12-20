package com.nifa.fuel_buddy.core.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import coil.compose.rememberAsyncImagePainter
import coil.request.CachePolicy
import coil.request.ImageRequest
import coil.size.Scale
import com.nifa.fuel_buddy.R

@Composable
fun imagePainter(data: String) =
    if (LocalInspectionMode.current)
        painterResource(id = R.drawable.petrol)
    else
        rememberAsyncImagePainter(
            model = ImageRequest.Builder(LocalContext.current)
                .data(data)
                .memoryCachePolicy(CachePolicy.ENABLED)
                .memoryCacheKey(data)
                .diskCachePolicy(CachePolicy.ENABLED)
                .diskCacheKey(data)
                .networkCachePolicy(CachePolicy.ENABLED)
                .placeholderMemoryCacheKey(data)
                .crossfade(true)
                .scale(Scale.FIT)
                .build(),
            contentScale = ContentScale.FillBounds,
            filterQuality = FilterQuality.None
        )