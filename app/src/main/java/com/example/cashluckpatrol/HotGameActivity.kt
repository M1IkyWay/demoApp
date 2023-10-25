package com.example.cashluckpatrol

import android.app.AlertDialog
import android.content.Intent
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import androidx.core.content.ContextCompat
import com.example.cashluckpatrol.databinding.ActivityHotGameBinding
import java.security.SecureRandom
import java.util.HashMap

class HotGameActivity : AppCompatActivity() {

    lateinit var spinButton : ImageView
    lateinit var binding : ActivityHotGameBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHotGameBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        val selectedBet : ImageButton? = null

        fun betClicked (imageButton : View) {
            val bet = imageButton.tag.toString()
            binding.choosenBet.setText(bet)

        }

        val betList = listOf(binding.bet50, binding.bet100, binding.bet150, binding.bet200, binding.bet250,
            binding.bet300, binding.bet350, binding.bet500)

        betList.forEach {
            it.setOnClickListener {
                betClicked(it)

            }
        }
        spinButton = binding.btnSpin
//        if (savedInstanceState == null) {
//            mScores = resources.getInteger(R.integer.default_score)
//            setScoreAmounts()
//        }

        setupSlotsMachine()


    }

//    override fun onBackPressed() {
//        super.onBackPressed()
//        finish()
//    }

//    override fun onSaveInstanceState(outState: Bundle) {
//        super.onSaveInstanceState(outState)
//        outState.putInt(CREDIT_EXTRA, mScores)
//        outState.putInt(BET_INDEX_EXTRA, mBetIndex)
//    }

//    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
//        super.onRestoreInstanceState(savedInstanceState)
//        mScores = savedInstanceState.getInt(CREDIT_EXTRA)
//        mBetIndex = savedInstanceState.getInt(BET_INDEX_EXTRA)
//        setScoreAmounts()
//    }

//    private fun decreaseScores() {
//        mScores -= mBets[mBetIndex]
//        if (mBets[mBetIndex] > mScores) mBetIndex = 0
//        setScoreAmounts()
//        if (mScores == 0) mSpinButton.isEnabled = false
//    }

//    private fun updateBet() {
//        val size = mBets.size
//        mBetIndex = if (mBetIndex >= size - 1) 0 else mBetIndex + 1
//        if (mBets[mBetIndex] > mScores) mBetIndex = 0
//        setScoreAmounts()
//    }

//    private fun setScoreAmounts() {
//        mCreditView.text = mScores.toString()
//        mBetView.text = mBets[mBetIndex].toString()
//    }


    private fun setupSlotsMachine() {
        val builder = SlotsBuilder().Builder(this)
        val slots = builder
            .addSlots(R.id.slot_one, R.id.slot_two, R.id.slot_three)
            .addDrawables(
                R.drawable.hot_im1,
                R.drawable.hot_im2,
                R.drawable.hot_im3,
                R.drawable.hot_im4,
                R.drawable.hot_im5
            )
            .setScrollTimePerInch(1f)
            .setDockingTimePerInch(0f)
            .setScrollTime(500 + SecureRandom().nextInt(1500))
            .setChildIncTime(1000)
            .setOnFinishListener(object : Callback() {
                override fun onFinishListener() {
                    val layoutManagers = getLayoutManagers()
                    val match = HashMap<Int, Int>()

                    for (i in 0 until 3) {
                        val imageView = layoutManagers?.get(i)
                            ?.findViewByPosition(
                                (layoutManagers.get(i)
                                    .findFirstVisibleItemPosition() + 3)
                            ) as ImageView
                        val drawableId = imageView.tag as Int

                        match[drawableId] = match.getOrDefault(drawableId, 0) + 1
                    }

                    var resultMatch = 0
                    match.values.forEach {
                            value ->
                        if (resultMatch < value ) {
                            resultMatch = value
                        }
                    }

                    if (resultMatch == 3 && !binding.btnSpin.isEnabled) {
                        binding.btnSpin.isEnabled = true
                    //bet * 2

                    //add visual changes
                    }

//                    setScoreAmounts()
                }
            })
            .build()

        spinButton.setOnClickListener {

            slots.start()
        }
//        findViewById<Button>(R.id.btn_menu).setOnClickListener {
//            startActivity(Intent(this, MenuActivity::class.java))
//        }
//        findViewById<Button>(R.id.btn_bet).setOnClickListener { updateBet() }
//
//        mSpinButton.setOnClickListener {
//            decreaseScores()
//            slots.start()
//        }
    }

//    private fun showScoresDialog() {
//        val dialog = AlertDialog.Builder(this)
//            .setMessage(getString(R.string.scores_dialog_text))
//            .setPositiveButton(
//                getString(R.string.play_btn_title)) { dialog, whicn ->
//                startActivity(GameActivity::class.java)
//            }
//            .setNegativeButton(
//                getString(R.string.menu_btn_title)) {dialog, whicn ->
//                startActivity(MenuActivity::class.java)
//
//            }
//
//        dialog.setCancelable(false)
//        dialog.show()
//    }

//    private fun startActivity(targetActivity: Class<out AppCompatActivity>) {
//        val intent = Intent(this, targetActivity)
//        startActivity(intent)
//        finish()
//    }
}










