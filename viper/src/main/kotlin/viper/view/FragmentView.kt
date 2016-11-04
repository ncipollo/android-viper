package viper.view

import viper.presenter.ActivityPresenter

/**
 * A view which represents a fragment.
 * Created by Nick Cipollo on 11/4/16.
 */
interface FragmentView {
    /**
     * Provides a reference to the activity presenter if the view is onscreen. This is used by
     * the fragment presenter to obtain a reference to the activity's presenter.
     */
    val activityPresenter : ActivityPresenter<*>?
}