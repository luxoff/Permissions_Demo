package com.appsflow.permissionsdemo

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import com.appsflow.permissionsdemo.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private val requestFineLocationPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                binding.tvStatusAccessFineLocation.setTextColor(getColor(R.color.green))
                binding.tvStatusAccessFineLocation.text = getText(R.string.granted)
            }
        }

    private val requestPhoneCallsPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                binding.tvStatusPhoneCalls.setTextColor(getColor(R.color.green))
                binding.tvStatusPhoneCalls.text = getText(R.string.granted)
            }
        }

    private val requestCameraPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                binding.tvStatusCameraPermission.setTextColor(getColor(R.color.green))
                binding.tvStatusCameraPermission.text = getText(R.string.granted)
            }
        }

    private val requestReadSmsPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                binding.tvStatusReadSms.setTextColor(getColor(R.color.green))
                binding.tvStatusReadSms.text = getText(R.string.granted)
            }
        }

    private val requestAllPermissions =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { it ->
            it.entries.forEach() {
                val isGranted = it.value
                if (isGranted) {
                    when (it.key) {
                        Manifest.permission.ACCESS_FINE_LOCATION -> {
                            binding.tvStatusAccessFineLocation.setTextColor(getColor(R.color.green))
                            binding.tvStatusAccessFineLocation.text = getText(R.string.granted)
                        }
                        Manifest.permission.ANSWER_PHONE_CALLS -> {
                            binding.tvStatusPhoneCalls.setTextColor(getColor(R.color.green))
                            binding.tvStatusPhoneCalls.text = getText(R.string.granted)
                        }
                        Manifest.permission.CAMERA -> {
                            binding.tvStatusCameraPermission.setTextColor(getColor(R.color.green))
                            binding.tvStatusCameraPermission.text = getText(R.string.granted)
                        }
                        Manifest.permission.READ_SMS -> {
                            binding.tvStatusReadSms.setTextColor(getColor(R.color.green))
                            binding.tvStatusReadSms.text = getText(R.string.granted)
                        }
                    }
                }

            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val permissions = arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.CALL_PHONE,
            Manifest.permission.CAMERA, Manifest.permission.READ_SMS
        )

        binding.apply {
            if (ContextCompat.checkSelfPermission(
                    this@MainActivity,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                tvStatusAccessFineLocation.setTextColor(getColor(R.color.green))
                tvStatusAccessFineLocation.text = getText(R.string.granted)
            }

            if (ContextCompat.checkSelfPermission(
                    this@MainActivity,
                    Manifest.permission.CALL_PHONE
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                tvStatusPhoneCalls.setTextColor(getColor(R.color.green))
                tvStatusPhoneCalls.text = getText(R.string.granted)
            }

            if (ContextCompat.checkSelfPermission(
                    this@MainActivity,
                    Manifest.permission.CAMERA
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                tvStatusCameraPermission.setTextColor(getColor(R.color.green))
                tvStatusCameraPermission.text = getText(R.string.granted)
            }

            if (ContextCompat.checkSelfPermission(
                    this@MainActivity,
                    Manifest.permission.READ_SMS
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                tvStatusReadSms.setTextColor(getColor(R.color.green))
                tvStatusReadSms.text = getText(R.string.granted)
            }


            btnGrantAccessFineLocation.setOnClickListener {
                requestFineLocationPermission(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    tvStatusAccessFineLocation
                )
            }
            btnGrantCallPhone.setOnClickListener {
                requestAnswerPhoneCallsPermission(
                    Manifest.permission.CALL_PHONE,
                    tvStatusPhoneCalls
                )
            }
            btnGrantCamera.setOnClickListener {
                requestCameraPermission(
                    Manifest.permission.CAMERA,
                    tvStatusCameraPermission
                )
            }
            btnGrantReadSms.setOnClickListener {
                requestReadSmsPermission(
                    Manifest.permission.READ_SMS,
                    tvStatusReadSms
                )
            }

            btnGrantAll.setOnClickListener {
                requestAllPermissions(permissions)
            }
        }
    }

    private fun requestAllPermissions(permissions: Array<String>) {
        permissions.forEach{
            // if should show rationale
            if (shouldShowRequestPermissionRationale(it)) {
                Snackbar.make(
                    binding.root,
                    "We need you to grant all the permissions for app to work correctly.",
                    Snackbar.LENGTH_LONG
                ).setAction("GRANT") {
                    requestAllPermissions.launch(permissions)
                }.show()
            } else { // if shouldn't, then just request
                requestAllPermissions.launch(permissions)
            }
        }
    }

    private fun requestFineLocationPermission(
        permission: String,
        tvStatus: TextView
    ) {
        if (ContextCompat.checkSelfPermission(
                this, permission
            ) == PackageManager.PERMISSION_DENIED
        ) {
            tvStatus.setTextColor(getColor(R.color.red))
            tvStatus.text = getText(R.string.not_granted)

            // if should show rationale
            if (shouldShowRequestPermissionRationale(permission)) {
                Snackbar.make(
                    binding.root,
                    "We need you to grant a permission for app to work correctly.",
                    Snackbar.LENGTH_LONG
                ).setAction("GRANT") {
                    requestFineLocationPermissionLauncher.launch(permission)
                }.show()
            } else { // if shouldn't, then just request
                requestFineLocationPermissionLauncher.launch(permission)
            }
        } else { // if permission is granted
            tvStatus.setTextColor(getColor(R.color.green))
            tvStatus.text = getText(R.string.granted)
        }
    }

    private fun requestAnswerPhoneCallsPermission(
        permission: String,
        tvStatus: TextView
    ) {
        if (ContextCompat.checkSelfPermission(
                this, permission
            ) == PackageManager.PERMISSION_DENIED
        ) {
            tvStatus.setTextColor(getColor(R.color.red))
            tvStatus.text = getText(R.string.not_granted)

            // if should show rationale
            if (shouldShowRequestPermissionRationale(permission)) {
                Snackbar.make(
                    binding.root,
                    "We need you to grant a permission for app to work correctly.",
                    Snackbar.LENGTH_LONG
                ).setAction("GRANT") {
                    requestPhoneCallsPermissionLauncher.launch(permission)
                }.show()
            } else { // if shouldn't, then just request
                requestPhoneCallsPermissionLauncher.launch(permission)
            }
        } else { // if permission is granted
            tvStatus.setTextColor(getColor(R.color.green))
            tvStatus.text = getText(R.string.granted)
        }
    }

    private fun requestCameraPermission(
        permission: String,
        tvStatus: TextView
    ) {
        if (ContextCompat.checkSelfPermission(
                this, permission
            ) == PackageManager.PERMISSION_DENIED
        ) {
            tvStatus.setTextColor(getColor(R.color.red))
            tvStatus.text = getText(R.string.not_granted)

            // if should show rationale
            if (shouldShowRequestPermissionRationale(permission)) {
                Snackbar.make(
                    binding.root,
                    "We need you to grant a permission for app to work correctly.",
                    Snackbar.LENGTH_LONG
                ).setAction("GRANT") {
                    requestCameraPermissionLauncher.launch(permission)
                }.show()
            } else { // if shouldn't, then just request
                requestCameraPermissionLauncher.launch(permission)
            }
        } else { // if permission is granted
            tvStatus.setTextColor(getColor(R.color.green))
            tvStatus.text = getText(R.string.granted)
        }
    }

    private fun requestReadSmsPermission(
        permission: String,
        tvStatus: TextView
    ) {
        if (ContextCompat.checkSelfPermission(
                this, permission
            ) == PackageManager.PERMISSION_DENIED
        ) {
            tvStatus.setTextColor(getColor(R.color.red))
            tvStatus.text = getText(R.string.not_granted)

            // if should show rationale
            if (shouldShowRequestPermissionRationale(permission)) {
                Snackbar.make(
                    binding.root,
                    "We need you to grant a permission for app to work correctly.",
                    Snackbar.LENGTH_LONG
                ).setAction("GRANT") {
                    requestReadSmsPermissionLauncher.launch(permission)
                }.show()
            } else { // if shouldn't, then just request
                requestReadSmsPermissionLauncher.launch(permission)
            }
        } else { // if permission is granted
            tvStatus.setTextColor(getColor(R.color.green))
            tvStatus.text = getText(R.string.granted)
        }
    }
}