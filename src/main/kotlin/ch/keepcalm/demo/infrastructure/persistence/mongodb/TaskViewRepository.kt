package ch.keepcalm.demo.infrastructure.persistence.mongodb

import org.springframework.context.annotation.Profile
import org.springframework.data.mongodb.repository.MongoRepository

@Profile("query")
interface TaskViewRepository : MongoRepository<TaskView, String>
