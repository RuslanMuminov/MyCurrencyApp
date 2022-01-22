package com.example.mycurrencyapp.fragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.mycurrencyapp.R
import com.example.mycurrencyapp.databinding.FragmentConvertBinding
import com.example.mycurrencyapp.model.CurrencyModel

class ConvertFragment : Fragment() {

    private lateinit var binding: FragmentConvertBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentConvertBinding.inflate(layoutInflater, container, false)

        binding.back.setOnClickListener {
            findNavController().navigate(R.id.homeFragment)
        }

        val currency = arguments?.getSerializable("it") as CurrencyModel
        var sum = 0.0
        var current = 0.0

        val textWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                val etValue = binding.currencyEdit.text.toString()

                if(etValue.isNotEmpty()) {
                    sum = etValue.toDouble() * currency.Rate.toDouble()
                    current = (etValue.toDouble() / currency.Rate.toDouble())

                    binding.currentSum.text = "$sum so'm"
                    binding.currentSumma.text = current.toString() + " ${currency.CcyNm_UZ}"
                }
                if(etValue.isEmpty()){
                    binding.currentSum.text =""
                    binding.currentSumma.text = ""
                }
            }
        }
        binding.currencyEdit.addTextChangedListener(textWatcher)
        return binding.root
    }
}