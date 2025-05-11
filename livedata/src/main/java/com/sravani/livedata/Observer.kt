package com.sravani.livedata;

fun interface Observer<T> {

    /**
     * Called when the data is changed is changed to [value].
     */
    fun onChanged(value: T)
}
