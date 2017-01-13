package viper.sample.ui.fragments

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import com.jakewharton.rxbinding.view.clicks
import kotlinx.android.synthetic.main.fragment_commits.*
import kotlinx.android.synthetic.main.item_commit.view.*
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
class CommitsFragment : GitCollectionFragment<CommitsPresenter>() {
    override val layoutId: Int
        get() = R.layout.fragment_commits
    override fun createAdapter(): ViperRecyclerAdapter<*, *, CommitsPresenter> {
        return ViperRecyclerAdapter(::CommitViewHolder)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        branchSelector.setOnClickListener { presenter.openBranchPicker() }
    }

    override fun onResume() {
        super.onResume()
        branchName.text = presenter.branch.name
    }
}

class CommitViewHolder(parent: ViewGroup)
    : ViperViewHolder<CommitListItem>(R.layout.item_commit, parent) {
    override fun bindListItem(item: CommitListItem) {
        view.message.text = item.message
        view.author.text = item.author
        view.sha.text = item.shortSha
        view.setOnClickListener {
            sendAction()
        }
    }
}