package viper.sample.ui.activities

import android.os.Bundle
import nucleus.factory.RequiresPresenter
import viper.sample.R
import viper.sample.ui.presenters.MainActivityPresenter
import viper.view.activities.ViperActivity

@RequiresPresenter(MainActivityPresenter::class)
class MainActivity : ViperActivity<MainActivityPresenter>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
