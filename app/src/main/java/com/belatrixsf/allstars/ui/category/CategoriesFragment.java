package com.belatrixsf.allstars.ui.category;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.belatrixsf.allstars.R;
import com.belatrixsf.allstars.adapters.CategoriesAdapter;
import com.belatrixsf.allstars.entities.Category;
import com.belatrixsf.allstars.ui.common.AllStarsFragment;
import com.belatrixsf.allstars.ui.common.views.DividerItemDecoration;
import com.belatrixsf.allstars.utils.AllStarsApplication;
import com.belatrixsf.allstars.utils.di.modules.presenters.CategoriesListModule;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;

/**
 * Created by gyosida on 4/27/16.
 */
public class CategoriesFragment extends AllStarsFragment implements CategoriesView, CategoriesAdapter.CategoriesListListener {

    private static final String CATEGORY_ID_KEY = "category_id_key";
    private static final String CATEGORIES_KEY = "categories_key";

    private SubcategorySelectionListener subcategorySelectionListener;
    private CategoriesAdapter categoriesAdapter;

    @Inject CategoriesPresenter categoriesPresenter;

    @Bind(R.id.categories) RecyclerView categoriesRecyclerView;


    public CategoriesFragment() {
        // Required empty public constructor
    }

    public static CategoriesFragment newInstance(int categoryId) {
        CategoriesFragment fragment = new CategoriesFragment();
        Bundle args = new Bundle();
        args.putInt(CATEGORY_ID_KEY, categoryId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        castOrThrowException(context);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        castOrThrowException(activity);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_categories, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews();
        if (savedInstanceState != null) {
            restoreState(savedInstanceState);
        }
        categoriesPresenter.init();
        categoriesPresenter.getCategories();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        saveState(outState);
        super.onSaveInstanceState(outState);
    }

    private void castOrThrowException(Context context) {
        try {
            subcategorySelectionListener = (SubcategorySelectionListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException("Activity has to implement SubcategorySelectionListener");
        }
    }

    private void initViews() {
        categoriesAdapter = new CategoriesAdapter();
        categoriesAdapter.setCategoriesListListener(this);
        categoriesRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        categoriesRecyclerView.addItemDecoration(new DividerItemDecoration(ContextCompat.getDrawable(getActivity(), android.R.drawable.divider_horizontal_bright)));
        categoriesRecyclerView.setAdapter(categoriesAdapter);
    }

    private Integer getCategoryIdIfExists() {
        if (getArguments() != null) {
            return getArguments().getInt(CATEGORY_ID_KEY);
        }
        return null;
    }

    private void restoreState(Bundle savedInstanceState) {
        List<Category> savedCategories = savedInstanceState.getParcelableArrayList(CATEGORIES_KEY);
        categoriesPresenter.loadSavedCategories(savedCategories);
    }

    private void saveState(Bundle outState) {
        List<Category> forSavingCategories = categoriesPresenter.forSavingCategories();
        if (forSavingCategories != null && forSavingCategories instanceof ArrayList) {
            outState.putParcelableArrayList(CATEGORIES_KEY, (ArrayList<Category>) forSavingCategories);
        }
    }

    @Override
    protected void initDependencies(AllStarsApplication allStarsApplication) {
        allStarsApplication
                .getApplicationComponent()
                .categoriesListComponent(new CategoriesListModule(this, getCategoryIdIfExists()))
                .inject(this);
    }

    @Override
    public void notifyAreSubcategories(boolean areSubcategories) {
        categoriesAdapter.setAreSubcategories(areSubcategories);
    }

    @Override
    public void notifySelection(Category subcategory) {
        subcategorySelectionListener.onSubcategorySelected(subcategory);
    }

    @Override
    public void showCategories(List<Category> categories) {
        categoriesAdapter.update(categories);
    }

    @Override
    public void onCategorySelected(int position) {
        categoriesPresenter.categorySelected(position);
    }

    @Override
    public void showSubcategories(Category category) {
        fragmentListener.replaceFragment(CategoriesFragment.newInstance(category.getId()), true);
    }

    public interface SubcategorySelectionListener {
        void onSubcategorySelected(Category subcategory);
    }

}
