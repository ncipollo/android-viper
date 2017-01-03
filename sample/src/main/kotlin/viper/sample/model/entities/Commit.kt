package viper.sample.model.entities

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

/**
 * Represents a git commit.
 * Created by Nick Cipollo on 1/3/17.
 */
data class Commit(val sha: String?,
                  @SerializedName("commit") val body: CommitBody?,
                  val author: User? = User()) : Parcelable {
    val message: String
        get() = body?.message.orEmpty()
    val name: String
        get() = body?.committer?.name.orEmpty()
    val login: String
        get() = author?.login.orEmpty()
    val email: String
        get() = body?.committer?.email.orEmpty()
    val date: String
        get() = body?.committer?.date.orEmpty()

    companion object {
        @JvmField val CREATOR: Parcelable.Creator<Commit> = object : Parcelable.Creator<Commit> {
            override fun createFromParcel(source: Parcel): Commit = Commit(source)
            override fun newArray(size: Int): Array<Commit?> = arrayOfNulls(size)
        }
    }

    constructor(source: Parcel) : this(source.readString(), source.readParcelable<CommitBody>(CommitBody::class.java.classLoader), source.readParcelable<User>(User::class.java.classLoader))

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.writeString(sha)
        dest?.writeParcelable(body, 0)
        dest?.writeParcelable(author, 0)
    }
}

data class CommitBody(val message: String?, val committer: Committer?) : Parcelable {
    companion object {
        @JvmField val CREATOR: Parcelable.Creator<CommitBody> = object : Parcelable.Creator<CommitBody> {
            override fun createFromParcel(source: Parcel): CommitBody = CommitBody(source)
            override fun newArray(size: Int): Array<CommitBody?> = arrayOfNulls(size)
        }
    }

    constructor(source: Parcel) : this(source.readString(), source.readParcelable<Committer>(Committer::class.java.classLoader))

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.writeString(message)
        dest?.writeParcelable(committer, 0)
    }
}

data class Committer(val name: String?, val email: String?, val date: String?) : Parcelable {
    companion object {
        @JvmField val CREATOR: Parcelable.Creator<Committer> = object : Parcelable.Creator<Committer> {
            override fun createFromParcel(source: Parcel): Committer = Committer(source)
            override fun newArray(size: Int): Array<Committer?> = arrayOfNulls(size)
        }
    }

    constructor(source: Parcel) : this(source.readString(), source.readString(), source.readString())

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.writeString(name)
        dest?.writeString(email)
        dest?.writeString(date)
    }
}