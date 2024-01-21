package settlement.core

import jakarta.annotation.PostConstruct
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling
import settlement.core.domain.company.Company
import settlement.core.domain.company.repository.CompanyRepository
import settlement.core.domain.settlement.DeliverySettlement
import settlement.core.domain.settlement.PackagingSettlement
import settlement.core.domain.settlement.ShippingSettlement
import settlement.core.domain.settlement.repository.DeliverySettlementRepository
import settlement.core.domain.settlement.repository.PackagingSettlementRepository
import settlement.core.domain.settlement.repository.ShippingSettlementRepository
import java.time.YearMonth
import java.util.*
import kotlin.random.Random

@SpringBootApplication
@EnableScheduling
class CoreApplication(
    private val companyRepository: CompanyRepository,
    private val deliverySettlementRepository: DeliverySettlementRepository,
    private val packagingSettlementRepository: PackagingSettlementRepository,
    private val shippingSettlementRepository: ShippingSettlementRepository,
) {

    @PostConstruct
    fun initData() {
        val date = YearMonth.now().atDay(1)
        val companies = (1..100).map {
            Company("회사$it", UUID.randomUUID().toString())
        }
        companyRepository.saveAllAndFlush(companies)

        companies.map { company ->
            val settlements = (1..50).map {
                val daysToAdd = Random.nextLong(0, 20)
                val deliverySettlement = DeliverySettlement(company.id, date.plusDays(daysToAdd), Random.nextInt(10000, 100000))
                val packagingSettlement = PackagingSettlement(company.id, date.plusDays(daysToAdd), Random.nextInt(10000, 100000))
                val shippingSettlement = ShippingSettlement(company.id, date.plusDays(daysToAdd), Random.nextInt(10000, 100000))
                Triple(deliverySettlement, packagingSettlement, shippingSettlement)
            }
            val deliverySettlements = settlements.map { it.first }
            val packagingSettlements = settlements.map { it.second }
            val shippingSettlements = settlements.map { it.third }

            deliverySettlementRepository.saveAllAndFlush(deliverySettlements)
            packagingSettlementRepository.saveAllAndFlush(packagingSettlements)
            shippingSettlementRepository.saveAllAndFlush(shippingSettlements)
        }
    }
}

fun main(args: Array<String>) {
    runApplication<CoreApplication>(*args)
}
