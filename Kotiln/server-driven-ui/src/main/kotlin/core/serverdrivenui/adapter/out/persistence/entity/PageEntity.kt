package core.serverdrivenui.adapter.out.persistence.entity

import jakarta.persistence.*

@Entity
@Table(name = "page")
class PageEntity(
    @Column(name = "page_key", unique = true, nullable = false, length = 100)
    val pageKey: String,

    @Column(nullable = false)
    val title: String,

    @OneToMany(mappedBy = "page", cascade = [CascadeType.ALL], orphanRemoval = true)
    @OrderBy("sortOrder ASC")
    val pageComponents: MutableList<PageComponentEntity> = mutableListOf(),
) : BaseEntity()
