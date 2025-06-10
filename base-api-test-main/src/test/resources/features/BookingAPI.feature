@BookingAll
Feature: APIS from Booking

  Como usuario de Booking
  Quiero obtener la lista de bookings
  Para poder verificar los detalles de los registros

  @CP01_Booking @Booking
  Scenario: Obtener todas los booking exitosamente
    Given el actor establece el endpoint de booking
    When el actor realiza una solicitud GET
    Then el codigo de respuesta debe ser 200


  @CP02_Booking @Patch_Booking @Booking
  Scenario Outline: Crear un booking exitosamente
    Given el actor establece el endpoint de booking
    When el actor crea un booking con el "<firstname>" "<lastname>" "<totalprice>" "<depositpaid>" "<checkin>" "<checkout>" "<additionalneeds>"
    Then el codigo de respuesta debe ser 200

    Examples:
      | firstname | lastname | totalprice | depositpaid | checkin    | checkout   | additionalneeds |
      | Mario     | Castilla | 111        | true        | 2018-01-01 | 2019-01-01 | Breakfast       |
      #| Roberto   | Alfredo  | 222        | true        | 2018-01-01 | 2019-01-01 | Breakfast       |


  @CP03_Booking @Patch_Booking @Booking

  Scenario Outline: Actualizar datos de un booking exitosamente
    Given el actor establece el endpoint de booking
    And el actor crea un booking con el "Ada" "Wong" "111" "true" "checkin" "checkout" "Run"
    When el actor actualiza un booking con los datos "<firstname>" "<lastname>" "<totalprice>"
    Then el codigo de respuesta debe ser 200

    Examples:
      | firstname | lastname  | totalprice |
      | Carlos    | Alcantara | 999        |


  @CP04_Booking @Put_Booking @Booking
  Scenario Outline: Actualizar completamente un booking
    Given el actor establece el endpoint de booking
    And el actor crea un booking con el "Ada" "Wong" "111" "true" "2018-01-01" "2019-01-01" "Run"
    When el actor actualiza completamente un booking con los datos "<firstname>" "<lastname>" "<totalprice>" <depositpaid> "<checkin>" "<checkout>" "<additionalneeds>"
    Then el codigo de respuesta debe ser 200

    Examples:
      | firstname  | lastname  | totalprice  | depositpaid  | checkin     | checkout    | additionalneeds   |
      | Carlos     | Alcantara | 999         | false        | 2020-01-01  | 2021-01-01  | Dinner            |



  @CP05_Booking @Delete_Booking @Booking
  Scenario: Eliminar un booking exitosamente
    Given el actor establece el endpoint de booking
    And el actor obtiene el token de autenticación
    And el actor crea un booking con el "Delete" "User" "123" "true" "2018-01-01" "2019-01-01" "Dinner"
    When el actor elimina el booking con el ID creado
    Then el codigo de respuesta debe ser 201