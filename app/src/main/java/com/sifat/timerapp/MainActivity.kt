package com.sifat.timerapp

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.sifat.timerapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    private var isRunning = false
    private var timerSecond = 0
    private var handler = Handler(Looper.getMainLooper())


    private val runable = object : Runnable{
        override fun run() {
            timerSecond++

            val hours = timerSecond / 3600
            val minutes = (timerSecond % 3600) / 60
            val second = timerSecond % 60

            val time = String.format("%02d:%02d:%02d", hours, minutes, second)
            binding.tvDisplay.text = time

            handler.postDelayed(this, 1000)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.start.setOnClickListener {
            startTimer()
        }
        binding.stop.setOnClickListener {
            stopTimer()
        }
        binding.reset.setOnClickListener {
            resetTimer()
        }
    }

    private fun startTimer(){
        if (!isRunning) {
            handler.postDelayed(runable, 1000)
            isRunning = true
            binding.start.isEnabled = false
            binding.stop.isEnabled = true
            binding.reset.isEnabled = true
        }
    }


    private fun stopTimer(){
        if (isRunning){
            handler.removeCallbacks(runable)
            isRunning = false

        binding.start.isEnabled = true
        binding.start.text = "Resume"
        binding.stop.isEnabled = false
        binding.reset.isEnabled = true
        }
    }
    private fun resetTimer(){
        stopTimer()

        timerSecond = 0
        binding.tvDisplay.text ="00:00:00"
        binding.start.isEnabled = true
        binding.start.text = "Start"
        binding.stop.isEnabled = false
        binding.reset.isEnabled = false
    }
}