package com.tuankhaiit.fileexplorer.presentation.container

import android.os.Bundle
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.VERTICAL
import com.tuankhaiit.fileexplorer.R
import com.tuankhaiit.fileexplorer.databinding.FragmentContainerBinding
import com.tuankhaiit.fileexplorer.model.SortMode
import com.tuankhaiit.fileexplorer.model.SortType
import com.tuankhaiit.fileexplorer.presentation.base.BaseFragment
import com.tuankhaiit.fileexplorer.presentation.container.adapter.FileAdapter
import com.tuankhaiit.fileexplorer.presentation.container.adapter.OnFileClickListener
import com.tuankhaiit.fileexplorer.presentation.main.FileExplorerNavigation
import com.tuankhaiit.fileexplorer.presentation.main.MainFragment
import com.tuankhaiit.fileexplorer.presentation.main.MainViewModel
import kotlinx.coroutines.launch

class ContainerFragment : BaseFragment(), OnFileClickListener {
    private lateinit var binding: FragmentContainerBinding
    private val mainViewModel: MainViewModel by viewModels(ownerProducer = { requireParentFragment() })
    private val viewModel: ContainerViewModel by viewModels()
    private lateinit var adapter: FileAdapter

    lateinit var rootPath: String
    lateinit var sortType: SortType
    lateinit var sortMode: SortMode

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        rootPath = arguments?.getString(ROOT_FILE_PATH_KEY)
            ?: Environment.getExternalStorageDirectory().absolutePath
        sortType = SortType.valueOf(arguments?.getString(SORT_TYPE_KEY) ?: SortType.NAME.name)
        sortMode = SortMode.valueOf(arguments?.getString(SORT_MODE_KEY) ?: SortMode.ASCENDING.name)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentContainerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun initViews() {
        super.initViews()
        adapter = FileAdapter(this)

        binding.rvFile.apply {
            val decoration = DividerItemDecoration(context, VERTICAL)
            addItemDecoration(decoration)
            layoutManager = LinearLayoutManager(context)
            adapter = this@ContainerFragment.adapter
        }
        binding.btnSortMode.setOnClickListener {
            sortMode =
                if (sortMode == SortMode.ASCENDING) SortMode.DESCENDING else SortMode.ASCENDING
            onUpdateSortState()
            loadFiles()
        }

        onUpdateSortState()
    }

    private fun loadFiles() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.loadFiles(
                requireContext(),
                rootPath,
                sortType,
                sortMode
            )
        }
    }

    override fun initObservers() {
        super.initObservers()
        viewModel.listData.observe(viewLifecycleOwner) {
            if (it.isEmpty()) {
                onNoFilesState()
            } else {
                onHasDataState(it)
            }
        }
        viewModel.navigateToPathEvent.observe(viewLifecycleOwner) { path ->
            getNavigation()?.navigateToPath(path, sortType, sortMode)
        }
    }

    override fun loadData() {
        super.loadData()
        loadFiles()
    }

    private fun onHasDataState(files: List<FileUI>) {
        adapter.submitList(files)
    }

    private fun onNoFilesState() {
        binding.txtNoFiles.isVisible = true
        binding.btnSortMode.isVisible = false
        binding.btnSortType.isVisible = false
    }

    private fun onUpdateSortState() {
        binding.btnSortType.text = sortType.name
        binding.btnSortMode.setImageResource(
            if (sortMode == SortMode.ASCENDING)
                R.drawable.ic_baseline_sort_ascending
            else R.drawable.ic_baseline_sort_descending
        )
    }

    override fun onFileClick(fileUI: FileUI) {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.onFileClick(fileUI)
        }
    }

    private fun getNavigation(): FileExplorerNavigation? {
        return requireParentFragment() as? MainFragment
    }

    companion object {
        val TAG = ContainerFragment::class.simpleName
        private const val ROOT_FILE_PATH_KEY = "ROOT_FILE_PATH_KEY"
        private const val SORT_TYPE_KEY = "SORT_TYPE_KEY"
        private const val SORT_MODE_KEY = "SORT_MODE_KEY"

        fun newInstance(
            rootPath: String,
            sortType: SortType,
            sortMode: SortMode
        ): ContainerFragment {
            return ContainerFragment().apply {
                arguments = bundleOf(
                    ROOT_FILE_PATH_KEY to rootPath,
                    SORT_TYPE_KEY to sortType.name,
                    SORT_MODE_KEY to sortMode.name
                )
            }
        }
    }
}