package com.example.worldwise.presentation.screens.translator

import android.app.Application
import androidx.lifecycle.ViewModel
import com.example.worldwise.R
import com.example.worldwise.presentation.screens.translator.language.SourceLanguage
import com.example.worldwise.presentation.screens.translator.language.TargetLanguage
import com.google.firebase.ml.common.modeldownload.FirebaseModelDownloadConditions
import com.google.firebase.ml.naturallanguage.FirebaseNaturalLanguage
import com.google.firebase.ml.naturallanguage.translate.FirebaseTranslatorOptions
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class TranslatorViewModel(
    private val application: Application
) : ViewModel() {

    private val _sourceLanguages: MutableStateFlow<List<SourceLanguage>> =
        MutableStateFlow(SourceLanguage.values().toList())
    val sourceLanguages: Flow<List<SourceLanguage>> = _sourceLanguages

    private val _targetLanguages: MutableStateFlow<List<TargetLanguage>> =
        MutableStateFlow(TargetLanguage.values().toList())
    val targetLanguages: Flow<List<TargetLanguage>> = _targetLanguages

    private val _translatorResult: MutableStateFlow<String> =
        MutableStateFlow(DEFAULT_RESULT_TEXT)
    val translatorResult: Flow<String> = _translatorResult

    private var sourceLanguageCode: Int? = null
    private var targetLanguageCode: Int? = null

    fun fromLanguageSelected(index: Int) {
        sourceLanguageCode = _sourceLanguages.value[index].codeCountry
    }

    fun toLanguageSelected(index: Int) {
        targetLanguageCode = _targetLanguages.value[index].codeCountry
    }

    fun swapLanguage(text: String) {
        val sourceCode = sourceLanguageCode
        sourceLanguageCode = targetLanguageCode
        targetLanguageCode = sourceCode
        submitTranslate(text)
    }

    fun submitTranslate(text: String) {
        if (sourceLanguageCode != null && targetLanguageCode != null && text.isNotBlank()) {
            _translatorResult.value = application.getString(R.string.translator__pending_message)
            val options = FirebaseTranslatorOptions.Builder()
                .setSourceLanguage(sourceLanguageCode!!)
                .setTargetLanguage(targetLanguageCode!!)
                .build()

            val translator = FirebaseNaturalLanguage.getInstance().getTranslator(options)
            val conditions = FirebaseModelDownloadConditions.Builder().build()

            translator.downloadModelIfNeeded(conditions)
                .addOnSuccessListener {
                translator.translate(text)
                    .addOnSuccessListener { _translatorResult.value = it }
                    .addOnFailureListener { _translatorResult.value = it.message.toString() }
            }
        }
    }

    companion object {
        private const val DEFAULT_RESULT_TEXT = "Перевод"
    }
}
