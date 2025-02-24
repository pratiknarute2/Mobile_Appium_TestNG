package test;

import BaseClass.BaseClass;
import org.testng.annotations.Test;
import pom.CollectionDashboard;

public class Verify_Collaction_Dashboard_Scenarios extends BaseClass {

    @Test(priority = 1)
    public void verify_collection_dashboard_scenarioss() throws InterruptedException {
        new CollectionDashboard().handle_customer_name_dropdown_list();
    }

}
