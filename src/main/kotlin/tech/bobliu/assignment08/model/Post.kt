package tech.bobliu.assignment08.model

import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "posts")
class Post(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    var sender: User? = null,

    @Column(columnDefinition = "TEXT")
    var content: String = "",

    var datetime: Date = Date(),

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reply_to")
    var parent: Post? = null,

    @ManyToOne
    @JoinColumn(name = "thread_id")
    var thread: Thread? = null,
)
