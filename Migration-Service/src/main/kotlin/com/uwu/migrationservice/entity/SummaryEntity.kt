package com.uwu.migrationservice.entity

import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "summaries")
class SummaryEntity {

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
    lateinit var category: SummaryCategoryEntity

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, unique = false)
    lateinit var status: SummaryStatus

    @Column(name = "github", unique = false, nullable = true)
    lateinit var github: String

    @Column(unique = false, nullable = false)
    lateinit var about: String

}