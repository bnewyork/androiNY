package com.example.dahae.myandroiice.ExistingPlans;

public class ChangeName {

    public String Trigger(String English) {
        String result = English;

        if (English.equals("And"))
            result = "AND";
        else if (English.equals("Or"))
            result = "OR";
        else if (English.equals("WifiOn"))
            result = "Wi-Fi 켜짐";
        else if (English.equals("WifiOff"))
            result = "Wi-Fi 꺼짐";
        else if (English.equals("ScreenOn"))
            result = "화면 켜짐";
        else if (English.equals("ScreenOff"))
            result = "화면 꺼짐";
        else if (English.equals("Sound"))
            result = "소리모드";
        else if (English.equals("Vibration"))
            result = "진동모드";
        else if (English.equals("Silence"))
            result = "무음모드";
        else if (English.equals("DataOn"))
            result = "데이터네트워크 켜짐";
        else if (English.equals("DataOff"))
            result = "데이터네트워크 꺼짐";
        else if (English.equals("BluetoothOn"))
            result = "블루투스 켜짐";
        else if (English.equals("BluetoothOff"))
            result = "블루투스 꺼짐";
        else if (English.equals("SMSreceiver"))
            result = "SMS 수신시";
        else if (English.equals("AirplaneModeOff"))
            result = "비행기모드 꺼짐";
        else if (English.equals("AirplaneModeOn"))
            result = "비행기모드 켜짐";
        else if (English.equals("CallEnded"))
            result = "통화 종료시";
        else if (English.equals("PhoneReception"))
            result = "전화 수신시";
        else if (English.equals("PowerConnected"))
            result = "충전기 연결시";
        else if (English.equals("PowerDisConnected"))
            result = "충전기 해제시";
        else if (English.equals("EarphoneIn"))
            result = "이어폰 연결시";
        else if (English.equals("EarphoneOut"))
            result = "이어폰 해제시";
        else if (English.equals("LowBattery"))
            result = "배터리 N이하";
        else if (English.equals("FullBattery"))
            result = "배터리 N이상";
        else if (English.equals("SensorLR"))
            result = "양쪽 흔들기";
        else if (English.equals("UpsideDown"))
            result = "폰뒤집기";
        else if (English.equals("SensorBright"))
            result = "밝기 센서";
        else if (English.equals("SensorUPDOWN"))
            result = "위아래 흔들기";
        else if (English.equals("SensorClose"))
            result = "근접 센서";
        else if (English.equals("Location"))
            result = "장소";
        else if (English.equals("Time"))
            result = "요일/시간";

        return result;
    }
    public String TriggerToEglish(String English){


        String result = English;
        if (English.equals("AND"))
            result = "And";
        else if (English.equals("OR"))
            result = "Or";
        else if (English.equals("Wi-Fi 켜짐"))
            result = "WifiOn";
        else if (English.equals("Wi-Fi 꺼짐"))
            result = "WifiOff";
        else if (English.equals("화면 켜짐"))
            result = "ScreenOn";
        else if (English.equals("화면 꺼짐"))
            result = "ScreenOff";
        else if (English.equals("소리모드"))
            result = "Sound";
        else if (English.equals("진동모드"))
            result = "Vibration";
        else if (English.equals("무음모드"))
            result = "Silence";
        else if (English.equals("데이터네트워크 켜짐"))
            result = "DataOn";
        else if (English.equals("데이터네트워크 꺼짐"))
            result = "DataOff";
        else if (English.equals("비행기모드 꺼짐"))
            result = "AirplaneModeOff";
        else if (English.equals("비행기모드 켜짐"))
            result = "AirplaneModeOn";
        else if (English.equals("블루투스 켜짐"))
            result = "BluetoothOn";
        else if (English.equals("블루투스 꺼짐"))
            result = "BluetoothOff";
        else if (English.equals("SMS 수신시"))
            result = "SMSreceiver";
        else if (English.equals("통화 종료시"))
            result = "CallEnded";
        else if (English.equals("전화 수신시"))
            result = "PhoneReception";
        else if (English.equals("충전기 연결시"))
            result = "PowerConnected";
        else if (English.equals("충전기 해제시"))
            result = "PowerDisConnected";
        else if (English.equals("이어폰 연결시"))
            result = "EarphoneIn";
        else if (English.equals("이어폰 해제시"))
            result = "EarphoneOut";
        else if (English.equals("배터리 N이하"))
            result = "LowBattery";
        else if (English.equals("배터리 N이상"))
            result = "FullBattery";
        else if (English.equals("양쪽 흔들기"))
            result = "SensorLR";
        else if (English.equals("폰뒤집기"))
            result = "UpsideDown";
        else if (English.equals("밝기 센서"))
            result = "SensorBright";
        else if (English.equals("위아래 흔들기"))
            result = "SensorUPDOWN";
        else if (English.equals("근접 센서"))
            result = "SensorClose";
        else if (English.equals("장소"))
            result = "Location";
        else if (English.equals("요일/시간"))
            result = "Time";

        return result;
    }

