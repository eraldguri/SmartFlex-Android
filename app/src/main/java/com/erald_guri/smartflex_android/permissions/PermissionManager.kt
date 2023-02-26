package com.erald_guri.smartflex_android.permissions

import android.content.pm.PackageManager
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.erald_guri.smartflex_android.R
import com.erald_guri.smartflex_android.dialogs.PermissionDialog
import java.lang.ref.WeakReference

class PermissionManager private constructor(private val fragment: WeakReference<Fragment>) {

    private val requiredPermissions = mutableListOf<Permission>()
    private var rationale: String? = null
    private var callback: (Boolean) -> Unit = {}
    private var detailedCallback: (Map<Permission, Boolean>) -> Unit = {}

    private val permissionCheck =
        fragment.get()?.registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { grantResults ->
        sendResultAndCleanUp(grantResults)
    }

    companion object {
        fun form(fragment: Fragment) = PermissionManager(WeakReference(fragment))
    }

    fun rationale(description: String): PermissionManager {
        rationale = description
        return this
    }

    fun request(vararg permissions: Permission): PermissionManager {
        requiredPermissions.addAll(permissions)
        return this
    }

    fun checkPermission(callback: (Boolean) -> Unit) {
        this.callback = callback
        handlePermissionRequest()
    }

    private fun handlePermissionRequest() {
        fragment.get()?.let { fragment ->
            when {
                areAllPermissionsGranted(fragment) -> sendPositiveResult()
                shouldShowPermissionRationale(fragment) -> displayRationale(fragment)
                else -> requestPermissions()
            }
        }
    }

    private fun sendPositiveResult() {
        sendResultAndCleanUp(getPermissionList().associateWith { true })
    }

    private fun shouldShowPermissionRationale(fragment: Fragment) = requiredPermissions.any { it.requiresRationale(fragment) }

    private fun requestPermissions() {
        permissionCheck?.launch(getPermissionList())
    }

    private fun getPermissionList() = requiredPermissions.flatMap { it.permissions.toList() }.toTypedArray()

    private fun areAllPermissionsGranted(fragment: Fragment) = requiredPermissions.all { it.isGranted(fragment) }

    private fun Permission.isGranted(fragment: Fragment) = permissions.all { hasPermission(fragment, it) }

    private fun Permission.requiresRationale(fragment: Fragment) = permissions.any {
        fragment.shouldShowRequestPermissionRationale(it)
    }

    private fun hasPermission(fragment: Fragment, permission: String): Boolean {
        return ContextCompat.checkSelfPermission(fragment.requireContext(), permission) == PackageManager.PERMISSION_GRANTED
    }

    private fun sendResultAndCleanUp(grantResults: Map<String, Boolean>) {
        callback(grantResults.all { it.value })
        detailedCallback(grantResults.mapKeys { Permission.from(it.key) })
        cleanUp()
    }

    private fun displayRationale(fragment: Fragment) {
        val context = fragment.requireContext()
        val permissionDialog = PermissionDialog(context)
        permissionDialog.show()
    }

    private fun cleanUp() {
        requiredPermissions.clear()
        rationale = null
        callback = {}
        detailedCallback = {}
    }

}