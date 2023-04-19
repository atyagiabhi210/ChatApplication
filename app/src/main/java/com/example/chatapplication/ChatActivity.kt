package com.example.chatapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.chatapplication.databinding.ActivityChatBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class ChatActivity : AppCompatActivity() {
    private lateinit var binding: ActivityChatBinding
    private lateinit var messageAdapter: MessageAdapter
    private lateinit var messageList: ArrayList<Message>
    private lateinit var myDbRef:DatabaseReference
    //we will create rooms to create a private environment so that the messages are not reflected
    //to all the users using the application
    var recieverRoom:String?=null
    var senderRoom:String?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityChatBinding.inflate(layoutInflater)
        myDbRef=FirebaseDatabase.getInstance().reference
        setContentView(binding.root)
        binding.chatRecyclerView
        binding.messageBox
        binding.sentButton
        messageList= ArrayList()

        messageAdapter= MessageAdapter(this,messageList)
        binding.chatRecyclerView.layoutManager=LinearLayoutManager(this)
        binding.chatRecyclerView.adapter=messageAdapter
        //logic for adding the data into the recycler view


        //val intent=Intent()
        val name= intent.getStringExtra("name")
        val recieverUid=intent.getStringExtra("uid")
        val senderUid=FirebaseAuth.getInstance().currentUser?.uid

        senderRoom=recieverUid+senderUid;
        recieverRoom=senderUid+recieverUid;
        myDbRef.child("chats").child(senderRoom!!).child("messages")
            .addValueEventListener(object :ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    messageList.clear()
                    for (postsnapshot in snapshot.children){
                        val message=postsnapshot.getValue(Message::class.java)
                        messageList.add(message!!)
                    }
                    messageAdapter.notifyDataSetChanged()
                }

                override fun onCancelled(error: DatabaseError) {

                }

            })


        //logic of adding the message to the data-base
        binding.sentButton.setOnClickListener {
            val message=binding.messageBox.text.toString()
            // we need to create message object
            val messageObject=Message(message,senderUid)
            //updated the sender room
            myDbRef.child("chats").child(senderRoom!!).child("messages").push()
                .setValue(messageObject).addOnSuccessListener {
                    myDbRef.child("chats").child(recieverRoom!!).child("messages").push()
                        .setValue(messageObject)
                }
            binding.messageBox.setText("")
        }

        supportActionBar?.title=name
    }
}