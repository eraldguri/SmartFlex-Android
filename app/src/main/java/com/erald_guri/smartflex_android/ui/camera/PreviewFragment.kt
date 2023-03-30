package com.erald_guri.smartflex_android.ui.camera

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.ImageFormat
import android.graphics.SurfaceTexture
import android.hardware.camera2.*
import android.media.Image
import android.media.ImageReader
import android.os.Bundle
import android.os.Environment
import android.os.Handler
import android.os.HandlerThread
import android.util.Log
import android.util.Size
import android.util.SparseIntArray
import android.view.Surface
import android.view.TextureView
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import com.erald_guri.smartflex_android.base.BaseFragment
import com.erald_guri.smartflex_android.databinding.FragmentPreviewBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream
import java.util.*

class PreviewFragment : BaseFragment<FragmentPreviewBinding>(
    FragmentPreviewBinding::inflate
) {

    private val orientations: SparseIntArray = SparseIntArray()
    private lateinit var cameraId: String
    private lateinit var imageDimension: Size
    private var cameraDevice: CameraDevice? = null
    private lateinit var captureRequestBuilder: CaptureRequest.Builder
    private lateinit var cameraCaptureSession: CameraCaptureSession
    private var backgroundHandler: Handler? = null
    private var backgroundThread: HandlerThread? = null
    private lateinit var file: File
    private var imageReader: ImageReader? = null

    init {
        orientations.append(Surface.ROTATION_0, 90)
        orientations.append(Surface.ROTATION_90, 0)
        orientations.append(Surface.ROTATION_180, 270)
        orientations.append(Surface.ROTATION_270, 180)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.previewTextureView.surfaceTextureListener = textureListener
//        binding.btnTakePhoto.setOnClickListener { takePicture() }
    }

    private fun startBackgroundThread() {
        backgroundThread = HandlerThread("SmartFLEX-Camera")
        backgroundThread?.start()
        backgroundHandler = Handler(backgroundThread!!.looper)
    }

    private fun stopBackgroundThread() {
        backgroundThread?.quitSafely()
        try {
            backgroundThread?.join()
            backgroundThread = null
            backgroundHandler = null
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
    }

    private val textureListener = object : TextureView.SurfaceTextureListener {
        override fun onSurfaceTextureAvailable(surface: SurfaceTexture, width: Int, height: Int) {
            openCamera()
        }

        override fun onSurfaceTextureSizeChanged(surface: SurfaceTexture, width: Int, height: Int) {
            //TODO: Transform you image captured size according to the surface width and height
        }

        override fun onSurfaceTextureDestroyed(surface: SurfaceTexture): Boolean = false

        override fun onSurfaceTextureUpdated(surface: SurfaceTexture) {

        }

    }

    private val cameraStateCallback = object : CameraDevice.StateCallback() {
        override fun onOpened(camera: CameraDevice) {
            Log.e(TAG, "onOpened")
            cameraDevice = camera
            createCameraPreview()
        }

        override fun onDisconnected(camera: CameraDevice) {
            cameraDevice?.close()
        }

        override fun onError(camera: CameraDevice, error: Int) {
            cameraDevice?.close()
            cameraDevice = null
        }

    }

//    private val cameraCaptureCallback = object : CameraCaptureSession.CaptureCallback() {
//        override fun onCaptureCompleted(session: CameraCaptureSession, request: CaptureRequest, result: TotalCaptureResult) {
//            super.onCaptureCompleted(session, request, result)
//            Toast.makeText(requireContext(), "Saved:" + file, Toast.LENGTH_SHORT).show();
//            createCameraPreview()
//        }
//    }

    private fun takePicture() {
        if (cameraDevice == null) {
            Log.e(TAG, "cameraDevice is null")
            return
        }
        val cameraManager = requireActivity().getSystemService(Context.CAMERA_SERVICE) as CameraManager
        try {
            val cameraCharacteristics = cameraManager.getCameraCharacteristics(cameraDevice!!.id)
            val jpegImageSizes = cameraCharacteristics.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP)?.getOutputSizes(ImageFormat.JPEG)
            var width = 640
            var height = 480
            if (jpegImageSizes != null && jpegImageSizes.isNotEmpty()) {
                width = jpegImageSizes[0].width
                height = jpegImageSizes[0].height
            }
            imageReader = ImageReader.newInstance(width, height, ImageFormat.JPEG, 1)
            val outputSurfaces = ArrayList<Surface>(2)
            outputSurfaces.add(imageReader!!.surface)
            outputSurfaces.add(Surface(binding.previewTextureView.surfaceTexture))

            val captureBuilder = cameraDevice?.createCaptureRequest(CameraDevice.TEMPLATE_STILL_CAPTURE)
            captureBuilder?.addTarget(imageReader!!.surface)
            captureBuilder?.set(CaptureRequest.CONTROL_MODE, CameraMetadata.CONTROL_MODE_AUTO)

            val rotation = requireActivity().windowManager.defaultDisplay.orientation
            captureBuilder?.set(CaptureRequest.JPEG_ORIENTATION, orientations.get(rotation))

            val imageFile = File("${Environment.getExternalStorageDirectory()} /image.jpg")
            val readerListener = ImageReader.OnImageAvailableListener {
                var image: Image? = null
                try {
                    image = imageReader?.acquireLatestImage()
                    val buffer = image!!.planes[0].buffer
                    val bytes = ByteArray(buffer.capacity())
                    buffer.get(bytes)
                    save(bytes, imageFile)
                } catch (e: FileNotFoundException) {
                    e.printStackTrace()
                } catch (e: IOException) {
                    e.printStackTrace()
                } finally {
                    image?.close()
                }
            }

            imageReader?.setOnImageAvailableListener(readerListener, backgroundHandler)
            val captureListener = object : CameraCaptureSession.CaptureCallback() {
                override fun onCaptureCompleted(session: CameraCaptureSession, request: CaptureRequest, result: TotalCaptureResult) {
                    super.onCaptureCompleted(session, request, result)
                    Toast.makeText(requireContext(), "Saved:" + file, Toast.LENGTH_SHORT).show();
                    createCameraPreview();
                }
            }
            cameraDevice?.createCaptureSession(outputSurfaces, object: CameraCaptureSession.StateCallback() {
                override fun onConfigured(session: CameraCaptureSession) {
                    try {
                        session.capture(captureBuilder!!.build(), captureListener, backgroundHandler)
                    } catch (e: CameraAccessException) {
                        e.printStackTrace()
                    }
                }

                override fun onConfigureFailed(session: CameraCaptureSession) {
                    Toast.makeText(requireContext(), "Configuration change", Toast.LENGTH_SHORT).show();
                }

            }, backgroundHandler)
        } catch (e: CameraAccessException) {
            e.printStackTrace()
        }
    }

    @Throws(IOException::class)
    private fun save(bytes: ByteArray, imageFile: File) {
        var outputStream: OutputStream? = null
        try {
            outputStream = FileOutputStream(imageFile)
            outputStream.write(bytes)
        } finally {
            outputStream?.close()
        }
    }

    private fun createCameraPreview() {
        try {
            val surfaceTexture = binding.previewTextureView.surfaceTexture
            surfaceTexture?.setDefaultBufferSize(imageDimension.width, imageDimension.height)
            val surface = Surface(surfaceTexture)
            captureRequestBuilder = cameraDevice!!.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW)
            captureRequestBuilder.addTarget(surface)
            cameraDevice!!.createCaptureSession(arrayOf(surface).toList(), object: CameraCaptureSession.StateCallback() {
                override fun onConfigured(session: CameraCaptureSession) {
                    //The camera is already closed
                    if (cameraDevice == null) {
                        return
                    }
                    // When the session is ready, we start displaying the preview.
                    cameraCaptureSession = session
                    updatePreview()
                }

                override fun onConfigureFailed(session: CameraCaptureSession) {
                    Toast.makeText(this@PreviewFragment.requireContext(), "Configuration change", Toast.LENGTH_SHORT).show();
                }

            }, null)
        } catch (e: CameraAccessException) {
            e.printStackTrace()
        }
    }

    @SuppressLint("MissingPermission")
    private fun openCamera() {
        val cameraManager = requireActivity().getSystemService(Context.CAMERA_SERVICE) as CameraManager
        Log.e(TAG, "Camera is open?")
        try {
            cameraId = cameraManager.cameraIdList[0]
            val cameraCharacteristics = cameraManager.getCameraCharacteristics(cameraId)
            val configurationMap = cameraCharacteristics.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP)
            imageDimension = configurationMap!!.getOutputSizes(SurfaceTexture::class.java)[0]
            cameraManager.openCamera(cameraId, cameraStateCallback, null)
        } catch (e: CameraAccessException) {
            Log.e(TAG, e.toString())
        }
    }

    private fun updatePreview() {
        if (cameraDevice == null) {
            Log.e(TAG, "updatePreview error, return");
        }
        captureRequestBuilder.set(CaptureRequest.CONTROL_MODE, CameraMetadata.CONTROL_MODE_AUTO)
        try {
            cameraCaptureSession.setRepeatingRequest(captureRequestBuilder.build(), null, backgroundHandler)
        } catch (e: CameraAccessException) {
            e.printStackTrace()
        }
    }

    private fun closeCamera() {
        if (cameraDevice != null) {
            cameraDevice?.close()
            cameraDevice = null
        }
        if (imageReader != null) {
            imageReader?.close()
            imageReader = null
        }
    }

    override fun onResume() {
        super.onResume()
        startBackgroundThread()
        if (binding.previewTextureView.isAvailable) {
            openCamera()
        } else {
            binding.previewTextureView.surfaceTextureListener = textureListener
        }
    }

    override fun onPause() {
        stopBackgroundThread()
        closeCamera()
        super.onPause()
    }

    companion object {
        private val TAG = PreviewFragment::class.java.canonicalName
        const val MAX_PREVIEW_WIDTH = 640
        const val MAX_PREVIEW_HEIGHT = 480
    }

    override fun onFabButton(fabButton: FloatingActionButton?) {
        fabButton?.hide()
    }

    override fun onToolbar(toolbar: Toolbar?) {

    }

}