package com.uwu.migrationservice.entity

import jakarta.persistence.*
import java.util.*

class ApplicationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    var id: UUID? = null

    @ManyToOne
    @JoinColumn(name = "summary", referencedColumnName = "id", unique = false, nullable = false)
    lateinit var summary: SummaryEntity

    @ManyToOne
    @JoinColumn(name = "project", referencedColumnName = "id", unique = false, nullable = false)
    lateinit var project: ProjectEntity

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, unique = false)
    lateinit var status: ApplicationStatus

}