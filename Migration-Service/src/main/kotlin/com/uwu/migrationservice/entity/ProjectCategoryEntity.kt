package com.uwu.migrationservice.entity

import jakarta.persistence.*

@Entity
@Table(name = "project_categories")
class ProjectCategoryEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null
    
    @Column(length = 50, unique = false, nullable = false)
    lateinit var label: String

    @Column(unique = false, nullable = false)
    lateinit var description: String

    @Column(length = 20, unique = true, nullable = false)
    lateinit var url: String
    
}