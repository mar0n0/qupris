package com.qupris;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;

public interface RecyclerViewInterface {
    void onItemClick(int position);
    void onAnalyseButtonClick(int position) throws JSONException, IOException;
}
