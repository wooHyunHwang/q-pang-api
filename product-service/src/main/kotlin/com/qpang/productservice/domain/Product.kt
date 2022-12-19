package com.qpang.productservice.domain

import com.qpang.productservice.common.entity.JpaAuditEntity
import javax.persistence.*

@Entity
@Table(name = "products")
class Product(
    name: String,
    stock: Long,
    category: ProductCategory
) : JpaAuditEntity() {
    @Column(name = "name", nullable = false)
    var name: String = name
        protected set

    @Column(name = "stock", nullable = false)
    var stock: Long = stock
        protected set

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "product_category_id")
    var category: ProductCategory = category
        protected set
}