package com.rmf.infiniteloadmore

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.children
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONArray
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory

class MainActivity : AppCompatActivity() {
    lateinit var list : ArrayList<MainData>
    lateinit var adapter : MainAdapter
    var page=1
    var limit=10

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        list = ArrayList()
//        adapter = MainAdapter(list)

        rv.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.VERTICAL,
            false)
//        rv.adapter = adapter

        getData(page,limit)

        scroll_view.setOnScrollChangeListener(object : NestedScrollView.OnScrollChangeListener {
            override fun onScrollChange(
                v: NestedScrollView?,
                scrollX: Int,
                scrollY: Int,
                oldScrollX: Int,
                oldScrollY: Int
            ) {
               if(scrollY== v?.getChildAt(0)?.measuredHeight?.minus(v?.measuredHeight) ){

                   page++
                   progress_bar.visibility=View.VISIBLE
                   getData(page,limit)
               }
            }

        })
    }

    private fun getData(page: Int, limit: Int) {

        var retrofit = Retrofit.Builder()
            .baseUrl("https://picsum.photos/")
            .addConverterFactory(ScalarsConverterFactory.create())
            .build()

        var mainInterface = retrofit.create(MainInterface::class.java)

        var call = mainInterface.stringCall(page,limit)

        call.enqueue(object : Callback<String>{
            override fun onFailure(call: Call<String>, t: Throwable) {
                Toast.makeText(this@MainActivity,"Gagal Mengambil Data",Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<String>, response: Response<String>) {
                if (response.isSuccessful && response.body()!=null){
                    progress_bar.visibility = View.GONE
                    var jsonArray = JSONArray(response.body())
                    parseResult(jsonArray)

                }
            }

        })
    }
    fun parseResult(jsonArray: JSONArray){
        for(i in 0 until jsonArray.length()){
            var oi  = jsonArray.getJSONObject(i)
            var data = MainData(oi.getString("download_url"),oi.getString("author"))
            list.add(data)
        }

        adapter = MainAdapter(list)
        rv.adapter=adapter
    }
}