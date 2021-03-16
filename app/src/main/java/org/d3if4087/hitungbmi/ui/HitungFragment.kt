package org.d3if4087.hitungbmi.ui

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import org.d3if4087.hitungbmi.R
import org.d3if4087.hitungbmi.databinding.FragmentHitungBinding

class HitungFragment : Fragment() {
    private lateinit var binding: FragmentHitungBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHitungBinding.inflate(layoutInflater, container, false)
        binding.btnSave.setOnClickListener { HitungBmi() }
        return binding.root
    }

    private fun HitungBmi(){

        val berat = binding.editBeratBadan.text.toString()
        if (TextUtils.isEmpty(berat)){
            Toast.makeText(context, R.string.berat_Invalid, Toast.LENGTH_LONG).show()
            return
        }

        val tinggi = binding.editTinggiBadan.text.toString()
        if (TextUtils.isEmpty(tinggi)){
            Toast.makeText(context, R.string.tinggi_Invalid, Toast.LENGTH_LONG).show()
            return
        }
        val tinggiCM = tinggi.toFloat() / 100

        val pilihId = binding.RadioGroup.checkedRadioButtonId
        if (pilihId == -1){
            Toast.makeText(context, R.string.gender_Invalid, Toast.LENGTH_LONG).show()
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