package pl.SiMMo.PasswordService;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.SiMMo.PasswordService.Model.PasswordEntity;

import java.util.List;
@Repository
public interface PasswordRepo extends JpaRepository<PasswordEntity,Long> {
    List<PasswordEntity> findAllByUserId(Long id);
}
