package com.example.dahae.myandroiice.NewPlan;

import android.database.Cursor;
import android.util.Log;

import com.example.dahae.myandroiice.MainActivity;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

public class NewPlanCheck {

    public boolean NoSameName(String planName){

        if(!planName.equals("")) {
            Cursor cursor = MainActivity.database.rawQuery("SELECT name FROM sqlite_master WHERE type='table'", null);
            try {
                if (cursor != null) {
                    if (cursor.moveToFirst()) {
                        while (!cursor.isAfterLast()) {
                            String tableName = cursor.getString(0);
                            // Log.d(TAG, "compare "+ tableName+ " and " + planName);
                            if (tableName.equals(planName)) {
                                return false;
                            }
                            cursor.moveToNext();
                        }
                    }
                }
            } finally {
                if (cursor != null)
                    cursor.close();
            }
            return true;
        }else{
            return false;
        }
    }

    public Boolean AsSyntax(List listTrigger, String ActionName, String ActionInfo){

        Boolean resultTrigger = false;
        Boolean resultAction;

        String[] arr = (String[]) listTrigger.toArray(new String[listTrigger.size()]);
        //   Log.d(TAG, "listTrigger.size() : " + listTrigger.size());
        for(int i =0; i < arr.length; i++){
            Log.d(MainActivity.TAG, i + " ; " + arr[i]);
        }

        if (arr.length == 1) {
            if (arr[0].equals("And") || arr[0].equals("Or") || arr[0].equals("Done")) {
                Log.e(MainActivity.TAG, "error1");
                //  Toast.makeText(getApplicationContext(), "잘못된 입력이 들어왔습니다", Toast.LENGTH_LONG).show();
            } else {
                StringTokenizer st = new StringTokenizer(arr[0], "/");
                if (st.countTokens() == 1)
                    resultTrigger = true;
                else{
                    Log.e(MainActivity.TAG, "error1.1");
                    //   Toast.makeText(getApplicationContext(), "잘못된 입력이 들어왔습니다", Toast.LENGTH_LONG).show();
                }
            }
        } else if (arr.length == 3) {
            if (arr[0].equals("And") || arr[0].equals("Or")) {

                StringTokenizer st = new StringTokenizer(arr[1], "/");
                if (st.countTokens() < 2) { //num: 0, 1일때
                    Log.e(MainActivity.TAG, "error2.1");
                    ///  Toast.makeText(getApplicationContext(), "잘못된 입력이 들어왔습니다", Toast.LENGTH_LONG).show();
                    resultTrigger = false;
                } else {
                    resultTrigger = true;
                }
                if (!arr[2].equals("Done")) {
                    Log.e(MainActivity.TAG, "error2.2");
                    // Toast.makeText(getApplicationContext(), "잘못된 입력이 들어왔습니다", Toast.LENGTH_LONG).show();
                    resultTrigger = false;
                }
            }else{
                Log.e(MainActivity.TAG, "error2");
                // Toast.makeText(getApplicationContext(), "잘못된 입력이 들어왔습니다", Toast.LENGTH_LONG).show();
            }
        } else if (listTrigger.size() > 3) {
            if (arr[0].equals("And") || arr[0].equals("Or")) {
                resultTrigger = CheckNewPlanForComplex(listTrigger);
            }else{
                Log.e(MainActivity.TAG,"error3");
            }
            Log.d(MainActivity.TAG,"Complex resultTrigger is " + resultTrigger);
        } else if (listTrigger.size() == 0) {
            Log.e(MainActivity.TAG, "error0");
            //Toast.makeText(getApplicationContext(), "명령에 아무값도 없습니다.", Toast.LENGTH_LONG).show();
            resultTrigger = false;
        }
        resultAction = CheckNewPlanForAction(ActionName, ActionInfo);


        Log.d(MainActivity.TAG, resultTrigger + " / " + resultAction);

        if(resultAction && resultTrigger )
            return true;
        else
            return false;
    }


    public boolean CheckNewPlanForComplex(List listTrigger) {
        boolean result = false;

        // int count = 0;
        String[] arr = (String[]) listTrigger.toArray(new String[listTrigger.size()]);

        Log.d(MainActivity.TAG, "========================");
        for( int i = 0; i< arr.length; i++) {
            Log.d(MainActivity.TAG, i + "; " + arr[i]);
        }
        Log.d(MainActivity.TAG, "========================");

        if (arr[0].equals("And") || arr[0].equals("Or")) {
            Log.d(MainActivity.TAG, "+++In tree");
            index = 1;
            result = Tree(arr);
            Log.d(MainActivity.TAG, "+++Out tree");
        }else{
            Log.i(MainActivity.TAG, "ERROE");
        }

        Log.i(MainActivity.TAG, "+++result " + result);
        return result;
    }

    public boolean result(List listResult){
        Iterator iterator = listResult.iterator();
        boolean result = false;

        while (iterator.hasNext()) {
            String and = (String) iterator.next();
            Log.i(MainActivity.TAG, "and  "+ and);
            if (and.equals("T")) {
                result = true;
            } else {
                result = false;
                break;
            }
        }
        return result;
    }

