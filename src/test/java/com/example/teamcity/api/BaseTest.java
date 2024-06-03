package com.example.teamcity.api;

import com.example.teamcity.api.generators.TestDataStorage;
import com.example.teamcity.api.requests.CheckedRequests;
import com.example.teamcity.api.requests.UncheckedRequests;
import com.example.teamcity.api.spec.Specifications;
import org.assertj.core.api.SoftAssertions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import java.io.IOException;

import static io.restassured.RestAssured.given;

public class BaseTest {
    protected SoftAssertions softy;
    public TestDataStorage testDataStorage;
    public CheckedRequests checkedWithSuperUser = new CheckedRequests(Specifications.getSpec().superUserSpec());
    public UncheckedRequests uncheckedWithSuperUser = new UncheckedRequests(Specifications.getSpec().superUserSpec());

    @Test
    public static void setAuthSettings() throws IOException {
        String body = """
                {"perProjectPermissions": true,
                  "modules": {
                    "module": [
                      {
                        "name": "HTTP-Basic"
                      },
                      {
                        "name": "Default",
                        "properties": {
                          "property": [
                            {
                              "name": "usersCanResetOwnPasswords",
                              "value": true
                            },
                            {
                              "name": "usersCanChangeOwnPasswords",
                              "value": true
                            },
                            {
                              "name": "usersCanChangeOwnPasswords",
                              "value": false
                            }
                          ]
                        }
                      },
                      {
                        "name": "Token-Auth"
                      },
                      {
                        "name": "LDAP",
                        "properties": {
                          "property": [
                            {
                              "name": "allowCreatingNewUsersByLogin",
                              "value": true
                            }
                          ]
                        }
                      }
                    ]
                  }
                }""";

        given()
                .spec(Specifications.getSpec().superUserSpec())
                .body(body)
                .put("/app/rest/server/authSettings");
    }

    @BeforeMethod
    public void beforeTest() {
        softy = new SoftAssertions();
        testDataStorage = TestDataStorage.getStorage();
    }

    @AfterMethod
    public void afterTest() {
        testDataStorage.delete();
        softy.assertAll();
    }

}