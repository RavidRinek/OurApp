package com.our.domain.base.usecases

abstract class BaseSuspendedUseCase<IN, OUT : Any> {
    abstract suspend operator fun invoke(param: IN): OUT
}