package com.nttdata.testing.tasks;

import io.restassured.http.ContentType;
import net.serenitybdd.rest.SerenityRest;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actors.OnStage;
import net.serenitybdd.screenplay.rest.interactions.Put;
import net.thucydides.core.annotations.Step;

import static net.serenitybdd.screenplay.Tasks.instrumented;

public class PutBooking implements Task {
    private final String firstname;
    private final String lastname;
    private final String totalprice;
    private final String depositpaid;
    private final String checkin;
    private final String checkout;
    private final String additionalneeds;
    private final String token;
    private final String bookingId;

    public PutBooking(String firstname, String lastname, String totalprice, String depositpaid,
                      String checkin, String checkout, String additionalneeds) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.totalprice = totalprice;
        this.depositpaid = depositpaid;
        this.checkin = checkin;
        this.checkout = checkout;
        this.additionalneeds = additionalneeds;
        this.token = OnStage.theActorInTheSpotlight().recall("token");
        this.bookingId = OnStage.theActorInTheSpotlight().recall("bookingId");
    }

    public static Performable withData(String firstname, String lastname, String totalprice,
                                       String depositpaid, String checkin, String checkout, String additionalneeds) {
        return instrumented(PutBooking.class, firstname, lastname, totalprice, depositpaid, checkin, checkout, additionalneeds);
    }

    @Step("{0} actualiza completamente la reserva con los nuevos datos")
    @Override
    public <T extends Actor> void performAs(T actor) {


        actor.attemptsTo(
                Put.to("/booking/" + bookingId)
                        .with(requestSpecification -> requestSpecification
                                .contentType(ContentType.JSON)
                                .header("Accept", "application/json")
                                .header("Cookie", "token=" + token)
                                .body("{\"firstname\":" + firstname + ","
                                        + "\"lastname\":" + lastname + ","
                                        + "\"totalprice\":" + totalprice + ","
                                        + "\"depositpaid\":" + depositpaid + ","
                                        + "\"bookingdates\":" + "{"
                                        + "\"checkin\":" + checkin + ","
                                        + "\"checkout\":" + checkout + "},"
                                        + "\"additionalneeds\":" + additionalneeds + "}")
                                .log().all()
                        )
        );

        SerenityRest.lastResponse().prettyPrint();

        int statusCode = SerenityRest.lastResponse().statusCode();
        if (statusCode != 200) {
            throw new AssertionError("❌ La actualización falló. Código de estado: " + statusCode);
        } else {
            System.out.println("✅ PUT exitoso: Reserva actualizada correctamente.");
        }
    }
}