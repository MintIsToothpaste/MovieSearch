package com.example.moviesearch

import android.app.Activity
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.InputMethodManager
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moviesearch.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.*
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header
import java.io.IOException
import java.net.URL
import java.net.URLEncoder

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var searchDao: SearchDAO

    private val viewModel by viewModels<MyViewModel>()

    val clientID = "S98hy_1P5a7uibjex2I0"
    val clientSecret = "9vm8pNa1tc"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        searchDao = SearchDatabase.getDatabase(this).getSearchDao()

        //최근검색화면에서 영화검색어 클릭해서 들어온경우
        if(viewModel.getCheck() == 1){
            binding.movieNameEditText.setText(viewModel.getMovieName())

            naverApi()

            binding.recyclerView.adapter = MovieAdapter(viewModel)
            binding.recyclerView.layoutManager = LinearLayoutManager(this)
            binding.recyclerView.setHasFixedSize(true)

            viewModel.setCheck(-1)
        }

        // 검색 버튼
        binding.search.setOnClickListener {

            if (binding.movieNameEditText.text.isEmpty()){
                return@setOnClickListener
            }

            //최근검색어 저장
            CoroutineScope(Dispatchers.IO).launch {
                with(searchDao) {
                    insert(Search(binding.movieNameEditText.text.toString()))
                }
            }

            naverApi()
            //retrofitWithCoroutine()

            binding.recyclerView.adapter = MovieAdapter(viewModel)
            binding.recyclerView.layoutManager = LinearLayoutManager(this)
            binding.recyclerView.setHasFixedSize(true)

            //val imm = this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            //imm.hideSoftInputFromWindow(binding.movieNameEditText.windowToken, 0)
            //binding.movieNameEditText.setText("")

        }

        // 최근 검색 버튼
        binding.recent.setOnClickListener {

            startActivity(
                Intent(this, RecentActivity::class.java)
            )
        }

    }

    private fun retrofitWithCoroutine() {
        val encodeText = URLEncoder.encode("${binding.movieNameEditText.text}", "UTF-8")

        val retrofit = Retrofit.Builder()
            .baseUrl("https://openapi.naver.com/v1/search/movie.json?query=${encodeText}&display=10&start=1&genre=")
            .addConverterFactory(ScalarsConverterFactory.create())
            .build()

        val api = retrofit.create(RestApi::class.java)
        var result : String

        CoroutineScope(Dispatchers.IO).launch {
            try {
                result = api.getRoot2(clientID, clientSecret)
            } catch (e: Exception) {
                result = "Failed to connect naverAPI"
            }
            withContext(Dispatchers.Main) {
                //outputBy.text = "Retrofit with Coroutine"
                //output.text = result
                //viewModel.addItem(Item(result, result, result))
                //println("Success to execute request : $result")
            }
        }
    }

    interface RestApi {
        @GET("/")
        fun getRoot(
            @Header("X-Naver-Client-Id") clientId : String,
            @Header("X-Naver-Client-Secret") clientSecret : String
        ): Call<String>

        @GET("/")
        suspend fun getRoot2(
            @Header("X-Naver-Client-Id") clientId : String,
            @Header("X-Naver-Client-Secret") clientSecret : String
        ): String

    }

    fun naverApi() {
        val text = URLEncoder.encode("${binding.movieNameEditText.text}", "UTF-8")
        //println(text)
        val url = URL("https://openapi.naver.com/v1/search/movie.json?query=${text}&display=10&start=1&genre=")
        val request = Request.Builder()
            .url(url)
            .addHeader("X-Naver-Client-Id", clientID)
            .addHeader("X-Naver-Client-Secret", clientSecret)
            .method("GET", null)
            .build()

        val client = OkHttpClient()
        client.newCall(request).enqueue(object : Callback {
            override fun onResponse(call: okhttp3.Call, response: Response) {
                val body = response.body()?.string()
                val items = body?.let { JSONObject(it) }
                val itemsArray = items?.optJSONArray("items")

                var i = 0

                if (itemsArray != null) {
                    while (i < itemsArray.length()){
                        val itemsObject = itemsArray.getJSONObject(i)

                        val title = itemsObject.getString("title")
                        val link = itemsObject.getString("link")
                        val image = itemsObject.getString("image")
                        val pubDate = itemsObject.getString("pubDate")
                        val userRating = itemsObject.getString("userRating")

                        println("SUCCESS $title $link $image $pubDate $userRating")

                        i++
                        viewModel.addItem(Item(title, link, image, pubDate, userRating))
                    }
                }

            }
            override fun onFailure(call: okhttp3.Call, e: IOException) {
                println("Failed to execute request")
            }
        })
    }

}





















