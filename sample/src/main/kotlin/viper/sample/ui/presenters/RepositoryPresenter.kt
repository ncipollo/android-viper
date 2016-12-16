package viper.sample.ui.presenters

import android.os.Bundle
import viper.presenters.CollectionPresenter
import viper.sample.ui.flow.SampleFlow
import viper.view.fragments.CollectionView

/**
 * Presents a list of GitHub repositories.
 * Created by Nick Cipollo on 12/19/16.
 */
class RepositoryPresenter
    : CollectionPresenter<CollectionView, RepoListItem>() {
    val repoList = listOf<RepoListItem>(RepoListItem("Repo 1", "Android app"),
            RepoListItem("Repo 2", "iOS App"))
    override val count: Int
        get() = repoList.size

    override fun getListItem(index: Int): RepoListItem = repoList[index]

    override fun onItemAction(actionId: Int, itemIndex: Int) {
        val args = Bundle()
        args.putString(SampleFlow.ARGS_REPO,repoList[itemIndex].title)
        moveToNextScreen(SampleFlow.SCREEN_COMMITS,args)
    }
}

data class RepoListItem(val title: String, val description: String)
