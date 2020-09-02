package com.example.testdrawableapplication

import android.graphics.Bitmap
import android.graphics.Typeface
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.FontRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.drawToBitmap
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.background_color_items.*
import kotlinx.android.synthetic.main.font_items.*
import kotlinx.android.synthetic.main.font_style_items.*
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupAutoresize()

        setUpColorClickListeners()

        setUpFontClickListeners()

        setUpFontStyleClickListeners()

        save_btn.setOnClickListener {
            if (canvas_text.text.isNotBlank()) {
                canvas_text.isCursorVisible = false
                val result = saveTextDrawable()
                canvas_text.isCursorVisible = true
                startActivity(ResultsActivity.newInstance(this, result?.absolutePath ?: ""))
            }else{
                Toast.makeText(this,"Enter some text", Toast.LENGTH_LONG).show()
            }
        }

    }

    private fun setUpColorClickListeners(){
        bg_color_red.setOnClickListener{
            canvas.setBackgroundColor(ContextCompat.getColor(this, R.color.tsu_red))

        }
        bg_color_blue.setOnClickListener{
            canvas.setBackgroundColor(ContextCompat.getColor(this, R.color.tsu_blue))
        }
        bg_color_green.setOnClickListener{
            canvas.setBackgroundColor(ContextCompat.getColor(this, R.color.tsu_green))
        }
        bg_color_yellow.setOnClickListener{
            canvas.setBackgroundColor(ContextCompat.getColor(this, R.color.tsu_yellow))
        }
    }

    private fun setUpFontClickListeners(){

        font_alef.setOnClickListener{
            updateFont(R.font.alef)
        }

        font_pacifico.setOnClickListener{
            updateFont( R.font.pacifico)
        }

        font_dancing_script.setOnClickListener{
            updateFont( R.font.dancing_script)
        }

        font_roboto.setOnClickListener{
            updateFont( R.font.roboto)
        }

    }

    private fun setUpFontStyleClickListeners(){

        font_bold.setOnClickListener{
            updateFontStyle(canvas_text.typeface, Typeface.BOLD)
        }

        font_italic.setOnClickListener{
            updateFontStyle(canvas_text.typeface, Typeface.ITALIC)
        }

        font_regular.setOnClickListener{
            updateFontStyle(Typeface.create(canvas_text.typeface, Typeface.NORMAL),Typeface.NORMAL)
        }

    }

    private fun updateFont(@FontRes fontResId:Int){
        canvas_autosizing_text_helper.typeface = ResourcesCompat.getFont(this, fontResId)
        canvas_text.typeface = ResourcesCompat.getFont(this, fontResId)
        autosizeText()
    }

    private fun updateFontStyle(typeface: Typeface, style:Int){
        canvas_autosizing_text_helper.setTypeface(typeface, style)
        canvas_text.setTypeface(typeface, style)
        autosizeText()
    }

    private fun setupAutoresize() {
        canvas_autosizing_text_helper.setText(getString(R.string.canvas_text_hint), TextView.BufferType.EDITABLE)
        autosizeText()
        canvas_text.hint = getString(R.string.canvas_text_hint)

        canvas_text.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(editable: Editable?) {
                autosizeText()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val text = if (s.isNullOrEmpty()) getString(R.string.canvas_text_hint) else s.toString()
                canvas_autosizing_text_helper.setText(text, TextView.BufferType.EDITABLE)
            }
        })
    }

    private fun autosizeText() {
        val textSize =  canvas_autosizing_text_helper.textSize / (resources.displayMetrics.density + 0.2f)
        canvas_text.textSize = textSize
    }

    private fun saveTextDrawable(): File?{

        val bitmap = canvas.drawToBitmap()

        val fileName = "IMG_${System.currentTimeMillis()}.png"
        return try {
            val file = File(externalCacheDir.toString() + File.separator + fileName)
            file.createNewFile()

            val bos = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.PNG, 0, bos)
            val bitmapData = bos.toByteArray()

            val fos = FileOutputStream(file)
            fos.write(bitmapData)
            fos.flush()
            fos.close()
            file
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }


}