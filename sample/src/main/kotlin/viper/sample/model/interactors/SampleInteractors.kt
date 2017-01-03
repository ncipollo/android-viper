package viper.sample.model.interactors

import javax.inject.Inject
import javax.inject.Singleton

/**
 * Interactors for the sample app.
 * Created by Nick Cipollo on 1/2/17.
 */
@Singleton
class SampleInteractors @Inject constructor(val gitInteractor: GitInteractor)