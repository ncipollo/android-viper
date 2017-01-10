package viper.sample.ui.fragments

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import kotlinx.android.synthetic.main.layout_recycler.*
import viper.presenters.CollectionPresenter
import viper.sample.R
import viper.view.fragments.ViperRecyclerFragment

/**
 * Base fragment for managing results of the git api in the sample app.
 * Created by Nick Cipollo on 1/10/17.
 */
abstract class GitCollectionFragment<P : CollectionPresenter<*, *, *>>
    : GitCollectionView, ViperRecyclerFragment<P>() {

    override fun onCreateView(inflater: LayoutInflater?,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_repo, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter
        refreshLayout.setOnRefreshListener {
            presenter.refresh()
        }
    }

    override fun finishRefresh() {
        refreshLayout.isRefreshing = false
    }

    override fun onError(error: Throwable) {
        Toast.makeText(context,"Git Error: ${error.message}",Toast.LENGTH_LONG).show()
        finishRefresh()
    }
}