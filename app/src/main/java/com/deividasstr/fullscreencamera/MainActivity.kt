package com.deividasstr.fullscreencamera

import android.Manifest
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.livinglifetechway.quickpermissions.annotations.WithPermissions
import io.fotoapparat.Fotoapparat
import io.fotoapparat.parameter.ScaleType
import io.fotoapparat.selector.back
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private lateinit var fotoapparat: Fotoapparat

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        GlobalScope.launch {
            while (true) {
                delay(3000)
                withContext(Dispatchers.Main) {
                    fullScreen()
                }
            }
        }

        fotoapparat = Fotoapparat(
            context = this,
            view = camera_view,                   // view which will draw the camera preview
            scaleType = ScaleType.CenterCrop,    // (optional) we want the preview to fill the view
            lensPosition = back(),               // (optional) we want back camera
            cameraErrorCallback = { error ->
                error.printStackTrace()
            }
        )
    }

    private fun fullScreen() {
        window?.decorView?.systemUiVisibility =
            View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_FULLSCREEN
    }

    override fun onResume() {
        super.onResume()
        startCamera()
    }

    @WithPermissions(permissions = [Manifest.permission.CAMERA])
    private fun startCamera() {
        fotoapparat.start()
    }

    override fun onPause() {
        super.onPause()
        stopCamera()
    }

    @WithPermissions(permissions = [Manifest.permission.CAMERA])
    private fun stopCamera() {
        fotoapparat.stop()
    }
}