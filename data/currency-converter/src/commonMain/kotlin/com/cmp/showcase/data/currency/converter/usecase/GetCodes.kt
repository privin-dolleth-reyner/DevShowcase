package com.cmp.showcase.data.currency.converter.usecase

import com.cpm.showcase.domain.currency.converter.Usecase
import com.cpm.showcase.domain.currency.converter.entity.Currency
import com.cpm.showcase.domain.currency.converter.repository.CurrencyConverterRepo

class GetCodes(private val currencyConverterRepo: CurrencyConverterRepo): Usecase {
    suspend operator fun invoke() : List<Currency> {
        return currencyConverterRepo.getCurrencyCodes()
    }
}