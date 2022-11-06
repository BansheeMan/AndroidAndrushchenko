package com.example.a14_dialogs.entities

class AvailableVolumeValues(
    val values: List<Int>,
    val currentIndex: Int
) {
    companion object {
        fun createVolumeValues(currentValues: Int) : AvailableVolumeValues {
            val values = (0..100 step 10)
            val currentIndex = values.indexOf(currentValues)
            return if (currentIndex == -1) {
                val list = values + currentValues
                AvailableVolumeValues(list, list.lastIndex)
            } else {
                AvailableVolumeValues(values.toList(), currentIndex)
            }
        }
    }
}