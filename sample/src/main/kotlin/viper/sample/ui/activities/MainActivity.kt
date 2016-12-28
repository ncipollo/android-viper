package viper.sample.ui.activities

import android.os.Bundle
import nucleus.factory.RequiresPresenter
import viper.presenters.ActivityPresenter
import viper.routing.Flow
import viper.sample.R
import viper.sample.ui.flow.SampleFlow
import viper.view.activities.ViperActivity

@RequiresPresenter(ActivityPresenter::class)
class MainActivity : ViperActivity<ActivityPresenter<MainActivity>>() {
    override fun createFlow(): Flow = SampleFlow()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
