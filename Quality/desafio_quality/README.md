# Quality challenge

The challenge is to develop a series of APIs for a scenario determined and perform appropriate tests to achieve greater than 60% code coverage.

## Activity status

US 0001. Finished.
US 0002. Finished.
US 0003. Finished.
US 0004. Finished.
US 0005. Finished.
US 0006. Finished.

## Endpoints

- GET: /api/v1/hotels
    Get a list of all available hotels.

- GET: /api/v1/hotels?dateFrom=dd/mm/aaaa&dateTo=dd/mm/aaaa&destination=Puerto Iguazu
    Get a list of all the hotels available in a given
    date range and according to the selected destination.

- POST: /api/v1/booking
    Make a reservation for a hotel, indicating the number of people, date of entry, date of departure and type of room. Obtain in response the total amount of the reservation made.
    * Request example: src/test/resources/mockBookingRequest.json
    * Response example: src/test/resources/mockBookingResponse.json
    
- GET: /api/v1/flights
    Get a list of all available flights.
    
- GET: /api/v1/flights?dateFrom=dd/mm/aaaa&dateTo=dd/mm/aaaa&origin=Buenos Aires&destination=Puerto Iguazú
    Get a list of all available flights in a certain range of dates and according to the selected destination and origin.
    
- POST: /api/v1/flight-reservation
    Make a reservation for a flight, indicating the number of people, origin, destination, departure date and return date. Obtain the total amount of the reservation made in response.
    * Request example: src/test/resources/mockReservationRequest.json
    * Response example: src/test/resources/mockReservationResponse.json
    
## Line coverage

(covered.png?raw=true "Covered")