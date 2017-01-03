package viper.sample.ui.presenters

import android.os.Bundle
import rx.Observable
import viper.presenters.CollectionPresenter
import viper.sample.model.entities.Repo
import viper.sample.model.interactors.SampleInteractors
import viper.sample.ui.router.SampleFlow
import viper.view.fragments.CollectionView

/**
 * Presents a list of GitHub repositories.
 * Created by Nick Cipollo on 12/19/16.
 */
class RepoPresenter
    : CollectionPresenter<CollectionView, RepoListItem, SampleInteractors>() {
    val repoList = mutableListOf<RepoListItem>()
    override val count: Int
        get() = repoList.size
    val user: String
        get() = args.getString(SampleFlow.ARGS_USER)

    override fun onTakeInteractors(interactors: SampleInteractors) {
        interactors.gitInteractor.fetchRepos(user)
                .map { RepoListItem(it) }
                .toList()
                .subscribe {
                    repoList.clear()
                    repoList.addAll(it)
                    notifyCollectionUpdated()
                }
    }

    override fun getListItem(index: Int): RepoListItem = repoList[index]

    override fun onItemAction(actionId: Int, itemIndex: Int) {
        val args = Bundle()
        args.putString(SampleFlow.ARGS_USER, user)
        args.putParcelable(SampleFlow.ARGS_REPO, repoList[itemIndex].repo)
        moveToNextScreen(SampleFlow.SCREEN_COMMITS, args)
    }
}

data class RepoListItem(val repo: Repo,
                        val title: String = repo.name,
                        val description: String = repo.description)
