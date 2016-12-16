package viper.sample.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_user.*
import nucleus.factory.RequiresPresenter
import viper.sample.R
import viper.sample.ui.presenters.UserPresenter
import viper.view.fragments.ViperFragment

/**
 * A fragment which allows the user to input a user.
 * Created by Nick Cipollo on 12/16/16.
 */
@RequiresPresenter(UserPresenter::class)
class UserFragment : ViperFragment<UserPresenter>() {
    override fun onCreateView(inflater: LayoutInflater?,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_user, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        doneButton.setOnClickListener {
            presenter.selectUser(userField.text.toString())
        }
    }
}
