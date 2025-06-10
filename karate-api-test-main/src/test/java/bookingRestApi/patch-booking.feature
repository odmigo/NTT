Feature: Partially update a Booking

  Background:
    * url 'https://restful-booker.herokuapp.com'
    * configure headers = { Accept: 'application/json' }

  Scenario: Partially update booking by valid ID successfully
    # Crear un booking
    Given path 'booking'
    And request
      """
      {
        "firstname": "Carlos",
        "lastname": "Perez",
        "totalprice": 200,
        "depositpaid": true,
        "bookingdates": {
          "checkin": "2025-07-01",
          "checkout": "2025-07-05"
        },
        "additionalneeds": "Lunch"
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

    # PATCH parcial: actualizar firstname y totalprice
    Given path 'booking', bookingId
    And header Cookie = 'token=' + token
    And request
      """
      {
        "firstname": "CarlosUpdated",
        "totalprice": 250
      }
      """
    When method PATCH
    Then status 200
    And match response.firstname == "CarlosUpdated"
    And match response.totalprice == 250
