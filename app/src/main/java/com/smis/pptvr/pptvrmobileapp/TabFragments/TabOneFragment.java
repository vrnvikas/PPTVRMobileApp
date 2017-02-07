package com.smis.pptvr.pptvrmobileapp.TabFragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.arlib.floatingsearchview.FloatingSearchView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GetTokenResult;
import com.smis.pptvr.pptvrmobileapp.SignInActivity;
import com.smis.pptvr.pptvrmobileapp.network.BooleanChangeListener;
import com.smis.pptvr.pptvrmobileapp.network.FilterChangeListener;
import com.smis.pptvr.pptvrmobileapp.MainActivity;
import com.smis.pptvr.pptvrmobileapp.R;
import com.smis.pptvr.pptvrmobileapp.network.Utility;
import com.smis.pptvr.pptvrmobileapp.network.WebService;
import com.smis.pptvr.pptvrmobileapp.pojo.Projects;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TabOneFragment extends Fragment implements SearchView.OnQueryTextListener, Callback<List<Projects>>,
        BooleanChangeListener, FilterChangeListener {

    private RecyclerView recyclerview;
    private List<Projects> projectsList;
    private RVAdapterTabOne adapter;
    private View loadingView;
    private TabOneFragment context;
    private static final String TAG_PRIVATE = "true";
    private boolean isVisible = false;
    private BooleanChangeListener mListener;
    private SwipeRefreshLayout swipeRefreshLayout;
    private String userIdToken;


    public TabOneFragment() {
        mListener = this;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.tab_one_fragment, container, false);
        loadingView = view.findViewById(R.id.loding_view);
        recyclerview = (RecyclerView) view.findViewById(R.id.recyclerview);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshLayout);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerview.setLayoutManager(layoutManager);
        return view;
    }

    @Override
    public void onViewCreated(final View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        context = this;
        setHasOptionsMenu(true);
        String[] locales = Locale.getISOCountries();
        projectsList = new ArrayList<>();


        adapter = new RVAdapterTabOne(projectsList, getActivity());
        recyclerview.setAdapter(adapter);
        hideRecyclerView();
        showLoadingView();

        FirebaseUser mUser = FirebaseAuth.getInstance().getCurrentUser();
        mUser.getToken(true).addOnCompleteListener(new OnCompleteListener<GetTokenResult>() {
            public void onComplete(@NonNull Task<GetTokenResult> task) {
                if (task.isSuccessful()) {
                    userIdToken = task.getResult().getToken();
                    Log.d("GoogleActivity", "userToken: " + userIdToken + " : ");
                    // Send token to your backend via HTTPS
                    final WebService service = Utility.createAppWebService(userIdToken);
                    service.getAllProjects(TAG_PRIVATE).enqueue(context);

                } else {
                    // Handle error -> task.getException();
                    //Toast.makeText(getActivity(), "No Internet Connection", Toast.LENGTH_LONG).show();
                    Log.d("GoogleActivity", "userToken: null");
                    Snackbar bar = Snackbar.make(view, "No Internet Connection", Snackbar.LENGTH_LONG)
                            .setAction("Dismiss", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    // Handle user action

                                }
                            });

                    if(getUserVisibleHint()){
                        bar.show();
                    }
                }
            }
        });


        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                FirebaseUser mUser = FirebaseAuth.getInstance().getCurrentUser();
                mUser.getToken(true).addOnCompleteListener(new OnCompleteListener<GetTokenResult>() {
                    public void onComplete(@NonNull Task<GetTokenResult> task) {
                        if (task.isSuccessful()) {
                            userIdToken = task.getResult().getToken();
                            Log.d("GoogleActivity", "userToken: " + userIdToken + " : ");
                            // Send token to your backend via HTTPS
                            final WebService service = Utility.createAppWebService(userIdToken);
                            service.getAllProjects(TAG_PRIVATE).enqueue(context);

                        } else {
                            // Handle error -> task.getException();
                            //Toast.makeText(getActivity(), "No Internet Connection", Toast.LENGTH_LONG).show();
                            Log.d("GoogleActivity", "userToken: null");
                            Snackbar bar = Snackbar.make(view, "No Internet Connection", Snackbar.LENGTH_LONG)
                                    .setAction("Dismiss", new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            // Handle user action

                                        }
                                    });

                            if(getUserVisibleHint()){
                                bar.show();
                            }
                            swipeRefreshLayout.setRefreshing(false);
                        }
                    }
                });
