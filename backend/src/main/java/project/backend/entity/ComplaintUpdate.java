package project.backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import project.backend.enums.Status;

import java.time.LocalDateTime;

@Entity
@Table(name = "complaint_updates")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ComplaintUpdate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String comment;
    @Enumerated(EnumType.STRING)
    private Status status;
    private LocalDateTime updatedAt;
    @ManyToOne
    @JoinColumn(name = "complaint_id")
    private Complaint complaint;
    @ManyToOne
    @JoinColumn(name = "updated_by")
    private User updatedBy;
}
