package hw.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import hw.entity.User;


public interface UserRepository extends JpaRepository<User, Long>{

	@Transactional
	@Modifying
	@Query("delete from Users e where id like ?1 and fileName like ?2")
	public void deleteUserWithFile(Long id, String fileName);
}
