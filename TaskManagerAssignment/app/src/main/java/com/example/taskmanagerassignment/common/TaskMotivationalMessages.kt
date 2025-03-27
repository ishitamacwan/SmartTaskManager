package com.example.taskmanagerassignment.common

class TaskMotivationalMessages {
    companion object {
        private val morningMessages = listOf(
            "Good morning! A fresh start awaits.",
            "Today is a perfect day to crush your goals!",
            "Rise and shine! Your productivity starts now.",
            "Every morning brings a new opportunity to achieve.",
            "Wake up with determination, go to bed with satisfaction."
        )
        private val afternoonMessages = listOf(
            "You've got this! Keep pushing forward.",
            "Small progress is still progress.",
            "Every task completed is a step closer to your dreams.",
            "Believe in yourself and your abilities.",
            "Your potential is limitless."
        )
        private val eveningMessages = listOf(
            "Reflect on your day and plan for tomorrow.",
            "Rest is important, but so is progress.",
            "Tomorrow is a new chance to start again.",
            "Celebrate small wins, no matter how tiny.",
            "Your journey of a thousand miles begins with a single task."
        )
        private val generalMessages = listOf(
            "No tasks yet? That's an opportunity to plan!",
            "Every great achievement starts with a single task.",
            "Your productivity journey begins here.",
            "An empty list is just a canvas for your success.",
            "Ready to turn your goals into reality?"
        )

        // Get a motivational message based on time of day
        fun getMotivationalMessage(): String {
            val currentHour = java.util.Calendar.getInstance().get(java.util.Calendar.HOUR_OF_DAY)
            return when (currentHour) {
                in 5..11 -> morningMessages.random()
                in 12..16 -> afternoonMessages.random()
                in 17..21 -> eveningMessages.random()
                else -> generalMessages.random()
            }
        }

        // Get a message for specific empty state scenarios
        fun getEmptyStateMessage(scenario: EmptyStateType): String {
            return when (scenario) {
                EmptyStateType.NO_TASKS -> "Your task list is empty. Ready to get started?"
                EmptyStateType.NO_COMPLETED_TASKS -> "No completed tasks yet. Keep pushing forward!"
                EmptyStateType.NO_PENDING_TASKS -> "All tasks completed. Great job!"
            }
        }


    }

    // Enum for different empty state scenarios
    enum class EmptyStateType {
        NO_TASKS,
        NO_COMPLETED_TASKS,
        NO_PENDING_TASKS
    }
}