package com.example.taskmanagerassignment.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.taskmanagerassignment.common.SortType
import com.example.taskmanagerassignment.databinding.FragmentSortBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class SortBottomSheetFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentSortBottomSheetBinding? = null
    private val binding get() = _binding!!

    interface SortSelectionListener {
        fun onSortSelected(sortType: SortType)
    }

    private var listener: SortSelectionListener? = null
    private var selectedSortType: SortType = SortType.DUEDATE // Default

    companion object {
        private const val ARG_SORT_TYPE = "sort_type"

        fun newInstance(sortType: SortType): SortBottomSheetFragment {
            val fragment = SortBottomSheetFragment()
            val args = Bundle()
            args.putSerializable(ARG_SORT_TYPE, sortType)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSortBottomSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Get selected sort type from arguments
        selectedSortType = arguments?.getSerializable(ARG_SORT_TYPE) as? SortType ?: SortType.DUEDATE

        // Set the correct RadioButton checked based on current sort type
        when (selectedSortType) {
            SortType.DUEDATE -> binding.radioSortByDueDate.isChecked = true
            SortType.PRIORITY -> binding.radioSortByPriority.isChecked = true
            SortType.ALPHABETICAL -> binding.radioSortAlphabetically.isChecked = true
        }

        // Handle radio button clicks
        binding.radioSortByDueDate.setOnClickListener {
            listener?.onSortSelected(SortType.DUEDATE)
            dismiss()
        }
        binding.radioSortByPriority.setOnClickListener {
            listener?.onSortSelected(SortType.PRIORITY)
            dismiss()
        }
        binding.radioSortAlphabetically.setOnClickListener {
            listener?.onSortSelected(SortType.ALPHABETICAL)
            dismiss()
        }
    }

    fun setSortSelectionListener(listener: SortSelectionListener) {
        this.listener = listener
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
