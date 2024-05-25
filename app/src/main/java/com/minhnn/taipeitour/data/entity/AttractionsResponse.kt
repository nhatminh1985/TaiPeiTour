package com.minhnn.taipeitour.data.entity

data class AttractionsResponse(
    val total: Int,
    val data: List<Data>
)

data class Data(
    val id: Int,
    val name: String,
    val name_zh: String?,
    val open_status: String?,
    val introduction: String?,
    val open_time: String?,
    val zipcode: String?,
    val distric: String?,
    val address: String?,
    val tel: String?,
    val fax: String?,
    val email: String?,
    val months: String?,
    val nlat: Double?,
    val elong: Double?,
    val official_site: String?,
    val facebook: String?,
    val ticket: String?,
    val remind: String?,
    val staytime: String?,
    val modified: String?,
    val url: String?,
    val category: List<Category>?,
    val target: List<Target>?,
    val service: List<Service>?,
    val friendly: List<Friendly>?,
    val images: List<Images>?,
    val files: List<Files>?,
    val links: List<Links>?
)

data class Category(
    val id: String?,
    val name: String?
)

data class Target(
    val id: String?,
    val name: String?
)

data class Service(
    val id: String?,
    val name: String?
)

data class Friendly(
    val id: String?,
    val name: String?
)

data class Images(
    val src: String?,
    val subject: String?,
    val ext: String?
)

data class Files(
    val id: String?,
    val name: String?
)

data class Links(
    val id: String?,
    val name: String?
)
