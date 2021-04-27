package org.d3if4087.hitungbmi.ui.hitung

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import org.d3if4087.hitungbmi.R
import org.d3if4087.hitungbmi.data.KategoriBmi
import org.d3if4087.hitungbmi.databinding.FragmentHitungBinding

class HitungFragment : Fragment() {
    private val vieModel: HitungViewModel by viewModels()
    private lateinit var binding: FragmentHitungBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHitungBinding.inflate(layoutInflater, container, false)
        binding.btnSave.setOnClickListener { HitungBmi() }
        binding.btnShare.setOnClickListener { shareData() }
        binding.btnSaran.setOnClickListener { vieModel.mulaiNavigasi() }
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        vieModel.getNavigasi().observe(viewLifecycleOwner, {
            if (it == null) return@observe
            findNavController().navigate(
                HitungFragmentDirections
                    .actionHitungFragmentToSaranFragment(it)
            )
            vieModel.selesaiNavigasi()
        })
        vieModel.getHasilBmi().observe(viewLifecycleOwner, {
            if (it == null) return@observe
            binding.txtResultBmi.text = getString(R.string.bmi_x, it.bmi)
            binding.txtCategory.text = getString(R.string.kategori_x,getKategori(it.kategori))
            binding.btnGroup.visibility = View.VISIBLE
        })
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


        val pilihId = binding.RadioGroup.checkedRadioButtonId
        if (pilihId == -1){
            Toast.makeText(context, R.string.gender_Invalid, Toast.LENGTH_LONG).show()
            return
        }
        val isMale = pilihId == R.id.radio_pria

        vieModel.hitungBmi(berat, tinggi, isMale)
    }

    private fun getKategori(kategori: KategoriBmi): String{

        val stringRes = when (kategori){
            KategoriBmi.KURUS -> R.string.kurus
            KategoriBmi.IDEAL -> R.string.ideal
            KategoriBmi.GEMUK -> R.string.gemuk
        }
        return getString(stringRes)
    }
    private fun shareData(){
        val selectedId = binding.RadioGroup.checkedRadioButtonId
        val gender = if (selectedId == R.id.radio_pria)
            getString(R.string.pria)
        else
            getString(R.string.wanita)

        val message = getString(R.string.bagikan_template,
        binding.editBeratBadan.text,
        binding.editTinggiBadan.text,gender,
        binding.txtResultBmi.text,
        binding.txtCategory.text)

        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.setType("text/plain").putExtra(Intent.EXTRA_TEXT, message)
        if (shareIntent.resolveActivity(
                requireActivity().packageManager) != null ){
            startActivity(shareIntent)
        }
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