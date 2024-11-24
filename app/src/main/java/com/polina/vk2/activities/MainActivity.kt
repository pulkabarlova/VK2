package com.polina.vk2.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.polina.vk2.fragments.MainFragment
import com.polina.vk2.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if(savedInstanceState == null)
            setFragment()
    }

    private fun setFragment(){
        val fragment = MainFragment()
        supportFragmentManager
            .beginTransaction()
            .replace(binding.container.id, fragment)
            .commit()
    }
}
