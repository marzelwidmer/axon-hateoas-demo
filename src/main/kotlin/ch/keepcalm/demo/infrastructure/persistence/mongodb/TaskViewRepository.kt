package ch.keepcalm.demo.infrastructure.persistence.mongodb

import org.springframework.data.mongodb.repository.MongoRepository
import java.util.*

interface TaskViewRepository : MongoRepository<TaskView, String>
