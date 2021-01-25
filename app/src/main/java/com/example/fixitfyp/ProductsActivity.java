package com.example.fixitfyp;

import androidx.fragment.app.Fragment;

public class ProductsActivity extends PlumberFragment {

    @Override
    protected Fragment createFragment() {
        return new RecyclerFragment().newInstance();
    }


    /**  private BottomNavigationView bottomNavigationView;
    private PlumberFragment plumberFragment;
    private PainterFragment painterFragment;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);


        bottomNavigationView = findViewById(R.id.bottombar);

        plumberFragment = new PlumberFragment();
        painterFragment = new PainterFragment();

        setFragment(plumberFragment);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {

                    case R.id.menuplumber:
                        bottomNavigationView.setBackgroundResource(R.color.plumber);
                        setFragment(plumberFragment);
                        return true;

                    case R.id.menupainter:
                        bottomNavigationView.setBackgroundResource(R.color.painter);
                        setFragment(painterFragment);
                        return true;

                    case R.id.menucarpenter:
                        bottomNavigationView.setBackgroundResource(R.color.carpenter);
                        return true;

                    case R.id.menuelectric:
                        bottomNavigationView.setBackgroundResource(R.color.electrician);
                        return true;

                    default:
                        return false;
                }
            }
        });
    }

    public void setFragment (Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.mainframe, fragment);
        fragmentTransaction.commit();
    }
**/
}

