package com.example.mediacenterfkam.footballappssubs_2.Utils

import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext

open class CoroutineContextProvider{
    open val main : CoroutineContext by lazy { Dispatchers.Main }
}

class TestContextProvider : CoroutineContextProvider(){
    override val main : CoroutineContext = Dispatchers.Unconfined
}