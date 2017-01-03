package viper.sample.ui.presenters

import android.os.Bundle
import viper.presenters.CollectionPresenter
import viper.sample.model.entities.Branch
import viper.sample.model.entities.Commit
import viper.sample.model.entities.Repo
import viper.sample.model.interactors.SampleInteractors
import viper.sample.ui.router.SampleFlow
import viper.view.fragments.CollectionView

/**
 *
 * Created by Nick Cipollo on 12/24/16.
 */
class CommitsPresenter : CollectionPresenter<CollectionView, CommitListItem, SampleInteractors>() {
    val commitList = mutableListOf<CommitListItem>()
    val repo: Repo
        get() = args.getParcelable(SampleFlow.ARGS_REPO)
    val user: String
        get() = args.getString(SampleFlow.ARGS_USER)

    override fun onCreate(savedState: Bundle?) {
        super.onCreate(savedState)
    }

    override val count: Int
        get() = commitList.size

    override fun onTakeInteractors(interactors: SampleInteractors) {
        interactors.gitInteractor.fetchCommits(user, repo, Branch("master"))
                .map(::CommitListItem)
                .toList()
                .subscribe {
                    commitList.clear()
                    commitList.addAll(it)
                    notifyCollectionUpdated()
                }
    }

    override fun getListItem(index: Int): CommitListItem = commitList[index]
}

data class CommitListItem(val sha: String, val author: String, val message: String) {
    constructor(commit: Commit) : this(commit.sha.orEmpty(),
            commit.login,
            commit.message)
}