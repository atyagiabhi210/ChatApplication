package com.example.chatapplication

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.chatapplication.databinding.ActivityChatBinding
import com.google.firebase.auth.FirebaseAuth

class MessageAdapter(val context:Context,val messageList:ArrayList<Message>):RecyclerView.Adapter<RecyclerView.ViewHolder>() {
//    private lateinit var binding: ActivityChatBinding
    val ITEM_RECIEVE=1;
    val ITEM_SENT=2;

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        if (viewType==1){
            //inflate recieve
            val view:View=LayoutInflater.from(context).inflate(R.layout.recieved,parent,false)
            return RecieveViewHolder(view)
        }else
        {
            //inflate sent
            val view:View=LayoutInflater.from(context).inflate(R.layout.sent,parent,false)
            return SentViewHolder(view)
        }

    }

    override fun getItemCount(): Int {
        return messageList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentMessage= messageList[position]
        if (holder.javaClass==SentViewHolder::class.java){

            //logic for sentview holder
            //we type cast the viewHolder as sentViewHolder
            val viewHolder= holder as SentViewHolder
            holder.sentMessage.text=currentMessage.message
        }
        else {
            //logic for recieve view holder
            val viewHolder= holder as RecieveViewHolder
            holder.recieveMessage.text=currentMessage.message
        }
    }

    override fun getItemViewType(position: Int): Int {
        val currentMessage=messageList[position]
        if (FirebaseAuth.getInstance().currentUser?.uid.equals(currentMessage.senderId)){
            return ITEM_SENT
        }else{
            return ITEM_RECIEVE
        }
    }
    inner class SentViewHolder(itemView: View):ViewHolder(itemView){
        val sentMessage=itemView.findViewById<TextView>(R.id.txt_msg_sent)

    }
    inner class RecieveViewHolder(itemView: View):ViewHolder(itemView){
        val recieveMessage=itemView.findViewById<TextView>(R.id.txt_msg_recieved)

    }
}