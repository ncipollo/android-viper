package viper.sample.model.entities

import android.os.Parcel
import android.os.Parcelable

/**
 * Represents a git branch.
 * Created by Nick Cipollo on 1/4/17.
 */
data class Branch(val name:String) : Parcelable {
    companion object {
        @JvmField val CREATOR: Parcelable.Creator<Branch> = object : Parcelable.Creator<Branch> {
            override fun createFromParcel(source: Parcel): Branch = Branch(source)
            override fun newArray(size: Int): Array<Branch?> = arrayOfNulls(size)
        }
    }

    constructor(source: Parcel) : this(source.readString())

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.writeString(name)
    }
}