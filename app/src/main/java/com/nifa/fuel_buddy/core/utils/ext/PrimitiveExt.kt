package com.nifa.fuel_buddy.core.utils.ext

fun String.prependRupees(): String = "₹ $this"

fun String?.nullAsEmpty(): String = this ?: ""