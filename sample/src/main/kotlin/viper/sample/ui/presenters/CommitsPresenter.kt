package viper.sample.ui.presenters

import android.os.Bundle
import android.util.Log
import viper.sample.model.entities.Branch
import viper.sample.model.entities.Commit
import viper.sample.model.entities.Repo
import viper.sample.model.interactors.SampleInteractors
import viper.sample.ui.router.SampleFlow

/**
 * Presents a list of commits.
 * Created by Nick Cipollo on 12/24/16.
 */
class CommitsPresenter : GitPresenter<CommitListItem, SampleInteractors>() {
    val TAG = "CommitsPresenter"
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
        if (commitList.isEmpty()) {
            refresh()
        }
    }

    override fun onRefresh() {
        interactors.gitInteractor.fetchCommits(user, repo, Branch("master"))
                .map(::CommitListItem)
                .toList()
                .subscribe {
                    commitList.clear()
                    commitList.addAll(it)
                    notifyCollectionUpdated()
                    finishRefresh()
                }
    }

    override fun getListItem(index: Int): CommitListItem = commitList[index]

    override fun onItemMovedOnScreen(item: CommitListItem, index: Int) {
        Log.i(TAG, "+ Commit moved onscreen: $item")
    }

    override fun onItemMovedOffScreen(item: CommitListItem, index: Int) {
        Log.i(TAG, "- Commit moved offscreen: $item")
    }
}

data class CommitListItem(val sha: String, val author: String, val message: String) {
    constructor(commit: Commit) : this(commit.sha.orEmpty(),
            commit.login,
            commit.message)
}