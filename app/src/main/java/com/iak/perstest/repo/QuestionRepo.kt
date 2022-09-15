package com.iak.perstest.repo


class QuestionRepo {
    companion object {
        var instance = QuestionRepo()
    }

    // move to array resource
    private val questions = listOf(
        "I prefer one-on-one conversations to group activities",
        "I often prefer to express myself in writing",
        "I enjoy solitude",
        "I seem to care about wealth, fame, and status less than my peers",
        "I dislike small talk, but I enjoy talking in-depth about topics that matter to me",
        "People tell me that I’m a good listener",
        "I’m not a big risk-taker",
        "I enjoy work that allows me to “dive in” with few interruptions",
        "I like to celebrate birthdays on a small scale, with only one or two close friends or family members",
        "People describe me as “soft-spoken” or “mellow",
        "I prefer not to show or discuss my work with others until it’s finished",
        "I dislike conflict",
        "I do my best work on my own",
        "I tend to think before I speak",
        "I feel drained after being out and about, even if I’ve enjoyed myself",
        "I often let calls go through to voice-mail.",
        "If I had to choose, I’d prefer a weekend with absolutely nothing to do to one with too many things scheduled",
        "I don’t enjoy multi-tasking",
        "I can concentrate easily",
        "In classroom situations, I prefer lectures to seminars"
    )

    fun getQuestions(count: Int): List<String> {
        return questions.subList(0, count - 1)
    }

    fun assessAnswers(answers: List<Boolean>): String {
        var introvertCount = 0
        var extrovertCount = 0
        for (answer in answers) {
            if (answer) {
                introvertCount++
            } else {
                extrovertCount++
            }
        }
        val verdict: String = if (introvertCount > extrovertCount) {
            "Introverted"
        } else if (extrovertCount > introvertCount) {
            "Extroverted"
        } else {
            "Balanced"
        }
        return verdict
    }
}