package com.example.gifgif

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.MotionEvent
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_spalsh.*

class SpalshActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_spalsh)
        val uri = Uri.parse("android.resource://" + packageName + "/" + R.raw.giphyintro)
//        splashVideo.setVideoURI(uri)
//        splashVideo.setOnCompletionListener { splashVideo.start() }
//        splashVideo.start()
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        jump()
        return true
    }


    private fun jump() {
        if (isFinishing) return
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}