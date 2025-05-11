package com.sravani.livedatasample

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.sravani.livedata.LiveData
import com.sravani.livedata.MutableLiveData
import com.sravani.livedata.Observer



class MainActivity : AppCompatActivity() {

    lateinit var simpleTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        simpleTextView = findViewById(R.id.simple_tv)
        val liveData = MutableLiveData<String>()

        liveData.observe(this, object : Observer<String>{
            override fun onChanged(value: String) {
                simpleTextView.setText(value)
            }
        })

        liveData.setValue("Hello lifecycle-aware LiveData!")

    }
}