package viper.sample.ui.activities

import android.os.Bundle
import nucleus.factory.RequiresPresenter
import viper.sample.R
import viper.sample.dagger.DaggerProductionAppComponent
import viper.sample.dagger.InteractorComponent
import viper.sample.ui.presenters.MainActivityPresenter
import viper.view.activities.ViperActivity

@RequiresPresenter(MainActivityPresenter::class)
class MainActivity : MainActivityView, ViperActivity<MainActivityPresenter>() {
    override val interactorComponent: InteractorComponent by lazy {
        DaggerProductionAppComponent.builder()
                .build()
                .createInteractorComponent()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
