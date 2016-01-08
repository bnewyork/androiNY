package com.example.dahae.myandroiice.ModifyPlan;

import com.example.dahae.myandroiice.Adapter.PlanListAdapter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ModifyPalnChecking {

    List<String> listResult = new ArrayList<>();
    int index = 0;

    public boolean TriggerChecktoDelete(PlanListAdapter TriggerPlanAdapter, int DialogNum){

        boolean result = false;
        String[] arr;

        if(TriggerPlanAdapter.getCount() != 1) {
            arr = new String[TriggerPlanAdapter.getCount() - 1];
            int j =0;

            for(int i = 0 ; i <TriggerPlanAdapter.getCount() -1  ;  i++)
                if(i != DialogNum)
                    arr[j++] = TriggerPlanAdapter.items.get(i).getKeyword();

            index = 0;
            ComplexChecktoDelete(arr);

            Iterator iterator = listResult.iterator();
            while (iterator.hasNext()) {
                String and = (String) iterator.next();
                if (and.equals("T"))
                    result = true;
                else {
                    result = false;
                    break;
                }
            }
            return result;
        }else
            return false;
    }

    public void ComplexChecktoDelete(String[] arr){

        int count=0;
        try{
            while(index< arr.length-1){
                if (arr[index].equals("AND") || arr[index].equals("OR")) {
                    count++;
                    index++;
                    ComplexChecktoDelete(arr);

                }else if(arr[index].equals("Done") ){
                    index++;
                    if(count > 1)
                        listResult.add("T");
                    else
                        listResult.add("F");
                    return;
                }else{
                    count++;
                    index++;
                }
            }
        }catch(ArrayIndexOutOfBoundsException e) {// 배열의 인덱스가 범위를 벗어나서 사용함
            listResult.add("F");
        }
    }
}
