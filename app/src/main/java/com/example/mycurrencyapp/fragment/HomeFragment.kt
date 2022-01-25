package com.example.mycurrencyapp.fragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.mycurrencyapp.R
import com.example.mycurrencyapp.adapter.CurrencyAdapter
import com.example.mycurrencyapp.databinding.FragmentHomeBinding
import com.example.mycurrencyapp.model.CurrencyModel
import com.example.mycurrencyapp.networking.CurrencyRetrofit
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : Fragment(){

    private lateinit var binding: FragmentHomeBinding
    private lateinit var currencyList: ArrayList<CurrencyModel>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)

        binding.refresh.isRefreshing = false

        load()

        binding.searchView.addTextChangedListener(textWatcher)

        return binding.root
    }

    private val textWatcher = object : TextWatcher{
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

        }

        override fun afterTextChanged(s: Editable?) {

            val result = arrayListOf<CurrencyModel>()

            currencyList.forEach {
                if(it.CcyNm_UZ.toUpperCase()?.contains(s.toString().toUpperCase())
                    || it.Ccy.toUpperCase()?.contains(s.toString().toUpperCase()))
                {
                    result.add(it)
                }
            }
            binding.rv.adapter = CurrencyAdapter(requireContext(), result) {
                it, pos ->
            }
        }
    }

    private fun load(){

        val api = CurrencyRetrofit.api

        api.getAllCurrency().enqueue(object : Callback<List<CurrencyModel>>{
            override fun onResponse(
                call: Call<List<CurrencyModel>>,
                response: Response<List<CurrencyModel>>
            ) {
                currencyList = response.body() as ArrayList<CurrencyModel>
                if (response.isSuccessful){
                    binding.rv.adapter = CurrencyAdapter(requireContext(), currencyList){
                            it, pos ->

                        val bundle = Bundle()
                        bundle.putSerializable("it", it)
                        findNavController().navigate(R.id.convertFragment, bundle)
                    }
                }
            }

            override fun onFailure(call: Call<List<CurrencyModel>>, t: Throwable) {
                Toast.makeText(requireContext(), "Error !!!", Toast.LENGTH_SHORT).show()
            }
        })
    }
}