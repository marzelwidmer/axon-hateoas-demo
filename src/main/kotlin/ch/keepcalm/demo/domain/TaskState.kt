package ch.keepcalm.demo.domain
/**
 * An Task is used to define the state of a ReceivingTask
 */
enum class TaskState {
    /** When the Task has been created.  */
    Open,
    /** Task completed successfully.  */
    Done
}
