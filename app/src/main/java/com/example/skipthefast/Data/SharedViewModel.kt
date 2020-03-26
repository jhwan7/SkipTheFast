package com.example.skipthefast.Data

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SharedViewModel {
    private var frequencyGoal = 0
    private var costGoal = 0f
    private var frequencyUser = 0
    private var costUser = 0f



    companion object {
        private val instance = SharedViewModel()
        fun getInstance(): SharedViewModel {
            return instance
        }
    }
    fun setFrequencyGoal(frequency: Int) {
        this.frequencyGoal = frequency
    }
    fun setCostGoal(cost: Float) {
        this.costGoal = cost
    }
    fun setFrequencyUser(frequency: Int) {
        this.frequencyUser = frequency
    }
    fun setCostUser(cost: Float) {
        this.costUser = cost
    }

    fun getFrequencyGoal(): Int {
        return this.frequencyGoal
    }
    fun getCostGoal(): Float {
        return this.costGoal
    }
    fun getFrequencyUser(): Int {
        return this.frequencyUser
    }
    fun getCostUser(): Float {
        return this.costUser
    }



}