    public String Action(String English) {

        String result = English;

        if(English.equals("WifiOn"))
            result = "Wi-Fi 켜기";
        else if(English.equals("WifiOff"))
            result = "Wi-Fi 끄기";
        else if(English.equals("Sound"))
            result = "소리모드로 전환";
        else if(English.equals("Vibration"))
            result ="진동모드로 전환";
        else if(English.equals("Silence"))
            result = "무음모드로 전환";
        else if(English.equals("DataOn"))
            result = "데이터네트워크 켜기";
        else if(English.equals("DataOff"))
            result = "데이터네트워크 끄기";
        else if (English.equals("BluetoothOn"))
            result = "블루투스 켜기";
        else if (English.equals("BluetoothOff"))
            result = "블루투스 끄기";
        else if (English.equals( "TellPhoneNum"))
            result = "번호읽어주기";
        else if (English.equals("TellSMS"))
            result = "문자메세지 읽어주기";
        else if (English.equals("Camera"))
            result = "카메라";
        else if (English.equals("Flash"))
            result = "후레쉬" ;
        else if (English.equals("Bookmark"))
            result = "즐겨찾기";
        else if (English.equals("AudioRecorder"))
            result = "녹음";
        else if (English.equals("Call"))
            result ="전화걸기";
        else if (English.equals("SendingSMS"))
            result = "메세지 보내기";
        else if (English.equals("Notification"))
            result = "알림메세지";
        else if (English.equals("TextToVoice"))
            result = "음성으로바꾸기";
        else if (English.equals("VolumeRing"))
            result = "벨볼륨바꾸기" ;
        else if (English.equals("VolumeMusic"))
            result ="음악볼륨바꾸기";
        else if (English.equals("HomeScreen"))
            result ="홈화면가기";
        else if (English.equals("keyLock"))
            result = "잠금해제";
        else if (English.equals("Plantrue"))
            result ="명령 활성화";
        else if (English.equals("Planflase"))
            result = "명령 비활성화";
        else if (English.equals("AirplaneModeOff"))
            result = "비행기모드 꺼짐";
        else if (English.equals("AirplaneModeOn"))
            result = "비행기모드 켜짐";

        return result;
    }
    public String ActionToEglish(String English){


        String result = English;

        if(English.equals("Wi-Fi 켜기"))
            result = "WifiOn";
        else if(English.equals("Wi-Fi 끄기"))
            result = "WifiOff";
        else if(English.equals("소리모드로 전환"))
            result = "Sound";
        else if(English.equals("진동모드로 전환"))
            result ="Vibration";
        else if(English.equals("무음모드로 전환"))
            result = "Silence";
        else if(English.equals("데이터네트워크 켜기"))
            result = "DataOn";
        else if(English.equals("데이터네트워크 끄기"))
            result = "DataOff";
        else if (English.equals("블루투스 켜기"))
            result = "BluetoothOn";
        else if (English.equals("블루투스 끄기"))
            result = "BluetoothOff";
        else if (English.equals( "비행기모드 켜기"))
            result = "AirplaneModeOn";
        else if (English.equals("비행기모드 끄기"))
            result = "AirplaneModeOff";
        else if (English.equals( "번호읽어주기"))
            result = "TellPhoneNum";
        else if (English.equals("문자메세지 읽어주기"))
            result = "TellSMS";
        else if (English.equals("카메라"))
            result = "Camera";
        else if (English.equals("후레쉬"))
            result = "Flash" ;
        else if (English.equals("즐겨찾기"))
            result = "Bookmark";
        else if (English.equals("녹음"))
            result = "AudioRecorder";
        else if (English.equals("전화걸기"))
            result ="Call";
        else if (English.equals("메세지 보내기"))
            result = "SendingSMS";
        else if (English.equals("알림메세지"))
            result = "Notification";
        else if (English.equals("음성바꾸기"))
            result = "TextToVoice";
        else if (English.equals("벨볼륨바꾸기"))
            result = "VolumeRing" ;
        else if (English.equals("음악볼륨바꾸기"))
            result ="VolumeMusic";
        else if (English.equals("바탕화면가기"))
            result ="HomeScreen";
        else if (English.equals("잠금해제"))
            result = "keyLock";
        else if (English.equals("명령 활성화"))
            result ="Plantrue";
        else if (English.equals("명령 비활성화"))
            result = "Planflase";

        return result;
    }

}