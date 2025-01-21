package com.cmp.showcase.data.currency.converter.usecase

import com.cpm.showcase.domain.currency.converter.Usecase
import com.cpm.showcase.domain.currency.converter.entity.Currency
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

class GetCodes: Usecase {
    fun invoke() : Flow<Currency> {
        return emptyFlow()
    }
}