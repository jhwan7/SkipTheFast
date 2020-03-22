package com.example.skipthefast.Data

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SharedViewModel {
    private var frequencyGoal = 0
    private var costGoal = 0
    private var frequencyUser = 0
    private var costUser = 0



    companion object {
        private val instance = SharedViewModel()
        fun getInstance(): SharedViewModel {
            return instance
        }
    }
    fun setFrequencyGoal(frequency: Int) {
        this.frequencyGoal = frequency
    }
    fun setCostGoal(cost: Int) {
        this.costGoal = cost
    }
    fun setFrequencyUser(frequency: Int) {
        this.frequencyUser = frequency
    }
    fun setGoalUser(cost: Int) {
        this.costUser = cost
    }

    fun getFrequencyGoal(): Int {
        return this.frequencyGoal
    }
    fun getCostGoal(): Int {
        return this.costGoal
    }
    fun getFrequencyUser(): Int {
        return this.frequencyUser
    }
    fun getCostUser(): Int {
        return this.costUser
    }



}