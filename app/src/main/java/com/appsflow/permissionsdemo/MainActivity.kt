package com.appsflow.permissionsdemo

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import com.appsflow.permissionsdemo.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

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
                requestPermission(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    tvStatusAccessFineLocation
                )
            }
            btnGrantCallPhone.setOnClickListener {
                requestPermission(
                    Manifest.permission.CALL_PHONE,
                    tvStatusPhoneCalls
                )
            }
            btnGrantCamera.setOnClickListener {
                requestPermission(
                    Manifest.permission.CAMERA,
                    tvStatusCameraPermission
                )
            }
            btnGrantReadSms.setOnClickListener {
                requestPermission(
                    Manifest.permission.READ_SMS,
                    tvStatusReadSms
                )
            }

            btnGrantAll.setOnClickListener {

            }
        }
    }

    private fun requestPermission(
        permission: String,
        tvStatus: TextView
    ) {
        val requestPermissionsLauncher =
            registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
                if (isGranted) {
                    tvStatus.setTextColor(getColor(R.color.green))
                    tvStatus.text = getText(R.string.granted)
                } else {
                    tvStatus.setTextColor(getColor(R.color.red))
                    tvStatus.text = getText(R.string.not_granted)
                }
            }

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
                    requestPermissionsLauncher.launch(permission)
                }.show()
            } else { // if shouldn't, then just request
                requestPermissionsLauncher.launch(permission)
            }
        } else { // if permission is granted
            tvStatus.setTextColor(getColor(R.color.green))
            tvStatus.text = getText(R.string.granted)
        }
    }
}