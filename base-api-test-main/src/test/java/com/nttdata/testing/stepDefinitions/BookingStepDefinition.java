package com.nttdata.testing.stepDefinitions;

import com.nttdata.testing.questions.ResponseCode;
import com.nttdata.testing.tasks.*;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.actors.OnStage;
import net.serenitybdd.screenplay.actors.OnlineCast;
import net.serenitybdd.screenplay.rest.abilities.CallAnApi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static net.serenitybdd.screenplay.actors.OnStage.theActorInTheSpotlight;
import static org.hamcrest.CoreMatchers.equalTo;

public class BookingStepDefinition {

    public static Logger LOGGER = LoggerFactory.getLogger(BookingStepDefinition.class);

    @Before
    public void setTheStage() {
        OnStage.setTheStage(new OnlineCast());
    }

    @Given("el {actor} establece el endpoint de booking")
    public void elActorEstableceElEndpointDeBooking(Actor actor) {
        actor.whoCan(CallAnApi.at("https://restful-booker.herokuapp.com"));
    }

    @When("el {actor} realiza una solicitud GET")
    public void elActorRealizaUnaSolicitudGET(Actor actor) {
        actor.attemptsTo(GetAllBookings.fromEndpoint("/booking"));
    }

    @Then("el codigo de respuesta debe ser {int}")
    public void elCodigoDeRespuestaDebeSer(int responseCode) {
        theActorInTheSpotlight().should(seeThat("El código de respuesta", ResponseCode.getStatus(), equalTo(responseCode)));
    }

    @When("^el actor crea un booking con el (.*) (.*) (.*) (.*) (.*) (.*) (.*)$")
    public void elActorCreaUnBookingConEl(String firstname, String lastname, String totalprice, String depositpaid, String checkin, String checkout, String additionalneeds) {
        theActorInTheSpotlight().attemptsTo(PostBooking.fromPage(firstname, lastname, totalprice, depositpaid, checkin, checkout, additionalneeds));
    }

    @When("^el actor actualiza un booking con los datos (.*) (.*) (.*)$")
    public void elActorActualizaUnBookingConLosDatos(String firstname, String lastname, String totalprice) {
        theActorInTheSpotlight().attemptsTo(PostToken.generateToken());
        theActorInTheSpotlight().attemptsTo(PatchBooking.fromPage(firstname, lastname, totalprice));
    }

    //cambios

    @When("^el actor actualiza completamente un booking con los datos (.*) (.*) (.*) (.*) (.*) (.*) (.*)$")
    public void elActorActualizaCompletamenteUnBooking(String firstname, String lastname, String totalprice, String depositpaid, String checkin, String checkout, String additionalneeds) {
        theActorInTheSpotlight().attemptsTo(PostToken.generateToken());
        theActorInTheSpotlight().attemptsTo(PutBooking.withData(firstname, lastname, totalprice, depositpaid, checkin, checkout, additionalneeds));
    }


    @And("el actor obtiene el token de autenticación")
    public void elActorObtieneElToken() {
        theActorInTheSpotlight().attemptsTo(PostToken.generateToken());
    }

    @When("el actor elimina el booking con el ID creado")
    public void elActorEliminaElBookingConElIdCreado() {
        theActorInTheSpotlight().attemptsTo(DeleteBooking.now());
    }

    @When("el actor elimina un booking")
    public void elActorEliminaUnBooking() {
        theActorInTheSpotlight().attemptsTo(PostToken.generateToken());
        theActorInTheSpotlight().attemptsTo(DeleteBooking.now());
    }

}
