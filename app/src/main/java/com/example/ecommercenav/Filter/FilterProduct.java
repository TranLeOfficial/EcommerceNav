package com.example.ecommercenav.Filter;

import android.widget.Filter;

import com.example.ecommercenav.Model.ProductModel;
import com.example.ecommercenav.fragment.home.AdapterProducts;
import com.example.ecommercenav.fragment.search.AdapterSearch;

import java.util.ArrayList;

public class FilterProduct extends Filter {

    //private RecyAdapterProduct adapterProduct;
    private AdapterProducts adapterProduct;
    private ArrayList<ProductModel> productModelListFilter;


    public FilterProduct(AdapterProducts adapterProduct, ArrayList<ProductModel> productModelListFilter) {
        this.adapterProduct = adapterProduct;
        this.productModelListFilter = productModelListFilter;
    }

    public FilterProduct(AdapterSearch adapterSearch, ArrayList<ProductModel> filterList) {

    }


    //Tìm kiếm sản phẩm
    @Override
    protected FilterResults performFiltering(CharSequence constraint) {
        FilterResults results = new FilterResults();
        //validate
        if(constraint != null && constraint.length() > 0)
        {
            //search file not Empty, searching something, perform search

            //đổi thành chữ hoa, để không phân biệt hoa - thường
            constraint  = constraint.toString().toUpperCase();
            //store our filtered List
            ArrayList<ProductModel> filteredModels = new ArrayList<>();
            for(int i =0; i < productModelListFilter.size(); i++)
            {
                //check
                if(productModelListFilter.get(i).getProductTitle().toUpperCase().contains(constraint)
                        || productModelListFilter.get(i).getProductCategory().toUpperCase().contains(constraint))
                {
                    //add filtered data to list
                    filteredModels.add(productModelListFilter.get(i));
                }
            }
            results.count = filteredModels.size();
            results.values  = filteredModels;
        }
        else {
            //search file Empty, not searching, return complete List
            results.count = productModelListFilter.size();
            results.values = productModelListFilter;
        }
        return results;
    }

    @Override
    protected void publishResults(CharSequence constraint, FilterResults results) {
        adapterProduct.productList = (ArrayList<ProductModel>) results.values;
        //refresh Adapter
        adapterProduct.notifyDataSetChanged();
    }
}
