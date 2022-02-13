package com.mustafasehirli.kenny

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {
 
    var score=0
    val imageArray=ArrayList<ImageView>()
    var runnable= Runnable {  }
    var handler =Handler(Looper.myLooper()!!)
   lateinit var sp:SharedPreferences



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

      sp= this.getSharedPreferences("com.mustafasehirli.kenny",Context.MODE_PRIVATE)


        tapScore.text=sp.getInt("son",0).toString()


        imageArray.add(kenny1)
        imageArray.add(kenny2)
        imageArray.add(kenny3)
        imageArray.add(kenny4)
        imageArray.add(kenny5)
        imageArray.add(kenny6)
        imageArray.add(kenny7)
        imageArray.add(kenny8)
        imageArray.add(kenny9)

        hideImages()



        object : CountDownTimer(15000,1000){
            override fun onTick(p0: Long) {
                timeText.text="Time:${p0/1000}"
            }

            override fun onFinish() {
                timeText.text="Time:0"

                handler.removeCallbacks(runnable)

                for (i in imageArray){
                    i.visibility=View.INVISIBLE
                }

                val alert = AlertDialog.Builder(this@MainActivity)
                alert.setTitle("Gamer Over")
                alert.setMessage("Restart The Game?")
                alert.setPositiveButton("YES"){dialog,which ->
                    val intent =intent
                    finish()
                    startActivity(intent)
                }
                alert.setNegativeButton("NO"){dialog,which->
                    Toast.makeText(this@MainActivity,"Game Over",Toast.LENGTH_LONG).show()
                }
                alert.show()

            }


        }.start()

    }

    fun hideImages() {

        runnable=object :Runnable{
            override fun run() {
                for (i in imageArray){
                    i.visibility=View.INVISIBLE
                }
                val random =Random().nextInt(9)
                imageArray[random].visibility=View.VISIBLE
                handler.postDelayed(this,500)
            }

        }
        handler.post(runnable)


    }

    fun increaseScore(view: View){
        score++
        scoreText.text="Score: $score"
        tapScore(score)
    }

    fun tapScore(score:Int){
             val yaz=sp.edit()
             yaz.putInt("score",score).apply()

    }

}


