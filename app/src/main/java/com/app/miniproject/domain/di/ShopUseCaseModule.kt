package com.app.miniproject.domain.di

import com.app.miniproject.domain.interfaces.ShopUseCase
import com.app.miniproject.domain.usecase.ShopUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ShopUseCaseModule {
    @Singleton
    @Binds
    abstract fun bindShopUseCase(shopUseCaseImpl: ShopUseCaseImpl): ShopUseCase
}