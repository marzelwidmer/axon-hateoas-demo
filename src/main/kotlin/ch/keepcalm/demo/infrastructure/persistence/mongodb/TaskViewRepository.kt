package ch.keepcalm.demo.infrastructure.persistence.mongodb

import org.springframework.data.mongodb.repository.MongoRepository

interface TaskViewRepository : MongoRepository<TaskView, String>
