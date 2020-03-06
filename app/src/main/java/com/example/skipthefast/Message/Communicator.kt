package com.example.skipthefast.Message

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class Communicator : ViewModel() {
    var chain = MutableLiveData<String>()
    var emotion = MutableLiveData<String>()
    var item = MutableLiveData<String>()
    var price = MutableLiveData<String>()
    var category = MutableLiveData<String>()

    fun setChainCommunicator(msg: String) {
        chain.value = msg
    }
    fun setCategoryCommunicator(msg: String) {
        category.value = msg
    }
    fun setEmotionCommunicator(msg: String) {
        emotion.value = msg
    }
    fun setItemCommunicator(msg: String) {
        item.value = msg
    }
    fun setPriceCommunicator(msg: String) {
        price.value = msg
    }
}