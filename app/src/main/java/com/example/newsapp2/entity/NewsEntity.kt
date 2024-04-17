package com.example.newsapp2.entity

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "news")
data class NewsEntity(
    @PrimaryKey(
        autoGenerate = true
    )
    val id: Long,
    var title: String?,
    var description: String?,
    var content: String?,
    var author: String?,
    var publishedAt: String?,
    var imageUrl: String?
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readLong(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(id)
        parcel.writeString(title)
        parcel.writeString(description)
        parcel.writeString(content)
        parcel.writeString(author)
        parcel.writeString(publishedAt)
        parcel.writeString(imageUrl)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<NewsEntity> {
        override fun createFromParcel(parcel: Parcel): NewsEntity {
            return NewsEntity(parcel)
        }

        override fun newArray(size: Int): Array<NewsEntity?> {
            return arrayOfNulls(size)
        }
    }
}