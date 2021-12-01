package com.devansab.major1.views.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.devansab.major1.R
import com.devansab.major1.adapters.SentMessagesRVAdapter
import com.devansab.major1.data.AppDatabase
import com.devansab.major1.data.entities.LastMessage
import com.devansab.major1.utils.DebugLog
import com.devansab.major1.utils.MainApplication
import com.devansab.major1.viewmodels.SentMessagesFragViewModel
import com.devansab.major1.views.activities.ChatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import es.dmoral.toasty.Toasty
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class SentMessagesFragment : Fragment() {
    private lateinit var rootView: View;
    private lateinit var viewModel: SentMessagesFragViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_sent_messages, container, false)

        initViews();
        return rootView
    }

    private fun initViews() {
        val toolbar: Toolbar? = rootView.findViewById(R.id.toolbar_sentMsg_toolbar)
        rootView.findViewById<FloatingActionButton>(R.id.fab_sentMsg_findUser)
            .setOnClickListener { showFindUserPopUp() }
        //(activity as AppCompatActivity).setSupportActionBar(toolbar)
        toolbar?.title = "Sent Messages"

        viewModel = ViewModelProvider(
            this, ViewModelProvider.AndroidViewModelFactory
                .getInstance(MainApplication.instance)
        )[SentMessagesFragViewModel::class.java]

        val appDatabase = AppDatabase.getInstance(requireContext())
        val lastMessageDao = appDatabase.lastMessageDao()

//        GlobalScope.launch {
//            lastMessageDao.insertLastMessage(
//                LastMessage(
//                    "ansab", "this is1 from ansab",
//                    System.currentTimeMillis(), "Ansab Parvez", false
//                )
//            )
//
//            lastMessageDao.insertLastMessage(
//                LastMessage(
//                    "ansab_2", "this is 1 from ansab_2",
//                    System.currentTimeMillis(), "John", false
//                )
//            )
//
//            lastMessageDao.insertLastMessage(
//                LastMessage(
//                    "ansab_3", "this is 1 from ansab_3",
//                    System.currentTimeMillis(), "Doe", false
//                )
//            )
//
//            lastMessageDao.insertLastMessage(
//                LastMessage(
//                    "ansab_2", "this is 2 from ansab_2",
//                    System.currentTimeMillis(), "John", false
//                )
//            )
//
//            lastMessageDao.insertLastMessage(
//                LastMessage(
//                    "ansab_2", "this is 3 from ansab_2",
//                    System.currentTimeMillis(), "John", false
//                )
//            )
//
//            lastMessageDao.insertLastMessage(
//                LastMessage(
//                    "ansab_4", "this is 1 from ansab_4",
//                    System.currentTimeMillis(), "Yuk", false
//                )
//            )
//
//            lastMessageDao.insertLastMessage(
//                LastMessage(
//                    "ansab", "this is last from ansab",
//                    System.currentTimeMillis(), "Ansab Parvez", false
//                )
//            )
//        }

        viewModel.viewModelScope.launch {
            viewModel.getAllUnAnonymousLastMessages().collect {
                displayLastMessages(ArrayList(it))
            }
        }
    }

    private fun showFindUserPopUp() {
        val findUserLayout = activity?.layoutInflater?.inflate(R.layout.layout_search_user, null)
        val alertDialog = AlertDialog.Builder(requireContext())
            .setView(findUserLayout)
            .create()

        val etSearchUserName = findUserLayout?.findViewById<EditText>(R.id.et_searchUser_userName);
        val btnSearchUser = findUserLayout?.findViewById<Button>(R.id.btn_searchUser_search);

        setFindUserObserver()
        btnSearchUser?.setOnClickListener {
            viewModel.findUser(etSearchUserName?.text.toString())
        }

        alertDialog.show()
    }

    private fun setFindUserObserver() {
        viewModel.getFindUserLiveData().observe(viewLifecycleOwner, Observer {
            if (it.success) {
                val intent = Intent(requireActivity(), ChatActivity::class.java)
                intent.putExtra("userName", it.user?.userName)
                startActivity(intent)
                Toasty.success(requireContext(), it.user?.userName.toString()).show()
            } else {
                Toasty.error(requireContext(), it.error.toString()).show()
            }
        })
    }

    private fun displayLastMessages(messagesList: ArrayList<LastMessage>) {
        if (messagesList.size == 0) {
            rootView.findViewById<TextView>(R.id.tv_sentMsg_noChatsText)
                .visibility = View.VISIBLE
            return
        }
        val rvLastMessages = rootView.findViewById<RecyclerView>(R.id.rv_sentMsg_chatPreview);
        rvLastMessages.layoutManager = LinearLayoutManager(requireContext())
        val sentMessagesAdapter = SentMessagesRVAdapter(messagesList)
        rvLastMessages.adapter = sentMessagesAdapter
        sentMessagesAdapter.notifyDataSetChanged()
    }


}