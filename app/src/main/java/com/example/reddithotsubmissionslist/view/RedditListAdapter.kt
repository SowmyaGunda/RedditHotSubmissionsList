package com.example.reddithotsubmissionslist.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import com.example.reddithotsubmissionslist.R
import com.example.reddithotsubmissionslist.model.data.Child
import com.example.reddithotsubmissionslist.model.data.ChildData

class RedditListAdapter(private var onItemClicked: OnItemClicked) : RecyclerView.Adapter<RedditListAdapter.RedditListHolder>() {


    private var redditList: List<Child> = emptyList<Child>()

    private fun ViewGroup.inflate(@LayoutRes layoutRes: Int, attachToRoot: Boolean = false): View {
        return LayoutInflater.from(context).inflate(layoutRes, this, attachToRoot)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RedditListHolder {
        val inflatedView = parent.inflate(R.layout.reddit_list_item)
        return RedditListHolder(inflatedView, onItemClicked)
    }


    override fun getItemCount() = redditList.size

    override fun onBindViewHolder(holder: RedditListHolder, position: Int) {
        val childData = redditList[position].data
        holder.bind(childData)

    }

    fun setAdapterList(list: List<Child>) {
        this.redditList = list
        notifyDataSetChanged()
    }


    class RedditListHolder(private var v: View, private var onItemClicked: OnItemClicked) : RecyclerView.ViewHolder(v) {
        private var author: TextView = v.findViewById(R.id.author)
        private var title: TextView = v.findViewById(R.id.title)
        fun bind(child: ChildData) {
            author.text = child.author
            title.text = "postedBy: "+child.title
            v.setOnClickListener {
                onItemClicked.openWebLink(child.url)
            }


        }
    }

    interface OnItemClicked {
        fun openWebLink(url: String)
    }

}