package com.example.cashluckpatrol

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Binder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import com.daimajia.androidanimations.library.Techniques
import com.daimajia.androidanimations.library.YoYo
import com.example.cashluckpatrol.databinding.ActivityFlashGame1Binding
import com.example.cashluckpatrol.databinding.ActivitySlotGame1Binding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.properties.Delegates

class FlashGame1Activity : AppCompatActivity() {

    lateinit var musicService: MusicService
    lateinit var scoreViewModel: ScoreViewModel
    lateinit var spinButton: ImageView
    var successGame by Delegates.notNull<Boolean>()
    var currentBet by Delegates.notNull<Int>()
    lateinit var soundHelper: SoundHelper
    var theEnd = 0
    var count = 0
    var gameWin = 0
    private val userViewModel by lazy {
        ViewModelProviders.of(this).get(ScoreViewModel::class.java)
    }
    lateinit var binding: ActivityFlashGame1Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFlashGame1Binding.inflate(layoutInflater)
        setContentView(binding.root)
        scoreViewModel = (application as MyApplication).scoreViewModel

        fun getStartBet(score: Int): Int {
            val currentBet = ((score / 100) * 5)
            val roundedBet = kotlin.math.round(currentBet / 10.0) * 10
            return roundedBet.toInt()
        }
        currentBet = getStartBet(scoreViewModel.getScore())


        binding.choosenBet.setText(currentBet.toString())
        val context: Context = this
        spinButton = binding.btnSpin
        spinButton.isEnabled = true
        soundHelper = (application as MyApplication).soundHelper


