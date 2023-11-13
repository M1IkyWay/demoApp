package com.example.cashluckpatrol

import android.content.Context
import android.content.pm.ActivityInfo
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Vibrator
import android.os.VibratorManager
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProviders
import com.daimajia.androidanimations.library.Techniques
import com.daimajia.androidanimations.library.YoYo
import com.example.cashluckpatrol.databinding.ActivityFlashGame1Binding
import com.example.cashluckpatrol.databinding.ActivitySlotGame1Binding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.contracts.Returns
import kotlin.properties.Delegates

class FlashGame1Activity : AppCompatActivity() {

    lateinit var musicService: MusicService
    lateinit var scoreViewModel: ScoreViewModel
    lateinit var spinBtn: ImageView
    var successGame by Delegates.notNull<Boolean>()
    var currentBet by Delegates.notNull<Int>()
    lateinit var soundHelper: SoundHelper
    var theEnd = 0
    private var count = 0
    var gameWin = 0
    private val userViewModel by lazy {
        ViewModelProviders.of(this).get(ScoreViewModel::class.java)
    }
    lateinit var binding: ActivityFlashGame1Binding
    var resultG = 1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFlashGame1Binding.inflate(layoutInflater)
        setContentView(binding.root)

        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LOCKED

        if (savedInstanceState != null) {
            currentBet = savedInstanceState.getInt("currentBet")
            count = savedInstanceState.getInt("count")
            theEnd = savedInstanceState.getInt("theEndOfMusic")
            // и остальные
        }




        spinBtn = binding.btnSpin
        spinBtn.isEnabled = true
        scoreViewModel = (application as MyApplication).scoreViewModel

        fun getStartBet(score: Int): Int {
            val currentBet = ((score / 100) * 5)
            val roundedBet = kotlin.math.round(currentBet / 10.0) * 10
            return roundedBet.toInt()
        }
        currentBet = getStartBet(scoreViewModel.getScore())


        binding.choosenBet.setText(currentBet.toString())
        val context: Context = this

        soundHelper = (application as MyApplication).soundHelper

        val vibrator = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val vibratorManager =
                getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager
            vibratorManager.defaultVibrator
        } else {
            @Suppress("DEPRECATION")
            getSystemService(VIBRATOR_SERVICE) as Vibrator
        }


        val scope = CoroutineScope(Dispatchers.Main)
        val soundVolume = scoreViewModel.getSoundVolume() * 0.7f
        val intensity = scoreViewModel.getVibroIntensity()
        musicService = MusicService(soundVolume * 0.7f, R.raw.flash_1, this)
        musicService.playMusic(theEnd)


        val line1 =
            mutableListOf(binding.firstIn1, binding.secondIn1, binding.thirdIn1, binding.fourthIn1)
        val line2 =
            mutableListOf(binding.firstIn2, binding.secondIn2, binding.thirdIn2, binding.fourthIn2)
        val line3 =
            mutableListOf(binding.firstIn3, binding.secondIn3, binding.thirdIn3, binding.fourthIn3)
        val line4 =
            mutableListOf(binding.firstIn4, binding.secondIn4, binding.thirdIn4, binding.fourthIn4)
        val line5 =
            mutableListOf(binding.firstIn5, binding.secondIn5, binding.thirdIn5, binding.fourthIn5)
        val listOfLines = listOf(line1, line2, line3, line4, line5)

        val line1T = mutableListOf(
            binding.firstIn1T,
            binding.secondIn1T,
            binding.thirdIn1T,
            binding.fourthIn1T
        )
        val line2T = mutableListOf(
            binding.firstIn2T,
            binding.secondIn2T,
            binding.thirdIn2T,
            binding.fourthIn2T
        )
        val line3T = mutableListOf(
            binding.firstIn3T,
            binding.secondIn3T,
            binding.thirdIn3T,
            binding.fourthIn3T
        )
        val line4T = mutableListOf(
            binding.firstIn4T,
            binding.secondIn4T,
            binding.thirdIn4T,
            binding.fourthIn4T
        )
        val line5T = mutableListOf(
            binding.firstIn5T,
            binding.secondIn5T,
            binding.thirdIn5T,
            binding.fourthIn5T
        )
        val listOfLinesT = listOf(line1T, line2T, line3T, line4T, line5T)

        val level1 = LevelFlash1(line1, line1T, binding.sum1, 1)
        val level2 = LevelFlash1(line2, line2T, binding.sum2, 2)
        val level3 = LevelFlash1(line3, line3T, binding.sum3, 3)
        val level4 = LevelFlash1(line4, line4T, binding.sum4, 4)
        val level5 = LevelFlash1(line5, line5T, binding.sum5, 5)
        val listOfLevels = listOf(level1, level2, level3, level4, level5)

        var listOfPoints = mutableListOf("+0", "+0", "+${(currentBet / 2).toInt()}", "+$currentBet")

        scoreViewModel.score.observe(this) { newScore ->
            binding.resultBalance.setText("$newScore")
            AnimationHelper.updateScoreOrBetTextViewAnimation(
                binding.resultBalance,
                newScore.toString()
            )
        }

        fun updateCount(newValue: Int) {
            count = newValue
        }

        fun getCount(): Int {
            return count
        }
