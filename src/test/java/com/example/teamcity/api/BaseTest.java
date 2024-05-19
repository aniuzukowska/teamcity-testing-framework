package com.example.teamcity.api;

import com.example.teamcity.api.spec.Specifications;
import org.assertj.core.api.SoftAssertions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import static io.restassured.RestAssured.given;

public class BaseTest {
    protected SoftAssertions softy;

    @BeforeMethod
    public void beforeTest() {
        softy = new SoftAssertions();
    }

    @AfterMethod
    public void afterTest() {
        softy.assertAll();
    }

   @BeforeSuite
    public static void setAuthSettings() {
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
}
""";
       given()
                .spec(Specifications.getSpec().superUserSpec())
                .body(body)
                .put("/app/rest/server/authSettings");
    }
}