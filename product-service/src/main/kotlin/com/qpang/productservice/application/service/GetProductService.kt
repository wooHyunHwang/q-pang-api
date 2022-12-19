package com.qpang.productservice.application.service

import com.qpang.productservice.application.port.`in`.usecase.GetProductUseCase
import com.qpang.productservice.application.port.out.persistence.ProductPersistencePort
import com.qpang.productservice.application.service.exception.ProductIdNotFoundException
import org.springframework.stereotype.Service

@Service
class GetProductService(
    private val productPersistencePort: ProductPersistencePort
) : GetProductUseCase {
    override fun command(command: GetProductUseCase.GetProductCommand): GetProductUseCase.GetProductInfo {
        val savedProduct = productPersistencePort.findProductById(command.id)
        savedProduct ?: throw ProductIdNotFoundException(command.id)

        return GetProductUseCase.GetProductInfo.from(savedProduct)
    }
}