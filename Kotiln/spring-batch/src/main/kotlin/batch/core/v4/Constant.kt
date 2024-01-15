package batch.core.v4

object Constant {
    object DataSourceA {
        const val ENTITY_MANAGER_FACTORY_REF = "entityManagerFactoryA"
        const val BASE_PACKAGE = "batch.core.v4.a.entity"
        const val TRANSACTION_MANGER_REF = "transactionManagerA"
        const val ENTITY_MANAGER = "entityManagerA"
    }

    object DataSourceB {
        const val ENTITY_MANAGER_FACTORY_REF = "entityManagerFactoryB"
        const val BASE_PACKAGE = "batch.core.v4.b.entity"
        const val TRANSACTION_MANGER_REF = "transactionManagerB"
        const val ENTITY_MANAGER = "entityManagerB"
    }
}
