package com.exam.gbm.utils

import android.content.Context
import android.content.Intent
import android.provider.Settings
import androidx.biometric.BiometricManager

/**
 * Interface to set and check biometrics
 *
 * @author Rigoberto Torres on 25/05/2022.
 * @version 0.0.1
 * @since 0.0.1
 */
interface BiometricAvailability {
    /**
     * onSuccess biometric Authenticate
     */
    fun onSuccess()

    /**
     * onError biometric Authenticate
     */
    fun onError()

    /**
     * onNoRegistered biometric Authenticate
     */
    fun onNoRegistered()

    /**
     * Launch enroll on biometrics
     * @param context
     */
    fun launchEnroll(intent: Intent)

    /**
     * Check is biometric is enable
     * @param context
     */
    fun checkBiometrics(context: Context) {
        val biometricManager = BiometricManager.from(context)
        when (biometricManager.canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_STRONG or BiometricManager.Authenticators.DEVICE_CREDENTIAL)) {
            BiometricManager.BIOMETRIC_SUCCESS ->
                onSuccess()
            BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE,
            BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE ->
                onError()
            BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED -> {
                onNoRegistered()
            }
        }
    }

    /**
     * enroll on biometrics
     * @param context
     */
    fun enrollBiometrics() {
        val enrollIntent = Intent(Settings.ACTION_BIOMETRIC_ENROLL).apply {
            putExtra(
                Settings.EXTRA_BIOMETRIC_AUTHENTICATORS_ALLOWED,
                BiometricManager.Authenticators.BIOMETRIC_STRONG or BiometricManager.Authenticators.DEVICE_CREDENTIAL
            )
        }
        launchEnroll(enrollIntent)

    }
}

