package com.smis.pptvr.pptvrmobileapp.TabFragments;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
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

public class RVAdapterTabOne extends RecyclerView.Adapter<RVAdapterTabOne.ItemViewHolder> {

    private final Context context;
    public List<Projects> projectsModel;
    private List<Projects> mOriginalCountryModel;
    private String[] projectNames;
    private int[] projectsStars;
    private int[] projectsViews;


    public RVAdapterTabOne(List<Projects> mCountryModel, Context context) {
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
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_row_tab_one, viewGroup, false);
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
        public TextView tvStars;

        public ItemViewHolder(View itemView) {
            super(itemView);
            itemView.setClickable(true);
            name_TextView = (TextView) itemView.findViewById(R.id.project_name);
            tvViews = (TextView) itemView.findViewById(R.id.views);
            tvProjectLetter = (TextView) itemView.findViewById(R.id.tvProjectLetter);
            projectContainer = (RelativeLayout) itemView.findViewById(R.id.projectTextContainer);
            ibShareWebLink = (ImageButton) itemView.findViewById(R.id.ibShareWebLink);
            tvStars = (TextView) itemView.findViewById(R.id.stars);
            projectContainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Utility.openCustomTab(context, Endpoints.BASE_WEBLINK + projectsModel.get(getPosition()).getId());
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
            tvProjectLetter.setText(project.getProjectname().substring(0,1));
            tvViews.setText(String.valueOf(project.getViews()));
            tvStars.setText(String.valueOf(project.getStars()));

        }

    }

}