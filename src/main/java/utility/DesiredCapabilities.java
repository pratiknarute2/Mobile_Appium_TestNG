package utility;

import java.util.HashMap;

public class DesiredCapabilities {

    String deviceName;
    public DesiredCapabilities(String deviceName){
        this.deviceName = deviceName;
    }
    public HashMap getDesiredCapabilitiesForAndroidEmulator(){
        HashMap  dc = new HashMap();
        if(deviceName.contains("5554")){
            dc.put("deviceName","emulator-5554");
            dc.put("platformVersion","15");

        }else if(deviceName.contains("5556")){
            dc.put("deviceName","emulator-5556"); // Corrected
            dc.put("platformVersion","14");
        }
        dc.put("platformName","Android");
        dc.put("appPackage","com.kolonizer.sales");
        dc.put("appActivity","com.kolonizer.sales.MainActivity");
        dc.put("noReset",false);
        dc.put("fullReset",false);
        dc.put("setAutoGrantPermissions",false);
        dc.put("setNewCommandTimeout",120);
        return dc;
    }


}
