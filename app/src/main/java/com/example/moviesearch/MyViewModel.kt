package com.example.moviesearch

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

data class Item(val title: String, val link: String, val image: String,  val pubDate: String, val userRating: String)

private var activityChangeCheck = -1
private var activityChangeMovieName = ""

class MyViewModel : ViewModel() {
    val itemsListData = MutableLiveData<ArrayList<Item>>()
    private val items = ArrayList<Item>()

    fun getItem(pos: Int) =  items[pos]

    val itemsSize
        get() = items.size

    fun addItem(item: Item) {
        items.add(item)
        itemsListData.postValue(items) // 메인스레드가 아닌 다른 스레드에서 setvalue 사용하면 죽음
    }

    fun deleteItem(pos: Int) {
        items.removeAt(pos)
        itemsListData.value = items
    }

    fun setCheck(check: Int) {
        activityChangeCheck = check
    }

    fun getCheck(): Int {
        return activityChangeCheck
    }

    fun setMovieName(movie: String) {
        activityChangeMovieName = movie
    }

    fun getMovieName(): String {
        return activityChangeMovieName
    }


}