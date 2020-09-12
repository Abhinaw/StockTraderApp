package com.nri.nriyahoofinanceap.repository.event

class QueryStockSymbolsEvent {

    private var mQuery: String? = null

    fun QueryStockSymbolsEvent(query: String?) {
        mQuery = query
    }

    fun getQuery(): String? {
        return mQuery
    }
}