package org.d3if4087.hitungbmi.ui

import android.os.Bundle
import android.text.TextUtils
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import org.d3if4087.hitungbmi.R
import org.d3if4087.hitungbmi.data.KategoriBmi
import org.d3if4087.hitungbmi.databinding.FragmentHitungBinding

class HitungFragment : Fragment() {
    private lateinit var binding: FragmentHitungBinding
    private lateinit var kategoriBmi: KategoriBmi

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHitungBinding.inflate(layoutInflater, container, false)
        binding.btnSave.setOnClickListener { HitungBmi() }
        binding.btnSaran.setOnClickListener { view: View ->
            view.findNavController().navigate(HitungFragmentDirections
                .actionHitungFragmentToSaranFragment(kategoriBmi))
        }
        setHasOptionsMenu(true)
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
        binding.btnSaran.visibility = View.VISIBLE
    }

    private fun getKategori(bmi: Float, isMale: Boolean): String{
        kategoriBmi = if(isMale) {
            when {
                bmi < 20.5 -> KategoriBmi.KURUS
                bmi >= 27.0 -> KategoriBmi.GEMUK
                else -> KategoriBmi.IDEAL
            }
        }else {
            when {
                bmi < 18.5 -> KategoriBmi.KURUS
                bmi > 25.0 -> KategoriBmi.GEMUK
                else -> KategoriBmi.IDEAL
            }
        }
        val stringRes = when (kategoriBmi){
            KategoriBmi.KURUS -> R.string.kurus
            KategoriBmi.IDEAL -> R.string.ideal
            KategoriBmi.GEMUK -> R.string.gemuk
        }
        return getString(stringRes)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.option_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_about){
            findNavController().navigate(
                R.id.action_hitungFragment_to_aboutFragment)
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}