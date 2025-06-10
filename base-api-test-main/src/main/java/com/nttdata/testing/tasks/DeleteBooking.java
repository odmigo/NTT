package com.nttdata.testing.tasks;

import io.restassured.http.ContentType;
import net.serenitybdd.rest.SerenityRest;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.rest.interactions.Delete;

import static net.serenitybdd.screenplay.Tasks.instrumented;

public class DeleteBooking implements Task {

        public static Performable now() {
            return instrumented(DeleteBooking.class);
        }

        @Override
        public <T extends Actor> void performAs(T actor) {
            // ✅ Recupera el bookingId como String y convierte a int (si es necesario)
            Object idObj = actor.recall("bookingId");
            int bookingId = Integer.parseInt(idObj.toString());

            String token = actor.recall("token");

            actor.attemptsTo(
                    Delete.from("/booking/" + bookingId)
                            .with(requestSpec -> requestSpec
                                    .contentType(ContentType.JSON)
                                    .header("Cookie", "token=" + token)
                                    .log().all()
                            )
            );

        }
    }

