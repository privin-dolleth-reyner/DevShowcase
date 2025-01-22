package com.cmp.showcase.data.currency.converter.usecase

import com.cpm.showcase.domain.currency.converter.Usecase
import com.cpm.showcase.domain.currency.converter.repository.CurrencyConverterRepo

class GetConversionAmount(private val currencyConverterRepo: CurrencyConverterRepo): Usecase {
    suspend operator fun invoke(base: String, target: String, amount: Double): Double {
        val rate = currencyConverterRepo.getRate(base, target)
        return amount * rate
    }
}