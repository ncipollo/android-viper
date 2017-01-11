package viper.sample.ui.presenters

import android.os.Bundle
import viper.sample.model.entities.Branch
import viper.sample.model.entities.Repo
import viper.sample.model.interactors.SampleInteractors
import viper.sample.ui.router.SampleFlow

/**
 * Presents a list of GitHub repositories.
 * Created by Nick Cipollo on 12/19/16.
 */
class BranchesPresenter
    : GitPresenter<BranchListItem, SampleInteractors>() {
    val user: String
        get() = args.getString(SampleFlow.ARGS_USER)
    val repo: Repo
        get() = args.getParcelable(SampleFlow.ARGS_REPO)

    override fun onRefresh() {
        interactors.gitInteractor.fetchBranches(user,repo)
                .map { BranchListItem(it) }
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
        args.putParcelable(SampleFlow.ARGS_BRANCH,itemList[itemIndex].branch)
        moveBack(args)
    }
}

data class BranchListItem(val branch: Branch,
                          val title: String = branch.name)
