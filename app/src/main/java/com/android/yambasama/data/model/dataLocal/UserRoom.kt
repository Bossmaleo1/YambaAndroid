package com.android.yambasama.data.model.dataLocal

import androidx.room.*

@Entity(
    tableName = "user_data_table",
    indices = [
        Index(value = ["user_email", "user_user_name"], unique = true)
    ],
)
data class UserRoom(
    @ColumnInfo(name = "user_id")
    @PrimaryKey(autoGenerate = false)
    var id: Int?,
    @ColumnInfo(name = "user_first_name")
    var firstName: String?,
    @ColumnInfo(name = "user_last_name")
    var lastName: String?,
    @ColumnInfo(name = "user_role")
    var role: String?,
    @ColumnInfo(name = "user_phone")
    var phone: String?,
    @ColumnInfo(name = "user_nationality")
    var nationality: String?,
    @ColumnInfo(name = "user_sex")
    var sex: String?,
    @ColumnInfo(name = "user_state")
    var state: String?,
    @ColumnInfo(name = "user_token")
    var userToken: String?,
    @ColumnInfo(name = "user_email")
    var email: String?,
    @ColumnInfo(name = "user_user_name")
    var userName: String?,
    @ColumnInfo(name = "user_push_notification_name")
    var pushNotification: String?,
    @ColumnInfo(name = "user_image_url")
    var imageUrl: String?,
)
