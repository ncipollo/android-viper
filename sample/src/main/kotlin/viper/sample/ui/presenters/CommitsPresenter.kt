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
    val repo: Repo
        get() = args.getParcelable(SampleFlow.ARGS_REPO)
    val user: String
        get() = args.getString(SampleFlow.ARGS_USER)
    val branch: Branch
        get() = args.getParcelable(SampleFlow.ARGS_BRANCH)

    override fun onCreate(savedState: Bundle?) {
        super.onCreate(savedState)
    }

    override fun onRefresh() {
        interactors.gitInteractor.fetchCommits(user, repo, branch)
                .map(::CommitListItem)
                .toList()
                .subscribe({
                    itemList.clear()
                    itemList.addAll(it)
                    notifyCollectionUpdated()
                    finishRefresh()
                }, {
                    showError(it)
                })
    }

    override fun onItemMovedOnScreen(item: CommitListItem, index: Int) {
        Log.d(TAG, "+ Commit moved onscreen: $item")
    }

    override fun onItemMovedOffScreen(item: CommitListItem, index: Int) {
        Log.d(TAG, "- Commit moved offscreen: $item")
    }

    fun openBranchPicker() {
        val args = Bundle()
        args.putString(SampleFlow.ARGS_USER, user)
        args.putParcelable(SampleFlow.ARGS_REPO, repo)
        moveToNextScreen(SampleFlow.SCREEN_BRANCHES, args)
    }

    override fun onPresenterResult(arguments: Bundle) {
        super.onPresenterResult(arguments)
        args.putParcelable(SampleFlow.ARGS_BRANCH,
                arguments.getParcelable(SampleFlow.ARGS_BRANCH))
        refresh()
    }
}

data class CommitListItem(val sha: String,
                          val author: String,
                          val message: String) {
    constructor(commit: Commit) : this(commit.sha.orEmpty(),
            commit.login,
            commit.message)
    val shortSha: String
        get() {
            val length = Math.min(sha.length,7)
            return sha.substring(0..length)
        }
}