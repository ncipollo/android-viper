package viper.sample.model.entities

import android.os.Parcel
import android.os.Parcelable

/**
 * Represents a Github repository.
 * Created by Nick Cipollo on 1/3/17.
 */

data class Repo(val name: String,
                val description: String = "") : Parcelable {
    companion object {
        @Suppress("unused")
        @JvmField val CREATOR: Parcelable.Creator<Repo> = object : Parcelable.Creator<Repo> {
            override fun createFromParcel(source: Parcel): Repo = Repo(source)
            override fun newArray(size: Int): Array<Repo?> = arrayOfNulls(size)
        }
    }

    constructor(source: Parcel) : this(source.readString(), source.readString())

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.writeString(name)
        dest?.writeString(description)
    }
}
