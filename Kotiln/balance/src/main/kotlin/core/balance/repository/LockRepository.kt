package core.balance.repository

import java.util.concurrent.locks.ReentrantLock

open class LockRepository {
    private val lock = ReentrantLock()

    fun <T> withLock(action: () -> T, rollback: () -> T): T? {
        try {
            lock.lock()
            return action()
        } catch (exception: Exception) {
            rollback()
            throw exception
        } finally {
            lock.unlock()
        }
    }
}