//        binding.incremBet.setOnClickListener {
//            //animation on touch
//            if (currentBet<=4950) {
//                currentBet+=50
//                binding.choosenBet.setText(currentBet.toString())
//            }
//            else {
//                //animation shake
//            }
//        }

        binding.decremBet.setOnClickListener {
            soundHelper.vibroClick(intensity)
            soundHelper.clickSound2(this, soundVolume)
            AnimationHelper.clickView(it, this)
            if (currentBet > 19) {
                currentBet -= 10
                AnimationHelper.updateAnotherBetOrScore(currentBet, binding.choosenBet)
            } else {
                AnimationHelper.wrongInputAnimation(binding.choosenBet)
                soundHelper.wrongInputSound(this, soundVolume)
                val toast = Toast.makeText(this, "Minimal bet is 10", Toast.LENGTH_SHORT)
                toast.show()
            }
        }

        binding.incremBet.setOnClickListener {
            soundHelper.vibroClick(intensity)
            soundHelper.clickSound2(this, soundVolume)
            AnimationHelper.clickView(it, this)
            binding.choosenBet.setTextColor(Color.WHITE)
            currentBet += 10
            AnimationHelper.updateAnotherBetOrScore(currentBet, binding.choosenBet)
        }

        fun randomizeList(): List<String> {
            val usableList = listOfPoints.shuffled()
            return usableList
        }

        suspend fun rotator(
            level: LevelFlash1,
            view: View,
            textResult: String,
            num: Int,
            drawable: Drawable
        ) {
            level.textList[num].text = textResult
            Log.d("aaaaaaaaaaaaaaaaaaaa", "textresult is $textResult")
            view as ImageView
            AnimationHelper.rotateBackward(view)
            delay(400)
            view.setImageDrawable(drawable)
            level.textList[num].isVisible = true
            Log.d(
                "aaaaaaaaaaaaa in openOtherLines",
                "in rotator text is ${level.textList[num].isVisible}"
            )
            Log.d("aaaaaaaaaaaaaaaaaaaa", "textlist became visible")
        }


        fun openOthersInLine(levelFlash1: LevelFlash1, listOfSenses: List<String>) {
            levelFlash1.imageList.forEach {
                if (it.isEnabled) {
                    it.isEnabled = false
                    val num = it.tag.toString().toInt()
                    val textResult: String = listOfSenses[num]
                    val drawable: Drawable =
                        AppCompatResources.getDrawable(this, R.drawable.butt_flash1)!!
                    scope.launch {
                        rotator(levelFlash1, it, textResult, num, drawable)
                        delay(400)
//                        AnimationHelper.updateScoreOrBetTextViewAnimation(
//                            levelFlash1.levelScore,
//                            "Win 0"
//                        )
                    }
                }
            }
        }

        fun openOtherLines(levelFlash1: LevelFlash1) {

            for (i in levelFlash1.currentLevel until listOfLevels.size) {

                val list = randomizeList()

                Log.d(
                    "aaaaaaaaaaaaa",
                    "this is list of strings in level ${list.forEach { it.toString() }}"
                )
                listOfLevels[i].imageList.forEach {
                    scope.launch {
                        it.isEnabled = false
                        val num = it.tag.toString().toInt()
                        val drawable: Drawable =
                            AppCompatResources.getDrawable(context, R.drawable.butt_flash1)!!
                        val textResult: String = list[num]

                        listOfLevels[i].textList[num].text = textResult
                        Log.d("aaaaaaaaaaaaaaaaaaaa", "textresult is $textResult")
                        AnimationHelper.rotateBackward(it)
                        delay(400)
                        it.setImageDrawable(drawable)
                        listOfLevels[i].textList[num].isVisible = true
                        delay(300)
                        Log.d(
                            "aaaaaaaaaaaaa in openOtherLines",
                            "in scope text is ${listOfLevels[i].textList[num].isVisible}"
                        )
                        listOfLevels.forEach {
                            AnimationHelper.updateScoreOrBetTextViewAnimation(
                                it.levelScore,
                                "Win 0"
                            )
                        }

                    }
                }
            }
            binding.incremBet.isEnabled = true
            binding.decremBet.isEnabled = true
            spinBtn.isEnabled = true

        }

        fun updateResult(resultL: String) {
            if (resultL.equals("+0")) {
                resultG = 0
            } else {
                resultG = 1
            }
        }


        fun getResult(): Int {
            return resultG
        }


        suspend fun setResultAndCount(level: LevelFlash1) {

            fun openNext(prevLevel: LevelFlash1, doingBusiness: (LevelFlash1) -> Unit) {
                Log.d("in open next now", "aaaaaaaaaaaa, opennexttttttttttttttttt")
                val indexCurr = prevLevel.currentLevel
                val currentLevel = listOfLevels[indexCurr]
                doingBusiness(currentLevel)
            }

            fun doBusiness(level: LevelFlash1) {
                val listOfP = randomizeList()

                level.imageList.forEach { it ->
                    it.isEnabled = true
                    val num = it.tag.toString().toInt()
                    val textResult: String = listOfP[num]

                    it.setOnClickListener {
                        it.isEnabled = false
                        Log.d(
                            "in setonclick listener",
                            "it was $textResult nowaaaaaaaaaaaaaaaaaaaaaaaaaaaa, ${getResult()}"
                        )
                        val drawable: Drawable =
                            AppCompatResources.getDrawable(
                                context,
                                R.drawable.butt_flash1
                            )!!

                        if (textResult.equals("+0")) {
//                                previousWin = false
                            Log.d(
                                "in result 0",
                                "it was 0000 nowaaaaaaaaaaaaaaaaaaaaaaaaaaaa, , ${getResult()}"
                            )
                            scope.launch {
                                rotator(level, it, textResult, num, drawable)
                                delay(400)
                                soundHelper.defeatFlash1(context, soundVolume)
                                soundHelper.vibroExplosion(intensity, vibrator)
                                YoYo.with(Techniques.Shake).duration(500).playOn(it)

                                val defeat = scoreViewModel.getScore() - currentBet

                                delay(800)
                                updateCount(0)
                                scoreViewModel.updateScore(defeat)
                                AnimationHelper.updateScoreOrBetTextViewAnimation(
                                    binding.winsCount,
                                    getCount().toString()
                                )
                                openOthersInLine(level, listOfPoints)
                                openOtherLines(level)
                                requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
                            }
//                            scoreViewModel.updateScore(scoreViewModel.getScore() - currentBet)
                            gameWin = 0
                            updateResult(textResult)

                        } else {
//                                previousWin = true
                            Log.d(
                                "in result else",
                                "it was norm nowaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
                            )

                            updateResult(textResult)
                            scope.launch {
                                rotator(level, it, textResult, num, drawable)
                                soundHelper.vibroClick(intensity)
                                //findCorrectSound
                                delay(400)
                                it as ImageView
                                AnimationHelper.onRotatedCorrect(
                                    it,
                                    level.textList[num]
                                )
                                AnimationHelper.updateScoreOrBetTextViewAnimation(
                                    level.levelScore,
                                    textResult
                                )
                                gameWin += textResult.toInt()
                                openOthersInLine(level, listOfPoints)
                                updateCount(getCount() + 1)

                                AnimationHelper.updateScoreOrBetTextViewAnimation(
                                    binding.winsCount,
                                    getCount().toString()
                                )

                                if (getCount() == 5) {
                                    scoreViewModel.updateScore(scoreViewModel.getScore() + gameWin)
                                    updateCount(0)
                                    AnimationHelper.updateScoreOrBetTextViewAnimation(
                                        binding.winsCount,
                                        getCount().toString()
                                    )
                                    binding.incremBet.isEnabled = true
                                    binding.decremBet.isEnabled = true
                                    spinBtn.isEnabled = true
                                    requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
                                    scoreViewModel.updateLevel(scoreViewModel.getLevel() + 1)
                                    //sound and popup and vibration
                                } else {

                                    openNext(level, ::doBusiness)
                                }
                            }
                        }
                    }
                }
            }

            doBusiness(level)

//            binding.decremBet.isEnabled = true
//            binding.incremBet.isEnabled = true

        }


