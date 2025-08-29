package com.miguelsjd.core.koin.provider

import org.koin.core.module.Module

object KoinProvider {
    val modules = mutableListOf<Module>()

    fun add(modules: List<Module>) {
        KoinProvider.modules.addAll(modules)
    }
}