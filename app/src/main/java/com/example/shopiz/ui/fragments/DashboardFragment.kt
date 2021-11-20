package com.example.shopiz.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.shopiz.R
import com.example.shopiz.databinding.FragmentDashboardBinding
import com.example.shopiz.firestore.FirestoreClass
import com.example.shopiz.models.Product
import com.example.shopiz.ui.activities.CartListActivity
import com.example.shopiz.ui.activities.ProductDetailsActivity
import com.example.shopiz.ui.activities.SettingsActivity
import com.example.shopiz.ui.adapters.DashboardItemsListAdapter
import com.example.shopiz.utils.Constants
import kotlinx.android.synthetic.main.fragment_dashboard.*


class DashboardFragment : BaseFragment() {


private var _binding: FragmentDashboardBinding? = null
  // This property is only valid between onCreateView and
  // onDestroyView.
  private val binding get() = _binding!!


  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setHasOptionsMenu(true)
  }

  override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
    inflater.inflate(R.menu.dashboard_menu, menu)
    super.onCreateOptionsMenu(menu, inflater)
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    val id = item.itemId

    when (id) {

      R.id.action_settings -> {

        startActivity(Intent(activity, SettingsActivity::class.java))
        return true
      }

      R.id.action_cart -> {
        startActivity(Intent(activity, CartListActivity::class.java))
        return true
      }
    }

    return super.onOptionsItemSelected(item)

  }

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {

    _binding = FragmentDashboardBinding.inflate(inflater, container, false)
    val root: View = binding.root


    return root
  }

  fun successDashboardItemsList(dashboardItemsList: ArrayList<Product>) {

    // Hide the progress dialog.
    hideProgressDialog()

    if (dashboardItemsList.size > 0) {

      rv_dashboard_items.visibility = View.VISIBLE
      tv_no_dashboard_items_found.visibility = View.GONE

      rv_dashboard_items.layoutManager = GridLayoutManager(activity, 2)
      rv_dashboard_items.setHasFixedSize(true)

      val adapter = DashboardItemsListAdapter(requireActivity(), dashboardItemsList)
      rv_dashboard_items.adapter = adapter

      adapter.setOnClickListener(object :
        DashboardItemsListAdapter.OnClickListener {
        override fun onClick(position: Int, product: Product) {

          val intent = Intent(context, ProductDetailsActivity::class.java)
          intent.putExtra(Constants.EXTRA_PRODUCT_ID, product.product_id)
          intent.putExtra(Constants.EXTRA_PRODUCT_OWNER_ID, product.user_id)
          startActivity(intent)
        }
      })
      // END
    } else {
      rv_dashboard_items.visibility = View.GONE
      tv_no_dashboard_items_found.visibility = View.VISIBLE
    }
  }


  private fun getDashboardItemsList() {
    // Show the progress dialog.
    showProgressDialog(resources.getString(R.string.please_wait))

    FirestoreClass().getDashboardItemsList(this@DashboardFragment)
  }

  override fun onResume() {
    super.onResume()
    getDashboardItemsList()
  }

override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}