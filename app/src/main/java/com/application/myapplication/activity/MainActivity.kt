package com.application.myapplication.activity

import android.os.Bundle
import android.view.KeyEvent
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.application.myapplication.R
import com.application.myapplication.ui.main.MainFragment

class MainActivity : AppCompatActivity() {

    private var backpress:Boolean=false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        //for adding MainFragment in MainActivity for showing Fragment implementation
        if (savedInstanceState == null) {
            addFragment(MainFragment.newInstance())
        }
    }

    //For back key implementation
    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        super.onKeyDown(keyCode, event)
        return if(supportFragmentManager.backStackEntryCount>1) {
            supportFragmentManager.popBackStack()
            false
        } else{
               finish()
            true
        }
    }
}
fun AppCompatActivity.addFragment(fragment: Fragment){
    val fragmentManager = supportFragmentManager
    val transaction = fragmentManager.beginTransaction()
    transaction.add(R.id.container,fragment)
    transaction.addToBackStack(fragment::class.toString())
    transaction.commit()
}