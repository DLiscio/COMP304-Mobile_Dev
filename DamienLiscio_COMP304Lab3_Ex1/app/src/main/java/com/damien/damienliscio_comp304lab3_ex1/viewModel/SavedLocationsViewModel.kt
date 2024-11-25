package com.damien.damienliscio_comp304lab3_ex1.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.damien.damienliscio_comp304lab3_ex1.data.SavedLocation
import com.damien.damienliscio_comp304lab3_ex1.data.SavedLocationRepository

class SavedLocationsViewModel(
    private val savedLocationRepository: SavedLocationRepository
): ViewModel() {
    private val _savedLocations = MutableLiveData<List<SavedLocation>>(emptyList())
    val savedLocations: LiveData<List<SavedLocation>> get() = _savedLocations

    init{
        getSavedLocations()
    }

    fun getSavedLocations() {
        _savedLocations.value = savedLocationRepository.getSavedLocations()
    }

    fun addSavedLocation(savedLocation: SavedLocation) {
        val updatedList = _savedLocations.value.orEmpty().toMutableList().apply {
            add(savedLocation)
        }
        _savedLocations.value = updatedList
    }
}