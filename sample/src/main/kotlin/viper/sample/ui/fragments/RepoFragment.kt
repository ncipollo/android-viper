package viper.sample.ui.fragments

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_repo.*
import kotlinx.android.synthetic.main.item_repository.view.*
import kotlinx.android.synthetic.main.layout_recycler.*
import nucleus.factory.RequiresPresenter
import viper.sample.R
import viper.sample.ui.presenters.RepoListItem
import viper.sample.ui.presenters.RepoPresenter
import viper.view.adapters.ViperRecyclerAdapter
import viper.view.adapters.ViperViewHolder
import viper.view.fragments.ViperRecyclerFragment

/**
 * Fragment which displays GitHub repositories.
 * Created by Nick Cipollo on 12/19/16.
 */
@RequiresPresenter(RepoPresenter::class)
class RepositoryFragment : GitCollectionFragment<RepoPresenter>() {
    override val layoutId: Int
        get() = R.layout.fragment_repo
    override fun createAdapter(): ViperRecyclerAdapter<*, *, RepoPresenter> {
        return ViperRecyclerAdapter(::RepoViewHolder)
    }
}

class RepoViewHolder(parent: ViewGroup)
    : ViperViewHolder<RepoListItem>(R.layout.item_repository, parent) {
    override fun bindListItem(item: RepoListItem) {
        view.name.text = item.title
        view.setOnClickListener {
            sendAction()
        }
    }
}