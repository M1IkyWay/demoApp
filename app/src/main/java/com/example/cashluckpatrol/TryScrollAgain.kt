package com.example.cashluckpatrol

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.graphics.drawable.AnimationDrawable
//import android.support.v7.app.AppCompatActivity
import android.os.Handler
import android.support.design.widget.Snackbar
import android.widget.Toast
import com.example.cashluckpatrol.databinding.ActivityTryScrollAgainBinding
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

    var random = Random()
    var slot1: Int = 0
    var slot2: Int = 0
    var slot3: Int = 0
    var ganancias: Int = 10000

    class TryScrollAgain : AppCompatActivity() {

        lateinit var binding: ActivityTryScrollAgainBinding

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            binding = ActivityTryScrollAgainBinding.inflate(layoutInflater)
            setContentView(binding.root)

            binding.mainActivityBtJugar.setOnClickListener({

                binding.mainActivityTvGanancias.text = "100"

                var toast =
                    Toast.makeText(this, "Has lanzado, descontado 1000 euros", Toast.LENGTH_SHORT)
                toast.show()

                binding.mainActivitySlot1.setImageResource(R.drawable.animation)
                val slot1Anin: AnimationDrawable = binding.mainActivitySlot1.drawable as AnimationDrawable
                slot1Anin.start()

                binding.mainActivitySlot2.setImageResource(R.drawable.animation)
                val slot2Anin: AnimationDrawable = binding.mainActivitySlot2.drawable as AnimationDrawable
                slot2Anin.start()

                binding.mainActivitySlot3.setImageResource(R.drawable.animation)
                val slot3Anin: AnimationDrawable = binding.mainActivitySlot3.drawable as AnimationDrawable
                slot3Anin.start()

                var handler = Handler()
                handler.postDelayed({
                    slot1Anin.stop()
                    slot2Anin.stop()
                    slot3Anin.stop()
                    toast.cancel()

                    setImagenes()
                    obtenerPutuacion()

                }, 1000)

            })
        }

        private fun setImagenes() {
            slot1 = random.nextInt(5)
            slot2 = random.nextInt(5)
            slot3 = random.nextInt(5)


            when (slot1) {
                0 -> binding.mainActivitySlot1.setImageResource(R.drawable.hot_im1)
                1 -> binding.mainActivitySlot1.setImageResource(R.drawable.hot_im2)
                2 -> binding.mainActivitySlot1.setImageResource(R.drawable.hot_im3)
                3 -> binding.mainActivitySlot1.setImageResource(R.drawable.hot_im4)
                4 -> binding.mainActivitySlot1.setImageResource(R.drawable.hot_im5)
            }

            when (slot2) {
                0 -> binding.mainActivitySlot1.setImageResource(R.drawable.hot_im1)
                1 -> binding.mainActivitySlot1.setImageResource(R.drawable.hot_im2)
                2 -> binding.mainActivitySlot1.setImageResource(R.drawable.hot_im3)
                3 -> binding.mainActivitySlot1.setImageResource(R.drawable.hot_im4)
                4 -> binding.mainActivitySlot1.setImageResource(R.drawable.hot_im5)
            }

            when (slot3) {
                0 -> binding.mainActivitySlot1.setImageResource(R.drawable.hot_im1)
                1 -> binding.mainActivitySlot1.setImageResource(R.drawable.hot_im2)
                2 -> binding.mainActivitySlot1.setImageResource(R.drawable.hot_im3)
                3 -> binding.mainActivitySlot1.setImageResource(R.drawable.hot_im4)
                4 -> binding.mainActivitySlot1.setImageResource(R.drawable.hot_im5)
            }
        }

        private fun obtenerPutuacion() {
            if ((slot1 == slot2) && (slot2 == slot3)) {
                var snackBar =
                    Snackbar.make(mainActivityRl, "Has ganado 100000", Snackbar.LENGTH_SHORT)
                var snackBarView = snackBar.view
                snackBarView.setBackgroundColor(resources.getColor(R.color.colorPrimary))
                snackBar.show()
                ganancias = ganancias + 100000
            } else if ((slot1 == slot2) || (slot2 == slot3) || (slot1 == slot3)) {
                var snackBar =
                    Snackbar.make(mainActivityRl, "Has ganado 5000", Snackbar.LENGTH_SHORT)
                var snackBarView = snackBar.view
                snackBarView.setBackgroundColor(resources.getColor(R.color.colorAccent))
                snackBar.show()
                ganancias = ganancias + 5000
            }

            ganancias = ganancias - 1000
            binding.mainActivityTvGanancias.text = binding.ganancias.toString()
        }
    }
}