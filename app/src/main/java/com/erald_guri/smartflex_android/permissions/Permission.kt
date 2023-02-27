package com.erald_guri.smartflex_android.permissions

import android.Manifest.permission.*

sealed class Permission(vararg val permissions: String) {

    object Storage: Permission(WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE)

    companion object {

        fun from(permission: String) = when (permission) {
            WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE -> Storage
            else -> throw IllegalArgumentException("Unknown permission: $permission")
        }

    }

}
