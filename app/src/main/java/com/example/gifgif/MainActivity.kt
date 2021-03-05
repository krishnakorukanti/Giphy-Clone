package com.example.gifgif

import android.content.Intent
import android.graphics.drawable.Drawable
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import androidx.core.view.MotionEventCompat
import com.bumptech.glide.Glide
import com.giphy.sdk.core.models.Media
import com.giphy.sdk.core.models.enums.MediaType
import com.giphy.sdk.ui.GPHContentType
import com.giphy.sdk.ui.GPHSettings
import com.giphy.sdk.ui.Giphy
import com.giphy.sdk.ui.GiphyLoadingProvider
import com.giphy.sdk.ui.pagination.GPHContent
import com.giphy.sdk.ui.themes.GPHTheme
import com.giphy.sdk.ui.themes.GridType
import com.giphy.sdk.ui.views.GiphyDialogFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    companion object {
        val TAG = MainActivity::class.java.simpleName
        val YOUR_API_KEY = "MguyuQd6ct9bF4GG5mmVJbB0LwGCn6Hd"
    }
    lateinit var shareMedia : Media
    var settings = GPHSettings(gridType = GridType.waterfall, useBlurredBackground = false, theme = GPHTheme.Light, stickerColumnCount = 3)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Giphy.configure(this, YOUR_API_KEY, true)

        setupToolbar()
        displayGiphy()
        shareBtn.setOnClickListener {
            try {
                val uri : Uri = Uri.parse(shareMedia.images.original?.mp4Url)

              val sendIntent : Intent = Intent().apply {
                  action = Intent.ACTION_SEND

                  putExtra(Intent.EXTRA_TEXT,shareMedia.images.original?.gifUrl+"\nSent Using GIF-GIF")
                  type ="text/plain"

              }
                val shareIntent = Intent.createChooser(sendIntent, "Share through these applications")
                startActivity(shareIntent)
            }catch (e:Exception){
                Log.d("tag",e.message.toString())
            }
        }
        displayGiphyBtn.setOnClickListener {
            displayGiphy()
        }

//       gifsGridView?.content = GPHContent.searchQuery("dogs",MediaType.gif)

        relativeParent.setOnTouchListener(object : OnSwipeTouchListener(this){


            override fun onSwipeUp() {
               displayGiphy()
            }



        })




    }

//    private fun setTrendingQuery() {
//        gifsGridView.content = when (DemoConfiguration.contentType) {
//            GPHContentType.gif -> GPHContent.trendingGifs
//            GPHContentType.sticker -> GPHContent.trendingStickers
//            GPHContentType.text -> GPHContent.trendingText
//            GPHContentType.emoji -> GPHContent.emoji
//            GPHContentType.recents -> GPHContent.recents
//            else -> throw Exception("MediaType ${DemoConfiguration.mediaType} not supported ")
//        }
//    }

//    override fun onTouchEvent(event: MotionEvent?): Boolean {
//        val action : Int = MotionEventCompat.getActionMasked(event)
//        return when(action){
//            MotionEvent.ACTION_UP ->{
//                displayGiphy()
//                true
//            }else -> super.onTouchEvent(event)
//        }
//    }
    private fun displayGiphy(){
        val dialog = GiphyDialogFragment.newInstance(settings)
        dialog.gifSelectionListener = getGifSelectionListener()
        dialog.show(supportFragmentManager, "gifs_dialog")
    }
    private fun getGifSelectionListener() = object : GiphyDialogFragment.GifSelectionListener {
        override fun onGifSelected(
            media: Media,
            searchTerm: String?,
            selectedContentType: GPHContentType
        ) {
            try {
                shareMedia = media

                Glide.with(gifShareImg).load(media.images.original?.gifUrl).placeholder(R.drawable.ic_baseline_cached_24).into(gifShareImg)
                shareBtn.visibility = View.VISIBLE


            }catch (e : Exception){
                Toast.makeText(this@MainActivity,e.message,Toast.LENGTH_SHORT).show()
            }


//            gifOne.setMedia(media)
//            gifOne.isBackgroundVisible = false

           }

        override fun onDismissed(selectedContentType: GPHContentType) {
            Log.d(TAG, "onDismissed")
        }

        override fun didSearchTerm(term: String) {
            Log.d(TAG, "didSearchTerm $term")
        }
    }

    private fun setupToolbar() {
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }
    private val loadingProviderClient = object : GiphyLoadingProvider {
        override fun getLoadingDrawable(position: Int): Drawable {
            return LoadingDrawable(if (position % 2 == 0) LoadingDrawable.Shape.Rect else LoadingDrawable.Shape.Circle)
        }
    }

}