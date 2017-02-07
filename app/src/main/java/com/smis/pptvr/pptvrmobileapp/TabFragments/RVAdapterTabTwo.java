package com.smis.pptvr.pptvrmobileapp.TabFragments;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.smis.pptvr.pptvrmobileapp.R;
import com.smis.pptvr.pptvrmobileapp.network.Endpoints;
import com.smis.pptvr.pptvrmobileapp.network.Utility;
import com.smis.pptvr.pptvrmobileapp.pojo.Projects;

import java.util.ArrayList;
import java.util.List;

public class RVAdapterTabTwo extends RecyclerView.Adapter<RVAdapterTabTwo.ItemViewHolder> {

    private final Context context;
    private List<Projects> projectsModel;
    private List<Projects> mOriginalCountryModel;
    private String[] projectNames;
    private int[] projectsStars;
    private int[] projectsViews;

    public RVAdapterTabTwo(List<Projects> mCountryModel, Context context) {
        this.projectsModel = mCountryModel;
        this.mOriginalCountryModel = mCountryModel;
        this.context = context;
    }

    @Override
    public void onBindViewHolder(ItemViewHolder itemViewHolder, int i) {
        if(projectsModel.size() != 0){
            itemViewHolder.bind(projectsModel.get(i));
        }
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_row_tab_two, viewGroup, false);
        return new ItemViewHolder(view);
    }

    @Override
    public int getItemCount() {
        if (projectsModel.size() != 0) {
            return projectsModel.size();
        } else {
            return 0;
        }
    }

    public void setFilter(List<Projects> countryModels) {
        projectsModel = new ArrayList<>();
        projectsModel.addAll(countryModels);
        notifyDataSetChanged();
    }


    public void swap(List<Projects> projectsList) {
        if (projectsList != null) {
            projectsModel = projectsList;
            notifyDataSetChanged();
        }
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {

        public TextView name_TextView;
        public TextView tvViews;
        public TextView tvProjectLetter;
        public TextView tvProjectDes;
        public RelativeLayout projectContainer;
        public ImageButton ibShareWebLink;
        //public TextView tvStars;

        public ItemViewHolder(View itemView) {
            super(itemView);
            itemView.setClickable(true);
            name_TextView = (TextView) itemView.findViewById(R.id.project_name);
            tvViews = (TextView) itemView.findViewById(R.id.views);
            tvProjectLetter = (TextView) itemView.findViewById(R.id.tvProjectLetter);
            projectContainer = (RelativeLayout) itemView.findViewById(R.id.projectTextContainer);
            ibShareWebLink = (ImageButton) itemView.findViewById(R.id.ibShareWebLink);
            //tvStars = (TextView) itemView.findViewById(R.id.stars);
            projectContainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ConnectivityManager ConnectionManager=(ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
                    NetworkInfo networkInfo=ConnectionManager.getActiveNetworkInfo();
                    if(networkInfo != null && networkInfo.isConnected()) {
                        Utility.openCustomTab(context, Endpoints.BASE_WEBLINK + projectsModel.get(getPosition()).getId());
                    } else {
                        Snackbar bar = Snackbar.make(view, "No Internet Connection", Snackbar.LENGTH_LONG)
                                .setAction("Dismiss", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        // Handle user action

                                    }
                                });
                        bar.show();

                    }
                }
            });

            ibShareWebLink.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Utility.shareWebLink(context,Endpoints.BASE_WEBLINK + projectsModel.get(getPosition()).getId());
                }
            });


        }

        public void bind(Projects project) {
            name_TextView.setText(project.getProjectname());
            name_TextView.setEllipsize(TextUtils.TruncateAt.END);
            tvProjectLetter.setText(project.getProjectname().substring(0,1).toUpperCase());
            tvViews.setText(String.valueOf(project.getViews()));
            //tvStars.setText(String.valueOf(project.getStars()));

        }

    }

}
