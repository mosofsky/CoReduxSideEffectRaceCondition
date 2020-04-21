package com.locuslabs.crserc

sealed class MyReduxAction {
    object InitCounterAction : MyReduxAction()
    object IncrementCounterBy1Action : MyReduxAction()
    object IncrementCounterBy2Action : MyReduxAction()
}