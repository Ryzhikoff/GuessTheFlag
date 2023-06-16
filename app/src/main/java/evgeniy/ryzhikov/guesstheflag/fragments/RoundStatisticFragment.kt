package evgeniy.ryzhikov.guesstheflag.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import evgeniy.ryzhikov.guesstheflag.databinding.FragmentRoundStatisticBinding
import evgeniy.ryzhikov.guesstheflag.statistic.RoundStatistic
import evgeniy.ryzhikov.guesstheflag.view.StatisticItemView

class RoundStatisticFragment : Fragment() {
    private var _binding: FragmentRoundStatisticBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRoundStatisticBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val roundStatistic = arguments?.get("statistic") as RoundStatistic

        //binding.roundContainer.addView(StatisticItemView(requireContext(), null))

//        binding.tvCorrectAnswer.text = roundStatistic.countCorrectAnswer.toString()
//        binding.tvWrongAnswer.text = roundStatistic.countWrongAnswer.toString()
//        binding.tvPoints.text = roundStatistic.points.toString()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}