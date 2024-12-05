package com.nifa.fuel_buddy.core.utils.ext

fun String.prependRupees(): String = "₹ $this"

fun String.prependHashTag(): String = "# $this"

fun String.appendAwayFrom(): String = "$this km away"

fun String?.nullAsEmpty(): String = this ?: ""