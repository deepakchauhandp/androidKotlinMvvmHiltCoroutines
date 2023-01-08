package com.dpassets.codebase.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import androidx.databinding.DataBindingUtil
import com.dpassets.codebase.ui.base.BaseActivity
import com.dpassets.codebase.R
import com.dpassets.codebase.databinding.ActivityDashboardBinding

class DashboardActivity : BaseActivity() {
    private lateinit var binding: ActivityDashboardBinding
    var inflater: LayoutInflater? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        inflater = this.getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater
    }
    override fun setBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_dashboard)
    }
}