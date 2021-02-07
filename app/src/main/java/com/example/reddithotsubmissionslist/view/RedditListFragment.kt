package com.example.reddithotsubmissionslist.view

import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener
import com.example.reddithotsubmissionslist.R
import com.example.reddithotsubmissionslist.model.data.Child
import com.example.reddithotsubmissionslist.model.data.RedditHotListData
import com.example.reddithotsubmissionslist.view.PaginationListener.Companion.PAGE_SIZE
import com.example.reddithotsubmissionslist.view.PaginationListener.Companion.PAGE_START
import com.example.reddithotsubmissionslist.viewmodel.RedditListViewModel
import com.example.reddithotsubmissionslist.viewmodel.ViewModelFactory

class RedditListFragment : Fragment(), RedditListAdapter.OnItemClicked, OnRefreshListener {

    companion object {
        fun newInstance() = RedditListFragment()
    }

    private lateinit var loadingDialog: AlertDialog;
    private val observable = Observer<RedditListViewModel.RedditListResponse> {
        if (it is RedditListViewModel.RedditListResponse.Success) updateUI(it.RedditHotListData)
        else if (it is RedditListViewModel.RedditListResponse.Failure) showErrorDialog()
        cancelLoading()
    }

    private fun showErrorDialog() {
        val builder = AlertDialog.Builder(this.context)
        builder.setTitle("Warning!!")
        builder.setMessage("Something went wrong try again later")
        builder.setNeutralButton("OK") { dialog, which ->

        }
        builder.show()
    }

    private fun cancelLoading() {
        loadingDialog.dismiss()
    }

    private fun showLoading() {
        loadingDialog = ProgressDialog(context)
        loadingDialog.setMessage("Loding..")
        loadingDialog.setCancelable(false)
        loadingDialog.setInverseBackgroundForced(false)
        loadingDialog.show()
    }

    private fun updateUI(redditHotListData: RedditHotListData) {
        redditList.layoutManager = layoutManager
        val dividerItemDecoration = DividerItemDecoration(context,
                layoutManager.orientation)
        redditList.addItemDecoration(dividerItemDecoration)
        this.redditListData = redditHotListData.data.children
        System.out.println("reddit list size "+ redditListData.size)

        onRefresh()
    }

    private fun caleculateTotalPages() {
        if (redditListData.size % PAGE_SIZE == 0) {
            totalPage = redditListData.size / PAGE_SIZE
        } else {
            totalPage = (redditListData.size / PAGE_SIZE) + 1
        }
    }

    private lateinit var viewModel: RedditListViewModel
    private lateinit var redditList: RecyclerView
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var listAdapter: RedditListAdapter
    private var currentPage: Int = PAGE_START
    private var isLastPage = false
    private var totalPage = 0
    private var isLoading = false
    var itemCount = 0
    val layoutManager = LinearLayoutManager(context)
    private lateinit var redditListData: List<Child>
    var startPos = 0
    var endPos = 9


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.reddit_list_fragment, container, false)
        redditList = view.findViewById(R.id.redditList)
        swipeRefreshLayout = view.findViewById(R.id.swipeRefresh)
        swipeRefreshLayout.setOnRefreshListener(this)
        redditList.setHasFixedSize(true)
        redditList.addOnScrollListener(object : PaginationListener(layoutManager) {
            override fun loadMoreItems() {
                isLoading = true
                currentPage++
                updatePage()
            }

            override val checkIsLastPage: Boolean = isLastPage

            override val checkIsLoading: Boolean = isLoading
        })
        initAdapter()
        initViewModel()
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    private fun initViewModel() {
        val retroViewModelFactory = ViewModelFactory()
        viewModel = ViewModelProviders.of(this, retroViewModelFactory).get(RedditListViewModel::class.java)
        viewModel.redditListLiveData.observe(this, observable)
        viewModel.fetchRedditListFromRepository()
        showLoading()
    }

    override fun openWebLink(url: String) {
        var webUrl = url
        if (!webUrl.startsWith("http://") && !webUrl.startsWith("https://"))
            webUrl = "http://$webUrl"
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(webUrl))
        context?.startActivity(browserIntent)
    }

    private fun initAdapter() {
        listAdapter = RedditListAdapter(this)
        redditList.adapter = listAdapter
    }

    private fun updatePage() {
        var pageList: List<Child> = emptyList<Child>()
        updatePositions()
        pageList = redditListData.subList(startPos, endPos)

        if (currentPage != PAGE_START) listAdapter.removeLoading()
        listAdapter.setAdapterList(pageList)
        swipeRefreshLayout.setRefreshing(false)

        // check weather is last page or not
        if (currentPage < totalPage) {
            listAdapter.addLoading()
        } else {
            isLastPage = true
        }
        isLoading = false
    }

    private fun updatePositions() {
        if (redditListData.size > PAGE_SIZE) {
            if (currentPage == PAGE_START) {
                startPos = 0
                endPos = 9
            } else {
                startPos += PAGE_SIZE
                if (currentPage == totalPage) {
                    if (endPos < redditListData.size - 1) {
                        endPos += (redditListData.size / PAGE_SIZE)
                    }
                } else {
                    endPos += PAGE_SIZE
                }
            }
        } else {
            startPos = 0
            endPos = redditListData.size - 1
        }
    }

    override fun onRefresh() {
        itemCount = 0
        currentPage = PAGE_START
        isLastPage = false
        // adapter.clear()
        updatePage()
    }


}