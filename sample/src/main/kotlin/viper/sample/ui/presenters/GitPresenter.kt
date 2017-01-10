package viper.sample.ui.presenters

import rx.Observable
import viper.presenters.CollectionPresenter
import viper.sample.ui.fragments.GitCollectionView
import viper.view.fragments.CollectionView

/**
 * Base class for presenters which utilize a Git API.
 * Created by Nick Cipollo on 1/10/17.
 */
abstract class GitPresenter<ListItem, Interactors : Any>
    : CollectionPresenter<GitCollectionView,ListItem,Interactors>() {
    val FINISH_REFRESHING_ID = 1
    val SHOW_ERROR = 2

    fun finishRefresh() {
        stop(FINISH_REFRESHING_ID)
        restartableFirst(FINISH_REFRESHING_ID,
                { Observable.just(true) },
                { view, noOp -> view?.finishRefresh() })
        start(FINISH_REFRESHING_ID)
    }

    fun showError(error:Throwable) {
        stop(SHOW_ERROR)
        restartableFirst(SHOW_ERROR,
                { Observable.just(error) },
                { view, err -> view?.onError(err) })
        start(SHOW_ERROR)
    }
}