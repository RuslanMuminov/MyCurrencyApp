package com.example.mycurrencyapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mycurrencyapp.databinding.ItemListBinding
import com.example.mycurrencyapp.model.CurrencyModel
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import java.nio.charset.Charset

class CurrencyAdapter(val context: Context, var list: List<CurrencyModel>, val onClick: (currency: CurrencyModel, pos: Int) -> Unit): RecyclerView.Adapter<CurrencyAdapter.VH>() {

    inner class VH(private var binding: ItemListBinding):RecyclerView.ViewHolder(binding.root){

        var images: ArrayList<String> =  arrayListOf()
        val imageUrl = binding.image
        fun getJSONFromAssets(): String? {

            var json: String? = null
            val charset: Charset = Charsets.UTF_8
            try {
                val myUsersJSONFile = context.assets.open("Photos.json")
                val size = myUsersJSONFile.available()
                val buffer = ByteArray(size)
                myUsersJSONFile.read(buffer)
                myUsersJSONFile.close()
                json = String(buffer, charset)

            } catch (ex: IOException) {
                ex.printStackTrace()
                return null
            }
            return json
        }

        fun onBind(currencyModel: CurrencyModel, position: Int) {

            binding.USD.text = currencyModel.Ccy
            binding.USDDollar.text = currencyModel.CcyNm_UZ
            binding.date.text = currencyModel.Date
            binding.rate.text = currencyModel.Rate
            Glide.with(context).load(currencyModel.image).into(binding.image)

            try {

                val obj = JSONObject(getJSONFromAssets()!!)


                val citiesArray = obj.getJSONArray("photos")
                for (i in 0 until citiesArray.length()) {

                    val city = citiesArray.getJSONObject(i)
                    images.add(city.getString("url"))

                }
            } catch (e: JSONException) {
                e.printStackTrace()
            }
            Glide.with(context).load(images[position]).into(imageUrl)

            itemView.setOnClickListener {
                onClick(currencyModel, position)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(ItemListBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.onBind(list[position], position)

    }

    override fun getItemCount(): Int = list.size


}