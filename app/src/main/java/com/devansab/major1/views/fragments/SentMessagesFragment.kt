package com.devansab.major1.views.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.devansab.major1.R
import com.devansab.major1.adapters.SentMessagesRVAdapter
import com.devansab.major1.data.entities.ChatPreview

class SentMessagesFragment : Fragment() {
    private var rootView: View? = null;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_sent_messages, container, false)

        initViews();
        return rootView
    }

    private fun initViews() {
        val toolbar : Toolbar? = rootView?.findViewById(R.id.toolbar_sentMsg_toolbar)
        //(activity as AppCompatActivity).setSupportActionBar(toolbar)
        toolbar?.title = "Sent Messages"

        val rvChatPreview = rootView?.findViewById<RecyclerView>(R.id.rv_sentMsg_chatPreview)
        val chatPreviewList = ArrayList<ChatPreview>()
        chatPreviewList.add(ChatPreview("ansab", "This is a text message", "15/12/2020"))
        chatPreviewList.add(ChatPreview("ansab", "This is a text message", "15/12/2020"))
        chatPreviewList.add(ChatPreview("ansab", "This is a text message", "15/12/2020"))
        chatPreviewList.add(ChatPreview("ansab", "This is a text message", "15/12/2020"))
        chatPreviewList.add(ChatPreview("ansab", "This is a text message", "15/12/2020"))
        chatPreviewList.add(ChatPreview("ansab", "This is a text message", "15/12/2020"))
        chatPreviewList.add(ChatPreview("ansab", "This is a text message", "15/12/2020"))
        chatPreviewList.add(ChatPreview("ansab", "This is a text message", "15/12/2020"))
        chatPreviewList.add(ChatPreview("ansab", "This is a text message", "15/12/2020"))
        chatPreviewList.add(ChatPreview("ansab", "This is a text message", "15/12/2020"))
        chatPreviewList.add(ChatPreview("ansab", "This is a text message", "15/12/2020"))
        chatPreviewList.add(ChatPreview("ansab", "This is a text message", "15/12/2020"))
        chatPreviewList.add(ChatPreview("ansab", "This is a text message", "15/12/2020"))
        chatPreviewList.add(ChatPreview("ansab", "This is a text message", "15/12/2020"))
        chatPreviewList.add(ChatPreview("ansab last", "This is a text message", "15/12/2020"))

        val chatPreviewAdapter = SentMessagesRVAdapter(chatPreviewList)
        rvChatPreview?.layoutManager = LinearLayoutManager(context)
        rvChatPreview?.adapter = chatPreviewAdapter
        chatPreviewAdapter.notifyItemRangeInserted(0, chatPreviewList.size)
    }
}