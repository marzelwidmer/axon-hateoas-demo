package ch.keepcalm.demo.domain

//@JvmInline
data class TaskId(val value: String) {
    init {
        require(value.isNotEmpty()) { "TaskId must be non-empty" }
        require(value.trim().length <= 40) { "wrong TaskId length" }
        require(value.trim().length > 2) { "wrong TaskId length" }
    }

    override fun toString() = value
}