        val scope = CoroutineScope(Dispatchers.Main)
        val soundVolume = scoreViewModel.getSoundVolume() * 0.7f
        musicService = MusicService(soundVolume * 0.7f, R.raw.flash_1, this)
        musicService.playMusic(0)


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
            soundHelper.clickSound2(this, soundVolume)
            AnimationHelper.clickView(it, this)
            binding.choosenBet.setTextColor(Color.WHITE)
            currentBet += 10
            AnimationHelper.updateAnotherBetOrScore(currentBet, binding.choosenBet)
        }




        fun openOthersInLine(levelFlash1: LevelFlash1, listOfSenses: List<String>) {
            levelFlash1.imageList.forEach {
                if (it.isEnabled) {
                    val num = it.tag.toString().toInt()
                    val textResult: String = listOfSenses[num]
                    scope.launch {
                        AnimationHelper.rotateBackward(it)
                        delay(400)
                        levelFlash1.textList[num].text = textResult
                        delay(500)
                        AnimationHelper.updateScoreOrBetTextViewAnimation(
                            levelFlash1.levelScore,
                            "Win 0"
                        )
                    }
                }
            }
        }

        fun openOtherLines(levelFlash1: LevelFlash1, listOfSenses: MutableList<String>) {
            listOfLevels.shuffled()
            levelFlash1.imageList.forEach {
                if (it.isEnabled) {
                    val num = it.tag.toString().toInt()
                    val textResult: String = listOfSenses[num]
                    scope.launch {
                        AnimationHelper.rotateBackward(it)
                        delay(400)
                        levelFlash1.textList[num].text = textResult
                        delay(500)
                        AnimationHelper.updateScoreOrBetTextViewAnimation(
                            levelFlash1.levelScore,
                            "Win 0"
                        )
                    }
                }
            }
        }


        fun setResultAndCount(level: LevelFlash1) {
            level.imageList.forEach { it ->
                val listOfSenses = listOfPoints.shuffled()
                it.setOnClickListener {
                    it.isEnabled = false
                    val num = it.tag.toString().toInt()
                    val textResult: String = listOfSenses[num]

                    when (textResult) {
                        "+0" -> scope.launch {
                            AnimationHelper.rotateBackward(it)
                            delay(400)
                            level.textList[num].text = textResult
                            soundHelper.defeatFlash1(context, soundVolume)
                            YoYo.with(Techniques.Shake).duration(500).playOn(it)


                            val defeat = scoreViewModel.getScore() - currentBet
                            scoreViewModel.updateScore(defeat)
                            delay(400)
                            count = 0
                            AnimationHelper.updateScoreOrBetTextViewAnimation(
                                binding.winsCount,
                                count.toString()
                            )
                            openOthersInLine(level, listOfSenses)

                            for (i in 0 until level.currentLevel - 2) {
                                openOtherLines(listOfLevels[i], listOfPoints)
                            }
                            scoreViewModel.updateScore(scoreViewModel.getScore() - currentBet)
                            binding.incremBet.isEnabled = true
                            binding.decremBet.isEnabled = true
                            gameWin = 0
                        }


                        "+${(currentBet / 2).toInt()}" -> scope.launch {
                            AnimationHelper.rotateBackward(it)
                            delay(400)
                            level.textList[num].text = textResult
                            //findCorrectSound
                            delay(300)
                            it as ImageView
                            AnimationHelper.onRotatedCorrect(it, level.textList[num])
                            AnimationHelper.updateScoreOrBetTextViewAnimation(
                                level.levelScore,
                                textResult
                            )
                            gameWin += textResult.toInt()
                            openOthersInLine(level, listOfSenses)
                            count += 1

                            AnimationHelper.updateScoreOrBetTextViewAnimation(
                                binding.winsCount,
                                count.toString()
                            )
                            if (count == 5) {
                                scoreViewModel.updateScore(scoreViewModel.getScore() + gameWin)
                                count = 0
                                AnimationHelper.updateScoreOrBetTextViewAnimation(
                                    binding.winsCount,
                                    count.toString()
                                )
                                binding.incremBet.isEnabled = true
                                binding.decremBet.isEnabled = true
                                //sound and popup and vibration
                            }
                        }

                        "+$currentBet" -> scope.launch {
                            AnimationHelper.rotateBackward(it)
                            delay(400)
                            level.textList[num].text = textResult
                            //findCorrectSound
                            delay(300)
                            it as ImageView
                            AnimationHelper.onRotatedCorrect(it, level.textList[num])
                            AnimationHelper.updateScoreOrBetTextViewAnimation(
                                level.levelScore,
                                textResult
                            )
                            gameWin += textResult.toInt()
                            openOthersInLine(level, listOfSenses)
                            count += 1

                            AnimationHelper.updateScoreOrBetTextViewAnimation(
                                binding.winsCount,
                                count.toString()
                            )
                            if (count == 5) {
                                scoreViewModel.updateScore(scoreViewModel.getScore() + gameWin)
                                count = 0
                                AnimationHelper.updateScoreOrBetTextViewAnimation(
                                    binding.winsCount,
                                    count.toString()
                                )
                                binding.incremBet.isEnabled = true
                                binding.decremBet.isEnabled = true
                                //sound and popup and vibration
                            }
                        }
                    }
                }
            }
        }

        fun controller () {
            setResultAndCount(level1)
            setResultAndCount(level2)
            setResultAndCount(level3)
            setResultAndCount(level4)
            setResultAndCount(level5)

        }

        fun spinViews(list: MutableList<ImageView>) {
            soundHelper.rotateSound(context, soundVolume)
            list.forEach {
                scope.launch {
                    AnimationHelper.rotateForward(it)
                    delay(400)
                    it.setImageResource(R.drawable.quest_reversed)
                    it.isEnabled = true
                }
            }
        }
            spinButton.setOnClickListener {
                binding.choosenBet.setTextColor(resources.getColor(R.color.white))
                AnimationHelper.clickView(it, this)
                soundHelper.clickSound2(this, soundVolume)
                binding.incremBet.isEnabled = false
                binding.decremBet.isEnabled = false
                it.isEnabled = false
                listOfLines.forEach {
                    spinViews(it)
                    setResultAndCount(level1)
                    setResultAndCount(level2)
                    setResultAndCount(level3)
                    setResultAndCount(level4)
                    setResultAndCount(level5)

                }
                it.isEnabled = true
            }


        }

    }
