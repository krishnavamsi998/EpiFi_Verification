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




        binding.panNumberInput.doOnTextChanged a1@{ text, _, _, _ ->
            validate(text)
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

    private fun validate(text : CharSequence?){
        b1 = validatePan(text)
        if(!b1){
            binding.panNumberInput.error = "Invalid Pan Number!"
            binding.next.isEnabled = false
            return
        }
        else{
            Log.d("meow","meow")
            val icon = getDrawable(R.drawable.ic_baseline_check_circle_24)
            icon?.setBounds(0,0,icon.intrinsicWidth,icon.intrinsicHeight)
            binding.panNumberInput.error = null
            binding.panNumberInput.setCompoundDrawables(null,null,null,null)
            binding.panNumberInput.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.ic_baseline_check_circle_24,0)
        }
        if(b2){

            binding.next.isEnabled = true
            return
        }
        binding.next.isEnabled = false
    }
    private fun validatePan(text : CharSequence?): Boolean{
        if(text.toString().length == 10){
            Log.d("here","it is 10")
            val str = text.toString()
            for(i in 0..4){
                if(!str[i].isUpperCase()){
                    return false
                }
            }
            for(i in 5..8){
                if(!str[i].isDigit()){
                    return false
                }
            }
            if(!str[9].isUpperCase()){
                return false
            }
            return true
        }
        return false
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
