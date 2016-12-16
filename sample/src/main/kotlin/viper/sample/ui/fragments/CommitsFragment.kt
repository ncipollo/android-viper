package viper.sample.ui.fragments

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_commits.*
import kotlinx.android.synthetic.main.item_repository.view.*
import nucleus.factory.RequiresPresenter
import viper.sample.R
import viper.sample.ui.presenters.CommitListItem
import viper.sample.ui.presenters.CommitsPresenter
import viper.view.adapters.ViperRecyclerAdapter
import viper.view.adapters.ViperViewHolder
import viper.view.fragments.ViperRecyclerFragment

/**
 * Fragment which displays GitHub repositories.
 * Created by Nick Cipollo on 12/19/16.
 */
@RequiresPresenter(CommitsPresenter::class)
class CommitsFragment : ViperRecyclerFragment<CommitsPresenter>() {

    override fun onCreateView(inflater: LayoutInflater?,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_commits, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter
    }

    override fun createAdapter(): ViperRecyclerAdapter<*, *, CommitsPresenter> {
        return ViperRecyclerAdapter(::CommitViewHolder)
    }
}

class CommitViewHolder(parent: ViewGroup)
    : ViperViewHolder<CommitListItem>(R.layout.item_repository, parent) {
    override fun bindListItem(item: CommitListItem) {
        view.name.text = item.message
        view.setOnClickListener {
            sendAction()
        }
    }
}