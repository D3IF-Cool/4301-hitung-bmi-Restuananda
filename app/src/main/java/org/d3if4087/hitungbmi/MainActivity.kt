package org.d3if4087.hitungbmi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import org.d3if4087.hitungbmi.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSave.setOnClickListener { HitungBmi() }
    }
    private fun HitungBmi(){
//        Log.d("MainActivity", "Tombol Diklik")
        val berat = binding.editBeratBadan.text.toString()
        if (TextUtils.isEmpty(berat)){
            Toast.makeText(this, R.string.berat_Invalid, Toast.LENGTH_LONG).show()
            return
        }

        val tinggi = binding.editTinggiBadan.text.toString()
        if (TextUtils.isEmpty(tinggi)){
            Toast.makeText(this, R.string.tinggi_Invalid, Toast.LENGTH_LONG).show()
            return
        }
        val tinggiCM = tinggi.toFloat() / 100

        val pilihId = binding.RadioGroup.checkedRadioButtonId
        if (pilihId == -1){
            Toast.makeText(this, R.string.gender_Invalid, Toast.LENGTH_LONG).show()
            return
        }
        val pria = pilihId == R.id.radio_pria
        val bmi = berat.toFloat() / (tinggiCM * tinggiCM)
        val kategori = getKategori(bmi, pria)

        binding.txtResultBmi.text = getString(R.string.bmi_x, bmi)
        binding.txtCategory.text = getString(R.string.kategori_x, kategori)
    }

    private fun getKategori(bmi: Float, isMale: Boolean): String{
        val stringRes = if (isMale){
            when {
                bmi < 20.5 -> R.string.kurus
                bmi >= 27.0 -> R.string.gemuk
                else -> R.string.ideal
            }
        }else {
            when {
                bmi < 18.5 -> R.string.kurus
                bmi > 25.0 -> R.string.gemuk
                else -> R.string.ideal
            }
        }
        return getString(stringRes)
    }

}