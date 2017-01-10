package viper.sample.ui.fragments

import android.view.ViewGroup
import kotlinx.android.synthetic.main.item_repository.view.*
import nucleus.factory.RequiresPresenter
import viper.sample.R
import viper.sample.ui.presenters.CommitListItem
import viper.sample.ui.presenters.CommitsPresenter
import viper.view.adapters.ViperRecyclerAdapter
import viper.view.adapters.ViperViewHolder

/**
 * Fragment which displays GitHub repositories.
 * Created by Nick Cipollo on 12/19/16.
 */
@RequiresPresenter(CommitsPresenter::class)
class CommitsFragment : SampleCollectionFragment<CommitsPresenter>() {
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