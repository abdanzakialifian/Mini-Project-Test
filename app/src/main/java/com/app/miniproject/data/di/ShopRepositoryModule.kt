package com.app.miniproject.data.di

import com.app.miniproject.data.repository.ShopRepositoryImpl
import com.app.miniproject.domain.interfaces.ShopRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ShopRepositoryModule {
    @Singleton
    @Binds
    abstract fun bindShopRepository(shopRepositoryImpl: ShopRepositoryImpl) : ShopRepository
}