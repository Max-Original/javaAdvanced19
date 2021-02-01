package hw.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import hw.domain.FileMultipart;

@Repository
public interface FileMultipartRepository extends JpaRepository<FileMultipart, String>{

}
