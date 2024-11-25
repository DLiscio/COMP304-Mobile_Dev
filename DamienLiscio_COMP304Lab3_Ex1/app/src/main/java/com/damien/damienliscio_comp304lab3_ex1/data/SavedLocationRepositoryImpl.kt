package com.damien.damienliscio_comp304lab3_ex1.data

class SavedLocationRepositoryImpl : SavedLocationRepository {
    private val savedLocations = mutableListOf<SavedLocation>()

    override fun getSavedLocations(): List<SavedLocation> {
        return savedLocations.toList()
    }

    override fun addSavedLocation(savedLocation: SavedLocation) {
        savedLocations.add(savedLocation)
    }
}