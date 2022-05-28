package com.exam.gbm.ui.main.viewmodel


import android.text.TextUtils
import android.util.Patterns
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.exam.gbm.models.Index
import dagger.hilt.android.lifecycle.HiltViewModel

/**
 * ViewModel to provide and get data to [LoginFragment]
 *
 * @author Rigoberto Torres on 25/05/2022.
 * @version 0.0.1
 * @since 0.0.1
 */
class LoginViewModel : ViewModel() {

    var loginValue = MutableLiveData<Boolean>()
    var loginEmailValid = MutableLiveData<Boolean>()
    var loginPassValid = MutableLiveData<Boolean>()

    /**
     * Validate user and password
     */
    fun login(user: CharSequence, password: String) {
        loginEmailValid.postValue(isValidEmail(user))
        loginPassValid.postValue(password.isNotBlank() && password.isNotEmpty())
        loginValue.postValue(isValidEmail(user) && (password.isNotBlank() && password.isNotEmpty()))

    }

    /**
     * Validate email
     */
    private fun isValidEmail(target: CharSequence): Boolean {
        return !TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches()
    }

}