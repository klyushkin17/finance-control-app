package ru.myitschool.lab23

import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import org.w3c.dom.Text


class RecyclerViewItem (private var names: MutableList<String>, private val mainView: View) : RecyclerView.Adapter<RecyclerViewItem.MyViewHolder>(){

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val type: TextView = itemView.findViewById(R.id.expense_type_text)
        val amount: TextView = itemView.findViewById(R.id.expense_amount_text)
        val date: TextView = itemView.findViewById(R.id.expense_date_text)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycler_view_item, parent, false)

        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val data = names[position].split("_")
        holder.type.text = data[1]
        holder.amount.text = data[0].toDouble().toString()
        holder.date.text = data[2]

        holder.itemView.setOnCreateContextMenuListener { menu, v, menuInfo ->
            menu?.add(Menu.NONE, 1, Menu.NONE, "Delete")?.setOnMenuItemClickListener {
                if (data[1] == "Income")
                {
                    mainView.findViewById<TextView>(R.id.ef_current_balance_text).text =
                        (mainView.findViewById<TextView>(R.id.ef_current_balance_text).text.toString().toDouble() - data[0].toDouble()).toString()
                }
                else
                {
                    if (data[1] == "Expenses")
                    {
                        mainView.findViewById<TextView>(R.id.ef_current_balance_text).text =
                            (mainView.findViewById<TextView>(R.id.ef_current_balance_text).text.toString().toDouble() + data[0].toDouble()).toString()
                    }
                }

                names.removeAt(position)
                notifyItemRemoved(position)

                true
            }

            menu?.add(Menu.NONE, 2, Menu.NONE, "Duplicate")?.setOnMenuItemClickListener {
                names.add("${data[0]}_${data[1]}_${data[2]}")
                notifyItemInserted(names.size)

                if (data[1] == "Income")
                {
                    mainView.findViewById<TextView>(R.id.ef_current_balance_text).text =
                        (mainView.findViewById<TextView>(R.id.ef_current_balance_text).text.toString().toDouble() + data[0].toDouble()).toString()
                }
                else
                {
                    if (data[1] == "Expenses")
                    {
                        mainView.findViewById<TextView>(R.id.ef_current_balance_text).text =
                            (mainView.findViewById<TextView>(R.id.ef_current_balance_text).text.toString().toDouble() - data[0].toDouble()).toString()
                    }
                }

                true
            }
        }
    }

    override fun getItemCount(): Int {
        return names.size
    }
}