//        suspend fun controller() {
//            var resul : Int = 1
//
//            scope.launch {
//                for (i in 0 until listOfLevels.size) {
//                        val job1 = setResultAndCount(listOfLevels[i], resul)
//                        job1.join()
//                        resul = getResult()
//                        Log.d(
//                            "in controller",
//                            "it have just finishedto work, and value was taken  nowaaaaaaaaaaaaaaaaaaaaaaaaaaaa, $resul")
//
//                    }
//
//                }
//            spinBtn.isEnabled = true
//            }


        fun spinViews(list: MutableList<ImageView>) {
            soundHelper.rotateSound(context, soundVolume)
            list.forEach {
                it.isEnabled = false
                scope.launch {
                    AnimationHelper.rotateForward(it)
                    delay(400)
                    it.setImageResource(R.drawable.quest_reversed)
                    listOfLevels.forEach {
                        it.textList.forEach {
                            it.isVisible = false
                        }
                    }
                }
                level1.imageList.forEach {
                    it.isEnabled = true
                }

            }
        }

        var isAllowedExecute = true

        spinBtn.setOnClickListener {

            if (isAllowedExecute) {
                isAllowedExecute = false



                it.isEnabled = false
                binding.incremBet.isEnabled = false
                binding.decremBet.isEnabled = false
                resultG = 0
                updateCount(0)
                binding.choosenBet.setTextColor(resources.getColor(R.color.white))
                binding.buttText?.setText("Spin")
                AnimationHelper.clickView(it, this)
                soundHelper.vibroClick(intensity)
                soundHelper.clickSound2(this, soundVolume)

                scope.launch {
                    requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LOCKED
                    listOfLevels.forEach {


                        it.textList.forEach {
                            it.isVisible = false
                        }
                        delay(50)
                        it.textList.forEach {
                            it.isVisible = false
                        }
                        spinViews(it.imageList)
                        it.levelScore.text = "Win 0"

                    }

                    setResultAndCount(level1)

//                scope.launch {
//                    val pred = 1
//                    setResultAndCount(level1, pred)
//                    delay(1000)
//
//                    if (getResult() > 0) {
//                        setResultAndCount(level2, getResult())
//                        delay(1000)
//
//
//                        if (getResult() > 0) {
//                            val res3 = setResultAndCount(level3, getResult())
//                            delay(1000)
//
//
//                            if (getResult() > 0) {
//                                val res4 = setResultAndCount(level4, getResult())
//                                delay(1000)
//
//
//                                if (getResult() > 0) {
//                                    val res5 = setResultAndCount(level5, getResult())
//                                    delay(1000)
//
//                                }
//
//                                else {
//                                    openOtherLines(level5, listOfPoints)
//                                }
//                            }
//
//                            else {
//                                openOtherLines(level4, listOfPoints)
//                                openOtherLines(level5, listOfPoints)
//                            }
//
//
//                        }
//                        else {
//                            openOtherLines(level3, listOfPoints)
//                            openOtherLines(level4, listOfPoints)
//                            openOtherLines(level5, listOfPoints)
//                        }
//
//
//
//                    }
//                    else {
//                        openOtherLines(level2, listOfPoints)
//                        openOtherLines(level3, listOfPoints)
//                        openOtherLines(level4, listOfPoints)
//                        openOtherLines(level5, listOfPoints)
                    Log.d("bbbbbbbbbbbbbbbbb", "the button spin finished it's work ")
                    isAllowedExecute = true
//            binding.decremBet.isEnabled = true


                }
//                    it.isEnabled = true
//            binding.incremBet.isEnabled = true
//


            }



        }
    }

        override fun onPause() {
            super.onPause()
            theEnd = musicService.findTheEnd()
            musicService.stopMusic()
            soundHelper.pause()

        }

        override fun onSaveInstanceState(outState: Bundle) {
            outState.putInt("currentBet", currentBet)
            outState.putInt("count", count)
            outState.putInt("theEndOfMusic", theEnd)

            super.onSaveInstanceState(outState)

        }

        override fun onResume() {
            super.onResume()
            musicService.playMusic(theEnd)
            soundHelper.resume()

}
        override fun onDestroy() {
            super.onDestroy()
            soundHelper.pause()
        }



}