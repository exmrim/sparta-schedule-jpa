package com.example.scheduleproject2.entity;

import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.validator.constraints.Range;

@Getter
@Entity
@Table(name="comment")
public class Comment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String comments;

    @ManyToOne
    @JoinColumn(name="member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name="schedule_id")
    private Schedule schedule;

    public Comment() {}

    public Comment(String comments) {
        this.comments = comments;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }

    public void update(String comments) {
        this.comments = comments;
    }

}
