package ug.co.absa.customer.inquiry.repository;

import java.util.UUID;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import ug.co.absa.customer.inquiry.domain.AccountDetails;

/**
 * Spring Data JPA repository for the AccountDetails entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AccountDetailsRepository extends JpaRepository<AccountDetails, UUID> {}
