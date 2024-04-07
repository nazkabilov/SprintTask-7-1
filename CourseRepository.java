package kz.bitlab.techorda.repository;

import kz.bitlab.techorda.model.Courses;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository  extends JpaRepository<Courses,Long> {
}
