package com.uwu.migrationservice.entity

import jakarta.persistence.*

@Entity
@Table(name = "vacancies")
class VacancyEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null

    @Column(length = 20, nullable = false)
    lateinit var name: String

    @Column(nullable = false)
    lateinit var about: String



}