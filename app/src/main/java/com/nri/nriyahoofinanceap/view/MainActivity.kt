package com.nri.nriyahoofinanceap.view

import android.net.Uri
import android.os.AsyncTask
import android.os.Bundle
import android.os.Handler
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.nri.nriyahoofinanceap.BaseActivity
import com.nri.nriyahoofinanceap.R
import com.nri.nriyahoofinanceap.model.suggestion.StockSymbol
import com.nri.nriyahoofinanceap.model.suggestion.SuggestionQuery
import com.nri.nriyahoofinanceap.util.Constants
import com.nri.nriyahoofinanceap.view.adapter.MainActivityAdapter
import com.squareup.okhttp.OkHttpClient
import com.squareup.okhttp.Request
import java.io.BufferedReader
import java.io.IOException
import java.util.*
import java.util.regex.Pattern


class MainActivity : BaseActivity(), IMainActivityView {

    private lateinit var recyclerView: RecyclerView
    private lateinit var edtSearch: EditText
    private lateinit var btnSearch: Button

    companion object {
        var list = mutableListOf<StockSymbol>()
        lateinit var adapter: MainActivityAdapter
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
        initRecyclerView()
        initThread()
        onSearch()
        sheduleThread()
    }

    override fun initView() {
        recyclerView = findViewById(R.id.recyclerView)
        edtSearch = findViewById(R.id.edtSearch)
        btnSearch = findViewById(R.id.btnSearch)
    }

    override fun initRecyclerView() {
        adapter = MainActivityAdapter(this, list)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }

    override fun initThread() {
        val thread = MyAsyncTask("ab")
        thread.execute(10)
    }

    override fun onSearch() {
        btnSearch.setOnClickListener {
            val thread = MyAsyncTask(edtSearch.text.toString())
            thread.execute(10)
        }
    }

    override fun sheduleThread() {
        val handler = Handler()
        val timer = Timer()
        val task: TimerTask = object : TimerTask() {
            override fun run() {
                handler.post(Runnable {
                    try {
                        val thread = MyAsyncTask(edtSearch.text.toString())
                        thread.execute(10)
                    } catch (e: Exception) {
                        // error, do something
                    }
                })
            }
        }
        timer.schedule(task, 0, 60 * 1000) // interval of one minute
    }

    internal class MyAsyncTask(var query: String) :
        AsyncTask<Int, String, List<StockSymbol>>() {
        override fun doInBackground(vararg params: Int?): List<StockSymbol>? {
            val callback = "YAHOO.Finance.SymbolSuggest.ssCallback"
            val region = "US"
            val language = "en-US"
            if (params[0].toString().isNotEmpty()) {
                query = params[0].toString()
            }
            val querySuggestionUri =
                Uri.parse("http://autoc.finance.yahoo.com/autoc?").buildUpon()
                    .appendQueryParameter("query", query)
                    .appendQueryParameter("callback", callback)
                    .appendQueryParameter("region", region)
                    .appendQueryParameter("lang", language)
                    .build()
            try {
                val client = OkHttpClient()
                val request =
                    Request.Builder().url(querySuggestionUri.toString()).build()
                val response = client.newCall(request).execute()
                if (response.isSuccessful) {
                    Constants.writeLogToConsole("Response: %s" + response)
                    val inn = response.body().charStream()
                    val reader = BufferedReader(inn)
                    val sb = StringBuilder()
                    var line: String?
                    while (reader.readLine().also { line = it } != null) {
                        sb.append(line)
                    }
                    val pattern =
                        Pattern.compile("YAHOO\\.Finance\\.SymbolSuggest\\.ssCallback\\((\\{.*?\\})\\)")
                    val matcher = pattern.matcher(sb)
                    if (matcher.find()) {
                        val suggestionQuery: SuggestionQuery = Gson().fromJson<SuggestionQuery>(
                            matcher.group(1),
                            SuggestionQuery::class.java
                        )
                        val symbols = suggestionQuery.getResultSet().getResult()
                        list.clear()
                        list.addAll(symbols)
                        Constants.writeLogToConsole("Size::" + list.size.toString())
                    }
                } else {
                    Constants.writeLogToConsole("Http response: %s" + response.toString())
                }
            } catch (e: IOException) {
                Constants.writeLogToConsole("Error executing connection: %s" + e.message)
            }
            return list
        }

        override fun onPostExecute(result: List<StockSymbol>) {
            if (result.isNotEmpty()) {
                list.addAll(result)
                adapter.notifyDataSetChanged()
            }
        }
    }
}
