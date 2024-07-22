package com.uwu.migrationservice.entity

import jakarta.persistence.*
import java.util.UUID

@Entity
@Table(name = "projects")
class ProjectEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    var id: UUID? = null

    @ManyToOne
    @JoinColumn(name = "user", referencedColumnName = "id")
    lateinit var user: UserEntity

    @Column(length = 50, unique = true, nullable = false)
    lateinit var name: String

    @Column(unique = true, nullable = true)
    @Lob
    var photo: ByteArray? = null

    @ManyToOne
    @JoinColumn(name = "category", referencedColumnName = "id", nullable = false)
    lateinit var category: ProjectCategoryEntity

    @Column(unique = false, nullable = false)
    lateinit var about: String

    @Column(name = "is_active", unique = false, nullable = false)
    var isActive: Boolean? = null

}