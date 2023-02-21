package com.example.moviesearch

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.moviesearch.databinding.ActivityRecentBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RecentActivity : AppCompatActivity() {
    lateinit var searchDao: SearchDAO

    private val viewModel by viewModels<MyViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityRecentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        searchDao = SearchDatabase.getDatabase(this).getSearchDao()

        var recentSearch = ""
        var check = 0
        var first = ""

        CoroutineScope(Dispatchers.IO).launch {
            val allSearch = searchDao.getAll()

            for(i in allSearch){

                if (check == 0) first = i.movieName
                check++
            }
            //searchDao.delete(Search(i.movieName))
            //recentSearch += "${i.movieName}\n"
            check = when(check){
                10 -> {
                    searchDao.delete(Search(first))
                    0
                }
                else -> 0
            }

            for(j in allSearch){
                when(check){
                    0 -> binding.recentTextView.text = j.movieName
                    1 -> binding.recentTextView2.text = j.movieName
                    2 -> binding.recentTextView3.text = j.movieName
                    3 -> binding.recentTextView4.text = j.movieName
                    4 -> binding.recentTextView5.text = j.movieName
                    5 -> binding.recentTextView6.text = j.movieName
                    6 -> binding.recentTextView7.text = j.movieName
                    7 -> binding.recentTextView8.text = j.movieName
                    8 -> binding.recentTextView9.text = j.movieName
                    9 -> binding.recentTextView10.text = j.movieName
                }
                check++
            }
        }

        binding.recentTextView.setOnClickListener {
            viewModel.setCheck(1)
            viewModel.setMovieName(binding.recentTextView.text.toString())

            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
        }

        binding.recentTextView2.setOnClickListener {
            viewModel.setCheck(1)
            viewModel.setMovieName(binding.recentTextView2.text.toString())

            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
        }

        binding.recentTextView3.setOnClickListener {
            viewModel.setCheck(1)
            viewModel.setMovieName(binding.recentTextView3.text.toString())

            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
        }

        binding.recentTextView4.setOnClickListener {
            viewModel.setCheck(1)
            viewModel.setMovieName(binding.recentTextView4.text.toString())

            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
        }

        binding.recentTextView5.setOnClickListener {
            viewModel.setCheck(1)
            viewModel.setMovieName(binding.recentTextView5.text.toString())

            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
        }

        binding.recentTextView6.setOnClickListener {
            viewModel.setCheck(1)
            viewModel.setMovieName(binding.recentTextView6.text.toString())

            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
        }

        binding.recentTextView7.setOnClickListener {
            viewModel.setCheck(1)
            viewModel.setMovieName(binding.recentTextView7.text.toString())

            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
        }

        binding.recentTextView8.setOnClickListener {
            viewModel.setCheck(1)
            viewModel.setMovieName(binding.recentTextView8.text.toString())

            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
        }

        binding.recentTextView9.setOnClickListener {
            viewModel.setCheck(1)
            viewModel.setMovieName(binding.recentTextView9.text.toString())

            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
        }

        binding.recentTextView10.setOnClickListener {
            viewModel.setCheck(1)
            viewModel.setMovieName(binding.recentTextView10.text.toString())

            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
        }
    }
}