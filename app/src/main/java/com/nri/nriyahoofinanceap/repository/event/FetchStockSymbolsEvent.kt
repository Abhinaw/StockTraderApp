package com.nri.nriyahoofinanceap.repository.event

import com.nri.nriyahoofinanceap.model.suggestion.StockSymbol
import java.util.*

class FetchStockSymbolsEvent(arrayList: ArrayList<StockSymbol>) {

    private var mList: ArrayList<StockSymbol>? = null

    fun FetchStockSymbolsEvent(list: ArrayList<StockSymbol>?) {
        mList = list
    }

    fun getList(): ArrayList<StockSymbol>? {
        return mList
    }
}