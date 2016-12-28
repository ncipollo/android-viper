package viper.sample.ui.presenters

import viper.presenters.CollectionPresenter
import viper.view.fragments.CollectionView

/**
 *
 * Created by Nick Cipollo on 12/24/16.
 */
class CommitsPresenter : CollectionPresenter<CollectionView, CommitListItem>() {
    val commitList = listOf(CommitListItem("Nick","Commit 1"),
            CommitListItem("Nick","Commit 2"))
    override val count: Int
        get() = commitList.size

    override fun getListItem(index: Int): CommitListItem = commitList[index]
}

data class CommitListItem(val author: String, val message: String)