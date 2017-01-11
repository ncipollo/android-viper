package viper.sample.ui.fragments

import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_commits.*
import kotlinx.android.synthetic.main.item_repository.view.*
import nucleus.factory.RequiresPresenter
import viper.sample.R
import viper.sample.ui.presenters.BranchListItem
import viper.sample.ui.presenters.BranchesPresenter
import viper.sample.ui.presenters.CommitListItem
import viper.sample.ui.presenters.CommitsPresenter
import viper.view.adapters.ViperRecyclerAdapter
import viper.view.adapters.ViperViewHolder

/**
 * Fragment which displays GitHub repositories.
 * Created by Nick Cipollo on 12/19/16.
 */
@RequiresPresenter(BranchesPresenter::class)
class BranchesFragment : GitCollectionFragment<BranchesPresenter>() {
    override val layoutId: Int
        get() = R.layout.fragment_commits

    override fun createAdapter(): ViperRecyclerAdapter<*, *, BranchesPresenter> {
        return ViperRecyclerAdapter(::BranchViewHolder)
    }
}

class BranchViewHolder(parent: ViewGroup)
    : ViperViewHolder<BranchListItem>(R.layout.item_repository, parent) {
    override fun bindListItem(item: BranchListItem) {
        view.name.text = item.title
        view.setOnClickListener {
            sendAction()
        }
    }
}