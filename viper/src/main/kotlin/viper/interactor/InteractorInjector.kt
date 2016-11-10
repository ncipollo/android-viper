package viper.interactor

/**
 * An object which will inject interactors into a presenter.
 * Created by Nick Cipollo on 11/10/16.
 */
interface InteractorInjector {
    /**
     * Injects dependant interactors into the presenter.
     */
    fun <P>injectInteractor(presenter: P)
}