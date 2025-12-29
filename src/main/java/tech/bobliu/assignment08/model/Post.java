package tech.bobliu.assignment08.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "posts")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User sender;

    @Column(columnDefinition = "TEXT")
    private String content;

    private Date datetime = new Date();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reply_to")
    private Post parent;

    @ManyToOne
    @JoinColumn(name = "thread_id")
    private Thread thread;
}
