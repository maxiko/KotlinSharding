package io.maxiko.kotlinsharding.service

import io.maxiko.kotlinsharding.configuration.DBContextHolder
import io.maxiko.kotlinsharding.configuration.ShardEnum
import io.maxiko.kotlinsharding.dto.UserEntity
import io.maxiko.kotlinsharding.dto.UserRepository
import io.maxiko.kotlinsharding.model.UserRequest
import org.springframework.stereotype.Service
import java.util.concurrent.Callable
import java.util.concurrent.Executors

@Service
class UserService(
    private val userRepository: UserRepository,
) {

    fun getUsersByName(name: String): List<UserEntity> {
        setDbContextByName(name)
        return userRepository.findByFirstName(name)
    }

    fun saveUser(userRequest: UserRequest) {
        setDbContextByName(userRequest.firstName)
        userRepository.save(
            UserEntity(
                firstName = userRequest.firstName,
                lastName = userRequest.lastName,
                middleName = userRequest.middleName,
            )
        )
    }

    fun getAllUsers(): List<UserEntity> {
        val executorService = Executors.newFixedThreadPool(ShardEnum.entries.size)
        val tasks = ShardEnum.entries.map { CallableTask(it, userRepository) }
        val results = try {
            executorService.invokeAll(tasks)
                .map { it.get() }
                .flatten()
        } catch (e: Exception) {
            emptyList()
        } finally {
            executorService.shutdown()
        }

        return results
    }

    /**
     * "Переключатель" шарда по имени
     */
    private fun setDbContextByName(name: String) {
        if (Character.toLowerCase(name[0]) in 'а'..'о') {
            DBContextHolder.setCurrentDbContext(ShardEnum.SHARD1)
        } else {
            DBContextHolder.setCurrentDbContext(ShardEnum.SHARD2)
        }

    }

    /**
     * Класс для получения списка пользователей из конкретного шарда
     */
    class CallableTask(
        private val shardEnum: ShardEnum,
        private val userRepository: UserRepository,
    ) : Callable<List<UserEntity>> {
        override fun call(): List<UserEntity> {
            DBContextHolder.setCurrentDbContext(shardEnum)
            return userRepository.findAll()
        }
    }

}