package com.application.myapplication.ui.main

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.text.TextUtils
import android.util.Log.i
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.application.myapplication.R
import com.application.myapplication.network.DataClass
import com.application.myapplication.network.ResponseData
import kotlinx.android.synthetic.main.main_fragment.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class MainFragment : Fragment(), AbsListView.OnScrollListener {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var progressCircular: ProgressBar
    private lateinit var root: View
    private var currentFirstVisPos: Int=0
    private var myLastVisiblePos: Int=0
    private lateinit var adapter: ImageListAdapter
    private lateinit var dataList: java.util.ArrayList<DataClass>
    private var MAX_ITEM_COUNT: Int=0
    private lateinit var mainActivity: Context
    private lateinit var viewModel: MainViewModel
    private var listTempData=ArrayList<DataClass>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        root   = inflater.inflate(R.layout.main_fragment, container, false)
        return root
    }
    override fun onSaveInstanceState(state: Bundle) {
        super.onSaveInstanceState(state)
        state.putInt("position",currentFirstVisPos)

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        progressCircular=root.findViewById(R.id.progress_circular)
        this.mainActivity =activity!!.applicationContext
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)

        // For fetching data from API if internet is available
        if (isConnectingToInternet(activity)) {
            progress_circular.visibility = View.VISIBLE
            viewModel.getAllData().observe(context@ this, Observer { status: ResponseData ->
                if(status!=null) {
                    dataList = status.data
                    MAX_ITEM_COUNT = status.data.size
                    initView(status.data)
                    if (savedInstanceState != null) {
                        currentFirstVisPos = savedInstanceState.getInt("position")
                        gridview.setSelection(currentFirstVisPos)
                    }
                }else{
                    progress_circular.visibility = View.GONE
                    Toast.makeText(context,R.string.failed,Toast.LENGTH_SHORT).show()
                }
            })
        }
        else{
            progress_circular.visibility = View.GONE
            Toast.makeText(context,R.string.no_internet,Toast.LENGTH_SHORT).show()
        }


        //on clicking search will load the data on the basis of title
        img_search.setOnClickListener {
            val search=edt_search.text.toString()
            progressCircular.visibility=View.VISIBLE
            if(TextUtils.isEmpty(search))
                initView(dataList)
            else
                loadSearchedItem(search)
        }

    }

    //Loading Searched data on the basis of matching of String in title of the items loaded
    private fun loadSearchedItem(search: String) {
        GlobalScope.launch {
            delay(4000L)
        }

        doAsync{
            var count=listTempData.size
            val searchedTempList :ArrayList<DataClass> =ArrayList()
            for(dataclass:DataClass in listTempData){
                if(dataclass.title.contains(search,true))
                    searchedTempList.add(dataclass)
            }
            listTempData.clear()
            listTempData.addAll(searchedTempList)

            uiThread {
                adapter.notifyDataSetChanged()
                gridview.invalidate()
                progressCircular.visibility=View.GONE
            }
        }
    }

    private fun isConnectingToInternet(context: Context?): Boolean {
        val connectivity = context?.getSystemService(
            Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (connectivity != null) {
            val info = connectivity.allNetworkInfo
            if (info != null)
                for (i in info)
                    if (i.state == NetworkInfo.State.CONNECTED) {
                        return true
                    }
        }
        return false
    }

//initial loading of data
    private fun initView(data: ArrayList<DataClass>) {
        listTempData.clear()
        for(i in 0..11){
            listTempData.add(data[i])
        }
        adapter = ImageListAdapter(mainActivity, R.layout.list_item, listTempData)
        gridview.adapter = adapter
        progressCircular.visibility=View.GONE
        myLastVisiblePos = listTempData.size
        gridview.setOnScrollListener(this)
    }

    // on scroll new data will be loaded in the GridView.
    private fun loadMore() {

        val count=listTempData.size
        for(i in 0..5){
            if((i+count)<dataList.size)
                listTempData.add(dataList[i+count])
        }



    }
    //Implementing on Scroll change listner
    override fun onScroll(
        view: AbsListView?,
        firstVisibleItem: Int,
        visibleItemCount: Int,
        totalItemCount: Int
    ) {
        currentFirstVisPos = view!!.lastVisiblePosition
        if (currentFirstVisPos ==listTempData.size-1) {
            progressCircular.visibility=View.VISIBLE

            doAsync {
                loadMore()
                Thread.sleep(1000)

                uiThread {
                    myLastVisiblePos = currentFirstVisPos
                    adapter.notifyDataSetChanged()
                    gridview?.invalidate()
                    progressCircular.visibility = View.GONE
                }
            }
        }


    }

    override fun onScrollStateChanged(view: AbsListView?, scrollState: Int) {

    }
}
