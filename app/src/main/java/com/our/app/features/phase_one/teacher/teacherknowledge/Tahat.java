package com.our.app.features.phase_one.teacher.teacherknowledge;

import java.util.ArrayList;

public class Tahat {
    private ArrayList<Integer> ids = new ArrayList<Integer>();

    public ArrayList<Integer> getIdsArray(int id) {
        // check whether it exists:
        for (int i = 0; i != ids.size(); i++) {
            if (ids.get(i) == id) {
                ids.remove(i);
                break;
            }
        }

        // add it to the array in case that it doesn't exist:
        ids.add(id);

        return ids;
    }

    public ArrayList<Integer> getIdsArray(ArrayList<Integer> newIds) {

        boolean isFoundAlready = false;

        for (int i = 0; i != newIds.size(); i++) {
            for (int j = 0; j != ids.size(); j++)
                if (newIds.get(i) == ids.get(j)) { // once the same id found
                    ids.remove(j);
                    isFoundAlready = true;
                    break;
                }


            if (!isFoundAlready)                   // in case that it was not found, add it to the ids list
                ids.add(newIds.get(i));

            isFoundAlready = false;
        }
        return ids;
    }
}