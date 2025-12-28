package tech.bobliu.assignment08.model

import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "threads")
class Thread(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    var title: String = "",

    var datetime: Date = Date(),

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    var sender: User? = null,

    @OneToMany(mappedBy = "thread", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    var posts: MutableList<Post> = mutableListOf(),
)
