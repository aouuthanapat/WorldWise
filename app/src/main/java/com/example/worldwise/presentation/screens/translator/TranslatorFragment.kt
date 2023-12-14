package com.example.worldwise.presentation.screens.translator

import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.worldwise.R
import com.example.worldwise.databinding.FragmentTranslatorBinding
import com.example.worldwise.presentation.screens.translator.language.SourceLanguage
import com.example.worldwise.presentation.screens.translator.language.TargetLanguage
import com.example.worldwise.utils.bind
import org.intellij.lang.annotations.Language
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.Locale
import java.util.Objects

class TranslatorFragment : Fragment(R.layout.fragment_translator) {

    private val binding by viewBinding(FragmentTranslatorBinding::bind)
    private val viewModel: TranslatorViewModel by viewModel()

    private var adapterToLanguage: ArrayAdapter<TargetLanguage>? = null
    private var adapterFromLanguage: ArrayAdapter<SourceLanguage>? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindViewModelInputs()
        bindViewModelOutputs()
        setupSpinners()
    }

    private fun setupSpinners() = with(binding) {
        adapterFromLanguage = ArrayAdapter(requireContext(), R.layout.spinner_from_language_item)
        adapterToLanguage = ArrayAdapter(requireContext(), R.layout.spinner_to_language_item)
        spinnerFrom.adapter = adapterFromLanguage
        spinnerTo.adapter = adapterToLanguage
    }

    private fun bindViewModelOutputs() = with(viewModel) {
        targetLanguages.bind(viewLifecycleOwner) {
            adapterToLanguage?.clear()
            adapterToLanguage?.addAll(it)
        }

        sourceLanguages.bind(viewLifecycleOwner) {
            adapterFromLanguage?.clear()
            adapterFromLanguage?.addAll(it)
        }

        translatorResult.bind(viewLifecycleOwner) {
            binding.textResult.text = it
        }
    }

    private fun bindViewModelInputs() = with(binding) {
        spinnerFrom.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                viewModel.fromLanguageSelected(position)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        spinnerTo.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                viewModel.toLanguageSelected(position)
                viewModel.submitTranslate(editFromLanguage.text.toString())
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        editFromLanguage.addTextChangedListener { text ->
            viewModel.submitTranslate(text.toString())
        }

        actionSwap.setOnClickListener {
            viewModel.swapLanguage(binding.editFromLanguage.text.toString())
            val spinnerToPosition = spinnerTo.selectedItemPosition
            spinnerTo.setSelection(spinnerFrom.selectedItemPosition)
            spinnerFrom.setSelection(spinnerToPosition)
        }

        actionMic.setOnClickListener {
            val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
                .putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
                .putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.ENGLISH)
                .putExtra(RecognizerIntent.EXTRA_PROMPT, requireContext().getString(R.string.translator__say_something))
            startActivityForResult(intent, REQUEST_PERMISSION_CODE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_PERMISSION_CODE) {
            val result = data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)?.get(0)
            result?.let {
                binding.editFromLanguage.setText(it)
                viewModel.submitTranslate(it)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        adapterToLanguage = null
        adapterFromLanguage = null
    }

    companion object {
        private const val REQUEST_PERMISSION_CODE = 1
    }

}

