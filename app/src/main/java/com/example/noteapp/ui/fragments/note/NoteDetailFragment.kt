package com.example.noteapp.ui.fragments.note

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.example.noteapp.App
import com.example.noteapp.R
import com.example.noteapp.data.model.NoteModel
import com.example.noteapp.databinding.FragmentNoteDetailBinding
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class NoteDetailFragment : Fragment() {

    private lateinit var binding: FragmentNoteDetailBinding
    private var selectedColor: String = "black"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNoteDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListener()
        currentDateTime()
        setupRadioGroupListener()
    }

    private fun setupListener() {
        binding.btnAddText.setOnClickListener{
            val etTitle = binding.etTitle.text.toString()
            val etDescription = binding.etDescription.text.toString()
            val currentDate = binding.tvDate.text.toString()
            val currentTime = binding.tvTime.text.toString()

            App().getInstance()?.noteDao()?.insertNote(NoteModel(etTitle, etDescription, currentDate, currentTime, selectedColor))
            findNavController().navigateUp()
        }
        binding.imgBack.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun currentDateTime() {
        val currentDate = SimpleDateFormat("dd MMM", Locale.getDefault()).format(Date())
        binding.tvDate.text = currentDate

        val currentTime = SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date())
        binding.tvTime.text = currentTime
    }

    private fun setupRadioGroupListener() {
        binding.radioGroup.setOnCheckedChangeListener { _, checkedId ->
            selectedColor = when (checkedId) {
                R.id.radio_btn_black -> "black"
                R.id.radio_btn_white -> "white"
                R.id.radio_btn_red -> "red"
                else -> "black"
            }
            updateBackgroundColor()
        }
    }
    private fun updateBackgroundColor() {
        val colorResId = when (selectedColor) {
            "white" -> {
                binding.etDescription.setTextColor(ContextCompat.getColor(requireContext(), R.color.beige))
                binding.etTitle.setTextColor(ContextCompat.getColor(requireContext(), R.color.beige))
                binding.tvDate.setTextColor(ContextCompat.getColor(requireContext(), R.color.beige))
                binding.tvTime.setTextColor(ContextCompat.getColor(requireContext(), R.color.beige))
                R.color.whiteRadio
            }
            "red" -> {
                binding.etDescription.setTextColor(ContextCompat.getColor(requireContext(), R.color.orange))
                binding.etTitle.setTextColor(ContextCompat.getColor(requireContext(), R.color.orange))
                binding.tvDate.setTextColor(ContextCompat.getColor(requireContext(), R.color.orange))
                binding.tvTime.setTextColor(ContextCompat.getColor(requireContext(), R.color.orange))
                R.color.redRadio
            }
            else -> {
                binding.etDescription.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
                binding.etTitle.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
                binding.tvDate.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
                binding.tvTime.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
                R.color.blackRadio
            }
        }
        binding.root.setBackgroundColor(ContextCompat.getColor(requireContext(), colorResId))
    }
}