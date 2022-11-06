package com.tuankhaiit.fileexplorer.presentation.main

import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.viewModels
import com.tuankhaiit.fileexplorer.R
import com.tuankhaiit.fileexplorer.databinding.FragmentMainBinding
import com.tuankhaiit.fileexplorer.model.SortMode
import com.tuankhaiit.fileexplorer.model.SortType
import com.tuankhaiit.fileexplorer.presentation.base.BaseFragment
import com.tuankhaiit.fileexplorer.presentation.container.ContainerFragment
import com.tuankhaiit.fileexplorer.util.FileUtils
import java.util.*

class MainFragment : BaseFragment(), FileExplorerNavigation {
    private lateinit var binding: FragmentMainBinding
    private val viewModel: MainViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun initViews() {
        super.initViews()
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            if (canBack()) {
                backToParent()
            } else {
                isEnabled = false
                requireActivity().onBackPressedDispatcher.onBackPressed()
            }
        }
        checkPermissions()
//        navigateToExternalStorage()
    }

    private fun checkPermissions() {

    }

    private fun navigateToExternalStorage() {
        navigateToPath(ROOT_PATH_DEFAULT, SORT_TYPE_DEFAULT, SORT_MODE_DEFAULT)
    }

    override fun navigateToPath(
        path: String,
        sortType: SortType,
        sortMode: SortMode
    ) {
        Log.e("MainFragment", "navigationToPath: $path")

        // Update address bar
        viewModel.currentPath.value = path
        viewModel.currentSortType.value = sortType
        viewModel.currentSortMode.value = sortMode

        // Update container
        val targetFragment = ContainerFragment.newInstance(path, sortType, sortMode)
        addContainer(targetFragment, ContainerFragment.TAG + path + UUID.randomUUID())
    }

    override fun backToParent() {
        val currentPath = viewModel.currentPath.value ?: return
        val currentSortType = viewModel.currentSortType.value ?: SORT_TYPE_DEFAULT
        val currentSortMode = viewModel.currentSortMode.value ?: SORT_MODE_DEFAULT
        val parentPath = FileUtils.getParentFromPath(currentPath)
        navigateToPath(parentPath, currentSortType, currentSortMode)
    }

    private fun addContainer(fragment: ContainerFragment, tag: String) {
        val fragmentPopped = childFragmentManager.popBackStackImmediate(tag, 0)
        if (fragmentPopped.not() && childFragmentManager.findFragmentByTag(tag) == null) {
            childFragmentManager.beginTransaction().apply {
                replace(R.id.containerFragment, fragment, tag)
                addToBackStack(tag)
                commit()
            }
        }
    }

    private fun canBack(): Boolean {
        return childFragmentManager.backStackEntryCount > 1
    }

    companion object {
        val ROOT_PATH_DEFAULT: String = Environment.getExternalStorageDirectory().absolutePath
        val SORT_TYPE_DEFAULT = SortType.NAME
        val SORT_MODE_DEFAULT = SortMode.ASCENDING
    }
}