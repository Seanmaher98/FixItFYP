package com.example.fixitfyp;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public abstract class PlumberFragment extends AppCompatActivity {
    protected abstract Fragment createFragment();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);

        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.fragment_container);

        if(fragment == null){
            fragment = createFragment();
            fragmentManager.beginTransaction()
                    .add(R.id.fragment_container, fragment)
                    .commit();
        }
    }

    /**  private RecyclerView plumberRecycler;
    List<Data> fetchData;
    HelperAdapter helperAdapter;
    private DatabaseReference plumberDatabase;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View myview = inflater.inflate(R.layout.fragment_plumber, container, false);

        plumberDatabase = FirebaseDatabase.getInstance().getReference("Products");

        /**plumber recycler
        plumberRecycler = myview.findViewById(R.id.recycler_plumber);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false);
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        plumberRecycler.setHasFixedSize(true);
        plumberRecycler.setLayoutManager(layoutManager);

        plumberRecycler = myview.findViewById(R.id.recycler_plumber);
        plumberRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        fetchData = new ArrayList<>();

        plumberDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds:snapshot.getChildren()){
                    Data data=ds.getValue(Data.class);
                    fetchData.add(data);
                }
                helperAdapter = new HelperAdapter(fetchData);
                plumberRecycler.setAdapter(helperAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return myview;


    }

  @Override
    public void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<Data> options = new FirebaseRecyclerOptions.Builder<Data>()
                .setQuery(plumberDatabase, Data.class).build();

        FirebaseRecyclerAdapter<Data, ServiceViewHolder> adapterOne = new FirebaseRecyclerAdapter<Data, ServiceViewHolder>
                (options) {

            @Override
            protected void onBindViewHolder(@NonNull ServiceViewHolder serviceViewHolder, int i, @NonNull Data data) {
                serviceViewHolder.mTitle.setText(data.getProdName());
                serviceViewHolder.mDesc.setText(data.getProdName());
            }

            @NonNull
            @Override
            public ServiceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
               View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_data, parent,false);

               return new ServiceViewHolder(view);
            }
        };

        adapterOne.startListening();
        plumberRecycler.setAdapter(adapterOne);
    }

    public static class PlumberViewHolder extends RecyclerView.ViewHolder{

        View myview;

        public PlumberViewHolder(View itemView) {
            super(itemView);
            myview = itemView;
        }

        public void setName(String name){
            TextView mName = myview.findViewById(R.id.textViewService);
            mName.setText(name);
        }

        public void setEmail(String email){
            TextView mEmail = myview.findViewById(R.id.textViewServiceDescription);
            mEmail.setText(email);
        }


    }**/
}