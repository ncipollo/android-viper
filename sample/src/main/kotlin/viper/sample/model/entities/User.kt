package viper.sample.model.entities

import android.os.Parcel
import android.os.Parcelable

/**
 * Represent a Github user
 * Created by Nick Cipollo on 1/4/17.
 */
data class User(val login: String? = "",
                val id: String? ="") : Parcelable {
    companion object {
        @JvmField val CREATOR: Parcelable.Creator<User> = object : Parcelable.Creator<User> {
            override fun createFromParcel(source: Parcel): User = User(source)
            override fun newArray(size: Int): Array<User?> = arrayOfNulls(size)
        }
    }

    constructor(source: Parcel) : this(source.readString(), source.readString())

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.writeString(login)
        dest?.writeString(id)
    }
}