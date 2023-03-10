package com.erald_guri.smartflex_android.ui.camera

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Paint.Cap
import android.graphics.SurfaceTexture
import android.hardware.camera2.CameraAccessException
import android.hardware.camera2.CameraCaptureSession
import android.hardware.camera2.CameraCharacteristics
import android.hardware.camera2.CameraDevice
import android.hardware.camera2.CameraManager
import android.hardware.camera2.CaptureRequest
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.util.Log
import android.view.*
import com.erald_guri.smartflex_android.base.BaseFragment
import com.erald_guri.smartflex_android.databinding.FragmentPreviewBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.util.*

class PreviewFragment : BaseFragment<FragmentPreviewBinding>(
    FragmentPreviewBinding::inflate
) {

    private lateinit var cameraDevice: CameraDevice
    private lateinit var backgroundThread: HandlerThread
    private lateinit var backgroundHandler: Handler
    private lateinit var cameraCaptureSession: CameraCaptureSession
    private lateinit var captureRequestBuilder: CaptureRequest.Builder

    private val cameraManager by lazy {
        activity?.getSystemService(Context.CAMERA_SERVICE) as CameraManager
    }

    private fun previewSession() {
        val surfaceTexture = binding.previewTextureView.surfaceTexture
        surfaceTexture?.setDefaultBufferSize(MAX_PREVIEW_WIDTH, MAX_PREVIEW_HEIGHT)
        val surface = Surface(surfaceTexture)

        captureRequestBuilder = cameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW)
        captureRequestBuilder.addTarget(surface)

        cameraDevice.createCaptureSession(listOf(surface), object: CameraCaptureSession.StateCallback() {
            override fun onConfigured(session: CameraCaptureSession) {
                cameraCaptureSession = session
                captureRequestBuilder.set(CaptureRequest.CONTROL_AF_MODE, CaptureRequest.CONTROL_AF_MODE_CONTINUOUS_PICTURE)
                cameraCaptureSession.setRepeatingRequest(captureRequestBuilder.build(), null, null)
            }

            override fun onConfigureFailed(session: CameraCaptureSession) {
                Log.e(TAG, "Camera capture session failed")
            }

        }, null)
    }

    private fun closeCamera() {
        if (this::cameraCaptureSession.isInitialized) {
            cameraCaptureSession.close()
        }
        if (this::cameraDevice.isInitialized) {
            cameraDevice.close()
        }
    }

    private val cameraDeviceStateCallback = object : CameraDevice.StateCallback() {

        override fun onOpened(camera: CameraDevice) {
            cameraDevice = camera
            previewSession()
            Log.d(TAG, "camera device opened")
        }

        override fun onDisconnected(camera: CameraDevice) {
            camera.close()
            Log.d(TAG, "camera device disconnected")
        }

        override fun onError(camera: CameraDevice, error: Int) {
            this@PreviewFragment.activity?.finish()
            Log.d(TAG, "camera device error")
        }

    }

    private fun startBackgroundThread() {
        backgroundThread = HandlerThread("SmartflexCamera").also { it.start() }
        backgroundHandler = Handler(backgroundThread.looper)
    }

    private fun stopBackgroundThread() {
        backgroundThread.quitSafely()
        try {
            backgroundThread.join()
        } catch (e: InterruptedException) {
            Log.d(TAG, e.toString())
        }
    }

    private fun <T> cameraCharacteristics(cameraId: String, key: CameraCharacteristics.Key<T>): T? {
        val characteristics = cameraManager.getCameraCharacteristics(cameraId)
        return when (key) {
            CameraCharacteristics.LENS_FACING -> characteristics.get(key)
            CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP -> characteristics.get(key)
            else -> throw IllegalArgumentException("Key not recognized")
        }
    }

    private fun cameraId(lens: Int): String {
        var deviceId = listOf<String>()
        try {
            val cameraIdList = cameraManager.cameraIdList
            deviceId = cameraIdList.filter { lens == cameraCharacteristics(it, CameraCharacteristics.LENS_FACING) }
        } catch (e: CameraAccessException) {
            Log.d(TAG, e.toString())
        }
        return deviceId[0]
    }

    @SuppressLint("MissingPermission")
    private fun connectCamera() {
        val deviceId = cameraId(CameraCharacteristics.LENS_FACING_BACK)
        Log.d(TAG, "deviceId: $deviceId")
        try {
            cameraManager.openCamera(deviceId, cameraDeviceStateCallback, backgroundHandler)
        } catch (e: CameraAccessException) {
            Log.d(TAG, e.toString())
        } catch (e: InterruptedException) {
            Log.d(TAG, e.toString())
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setFullScreen()
        startBackgroundThread()
        connectCamera()
    }

    private fun setFullScreen() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            activity?.window?.setDecorFitsSystemWindows(false)
            val controller: WindowInsetsController =
                activity?.window?.insetsController!!
            controller.hide(WindowInsets.Type.statusBars() or WindowInsets.Type.navigationBars())
            controller.systemBarsBehavior = WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        } else {
            activity?.window?.decorView?.systemUiVisibility = (View.SYSTEM_UI_FLAG_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_IMMERSIVE
                    or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION)
        }
    }

    private val surfaceListener = object: TextureView.SurfaceTextureListener {
        override fun onSurfaceTextureAvailable(surface: SurfaceTexture, width: Int, height: Int) {
            Log.d(TAG, "texture surface width: $width $height")
            openCamera()
        }

        override fun onSurfaceTextureSizeChanged(surface: SurfaceTexture, width: Int, height: Int) {
            TODO("Not yet implemented")
        }

        override fun onSurfaceTextureDestroyed(surface: SurfaceTexture): Boolean = true

        override fun onSurfaceTextureUpdated(surface: SurfaceTexture) = Unit

    }

    private fun openCamera() {

    }

    override fun onStart() {
        super.onStart()

        startBackgroundThread()
    }

    override fun onResume() {
        super.onResume()

        startBackgroundThread()
        if (binding.previewTextureView.isAvailable) {
            openCamera()
        } else {
            binding.previewTextureView.surfaceTextureListener = surfaceListener
        }
    }

    override fun onPause() {
        closeCamera()
        stopBackgroundThread()
        super.onPause()
    }

    override fun onFabButton(fabButton: FloatingActionButton?) {
        fabButton?.hide()
    }

    companion object {
        private val TAG = PreviewFragment::class.java.canonicalName
        const val MAX_PREVIEW_WIDTH = 1280
        const val MAX_PREVIEW_HEIGHT = 720
    }

}