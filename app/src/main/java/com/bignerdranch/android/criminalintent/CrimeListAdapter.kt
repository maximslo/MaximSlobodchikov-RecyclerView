package com.bignerdranch.android.criminalintent

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bignerdranch.android.criminalintent.databinding.ListItemCrimeBinding
import com.bignerdranch.android.criminalintent.databinding.ListItemCrimePoliceBinding // New layout for police required crimes

private const val VIEW_TYPE_NORMAL = 0
private const val VIEW_TYPE_POLICE = 1

class CrimeListAdapter(private val crimes: List<Crime>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun getItemViewType(position: Int): Int {
        return if (crimes[position].requiresPolice) {
            VIEW_TYPE_POLICE
        } else {
            VIEW_TYPE_NORMAL
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            VIEW_TYPE_POLICE -> {
                val binding = ListItemCrimePoliceBinding.inflate(inflater, parent, false)
                CrimePoliceHolder(binding)
            }
            else -> {
                val binding = ListItemCrimeBinding.inflate(inflater, parent, false)
                CrimeHolder(binding)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val crime = crimes[position]
        when (holder) {
            is CrimeHolder -> holder.bind(crime)
            is CrimePoliceHolder -> holder.bind(crime)
        }
    }

    override fun getItemCount() = crimes.size

    // Existing CrimeHolder for normal crimes
    class CrimeHolder(private val binding: ListItemCrimeBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(crime: Crime) {
            binding.crimeTitle.text = crime.title
            binding.crimeDate.text = crime.date.toString()
            // Existing click listener...
        }
    }

    // New CrimePoliceHolder for crimes that require police intervention
    class CrimePoliceHolder(private val binding: ListItemCrimePoliceBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(crime: Crime) {
            // Append " requires police!" to the crime title for crimes that require police intervention
            val displayTitle = if (crime.requiresPolice) "${crime.title} - Requires Police!" else crime.title
            binding.crimeTitle.text = displayTitle
            binding.crimeDate.text = crime.date.toString()
            // You can also handle the "contact police" button click event here if needed
        }
    }
}