package evgeniy.ryzhikov.guesstheflag.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import evgeniy.ryzhikov.guesstheflag.databinding.FragmentTotalStatisticBinding
import evgeniy.ryzhikov.guesstheflag.domain.statistic.PREFERENCES_STATISTIC
import evgeniy.ryzhikov.guesstheflag.domain.statistic.PREFERENCES_STATISTIC_CORRECT_ANSWERS
import evgeniy.ryzhikov.guesstheflag.domain.statistic.PREFERENCES_STATISTIC_TOTAL_ANSWERS
import evgeniy.ryzhikov.guesstheflag.domain.statistic.PREFERENCES_STATISTIC_TOTAL_POINTS
import evgeniy.ryzhikov.guesstheflag.domain.statistic.PREFERENCES_STATISTIC_WRONG_ANSWERS

class TotalStatisticFragment : Fragment() {
    private var _binding: FragmentTotalStatisticBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTotalStatisticBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setStatisticOnUi()
    }

    private fun setStatisticOnUi() {
        val sharedPreferences = requireContext().getSharedPreferences(
            PREFERENCES_STATISTIC,
            AppCompatActivity.MODE_PRIVATE
        )

        val correctAnswers = sharedPreferences.getInt(PREFERENCES_STATISTIC_CORRECT_ANSWERS, 0)
        val wrongAnswers = sharedPreferences.getInt(PREFERENCES_STATISTIC_WRONG_ANSWERS, 0)
        val totalAnswers = sharedPreferences.getInt(PREFERENCES_STATISTIC_TOTAL_ANSWERS, 0)

        var percent = 0f
        if (totalAnswers != 0) {
            percent = correctAnswers.toFloat() / totalAnswers * 100f
        }

        binding.tvPercentCorrectAnswers.text = String.format("%.2f%%", percent)
        binding.tvCorrectAnswer.text = correctAnswers.toString()
        binding.tvWrongAnswer.text = wrongAnswers.toString()
        binding.tvPoints.text =
            sharedPreferences.getInt(PREFERENCES_STATISTIC_TOTAL_POINTS, 0).toString()
        binding.tvTotalAnswers.text = totalAnswers.toString()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}