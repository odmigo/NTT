Feature: Delete a Booking

  Background:
    * url 'https://restful-booker.herokuapp.com'
    * configure headers = { Accept: 'application/json' }

  Scenario: Delete booking by valid ID successfully
    # Crear un booking
    Given path 'booking'
    And request
      """
      {
        "firstname": "Carlos",
        "lastname": "DeleteMe",
        "totalprice": 300,
        "depositpaid": true,
        "bookingdates": {
          "checkin": "2025-08-01",
          "checkout": "2025-08-05"
        },
        "additionalneeds": "Dinner"
      }
      """
    When method POST
    Then status 200
    * def bookingId = response.bookingid

    # Obtener token
    Given path 'auth'
    And request
      """
      {
        "username": "admin",
        "password": "password123"
      }
      """
    When method POST
    Then status 200
    * def token = response.token

    # DELETE la reserva
    Given path 'booking', bookingId
    And header Cookie = 'token=' + token
    When method DELETE
    Then status 201

    # Verificar que ya no existe
    Given path 'booking', bookingId
    When method GET
    Then status 404
