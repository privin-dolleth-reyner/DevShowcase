package com.cpm.showcase.domain.currency.converter.entity

import com.cpm.showcase.domain.currency.converter.Entity

data class Currency(
    val name: String,
    val code: String
): Entity