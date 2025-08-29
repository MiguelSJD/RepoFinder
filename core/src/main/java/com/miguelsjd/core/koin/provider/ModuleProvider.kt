package com.miguelsjd.core.koin.provider

import android.content.Context
import androidx.startup.Initializer
import org.koin.core.module.Module

abstract class ModuleProvider : Initializer<List<Module>> {

    abstract val modules: List<Module>

    override fun create(context: Context): List<Module> {
        KoinProvider.add(modules)
        return modules
    }

    override fun dependencies(): List<Class<out Initializer<*>>> = emptyList()
}
