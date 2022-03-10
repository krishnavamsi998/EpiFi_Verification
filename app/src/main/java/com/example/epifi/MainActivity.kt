package com.example.epifi


import android.graphics.Color
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged

import com.example.epifi.databinding.ActivityMainBinding
import java.util.regex.Pattern

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var b1: Boolean = false
    private var b2: Boolean = false
    private var date:Int = 0
    private var month:Int = 0
    private var year:Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val cs = object : ClickableSpan() {
            override fun onClick(textView: View) {
                Toast.makeText(this@MainActivity,"Learning more...",Toast.LENGTH_SHORT).show()
            }

            override fun updateDrawState(ds: TextPaint) {
                super.updateDrawState(ds)
                ds.isUnderlineText = false
                ds.color = getColor(R.color.purple_500)


            }
        }
        val ss = SpannableString("Providing PAN & Date of Birth helps us find and fetch your KYC from a central registry by the Government of India. Learn more")
        ss.setSpan(cs,115,125, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        binding.description.text = ss
        binding.description.movementMethod = LinkMovementMethod.getInstance()
        binding.description.highlightColor = Color.TRANSPARENT




        binding.panInput.doOnTextChanged a1@{ text, _, _, _ ->
            Log.d("meow","meow")
            validatePan(text)
        }

        binding.dayInput.doOnTextChanged{ text, _, _, _ ->
            validateDate(text,1)
        }
        binding.monthInput.doOnTextChanged{ text, _, _, _ ->
            validateDate(text,2)
        }
        binding.yearInput.doOnTextChanged{ text, _, _, _ ->
            validateDate(text,3)
        }

    }

    private fun validatePan(text : CharSequence?){

        //b1 = validatePan(text)
        b1 = Pattern.matches("[A-Z]{5}[0-9]{4}[A-Z]",text.toString())
        Log.d("metow",text.toString() + " ${b1}")
        if(!b1){

            binding.panInput.setCompoundDrawables(null,null,null,null)
            binding.panInput.error = "Invalid Pan Number!"
            binding.next.isEnabled = false
            return
        }
        else{
            val icon = getDrawable(R.drawable.ic_baseline_check_circle_24)
            icon?.setBounds(0,0,icon.intrinsicWidth,icon.intrinsicHeight)
            binding.panInput.error = null
            binding.panInput.setCompoundDrawables(null,null,null,null)
            binding.panInput.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.ic_baseline_check_circle_24,0)
        }
        if(b2){

            binding.next.isEnabled = true
            return
        }
        binding.next.isEnabled = false
    }


    private fun validateDate(text : CharSequence?, value:Int){
        if(text==null || text.isEmpty()){
            binding.dateInvalidate.text = getString(R.string.invalid_date)
            binding.dateInvalidate.setTextColor(Color.RED)
            binding.dateInvalidate.visibility = View.VISIBLE
            binding.next.isEnabled = false
            return
        }
        when(value){
            1->date = text.toString().toInt()
            2->month = text.toString().toInt()
            3->year = text.toString().toInt()
        }
        val v = DateFormat.validateDate(date,month,year)
        b2 = v == 1
        if(!b2){
            if(v==2){
                binding.dateInvalidate.text = getString(R.string.too_young)
                binding.dateInvalidate.setTextColor(getColor(R.color.orange))
            }
            else{
                binding.dateInvalidate.text = getString(R.string.invalid_date)
                binding.dateInvalidate.setTextColor(Color.RED)
            }

            binding.dateInvalidate.visibility = View.VISIBLE
            binding.next.isEnabled = false
            return
        }
        else{
            binding.dateInvalidate.text = getString(R.string.eligible)
            binding.dateInvalidate.setTextColor(Color.GREEN)
        }
        if(b1){
            binding.next.isEnabled = true
            return
        }
        else{
            binding.next.isEnabled = false
        }

    }

    fun onClickNext(view: View) {
        Toast.makeText(this,"Details submitted successfully!",Toast.LENGTH_SHORT).show()
        finish()
    }

    fun finishActivity(view: View) {
        finish()
    }

}
