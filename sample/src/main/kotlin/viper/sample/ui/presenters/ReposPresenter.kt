package viper.sample.ui.presenters

import android.os.Bundle
import rx.Observable
import rx.lang.kotlin.onError
import viper.presenters.CollectionPresenter
import viper.sample.model.entities.Repo
import viper.sample.model.interactors.SampleInteractors
import viper.sample.ui.router.SampleFlow
import viper.view.fragments.CollectionView

/**
 * Presents a list of GitHub repositories.
 * Created by Nick Cipollo on 12/19/16.
 */
class ReposPresenter
    : GitPresenter<RepoListItem, SampleInteractors>() {
    val user: String
        get() = args.getString(SampleFlow.ARGS_USER)

    override fun onRefresh() {
        interactors.gitInteractor.fetchRepos(user)
                .map { RepoListItem(it) }
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

    override fun onItemAction(actionId: Int, itemIndex: Int) {
        val args = Bundle()
        args.putString(SampleFlow.ARGS_USER, user)
        args.putParcelable(SampleFlow.ARGS_REPO, itemList[itemIndex].repo)
        args.putParcelable(SampleFlow.ARGS_BRANCH,itemList[itemIndex].repo.defaultBranch)
        moveToNextScreen(SampleFlow.SCREEN_COMMITS, args)
    }
}

data class RepoListItem(val repo: Repo,
                        val title: String = repo.name,
                        val description: String = repo.description ?: "")
