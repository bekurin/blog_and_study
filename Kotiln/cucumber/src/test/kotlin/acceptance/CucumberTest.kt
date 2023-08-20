package acceptance

import io.cucumber.java.en.Given
import io.cucumber.java.en.Then
import io.cucumber.java.en.When

class CucumberTest {

    @Given("uri hello를 가지고 있다")
    fun given() {
        println("/hello로 호출해야징")
    }

    @When("uri로 호출하면")
    fun `when`() {
        println("uri 호출!")
    }

    @Then("hello world 메시지를 응답받는다")
    fun then() {
        println("hello world!")
    }
}
