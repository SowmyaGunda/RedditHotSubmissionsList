package com.example.reddithotsubmissionslist.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import com.example.reddithotsubmissionslist.R
import com.example.reddithotsubmissionslist.model.data.Child
import com.example.reddithotsubmissionslist.model.data.ChildData

class RedditListAdapter(private var onItemClicked: OnItemClicked) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val VIEW_TYPE_LOADING = 0
    private val VIEW_TYPE_NORMAL = 1
    private var isLoaderVisible = false

    private var redditList: MutableList<Child> = arrayListOf()

    private fun ViewGroup.inflate(@LayoutRes layoutRes: Int, attachToRoot: Boolean = false): View {
        return LayoutInflater.from(context).inflate(layoutRes, this, attachToRoot)
    }

    override fun getItemViewType(position: Int): Int {
        return if (isLoaderVisible) {
            if (position == redditList.size - 1) VIEW_TYPE_LOADING else VIEW_TYPE_NORMAL
        } else {
            VIEW_TYPE_NORMAL
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_NORMAL -> {
                val inflatedView = parent.inflate(R.layout.reddit_list_item)
                RedditListHolder(inflatedView, onItemClicked)
            }
            else -> {
                val inflatedView = parent.inflate(R.layout.item_loading)
                ProgressHolder(inflatedView)
            }
        }
    }


    override fun getItemCount() = redditList.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is RedditListHolder) {
            val childData = redditList[position].data
            holder.bind(childData)
        }

    }

    fun setAdapterList(list: List<Child>) {
        this.redditList.addAll(list)
        notifyDataSetChanged()
    }

    fun addLoading() {
        isLoaderVisible = true
        notifyItemInserted(redditList.size - 1)
    }

    fun removeLoading() {
        isLoaderVisible = false
        notifyItemRemoved(redditList.size - 1)
    }


    class RedditListHolder(private var v: View, private var onItemClicked: OnItemClicked) : RecyclerView.ViewHolder(v) {
        private var author: TextView = v.findViewById(R.id.author)
        private var title: TextView = v.findViewById(R.id.title)
        fun bind(child: ChildData) {
            author.text = "postedBy: " + child.author
            title.text = child.title
            v.setOnClickListener {
                onItemClicked.openWebLink(child.url)
            }


        }
    }

    class ProgressHolder(private var v: View) : RecyclerView.ViewHolder(v) {
        private var progressBar: ProgressBar = v.findViewById(R.id.progressBar)

    }

    interface OnItemClicked {
        fun openWebLink(url: String)
    }

}