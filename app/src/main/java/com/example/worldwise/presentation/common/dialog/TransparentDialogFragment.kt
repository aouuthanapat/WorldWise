package com.example.worldwise.presentation.common.dialog

import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.LinearLayout
import androidx.annotation.LayoutRes
import androidx.fragment.app.DialogFragment
import com.example.worldwise.R
import fr.tvbarthel.lib.blurdialogfragment.BlurDialogFragment

abstract class TransparentDialogFragment(
    @LayoutRes private val layoutResId: Int
) : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(layoutResId, container, false).also {
            dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        dialog?.window?.apply {
            setLayout(
                resources.displayMetrics.widthPixels - MARGINS_HORIZONTAL,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
        }
    }

    companion object {
        private const val MARGINS_HORIZONTAL = 128
    }
}
