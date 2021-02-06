package com.example.reddithotsubmissionslist.view

import android.app.AlertDialog
import android.app.ProgressDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.reddithotsubmissionslist.R
import com.example.reddithotsubmissionslist.model.data.RedditHotListData
import com.example.reddithotsubmissionslist.viewmodel.RedditListViewModel
import com.example.reddithotsubmissionslist.viewmodel.ViewModelFactory

class RedditListFragment : Fragment() {

    companion object {
        fun newInstance() = RedditListFragment()
    }
    private lateinit var loadingDialog: AlertDialog;
    private val observable = Observer<RedditListViewModel.RedditListResponse> {
        if (it is RedditListViewModel.RedditListResponse.Success) updateUI(it.RedditHotListData)
        else if (it is RedditListViewModel.RedditListResponse.Failure) showErrorDialog()
        //cancelLoading()
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
    private fun updateUI(redditHotListData: RedditHotListData){
        System.out.println("RedditHotListData  "+ redditHotListData.data.children[0].data.title )
    }

    private lateinit var viewModel: RedditListViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.reddit_list_fragment, container, false)
        initViewModel()
        viewModel.redditListLiveData.observe(this, observable)
        viewModel.fetchRedditListFromRepository()
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }
    private fun initViewModel() {
        val retroViewModelFactory = ViewModelFactory()
        viewModel = ViewModelProviders.of(this, retroViewModelFactory).get(RedditListViewModel::class.java)
    }

}