package settlement.core.domain.company.repository

import org.springframework.data.jpa.repository.JpaRepository
import settlement.core.domain.company.Company

interface CompanyRepository : JpaRepository<Company, Int>
