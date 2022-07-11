package co.zip.candidate.userapi.repository;

import co.zip.candidate.userapi.model.Account;
import co.zip.candidate.userapi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

import static org.hibernate.loader.Loader.SELECT;

@Repository
@Transactional
public interface AccountRepository extends JpaRepository<Account, Long> {

}
