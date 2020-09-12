package com.nri.nriyahoofinanceap.repository.event

class AppMessageEvent(serverError: String) {
    private var mMessage: String? = null

    fun AppMessageEvent(message: String?) {
        mMessage = message
    }

    fun getMessage(): String? {
        return mMessage
    }

}