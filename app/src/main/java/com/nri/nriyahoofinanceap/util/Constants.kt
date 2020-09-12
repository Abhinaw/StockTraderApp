package com.nri.nriyahoofinanceap.util

import android.content.Context
import android.net.ConnectivityManager
import android.util.Log

object Constants {

    const val TAG = "NRI_TAG"
    const val STOCK_DOWNLOAD_COMPLETE = "StockItem indices have been updated"
    const val STOCK_DOWNLOAD_FAILED = "Failed to retrieve stock data, check connection"
    const val STOCK_PORTFOLIO_NOT_DEFINED = "Create a portfolio of stocks to follow"
    const val STOCK_PORTFOLIO_HAS_CHANGED = "StockItem portfolio has changed"
    const val STOCK_PORTFOLIO_UPDATED = "StockItem portfolio has been updated"
    const val STOCK_SYMBOL_DATA_MODEL_UPDATED =
        "StockItem symbol data model has been updated"

    const val NO_MATCHING_RECORDS_FOUND = "No matching records found"
    const val NO_RECORDS_FOUND = "No records found"

    const val SERVER_ERROR = "Server error, check connection and try again"
    const val FAILED_TO_CONNECT = "Failed to connect, check connection"
    const val CHECK_NETWORK_CONNECTION = "Client not connected, check connection"

    const val THREAD_TASK_COMPLETE = "Thread task complete"

    const val PREFS_STOCK_PORTFOLIO_SET = "prefs_stock_portfolio"

    const val CONFIRM_STOCK_ITEM_DELETION =
        "StockItem items were removed from your portfolio"
    const val ACTION_CANCELLED = "Action cancelled"
    const val STOCK_SYMBOL_ARRAY_LIST = "StockItem symbol array list"
    const val STOCK_SYMBOL_ITEM = "StockItem symbol"

    fun writeLogToConsole(msg: String) {
        Log.v(Constants.TAG, msg)
    }

    fun isClientConnected(context: Context): Boolean {
        val cm =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = cm.activeNetworkInfo
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting
    }
}