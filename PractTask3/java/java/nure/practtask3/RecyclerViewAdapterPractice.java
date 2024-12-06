package java.nure.practtask3;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerViewAdapterPractice extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_recycler_view_adapter_practice);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Button back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        String[] myDataset = {"Mango", "Banana", "Orange"};
        int[] images = {R.drawable.images, R.drawable.kiwi, R.drawable.orange};
        MyAdapter adapter = new MyAdapter(myDataset, images);
        recyclerView.setAdapter((adapter));
    }

    public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder>{

        public class ViewHolder extends RecyclerView.ViewHolder{
            public TextView textView;
            public ImageView imageView;
            public ViewHolder(View v) {
                super(v);
                textView = v.findViewById(R.id.textView);
                imageView = v.findViewById(R.id.imageView);
            }
        }
        private int[] mImages;
        private String[] mData;


        public MyAdapter(String[] data, int[] images){

            mImages = images;
            mData = data;
        }

        @NonNull
        @Override
        public MyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_item, parent, false);
            return new ViewHolder(v);
        }
        @Override
        public int getItemCount() {
            return mData.length;
        }
        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.textView.setText(mData[position]);
            holder.imageView.setImageResource(mImages[position]);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int currentPosition = holder.getAdapterPosition();
                    if (currentPosition != RecyclerView.NO_POSITION) {
                        Toast.makeText(v.getContext(), mData[currentPosition], Toast.LENGTH_LONG).show();
                    }
                }
            });
        }

    }
}