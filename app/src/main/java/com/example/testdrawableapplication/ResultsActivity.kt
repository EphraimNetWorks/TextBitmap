package com.example.testdrawableapplication

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_results.*
import java.io.File

class ResultsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_results)

        Glide.with(this)
            .load(File(intent.getStringExtra(EXTRA_PATH)?:""))
            .placeholder(R.drawable.ic_baseline_warning_24)
            .into(results)

    }

    companion object{
        private const val EXTRA_PATH = "path"
        fun newInstance(context: Context, resultsFilePath:String):Intent{
            return Intent(context, ResultsActivity::class.java).apply {
                putExtra(EXTRA_PATH, resultsFilePath)
            }
        }
    }
}