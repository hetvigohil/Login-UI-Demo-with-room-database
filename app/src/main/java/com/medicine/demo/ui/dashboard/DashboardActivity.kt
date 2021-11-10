package com.medicine.demo.ui.dashboard

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.medicine.demo.R
import com.medicine.demo.base.BaseActivity
import com.medicine.demo.base.Result
import com.medicine.demo.data.remote.AssociatedDrug
import com.medicine.demo.ui.dashboard.adapter.MedicineAdapter
import com.medicine.demo.viewmodel.DataViewModel
import com.medicine.demo.widget.RecyclerSectionItemDecoration
import com.medicine.demo.widget.RecyclerSectionItemDecoration.SectionCallback
import kotlinx.android.synthetic.main.activity_dashboard.*


class DashboardActivity : BaseActivity(R.layout.activity_dashboard) {

    private lateinit var mDataViewModel: DataViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mDataViewModel = ViewModelProvider(this).get(DataViewModel::class.java)
        mDataViewModel.getData().apply {
            pbDashboard.visibility = View.VISIBLE
        }

        rvData.layoutManager = LinearLayoutManager(this)
        val adapter = MedicineAdapter()
        rvData.adapter = adapter

        mDataViewModel.mData.observe(this, Observer {
            pbDashboard.visibility = View.GONE
            when (it) {
                is Result.Success -> {
                    makeToast("Success")
                    adapter.addItem(it.data)
                    val sectionItemDecoration =
                        RecyclerSectionItemDecoration(
                            resources.getDimensionPixelSize(R.dimen.recycler_section_header_height),
                            true,
                            getSectionCallback(it.data)
                        )
                    rvData.addItemDecoration(sectionItemDecoration)
                }
                is Result.Error -> {
                    makeToast("Something went wrong !!")
                }
            }
        })
    }

    private fun getSectionCallback(alData: ArrayList<AssociatedDrug>): SectionCallback {
        return object : SectionCallback {
            override fun isSection(position: Int): Boolean {
                return (position == 0 || alData[position].problems !== alData[position - 1].problems)
            }

            override fun getSectionHeader(position: Int): CharSequence {
                return alData[position].problems
            }
        }
    }
}