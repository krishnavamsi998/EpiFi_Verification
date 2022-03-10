package com.example.epifi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.SpannableString
import android.text.style.LeadingMarginSpan
import android.view.View
import com.example.epifi.databinding.ActivityIntroBinding

class IntroActivity : AppCompatActivity() {
    private lateinit var  binding : ActivityIntroBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        binding = ActivityIntroBinding.inflate(layoutInflater)
        binding.intro.text = setSpan(getString(R.string.intro))

        setContentView(binding.root)

    }

    private fun setSpan(str : String): SpannableString{
        val v = SpannableString(str)
        v.setSpan(LeadingMarginSpan.Standard(0,60),0,str.length,0)
        return v
    }
    fun getStarted(view: View) {
        val intent = Intent(this,MainActivity::class.java)
        startActivity(intent)
    }
}