package com.exam.gbm.ui.main


import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import com.exam.gbm.databinding.LoginFragmentBinding
import dagger.hilt.android.AndroidEntryPoint
import android.view.MenuItem
import androidx.fragment.app.viewModels
import com.exam.gbm.R
import com.exam.gbm.ui.main.viewmodel.LoginViewModel
import com.exam.gbm.utils.AppPreferences

/**
 * Fragment to show login
 *
 * @author Rigoberto Torres on 22/05/2022.
 * @version 0.0.1
 * @since 0.0.1
 */
@AndroidEntryPoint
class LoginFragment : BaseFragment<LoginFragmentBinding>() {

    override fun getViewBinding() = LoginFragmentBinding.inflate(layoutInflater)
    private val viewModel: LoginViewModel by viewModels()

    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity?)!!.supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(true)
            it.setHomeButtonEnabled(true)
            it.setHomeAsUpIndicator(R.drawable.ic_close)
        }
    }

    /**
     * Initializes class members
     */
    override fun setUpViews() {
        AppPreferences.init(requireContext())
        if (AppPreferences.isLogged && AppPreferences.biometricPreference) {
            goToBiometric()
        }

        binding.btnLogin.setOnClickListener {
            viewModel.login(
                binding.editTextTextEmailAddress.text,
                binding.editTextTextEmailPassword.text.toString()
            )
        }
    }

    /**
     * Attaches view model [androidx.lifecycle.liveData] observers
     */
    override fun attachObservers() {
        viewModel.loginValue.observe(viewLifecycleOwner) {
            if (it) {
                viewModel.loginValue.postValue(false)
                AppPreferences.isLogged = true
                goToBiometric()
            }
        }
        viewModel.loginEmailValid.observe(viewLifecycleOwner) {
            if (!it)
                binding.tilTextEmailAddress.error = getString(R.string.txt_login_error_mail)
        }
        viewModel.loginPassValid.observe(viewLifecycleOwner) {
            if (!it)
                binding.tiltTextTextEmailPassword.error = getString(R.string.txt_login_error_pass)
        }
    }

    /**
     * Go to [BiometricLoginFragment]
     */
    private fun goToBiometric() {
        findNavController().navigate(
            LoginFragmentDirections.actionLoginFragmentToBiometricLoginFragment()
        )
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == android.R.id.home) {
            activity?.finish()
            return true
        }
        return true
    }
}