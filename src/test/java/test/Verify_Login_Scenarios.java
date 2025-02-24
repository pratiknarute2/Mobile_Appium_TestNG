package test;

import BaseClass.BaseClass;

import org.testng.annotations.Test;
import pom.LoginPage;

public class Verify_Login_Scenarios extends BaseClass{

    @Test(priority = 1)
    public void verify_login() throws InterruptedException {
    }
    @Test(priority =2 )
    public void verify_logout() throws InterruptedException {
        new LoginPage().logout();
    }

}
