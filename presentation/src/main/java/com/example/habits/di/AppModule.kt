package com.example.habits.di

import dagger.Module

@Module(subcomponents = [HabitsComponent::class, EditHabitComponent::class])
class AppModule