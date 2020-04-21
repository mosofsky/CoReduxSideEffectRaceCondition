package com.locuslabs.crserc

data class MyReduxState(
    val counter: Int = INT_NOT_SET,
    val history: List<String> = listOf()
)