package com.example.a0913android;



import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<Msg> msgList=new ArrayList<>();
    private EditText inputText;
    private Button send;
    private RecyclerView msgRecyclerView;
    private MsgAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initMsgs();
        inputText=(EditText)findViewById(R.id.input_text);
        send=(Button)findViewById(R.id.send);
        msgRecyclerView=(RecyclerView)findViewById(R.id.msg_recycler_view);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);

        msgRecyclerView.addItemDecoration(new SpaceItemDecoration(50));//这里设置了RecyclerView子项的间隔
        msgRecyclerView.setLayoutManager(layoutManager);
        adapter=new MsgAdapter(msgList);
        msgRecyclerView.setAdapter(adapter);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String content=inputText.getText().toString();
                if(!"".equals(content)){//如果内容非空
                    Msg msg=new Msg(content,Msg.TYPE_SENT);
                    msgList.add(msg);
                    adapter.notifyItemInserted(msgList.size()-1);//调用适配器的notifyItemInserted()方法,用于通知适配器有新的数据插入
                    msgRecyclerView.scrollToPosition(msgList.size()-1);//将显示的数据定位到最后一行
                    inputText.setText("");
                }
            }
        });
    }
    public void initMsgs(){
        Msg msg1=new Msg("Time is money, my friend",Msg.TYPE_RECEIVED);
        msgList.add(msg1);
        Msg msg2=new Msg("Yes,my friend",Msg.TYPE_SENT);
        msgList.add(msg2);

    }



    class SpaceItemDecoration extends RecyclerView.ItemDecoration {
        int mSpace;

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);
            outRect.left = 0;
            outRect.right = 0;
            outRect.bottom =0;
            if (parent.getChildAdapterPosition(view) != 0) {
                outRect.top = mSpace;
            }
            else{
                outRect.top=0;
            }

        }

        public SpaceItemDecoration(int space) {
            this.mSpace = space;
        }
    }
}


