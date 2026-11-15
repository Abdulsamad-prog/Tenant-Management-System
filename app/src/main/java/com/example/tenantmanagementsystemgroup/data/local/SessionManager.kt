package com.example.tenantmanagementsystemgroup.data.local

import android.content.Context

class SessionManager(context: Context) {

    private val prefs = context.getSharedPreferences("tenant_app_prefs", Context.MODE_PRIVATE)

    fun saveUser(fullName: String, email: String) {
        prefs.edit()
            .putString("FULL_NAME", fullName.trim())
            .putString("EMAIL", email.trim())
            .apply()
    }

    fun getFullName(): String {
        val savedName = prefs.getString("FULL_NAME", "").orEmpty()
        if (savedName.isNotBlank()) return savedName

        val email = getEmail()
        return if (email.contains("@")) {
            email.substringBefore("@")
                .replaceFirstChar { it.uppercase() }
        } else {
            "Landlord"
        }
    }

    fun getEmail(): String {
        return prefs.getString("EMAIL", "").orEmpty()
    }

    fun clearSession() {
        prefs.edit().remove("FULL_NAME").apply()
    }
}