package com.application.myapplication.activity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.application.myapplication.R;
import com.application.myapplication.db.CommentsData;
import com.application.myapplication.db.CommentsDataDao;
import com.application.myapplication.db.CommentsDatabase;
import com.application.myapplication.network.DataClass;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;

public  class DetailActivity extends AppCompatActivity {


    private EditText edtComment;
    private Button btn_submit;
    private View progressCircular;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_fragment);


        /*DBHelper db = Room.databaseBuilder(
                getApplicationContext(),
                DBHelper.class, "Comments.db").build();*/

        String dataclass=getIntent().getStringExtra("data");
        final DataClass dataClass=new Gson().fromJson(dataclass,DataClass.class);

        ImageView img_data =  findViewById(R.id.img_data);
        TextView tv_detail =  findViewById(R.id.tv_detail);
         edtComment = findViewById(R.id.edt_comment);
         btn_submit = findViewById(R.id.btn_submit);
        progressCircular=findViewById(R.id.progress_circular);
        //For fetching Comments data on the basis of Id
        fetchData(dataClass.getId());
        tv_detail.setText(dataClass.getId());
        dataClass.getTitle();
        setTitle(dataClass.getTitle());
        String url=null;
        if (dataClass.getImages() != null) {
            dataClass.getImages().get(0).getLink();
        }
        url=dataClass.getImages().get(0).getLink();

        Glide.with(DetailActivity.this)
                .load(url)
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .fallback(R.mipmap.ic_launcher)
                .into(img_data);

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!TextUtils.isEmpty(edtComment.getText().toString()))
                saveDataToDB(dataClass,edtComment.getText().toString());
            }
        });
    }

    private void saveDataToDB(DataClass dataClass, String comments) {

        {
            CommentsDatabase db;
            db = CommentsDatabase.getDatabase(getApplicationContext());
            CommentsData commentsData = new CommentsData(dataClass.getId(), comments);
            CommentsDataDao commentDatasDao = db.commentsDataDao();
            new InsertAsyncTask(commentDatasDao).execute(commentsData);

        }
    }

    private void fetchData(String id){
        CommentsDatabase db;
        db = CommentsDatabase.getDatabase(getApplicationContext());
        CommentsDataDao commentDatasDao = db.commentsDataDao();
        new FetchRecordAsyncTask(commentDatasDao).execute(id);

    }

    class InsertAsyncTask extends AsyncTask<CommentsData, Void, Void> {

        private final CommentsDataDao asyncTaskDao;

        InsertAsyncTask(CommentsDataDao dao) {
            asyncTaskDao = dao;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressCircular.setVisibility(View.VISIBLE);
        }

        @Override
        protected Void doInBackground(final CommentsData... params) {
            asyncTaskDao.insert(params[0]);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progressCircular.setVisibility(View.GONE);
            Toast.makeText(DetailActivity.this,R.string.comment_saved,Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    class FetchRecordAsyncTask extends AsyncTask<String, Void, CommentsData> {

        private final CommentsDataDao asyncTaskDao;

        FetchRecordAsyncTask(CommentsDataDao dao) {
            asyncTaskDao = dao;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressCircular.setVisibility(View.VISIBLE);
        }

        @Override
        protected CommentsData doInBackground(final String... params) {
            return asyncTaskDao.getAll(params[0]);
        }

        @Override
        protected void onPostExecute(CommentsData commentsData) {
            super.onPostExecute(commentsData);
            progressCircular.setVisibility(View.GONE);
            if(commentsData != null) {
                commentsData.getData();
                edtComment.setText(commentsData.getData().toString());
                btn_submit.setText(getResources().getString(R.string.update));


            }

        }
    }

}