    int index;
    public boolean Tree(String[] arr){
        int Count = 0 ;
        boolean result;
        List<String> listResult = new ArrayList<>();
        try{
            for( ; index< arr.length; index++){

                Log.d(MainActivity.TAG,  "case "+ index + " ; " + arr[index]);
                if (arr[index].equals("And") || arr[index].equals("Or")) {
                    //(1)Tree 일경우
                    Count++;
                    Log.d(MainActivity.TAG, "*Count++");
                    Log.d(MainActivity.TAG, "In Tree");
                    index++;
                    result = Tree(arr);

                    if(result)
                        listResult.add("T");
                    else
                        listResult.add("F");

                    Log.d(MainActivity.TAG, "Out Tree " +result);
                }else if (arr[index].equals("Done")) {
                    //(2)Done일경우
                    if(index == arr.length-1){
                        //Tree문이 완전이 끝나는 경우
                        Log.d(MainActivity.TAG, "4End ; "  +index +" =" +arr.length);
                        if (Count > 1) {
                            listResult.add("T");
                        } else {
                            listResult.add("F");
                        }
                    }
                    //subTree의 Done일경우
                    Log.d(MainActivity.TAG, "4.1 ; "  +index +" =" +arr.length);
                    return result(listResult);
                } else {
                    //(3)나머지 trigger일경우

                    // or와 done 사이라면 적어도 trigger 2개 이상이여야 한다. 그렇지 않으면 1개여도 상관 없다.
                    //SubTree안의 trigger확인시
                    if (arr[index - 1].equals("And") || arr[index - 1].equals("Or")) {
                        if ((index + 1) == arr.length ||(index + 1) < arr.length){
                            if (arr[index + 1].equals("Done")) {
                                StringTokenizer st = new StringTokenizer(arr[index], "/");//tree(and,or)와 done 사이에 있는 trigger 확인
                                Log.d(MainActivity.TAG, "3.1 ;" + st.countTokens());

                                if (st.countTokens() > 1) {
                                    listResult.add("T");
                                } else {
                                    listResult.add("F");
                                }

                            } else {
                                Log.d(MainActivity.TAG, "3.2");
                                //그냥 trigger일 경우
                                Count++;
                                Log.d(MainActivity.TAG, "*Count++");
                            }
                        }
                    }else {
                        //Done다음의 trigger일 경우
                        Log.d(MainActivity.TAG, "3.22");
                        Count++;
                        Log.d(MainActivity.TAG, "*Count++");
                    }
                }

            }
        }catch(ArrayIndexOutOfBoundsException e) {// 배열의 인덱스가 범위를 벗어나서 사용함
            Log.e(MainActivity.TAG,"배열의 인덱스가 범위를 벗어났습니다.");return false;
        }
        return false;
    }

    public boolean CheckNewPlanForAction(String mActionNameString, String mActionInfoString){
        boolean result = false;

        if(!mActionNameString.isEmpty()) {
            StringTokenizer st = new StringTokenizer(mActionNameString, "/");
            StringTokenizer stInfo = new StringTokenizer(mActionInfoString, "/");

            if (st.countTokens() > 0) {

                while (st.hasMoreTokens()) {
                    String ActionName = st.nextToken();

                    if (ActionName == null) {
                        //  Toast.makeText(getApplicationContext(), "End?", Toast.LENGTH_LONG).show();
                        result = false;
                    } else {
                        result = true;
                        //actionInfo 확인하기
                        if (stInfo.hasMoreTokens()) {
                            String ActionInfo = stInfo.nextToken();

                            if (ActionName.equals("Call")) {
                                if ("null".equals(ActionInfo))
                                    result = false;
                                Log.d(MainActivity.TAG, "Call ActionInfo " + ActionInfo + result);
                            } else if (ActionName.equals("SMS")) {
                                if ("null".equals(ActionInfo))
                                    result = false;
                                Log.d(MainActivity.TAG, "SMS ActionInfo  " + ActionInfo + result);
                            } else if (ActionName.equals("TextToVoice")) {
                                if ("null".equals(ActionInfo))
                                    result = false;
                                Log.d(MainActivity.TAG, "TextToVoice " + ActionInfo + result);
                            } else if (ActionName.equals("Volume")) {
                                if ("null".equals(ActionInfo))
                                    result = false;
                                Log.d(MainActivity.TAG, "Volume " + ActionInfo + result);
                            } else if (ActionName.equals("Bookmark")) {
                                if ("null".equals(ActionInfo))
                                    result = false;
                                Log.d(MainActivity.TAG, "Bookmark In check" + ActionInfo + result);
                            } else if (ActionName.equals("Notification")) {
                                if ("null".equals(ActionInfo))
                                    result = false;
                                Log.d(MainActivity.TAG, "Notification In check" + ActionInfo + result);
                            }
                        }
                    }
                }
            } else {
                //   Toast.makeText(getApplicationContext(), "행동 값이 없습니다.", Toast.LENGTH_LONG).show();
            }
        } else {
            // Toast.makeText(getApplicationContext(), "행동 값이 없습니다.", Toast.LENGTH_LONG).show();
        }
        return result;
    }
}
