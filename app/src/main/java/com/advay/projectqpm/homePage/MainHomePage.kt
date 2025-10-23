package com.advay.projectqpm.homePage

import android.app.DatePickerDialog
import android.icu.util.Calendar
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.advay.projectqpm.R
import com.advay.projectqpm.databinding.FragmentHomePageBinding
import com.advay.projectqpm.designerPage.MainDesignerPage
import com.google.android.gms.ads.AdRequest // <-- Import these
import com.google.android.gms.ads.AdView

class MainHomePage : Fragment() {
    // Implement save stuff and auto complete in fields of dialog
    private var _binding: FragmentHomePageBinding? = null
    private var calender: Calendar = Calendar.getInstance()
    private val binding get() = _binding!!
    private val recentFiles = listOf(
        RecentFile("Main2.java", "222 Oct 2025", "120:42 AM"),
        RecentFile("Main2.java", "222 Oct 2025", "120:42 AM"),
        RecentFile("Main2.java", "222 Oct 2025", "120:42 AM"),
        RecentFile("Main2.java", "222 Oct 2025", "120:42 AM"),
        RecentFile("Main2.java", "222 Oct 2025", "120:42 AM"),
        RecentFile("Main2.java", "222 Oct 2025", "120:42 AM"),
        RecentFile("Main2.java", "222 Oct 2025", "120:42 AM"),
        RecentFile("Main2.java", "222 Oct 2025", "120:42 AM"),
        RecentFile("Main2.java", "222 Oct 2025", "120:42 AM"),
        RecentFile("Main2.java", "222 Oct 2025", "120:42 AM"),
        RecentFile("Main2.java", "222 Oct 2025", "120:42 AM"),
        RecentFile("Main2.java", "222 Oct 2025", "120:42 AM"),
        RecentFile("Main2.java", "222 Oct 2025", "120:42 AM"),
        RecentFile("Main2.java", "222 Oct 2025", "120:42 AM"),
        RecentFile("Main3.java", "232 Oct 2025", "130:42 AM")
    )


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomePageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.createNewDocBtn.setOnClickListener {
            createNewDoc()
        }

        binding.recyclerViewRecents.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewRecents.adapter = RecentsAdapterRV(recentFiles)

        val adRequest = AdRequest.Builder().build()
        binding.adView.loadAd(adRequest)
    }

    private fun createNewDoc() {
        val dialogView = layoutInflater.inflate(R.layout.create_new_doc_dialog, null)
        val btn = dialogView.findViewById<Button>(R.id.chooseDateExam)

        val adRequest2 = AdRequest.Builder().build()
        val adRequest3 = AdRequest.Builder().build()
        dialogView.findViewById<AdView>(R.id.adView2).loadAd(adRequest2)
        dialogView.findViewById<AdView>(R.id.adView3).loadAd(adRequest3)



        btn.setOnClickListener {
            showDatePickerDialog(btn)
        }
        val dialog = AlertDialog.Builder(requireContext()).setView(dialogView)
            .setPositiveButton("Create") { dialog, _ ->
                createDoc()
                dialog.dismiss()
            }.setNegativeButton("Close") { dialog, _ ->
                dialog.dismiss()
            }.create()

        dialog.show()
    }

    private fun createDoc() {
        parentFragmentManager.beginTransaction().replace(
            R.id.fragment_container, MainDesignerPage()
        ).addToBackStack(null).commit()
    }

    private fun showDatePickerDialog(btn: Button) {
        val year = calender.get(Calendar.YEAR)
        val month = calender.get(Calendar.MONTH)
        val day = calender.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog =
            DatePickerDialog(requireContext(), { _, selectedYear, selectedMonth, selectedDay ->
                val dateText = "$selectedDay/${selectedMonth + 1}/$selectedYear"
                calender.set(selectedYear, selectedMonth, selectedDay)
                btn.text = dateText
            }, year, month, day)
        datePickerDialog.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}