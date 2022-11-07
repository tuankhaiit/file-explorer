package com.tuankhaiit.fileexplorer.presentation.addressBar

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tuankhaiit.fileexplorer.databinding.FragmentAddressBarBinding
import com.tuankhaiit.fileexplorer.presentation.addressBar.adapter.AddressBarAdapter
import com.tuankhaiit.fileexplorer.presentation.addressBar.adapter.OnAddressBarClickListener
import com.tuankhaiit.fileexplorer.presentation.base.BaseFragment
import com.tuankhaiit.fileexplorer.presentation.main.FileExplorerNavigation
import com.tuankhaiit.fileexplorer.presentation.main.MainFragment
import com.tuankhaiit.fileexplorer.presentation.main.MainViewModel
import com.tuankhaiit.fileexplorer.presentation.main.NavigationRequest
import java.io.File

class AddressBarFragment : BaseFragment(), OnAddressBarClickListener {
    private lateinit var binding: FragmentAddressBarBinding
    private val mainViewModel: MainViewModel by viewModels(ownerProducer = { requireParentFragment() })
    private val viewModel: AddressBarViewModel by viewModels()
    private lateinit var adapter: AddressBarAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddressBarBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun initViews() {
        super.initViews()
        adapter = AddressBarAdapter(this)

        binding.rvAddressBar.apply {
            layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
            adapter = this@AddressBarFragment.adapter
        }
    }

    override fun initObservers() {
        super.initObservers()
        mainViewModel.currentNavigationState.observe(viewLifecycleOwner) {
            updateAddressBar(it.path)
        }
    }

    private fun updateAddressBar(path: String) {
        val rootPath = MainFragment.ROOT_PATH_DEFAULT
        val separator = File.separator
        val addressBars = path.replaceFirst(rootPath, "").split(separator)

        var tempPath = rootPath
        val result = addressBars.mapIndexed { index, item ->
            val addressBarName = if (index == 0) "Internal Storage" else item
            if (index > 0) {
                tempPath += separator + addressBarName
            }
            AddressBarUI(tempPath, addressBarName)
        }
        adapter.submitList(result)
    }

    override fun onAddressBarClick(addressBarUI: AddressBarUI) {
        val currentState = mainViewModel.currentNavigationState.value ?: return
        if (currentState.path == addressBarUI.path) return

        val sortType = currentState.sortType
        val sortMode = currentState.sortMode
        getNavigation()?.navigateToPath(NavigationRequest(addressBarUI.path, sortType, sortMode))
    }

    private fun getNavigation(): FileExplorerNavigation? {
        return requireParentFragment() as? MainFragment
    }

    companion object {
        val TAG: String = AddressBarFragment::class.java.simpleName
    }

}