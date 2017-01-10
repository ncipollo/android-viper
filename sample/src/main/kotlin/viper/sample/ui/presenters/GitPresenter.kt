package viper.sample.ui.presenters

import rx.Observable
import viper.presenters.CollectionPresenter
import viper.sample.ui.fragments.SampleCollectionView
import viper.view.fragments.CollectionView

/**
 * Base class for presenters which utilize a Git API.
 * Created by Nick Cipollo on 1/10/17.
 */
abstract class GitPresenter<ListItem, Interactors : Any>
    : CollectionPresenter<SampleCollectionView,ListItem,Interactors>() {
    val FINISH_REFRESHING_ID = 1

    fun finishRefresh() {
        stop(FINISH_REFRESHING_ID)
        restartableFirst(FINISH_REFRESHING_ID,
                { Observable.just(true) },
                { view, noOp -> view?.finishRefresh() })
        start(FINISH_REFRESHING_ID)
    }
}