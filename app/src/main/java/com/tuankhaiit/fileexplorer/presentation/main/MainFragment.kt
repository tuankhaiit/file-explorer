package com.tuankhaiit.fileexplorer.presentation.main

import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS
import android.provider.Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import com.tuankhaiit.fileexplorer.R
import com.tuankhaiit.fileexplorer.databinding.FragmentMainBinding
import com.tuankhaiit.fileexplorer.model.SortMode
import com.tuankhaiit.fileexplorer.model.SortType
import com.tuankhaiit.fileexplorer.presentation.base.BaseFragment
import com.tuankhaiit.fileexplorer.presentation.container.ContainerFragment
import com.tuankhaiit.fileexplorer.util.FileUtils

class MainFragment : BaseFragment(), FileExplorerNavigation {
    private lateinit var binding: FragmentMainBinding
    private val viewModel: MainViewModel by viewModels()

    private var currentNavigationRequest = NavigationRequest(
        ROOT_PATH_DEFAULT,
        SORT_TYPE_DEFAULT,
        SORT_MODE_DEFAULT
    )

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                    checkPermissionsForAndroidR()
                } else {
                    allPermissionsWasGranted()
                }
            } else {
                permissionsWasDeniedByUser()
            }
        }

    private val requestPermissionManualLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            checkPermissions()
        }

    @RequiresApi(Build.VERSION_CODES.R)
    private val requestAllFilesAccessLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            checkPermissionsForAndroidR()
        }

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
    }

    override fun initObservers() {
        super.initObservers()
        viewModel.currentNavigationState.observe(viewLifecycleOwner) {
            // Update container
            val targetFragment = ContainerFragment.newInstance(it.path, it.sortType, it.sortMode)
            addContainer(targetFragment, ContainerFragment.TAG + it.path)
        }
    }

    private fun checkPermissions() {
        when {
            ContextCompat.checkSelfPermission(
                requireContext(),
                READ_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED -> {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                    checkPermissionsForAndroidR()
                } else {
                    allPermissionsWasGranted()
                }
            }
            shouldShowRequestPermissionRationale(READ_EXTERNAL_STORAGE) -> {
                showRequestPermissionRationale()
            }
            else -> {
                requestPermissionLauncher.launch(READ_EXTERNAL_STORAGE)
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.R)
    private fun checkPermissionsForAndroidR() {
        if (Environment.isExternalStorageManager()) {
            allPermissionsWasGranted()
        } else {
            showRequestPermissionRationaleForAndroidR()
        }
    }

    @RequiresApi(Build.VERSION_CODES.R)
    private fun showRequestPermissionRationaleForAndroidR() {
        showAlertMessage(R.string.message_request_all_files_access) {
            navigationToSystemSettings()
        }
    }

    private fun showRequestPermissionRationale() {
        showAlertMessage(R.string.message_request_permission_rationale) {
            requestPermissionLauncher.launch(READ_EXTERNAL_STORAGE)
        }
    }

    private fun permissionsWasDeniedByUser() {
        showAlertMessage(R.string.message_request_permission_rationale) {
            navigationToPermissionSettings()
        }
    }

    private fun navigationToPermissionSettings() {
        val intent = Intent(
            ACTION_APPLICATION_DETAILS_SETTINGS,
            Uri.parse("package:${requireContext().packageName}")
        )
        requestPermissionManualLauncher.launch(intent)
    }

    @RequiresApi(Build.VERSION_CODES.R)
    private fun navigationToSystemSettings() {
        val intent = Intent(ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION)
        requestAllFilesAccessLauncher.launch(intent)
    }

    private fun allPermissionsWasGranted() {
        viewModel.currentNavigationState.value = currentNavigationRequest
    }

    override fun navigateToPath(navigationRequest: NavigationRequest) {
        currentNavigationRequest = navigationRequest
        checkPermissions()
    }

    override fun backToParent() {
        val currentState = viewModel.currentNavigationState.value ?: return
        val parentPath = FileUtils.getParentFromPath(currentState.path)
        navigateToPath(NavigationRequest(parentPath, currentState.sortType, currentState.sortMode))
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
        val TAG: String = MainFragment::class.java.simpleName

        val ROOT_PATH_DEFAULT: String = Environment.getExternalStorageDirectory().absolutePath
        val SORT_TYPE_DEFAULT = SortType.NAME
        val SORT_MODE_DEFAULT = SortMode.ASCENDING
    }
}