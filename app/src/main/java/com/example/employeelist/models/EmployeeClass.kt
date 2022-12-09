package com.example.employeelist.models

import android.os.Parcel
import android.os.Parcelable

data class EmployeeClass(
    val id: Int,
    val name: String?,
    val position: String?,
    val age: String?,
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(name)
        parcel.writeString(position)
        parcel.writeString(age)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<EmployeeClass> {
        override fun createFromParcel(parcel: Parcel): EmployeeClass {
            return EmployeeClass(parcel)
        }

        override fun newArray(size: Int): Array<EmployeeClass?> {
            return arrayOfNulls(size)
        }
    }
}