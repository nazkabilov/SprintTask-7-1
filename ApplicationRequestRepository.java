package kz.bitlab.techorda.repository;

import kz.bitlab.techorda.model.ApplicationRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApplicationRequestRepository extends JpaRepository<ApplicationRequest,Long> {
    List<ApplicationRequest> findByHandled(Boolean status);
}

