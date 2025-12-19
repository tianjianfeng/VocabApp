package com.vocabapp

import android.app.Application
import com.vocabapp.data.VocabDatabase

class VocabApplication : Application() {
    
    // Lazy initialization of database
    val database: VocabDatabase by lazy {
        VocabDatabase.getDatabase(this)
    }
    
    override fun onCreate() {
        super.onCreate()
        // Initialize database on app start to trigger sample data population
        database
    }
}

