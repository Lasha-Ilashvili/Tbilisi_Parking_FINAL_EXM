package com.example.tbilisi_parking_final_exm.presentation.extension

import android.graphics.drawable.Drawable
import androidx.core.graphics.drawable.toBitmap
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory



fun Drawable?.toBitmapDescriptor(): BitmapDescriptor {
    if (this == null) {
        return BitmapDescriptorFactory.defaultMarker()
    }

    return BitmapDescriptorFactory.fromBitmap(toBitmap())
}