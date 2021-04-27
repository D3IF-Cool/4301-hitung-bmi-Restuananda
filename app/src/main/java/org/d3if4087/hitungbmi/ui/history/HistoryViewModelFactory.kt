package org.d3if4087.hitungbmi.ui.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import org.d3if4087.hitungbmi.db.BmiDao

class HistoryViewModelFactory(
    private val db: BmiDao
): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HistoryViewModel::class.java)){
            return HistoryViewModel(db) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}