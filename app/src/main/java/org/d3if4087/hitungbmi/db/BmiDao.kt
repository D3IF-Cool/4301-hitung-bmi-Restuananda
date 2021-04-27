package org.d3if4087.hitungbmi.db

import androidx.lifecycle.LiveData
import androidx.room.Insert
import androidx.room.Query

interface BmiDao {

    @Insert
    fun insert(bmi: BmiEntity)

    @Query("SELECT * FROM bmi ORDER BY ID DESC LIMIT 1")
    fun getLastBmi(): LiveData<BmiEntity?>
}