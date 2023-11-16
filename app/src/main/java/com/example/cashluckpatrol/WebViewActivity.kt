package com.example.cashluckpatrol

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.core.view.isVisible
import com.daimajia.androidanimations.library.Techniques
import com.daimajia.androidanimations.library.YoYo
import com.example.cashluckpatrol.databinding.ActivityWebViewBinding

class WebViewActivity : AppCompatActivity() {
    //TODO change on release!!!
    private val LINK = "https://sites.google.com/view/weewewef/%D0%B3%D0%BB%D0%B0%D0%B2%D0%BD%D0%B0%D1%8F-%D1%81%D1%82%D1%80%D0%B0%D0%BD%D0%B8%D1%86%D0%B0"
    private lateinit var acceptButton : ImageView
    lateinit var binding : ActivityWebViewBinding
    lateinit var soundHelper: SoundHelper
    lateinit var scoreViewModel : ScoreViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        val windowInsetsController =
            ViewCompat.getWindowInsetsController(window.decorView) ?: return
        // Configure the behavior of the hidden system bars
        windowInsetsController.systemBarsBehavior =
            WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        // Hide both the status bar and the navigation bar
        windowInsetsController.hide(WindowInsetsCompat.Type.systemBars())
        super.onCreate(savedInstanceState)
        binding = ActivityWebViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        acceptButton = binding.acceptButton
        acceptButton.isVisible = false
        binding.text.isVisible = false

        soundHelper = (application as MyApplication).soundHelper
        scoreViewModel = (application as MyApplication).scoreViewModel
        val soundVolume = scoreViewModel.getSoundVolume()
        val intensity = scoreViewModel.getVibroIntensity()
        val url = LINK // ccылка может храниться в любом месте
        val webView: WebView = findViewById(R.id.webView)



        webView.settings.javaScriptEnabled = true

        webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                var _url = ""
                if (url!=null) {
                    _url= url
                }
                else _url = ""

                view?.loadUrl(_url)
                return true
            }
        }

        webView.loadUrl(url)



        webView.viewTreeObserver.addOnScrollChangedListener {
            val totalHeight = webView.height
            val contentHeight = (webView.contentHeight * webView.scaleY).toInt()
            val scrollY = webView.scrollY

            val percentScrolled = (scrollY.toFloat() / (contentHeight - totalHeight)) * 100

            val threshold = 99
            if (percentScrolled >= threshold) {
                acceptButton.visibility = View.VISIBLE
                binding.text.visibility = View.VISIBLE
            } else {
                acceptButton.visibility = View.GONE
                binding.text.visibility = View.GONE
            }
        }




        acceptButton.setOnClickListener {
            AnimationHelper.smallClickView(it, this)
            soundHelper.vibroClick(intensity)
            soundHelper.clickSound(this, soundVolume)

            startActivity(Intent(this, AgreementActivity::class.java))

            // Я ввела змінну isPrivacyPolicyAccepted (лайвдата у вьюмоделі), яка змінюється на true після
            // того, як користувач на екрані AgreemenyACtivity погодиться із PrivacyPolicy. Нижче перевіряється цей
            // момент, і якщо користувач погодився, то завжди відкриватиметься меню ігор після вебв'ю

//            if(scoreViewModel.getPrivacyPolicyAccepted()) {
//                startActivity(Intent(this, PlayActivity ::class.java))
//            }


        }


    }

    override fun onPause() {
        super.onPause()
        val soundService = Intent (this, SoundService::class.java)
        soundService.action = "pauseMusic"
        startService(soundService)
    }

    override fun onResume() {
        super.onResume()
        val soundService = Intent (this, SoundService::class.java)
        soundService.action = "resumeMusic"
        startService(soundService)
    }

}