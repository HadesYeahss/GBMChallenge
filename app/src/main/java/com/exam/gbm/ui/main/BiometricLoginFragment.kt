package com.exam.gbm.ui.main

import android.app.Activity
import android.content.Intent
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import java.util.concurrent.Executor
import android.view.MenuItem
import androidx.activity.result.contract.ActivityResultContracts
import com.exam.gbm.R
import com.exam.gbm.databinding.BiometricLoginFragmentBinding
import com.exam.gbm.utils.AppPreferences
import com.exam.gbm.utils.BiometricAvailability

/**
 * Fragment to show biometric access
 *
 * @author Rigoberto Torres on 26/05/2022.
 * @version 0.0.1
 * @since 0.0.1
 */
@AndroidEntryPoint
class BiometricLoginFragment : BaseFragment<BiometricLoginFragmentBinding>(),
    BiometricAvailability {

    override fun getViewBinding() = BiometricLoginFragmentBinding.inflate(layoutInflater)
    private lateinit var executor: Executor
    private lateinit var biometricPrompt: BiometricPrompt
    private lateinit var promptInfo: BiometricPrompt.PromptInfo

    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity?)!!.supportActionBar?.let {
            it.hide()
        }
    }

    /**
     * Initializes class members
     */
    override fun setUpViews() {
        AppPreferences.init(requireContext())
        setBiometrics()
        if (AppPreferences.biometricPreference) {
            checkBiometrics(requireContext())
        } else {
            binding.textViewFooter.text = getString(R.string.init_biometric_text_footer_alternative)
            binding.buttonActivate.visibility = View.VISIBLE
        }
        binding.buttonActivate.setOnClickListener {
            checkBiometrics(requireContext())
        }
        binding.buttonCancel.setOnClickListener {
            if (AppPreferences.isLogged) {
                AppPreferences.biometricPreference = false
                findNavController().navigate(
                    BiometricLoginFragmentDirections.actionBiometricLoginFragmentToIndexFragmentTwo()
                )
            } else {
                AppPreferences.clearPreferences()
                findNavController().popBackStack()
            }
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == android.R.id.home) {
            activity?.finish()
            return true
        }
        return true
    }

    /**
     * Set biometric  dialog in view
     */
    private fun setBiometrics() {
        executor = ContextCompat.getMainExecutor(requireContext())
        promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle(getString(R.string.txt_login_bio))
            .setNegativeButtonText(getString(R.string.txt_login_bio_cancel))
            .build()
        biometricPrompt = BiometricPrompt(this, executor,
            object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                    super.onAuthenticationError(errorCode, errString)
                    AppPreferences.clearPreferences()
                    binding.textViewFooter.text = errString
                    binding.textViewFooter.setTextColor(R.color.lower_color)
                    binding.buttonActivate.isEnabled = false
                    binding.buttonActivate.isClickable = false
                }

                override fun onAuthenticationSucceeded(
                    result: BiometricPrompt.AuthenticationResult
                ) {
                    super.onAuthenticationSucceeded(result)
                    AppPreferences.biometricPreference = true
                    findNavController().navigate(
                        BiometricLoginFragmentDirections.actionBiometricLoginFragmentToIndexFragment()
                    )
                }

                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                    binding.imageBiometric.setColorFilter(
                        ContextCompat.getColor(
                            requireContext(),
                            R.color.lower_color
                        )
                    )
                }
            })
    }

    /**
     * Callback to activity result on biometric manager
     */
    var resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            when (result.resultCode) {
                Activity.RESULT_OK -> biometricPrompt.authenticate(promptInfo)
                Activity.RESULT_CANCELED -> {
                }
            }

        }

    override fun onSuccess() {
        biometricPrompt.authenticate(promptInfo)
    }

    override fun onError() {
        AppPreferences.clearPreferences()
        findNavController().popBackStack()
    }

    override fun onNoRegistered() {
        enrollBiometrics()
    }

    override fun launchEnroll(intent: Intent) {
        resultLauncher.launch(intent)
    }

}