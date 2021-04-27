package org.d3if4087.hitungbmi.ui.history

import androidx.lifecycle.ViewModel
import org.d3if4087.hitungbmi.db.BmiDao

class HistoryViewModel(db: BmiDao) : ViewModel(){
    val data = db.getLastBmi()
}