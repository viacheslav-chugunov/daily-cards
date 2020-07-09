package io.dailycards.fragment

class ReversedCardFragment : CardFragment() {
    override val reversedStatus = true

    override fun onStart() {
        super.onStart()
        answers = questions.also { questions = answers }
        updateRecyclerView()
    }
}