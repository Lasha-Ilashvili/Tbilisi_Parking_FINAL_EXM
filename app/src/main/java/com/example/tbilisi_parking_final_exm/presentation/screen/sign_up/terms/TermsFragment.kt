package com.example.tbilisi_parking_final_exm.presentation.screen.sign_up.terms

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.text.Spannable
import android.text.SpannableString
import android.text.TextPaint
import android.text.style.ClickableSpan
import android.view.View
import androidx.core.text.method.LinkMovementMethodCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.tbilisi_parking_final_exm.R
import com.example.tbilisi_parking_final_exm.databinding.FragmentTermsBinding
import com.example.tbilisi_parking_final_exm.presentation.base.BaseFragment
import com.example.tbilisi_parking_final_exm.presentation.event.sign_up.terms.TermsEvent
import kotlinx.coroutines.launch


class TermsFragment :
    BaseFragment<FragmentTermsBinding>(FragmentTermsBinding::inflate) {

    private val viewModel: TermsViewModel by viewModels()

    override fun bind() {
        setUpSpannableString()
    }

    override fun bindViewActionListeners() = with(binding) {
        checkBox.setOnCheckedChangeListener { _, isChecked ->
            viewModel.onEvent(TermsEvent.SetButtonState(isChecked))
        }

        btnNext.setOnClickListener {
            findNavController().navigate(
                TermsFragmentDirections.actionTermsFragmentToPersonalInformationFragment()
            )
        }
    }

    override fun bindObserves() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.termsState.collect { termsPageState ->
                    binding.btnNext.isEnabled = termsPageState.isEnabled
                }
            }
        }
    }

    /* IMPLEMENTATION DETAILS */

    private fun setUpSpannableString() = with(binding.tvTerms) {
        text = getSpannableString()
        movementMethod = LinkMovementMethodCompat.getInstance()
        highlightColor = Color.TRANSPARENT
    }

    private fun getSpannableString(): SpannableString {
        val termsAgreement: String = getString(R.string.terms_agreement)
        val termsAgreementLink: String = getString(R.string.terms_agreement_link)

        return SpannableString(termsAgreement).apply {
            setSpan(
                getClickableSpan(),
                indexOf(termsAgreementLink),
                length,
                Spannable.SPAN_INCLUSIVE_EXCLUSIVE
            )
        }
    }

    private fun getClickableSpan() = object : ClickableSpan() {
        override fun onClick(widget: View) {
            openWebPage()
        }

        override fun updateDrawState(termsAgreementLink: TextPaint) {
            super.updateDrawState(termsAgreementLink)
            termsAgreementLink.isUnderlineText = false
        }
    }

    private fun openWebPage() {
        val intent = Intent(
            Intent.ACTION_VIEW,
            Uri.parse(getString(R.string.terms_and_agreements_url))
        )
        startActivity(intent)
    }
}