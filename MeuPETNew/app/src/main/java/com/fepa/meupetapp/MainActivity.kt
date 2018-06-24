package com.fepa.meupetapp

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                tf_name.setText("1.   Scoby")

                val fragment = supportFragmentManager.findFragmentByTag(DashboardFragment.toString())
                if (fragment != null)
                    supportFragmentManager.beginTransaction().remove(fragment).commit()

                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_dashboard -> {
                tf_name.setText("Scoby")
                val f_dashboard = DashboardFragment.newInstance()
                openFragment(f_dashboard)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    private fun openFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }
}
