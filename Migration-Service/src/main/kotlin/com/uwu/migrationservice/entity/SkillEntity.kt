package com.uwu.migrationservice.entity

import jakarta.persistence.*

@Entity
@Table(name = "skills")
class SkillEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null

    @Column(length = 20, unique = true, nullable = false)
    lateinit var label: String

}