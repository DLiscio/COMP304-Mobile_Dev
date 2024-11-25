package com.damien.damienliscio_comp304lab3_ex1.data

interface SavedLocationRepository {
    fun getSavedLocations(): List<SavedLocation>
    fun addSavedLocation(savedLocation: SavedLocation)
}