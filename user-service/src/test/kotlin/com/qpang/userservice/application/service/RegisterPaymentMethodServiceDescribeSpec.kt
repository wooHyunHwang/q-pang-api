package com.qpang.userservice.application.service

import com.qpang.userservice.application.port.`in`.usecase.RegisterPaymentMethodUseCase
import com.qpang.userservice.application.port.out.persistence.UserPersistencePort
import com.qpang.userservice.application.service.exception.UsernameNotFoundException
import com.qpang.userservice.domain.PaymentMethod
import com.qpang.userservice.domain.User
import io.kotest.assertions.assertSoftly
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk

class RegisterPaymentMethodServiceDescribeSpec : DescribeSpec({
    val mockUserPersistencePort: UserPersistencePort = mockk()
    val registerPaymentMethodService = RegisterPaymentMethodService(mockUserPersistencePort)

    describe("registerPaymentMethod") {
        context("회원가입된 username을 가진 Command가 주어지면") {
            val expectedUser = User(username = "username", password = "password", name = "name")
            every { mockUserPersistencePort.findByUsername(anyRegisterPaymentMethodCommand.username) } answers { expectedUser }
            it("결제수단 추가에 성공하고 RegisterPaymentMethodInfo 응답") {
                val registerPaymentMethodInfo = registerPaymentMethodService.command(anyRegisterPaymentMethodCommand)

                assertSoftly {
                    registerPaymentMethodInfo.username shouldBe anyRegisterPaymentMethodCommand.username
                    registerPaymentMethodInfo.type shouldBe anyRegisterPaymentMethodCommand.type
                    registerPaymentMethodInfo.company shouldBe anyRegisterPaymentMethodCommand.company
                    registerPaymentMethodInfo.number shouldBe anyRegisterPaymentMethodCommand.number
                }
            }
        }

        context("회원가입되지 않은 username을 가진 Command가 주어지면") {
            every { mockUserPersistencePort.findByUsername(anyRegisterPaymentMethodCommand.username) } answers { null }
            it("UsernameNotFoundException 발생") {
                shouldThrow<UsernameNotFoundException> {
                    registerPaymentMethodService.command(anyRegisterPaymentMethodCommand)
                }
            }
        }
    }
}) {
    companion object {
        private val anyRegisterPaymentMethodCommand = RegisterPaymentMethodUseCase.RegisterPaymentMethodCommand(
            username = "username",
            type = PaymentMethod.PaymentMethodType.CREDITCARD,
            company = PaymentMethod.CardCompany.SAMSUNG,
            number = "1234567890"
        )
    }
}