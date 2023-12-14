package com.example.worldwise.di

import com.example.worldwise.di.modules.dataModule
import com.example.worldwise.di.modules.domainModule
import com.example.worldwise.di.modules.presentationModule
import com.example.worldwise.di.network.submodules.serversModule
import org.koin.core.module.Module


internal val app: List<Module> = dataModule + domainModule + presentationModule + serversModule
