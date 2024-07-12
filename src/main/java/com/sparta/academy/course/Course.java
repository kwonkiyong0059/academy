package com.sparta.academy.course;

import com.sparta.academy.course.CourseCategoryEnum;
import com.sparta.academy.course.dto.CourseRequestDto;
import com.sparta.academy.instructor.Instructor;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column
    private Integer price;

    @Column
    private String description;

    @Column
    @Enumerated(EnumType.STRING)
    private CourseCategoryEnum category;

    @ManyToOne
    @JoinColumn(name="instructor_id")
    private Instructor instructor;

    @CreatedDate
    @Column(name="registered_at", updatable = false)
    private LocalDate registeredAt;

    @Builder
    public Course(String title, String description, Integer price, CourseCategoryEnum category, Instructor instructor) {
        this.title = title;
        this.description = description;
        this.price = price;
        this.category = category;
        this.instructor = instructor;
    }

    public void update(CourseRequestDto requestDto){
        if (requestDto.getTitle() != null){
            this.title = requestDto.getTitle();
        }
        if (requestDto.getDescription() != null){
            this.description = requestDto.getDescription();
        }
        if (requestDto.getPrice() != null){
            this.price = requestDto.getPrice();
        }
        if (requestDto.getCategory() != null) {
            this.category = requestDto.getCategory();
        }
    }
}