//                if(userIdToken != null){
//                    final WebService service = Utility.createAppWebService(userIdToken);
//                    service.getAllProjects(TAG_PRIVATE).enqueue(context);
//                }
            }
        });


    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        isVisible = isVisibleToUser;
        Log.d("vikas", "tab one:" + isVisibleToUser);
        mListener.valueChanged();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);

        final MenuItem item = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        searchView.setOnQueryTextListener(this);

        MenuItemCompat.setOnActionExpandListener(item,
                new MenuItemCompat.OnActionExpandListener() {
                    @Override
                    public boolean onMenuItemActionCollapse(MenuItem item) {
                        // Do something when collapsed
                        adapter.setFilter(projectsList);
                        return true; // Return true to collapse action view
                    }

                    @Override
                    public boolean onMenuItemActionExpand(MenuItem item) {
                        // Do something when expanded
                        return true; // Return true to expand action view
                    }
                });
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        final List<Projects> filteredModelList = filter(projectsList, newText);
        adapter.setFilter(filteredModelList);
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }


    private List<Projects> filter(List<Projects> models, String query) {
        query = query.toLowerCase();

        final List<Projects> filteredModelList = new ArrayList<>();
        for (Projects model : models) {
            final String text = model.getProjectname().toLowerCase();
            if (text.contains(query)) {
                filteredModelList.add(model);
            }
        }
        return filteredModelList;
    }

    public void hideLoadingView() {
        loadingView.setVisibility(View.GONE);
    }

    public void showLoadingView() {
        loadingView.setVisibility(View.VISIBLE);
    }

    public void hideRecyclerView() {
        recyclerview.setVisibility(View.GONE);
    }

    public void showRecyclerView() {
        recyclerview.setVisibility(View.VISIBLE);
    }

    @Override
    public void onResponse(Call<List<Projects>> call, Response<List<Projects>> response) {

        projectsList = response.body();
        String[] projectName = null;
        int[] projectsStars = null;
        int[] projectsViews = null;
        if (projectsList != null) {
            projectName = new String[projectsList.size()];
            projectsStars = new int[projectsList.size()];
            projectsViews = new int[projectsList.size()];
            for (int i = 0; i < projectsList.size(); i++) {
                projectsStars[i] = projectsList.get(i).getStars();
                projectsViews[i] = projectsList.get(i).getViews();
                projectName[i] = projectsList.get(i).getProjectname();
            }
        }

        adapter.swap(projectsList);
        hideLoadingView();
        showRecyclerView();
        swipeRefreshLayout.setRefreshing(false);
        Log.d("GoogleActivity", "response project: " + response.code());
    }

    @Override
    public void onFailure(Call<List<Projects>> call, Throwable t) {
        Toast.makeText(getActivity(), "No Internet Connection", Toast.LENGTH_LONG).show();
        Log.d("GoogleActivity", "response project: failure" + t.toString());
    }

    @Override
    public void valueChanged() {
        if (getUserVisibleHint() && getActivity() != null) {
            ((MainActivity) getActivity()).getSearchView().setOnQueryChangeListener(new FloatingSearchView.OnQueryChangeListener() {
                @Override
                public void onSearchTextChanged(String oldQuery, String newQuery) {
                    Log.d("vikas", newQuery);
                    final List<Projects> filteredModelList = filter(projectsList, newQuery);
                    adapter.setFilter(filteredModelList);
                }
            });
        }
    }

    @Override
    public void changeFilter(String filter) {
        if(projectsList != null && getUserVisibleHint()){
            final List<Projects> filteredModelList = Utility.bubblesort(projectsList, filter);
            adapter.setFilter(filteredModelList);
        }
    }

}
