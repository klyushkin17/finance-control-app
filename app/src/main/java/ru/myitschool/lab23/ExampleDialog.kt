package ru.myitschool.lab23

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.navigation.ActivityNavigatorExtras
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Recycler
import org.w3c.dom.Text
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class ExampleDialog : AppCompatDialogFragment(){
    private lateinit var mainView: View

    fun setMainView(view: View) {
        mainView = view
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireActivity())
        val dialogView: View = layoutInflater.inflate(R.layout.layout_dialog,null)
        builder.setView(dialogView)

        val dialogAddButton: Button = dialogView.findViewById(R.id.add_button)
        val dialogEditText: EditText = dialogView.findViewById(R.id.expense_amount_edit_text)

        var selectedOption: String = " "
        val spinnerOptions = resources.getStringArray(R.array.spinner_array)
        val dialogSpinner: Spinner = dialogView.findViewById(R.id.type_spinner)
        val spinnerAdapter = ArrayAdapter(requireActivity(), android.R.layout.simple_spinner_item, spinnerOptions)
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        dialogSpinner.adapter = spinnerAdapter
        dialogSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                selectedOption = spinnerOptions[p2]
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

        val recyclerView: RecyclerView = mainView.findViewById(R.id.ef_expenses_rv)
        val resultAmount: TextView = mainView.findViewById(R.id.ef_current_balance_text)

        dialogAddButton.setOnClickListener {
            val calendar = Calendar.getInstance()

            val dateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
            val formattedDate = dateFormat.format(calendar.time)

            MainActivity.list += "${dialogEditText.text}_${selectedOption}_$formattedDate"
            recyclerView.adapter = RecyclerViewItem(MainActivity.list, mainView)
            if (selectedOption == "Income")
            {
                resultAmount.text = (resultAmount.text.toString().toDouble() + dialogEditText.text.toString().toDouble()).toString()
            }
            else
            {
                if (selectedOption == "Expenses")
                {
                    resultAmount.text = (resultAmount.text.toString().toDouble() - dialogEditText.text.toString().toDouble()).toString()
                }
            }
            this.dismiss()
        }
        return builder.create()
    }
}