package com.example.skipthefast.Message

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class Communicator : ViewModel() {
    var chain = MutableLiveData<String>()
    var emotion = MutableLiveData<String>()

    fun setChainCommunicator(msg: String) {
        chain.value = msg
    }

    fun setEmotionCommunicator(msg: String) {
        emotion.value = msg
    }
}