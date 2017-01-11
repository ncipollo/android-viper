package viper.sample.ui.fragments

import android.content.Context
import android.hardware.input.InputManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import com.jakewharton.rxbinding.view.clicks
import com.jakewharton.rxbinding.widget.editorActionEvents
import com.jakewharton.rxbinding.widget.textChanges
import kotlinx.android.synthetic.main.fragment_user.*
import nucleus.factory.RequiresPresenter
import rx.Observable
import rx.lang.kotlin.addTo
import rx.subscriptions.CompositeSubscription
import viper.sample.R
import viper.sample.ui.presenters.UserPresenter
import viper.view.fragments.ViperFragment

/**
 * A fragment which allows the user to input a user.
 * Created by Nick Cipollo on 12/16/16.
 */
@RequiresPresenter(UserPresenter::class)
class UserFragment : UserView, ViperFragment<UserPresenter>() {
    override var user: String
        get() = userField.text.toString()
        set(value) {
            userField.setText(value)
        }
    override val onUserChanged: Observable<CharSequence>
        get() = userField.textChanges()
    override var doneEnabled: Boolean
        get() = doneButton.isEnabled
        set(value) {
            doneButton.isEnabled = value
        }
    var subscriptions: CompositeSubscription? = null

    override fun onCreateView(inflater: LayoutInflater?,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_user, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val subscriptions = CompositeSubscription()
        this.subscriptions = subscriptions
        userField.editorActionEvents()
                .filter { it.actionId() == EditorInfo.IME_ACTION_DONE }
                .subscribe { selectUser() }
                .addTo(subscriptions)
        doneButton.clicks()
                .subscribe { selectUser() }
                .addTo(subscriptions)
    }

    fun selectUser() {
        dismissKeyboard()
        presenter.selectUser(userField.text.toString())
    }

    fun dismissKeyboard() {
        val inputManager = context.getSystemService(Context.INPUT_METHOD_SERVICE)
                as InputMethodManager
        val windowToken = view?.windowToken
        if (windowToken != null) {
            inputManager.hideSoftInputFromWindow(windowToken, 0)
        }
    }
}
