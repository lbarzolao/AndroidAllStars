/* The MIT License (MIT)
* Copyright (c) 2016 BELATRIX
* Permission is hereby granted, free of charge, to any person obtaining a copy
* of this software and associated documentation files (the "Software"), to deal
* in the Software without restriction, including without limitation the rights
* to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
* copies of the Software, and to permit persons to whom the Software is
* furnished to do so, subject to the following conditions:
* The above copyright notice and this permission notice shall be included in all
* copies or substantial portions of the Software.
* THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
* IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
* FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
* AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
* LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
* OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
* SOFTWARE.
*/
package com.belatrixsf.allstars.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.belatrixsf.allstars.R;
import com.belatrixsf.allstars.entities.Employee;
import com.belatrixsf.allstars.ui.common.views.BorderedCircleTransformation;
import com.belatrixsf.allstars.ui.common.views.CircleTransformation;
import com.belatrixsf.allstars.utils.Constants;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by icerrate on 28/04/2016.
 */
public class RankingListAdapter extends RecyclerView.Adapter<RankingListAdapter.EmployeeViewHolder> {

    private List<Employee> rankingList;
    private RankingListClickListener rankingListClickListener;

    public RankingListAdapter(RankingListClickListener rankingListClickListener) {
        this(rankingListClickListener, new ArrayList<Employee>());
    }

    public RankingListAdapter(RankingListClickListener rankingListClickListener, List<Employee> rankingList) {
        this.rankingList = rankingList;
        this.rankingListClickListener = rankingListClickListener;
    }

    @Override
    public EmployeeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ranking, parent, false);
        return new EmployeeViewHolder(layoutView, rankingListClickListener);
    }

    @Override
    public void onBindViewHolder(EmployeeViewHolder holder, int position) {
        final Employee employee = rankingList.get(position);
        int place = position + Constants.ONE_UNIT;
        int cupResourceId;
        if (place<=3) {
            int crownResourceId;
            switch (place) {
                case 1:
                    crownResourceId = R.drawable.ic_first_place;
                    cupResourceId = R.drawable.ic_first_place_cup;
                    break;
                case 2:
                    crownResourceId = R.drawable.ic_second_place;
                    cupResourceId = R.drawable.ic_second_place_cup;
                    break;
                default:
                    crownResourceId = R.drawable.ic_third_place;
                    cupResourceId = R.drawable.ic_third_place_cup;
                    break;
            }
            holder.positionCrown.setBackgroundResource(crownResourceId);
            holder.positionCrown.setVisibility(View.VISIBLE);
            holder.positionNumber.setVisibility(View.GONE);
        }else{
            cupResourceId = R.drawable.ic_cup;
            holder.positionNumber.setText(String.valueOf(place));
            holder.positionCrown.setVisibility(View.GONE);
            holder.positionNumber.setVisibility(View.VISIBLE);
        }
        holder.scoreCup.setBackgroundResource(cupResourceId);
        holder.fullName.setText(employee.getFullName());
        String levelLabel = String.format(holder.level.getContext().getString(R.string.contact_list_level), String.valueOf(employee.getLevel()));
        holder.level.setText(levelLabel);
        holder.score.setText(String.valueOf(employee.getScore()));
        if (employee.getAvatar() != null) {
            Context context = holder.photo.getContext();
            Glide.with(context).load(employee.getAvatar()).fitCenter().transform(new CircleTransformation(context)).into(holder.photo);
        }
    }

    @Override
    public int getItemCount() {
        return this.rankingList.size();
    }

    public void updateData(List<Employee> ranking){
        rankingList.clear();
        rankingList.addAll(ranking);
        notifyDataSetChanged();
    }

    static class EmployeeViewHolder extends RecyclerView.ViewHolder{

        @Bind(R.id.position_number) public TextView positionNumber;
        @Bind(R.id.position_crown) public ImageView positionCrown;
        @Bind(R.id.photo) public ImageView photo;
        @Bind(R.id.full_name) public TextView fullName;
        @Bind(R.id.level) public TextView level;
        @Bind(R.id.score_cup) public ImageView scoreCup;
        @Bind(R.id.score_number) public TextView score;

        private RankingListClickListener rankingListClickListener;

        public EmployeeViewHolder(View view, RankingListClickListener rankingListClickListener) {
            super(view);
            this.rankingListClickListener = rankingListClickListener;
            ButterKnife.bind(this, view);
        }

        @OnClick(R.id.layout_container)
        public void onClick() {
            if (rankingListClickListener != null) {
                rankingListClickListener.onEmployeeClicked(getLayoutPosition());
            }
        }

    }

    public interface RankingListClickListener {

        void onEmployeeClicked(int position);

    }
}
