package com.bluetoolth.cupping

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import androidx.core.content.FileProvider
import com.bluetoolth.cupping.main.MainActivity
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by KimBH on 2023/02/20.
 */
class CameraUtil (private val activity: Activity) {

    fun checkPermission(isCaptureEnabled: Boolean) {
        if (isCaptureEnabled) {
            // 카메라..
            if (activity.checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                dispatchTakePictureIntent()
            } else {
                activity.requestPermissions(
                    arrayOf(Manifest.permission.CAMERA),
                    MainActivity.CAMERA_PERMISSION
                )
            }
        } else {
            // 갤러리..
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                if (activity.checkSelfPermission(Manifest.permission.READ_MEDIA_IMAGES) == PackageManager.PERMISSION_GRANTED) {
                    galleryIntent()
                } else {
                    activity.requestPermissions(
                        arrayOf(Manifest.permission.READ_MEDIA_IMAGES),
                        MainActivity.READ_EXTERNAL_STORAGE_PERMISSION
                    )
                }
            } else {
                if (activity.checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                    galleryIntent()
                } else {
                    activity.requestPermissions(
                        arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                        MainActivity.READ_EXTERNAL_STORAGE_PERMISSION
                    )
                }
            }
        }
    }

    private fun dispatchTakePictureIntent() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            // Ensure that there's a camera activity to handle the intent
            takePictureIntent.resolveActivity(activity.packageManager)?.also {
                // Create the File where the photo should go
                val photoFile: File? = try {
                    createImageFile()
                } catch (ex: IOException) {
                    // Error occurred while creating the File
                    null
                }
                // Continue only if the File was successfully created
                photoFile?.also {
                    val photoURI: Uri = FileProvider.getUriForFile(
                        activity,
                        activity.getString(R.string.file_provider_authorities),
                        it
                    )
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                    activity.startActivityForResult(
                        takePictureIntent,
                        MainActivity.CAMERA_PERMISSION
                    )
                }
            }
        }
    }

    @Throws(IOException::class)
    private fun createImageFile(): File {
        // Create an image file name
        val timeStamp: String =
            SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val storageDir: File? = activity.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
            "JPEG_${timeStamp}_", /* prefix */
            ".jpg", /* suffix */
            storageDir /* directory */
        ).apply {
            // Save a file: path for use with ACTION_VIEW intents
            if (activity is MainActivity) {
                activity.currentPhotoPath = absolutePath
            }
        }
    }

    private fun galleryIntent() {
        Intent(Intent.ACTION_GET_CONTENT).also { contentSelectionIntent ->
            contentSelectionIntent.type = "image/*"
            activity.startActivityForResult(
                contentSelectionIntent,
                MainActivity.READ_EXTERNAL_STORAGE_PERMISSION
            )
        }
    }
}