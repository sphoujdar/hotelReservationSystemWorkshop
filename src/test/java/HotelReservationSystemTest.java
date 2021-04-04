//https://www.youtube.com/watch?v=uFGrL5vyp54 - Java Enums Video
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

public class HotelReservationSystemTest {
    HotelReservationSystem runnerObject;
    Hotel lakewood;
    Hotel bridgewood;
    Hotel ridgewood;
    Hotel creekwood;
    Hotel valleywood;

    @Before
    public void setUp(){
        runnerObject = new HotelReservationSystem();
        HashMap<CustomerType, RateKey> hotelRateVariable = new HashMap<>();
        hotelRateVariable.put(CustomerType.REGULAR , new RateKey(90, 110));
        hotelRateVariable.put(CustomerType.REWARD , new RateKey(80, 80));
        lakewood = new Hotel("Lakewood", 3, hotelRateVariable);

        hotelRateVariable = new HashMap<>();
        hotelRateVariable.put(CustomerType.REGULAR , new RateKey(60, 160));
        hotelRateVariable.put(CustomerType.REWARD , new RateKey(50, 110));
        bridgewood = new Hotel("Bridgewood", 4, hotelRateVariable);

        hotelRateVariable = new HashMap<>();
        hotelRateVariable.put(CustomerType.REGULAR , new RateKey(150, 220));
        hotelRateVariable.put(CustomerType.REWARD , new RateKey(40, 100));
        ridgewood = new Hotel("Ridgewood", 5, hotelRateVariable);

        runnerObject.addHotel(lakewood);
        runnerObject.addHotel(bridgewood);
        runnerObject.addHotel(ridgewood);
    }

    @Test
    public void givenInputOfHotelDetails_AddHotelToHotelReservationSystem_ReturnTrueIfInfoCorrectlyAdded(){
        boolean testVariable = runnerObject.getHotelByName("Lakewood").hotelName.equals("Lakewood")
                               && runnerObject.getHotelByName("Lakewood").hotelRating == 3
                               && runnerObject.getHotelByName("Lakewood").rate.get(CustomerType.REGULAR).getWeekdayRates() == 110
                               && runnerObject.getHotelByName("Lakewood").rate.get(CustomerType.REWARD).getWeekdayRates() == 80
                               && runnerObject.getHotelByName("Lakewood").rate.get(CustomerType.REGULAR).getWeekendRates() == 90
                               && runnerObject.getHotelByName("Lakewood").rate.get(CustomerType.REWARD).getWeekendRates() == 80;
        Assert.assertTrue(testVariable);
    }

    @Test
    public void givenInputOfHotelDetailsAndDateRange_GetCheapestRate_ReturnTrueIfCheapestRateIsReturned(){
        String startDate = "10Sep2020";
        String endDate = "11Sep2020";

        Result cheapestRateRegular = runnerObject.getCheapestRateForADateRange(CustomerType.REGULAR,
                     LocalDate.parse( startDate, DateTimeFormatter.ofPattern("ddMMMyyyy")),
                     LocalDate.parse( endDate, DateTimeFormatter.ofPattern("ddMMMyyyy")));

        Assert.assertEquals(220 , (int) cheapestRateRegular.getTotalCalculatedRate());
        Assert.assertEquals("Lakewood" , cheapestRateRegular.getHotelName());
    }

    @Test
    public void givenHotelDetailsAndDateRange_GetCheapestRate_ReturnTrueIfCheapestRateIsReturned(){
        String startDate = "11Sep2020";
        String endDate = "12Sep2020";

        Result cheapestRateRegular = runnerObject.getCheapestRateForADateRange(CustomerType.REGULAR,
                LocalDate.parse( startDate, DateTimeFormatter.ofPattern("ddMMMyyyy")),
                LocalDate.parse( endDate, DateTimeFormatter.ofPattern("ddMMMyyyy")));

        Assert.assertEquals(200 , (int) cheapestRateRegular.getTotalCalculatedRate());
        Assert.assertEquals("Lakewood" , cheapestRateRegular.getHotelName());
        //OP is wrong in pdf file
    }

    @Test
    public void givenHotelDetailsAndDateRange_GetBestRatedHotel_ReturnTrueIfCorrectlyReturned(){
        String startDate = "11Sep2020";
        String endDate = "12Sep2020";

        Result cheapestRateRegular = runnerObject.getBestRatedHotelForADateRange(CustomerType.REGULAR,
                LocalDate.parse( startDate, DateTimeFormatter.ofPattern("ddMMMyyyy")),
                LocalDate.parse( endDate, DateTimeFormatter.ofPattern("ddMMMyyyy")));

        Assert.assertEquals(370 , (int) cheapestRateRegular.getTotalCalculatedRate());
        Assert.assertEquals("Ridgewood" , cheapestRateRegular.getHotelName());
        //OP is wrong in pdf file
    }

    @Test
    public void givenDateRange_GetBestRatedHotelForRewardsCustomer_ReturnTrueIfCorrectlyReturned(){
        String startDate = "11Sep2020";
        String endDate = "12Sep2020";

        Result cheapestRateReward = runnerObject.getCheapestRateForADateRange(CustomerType.REWARD,
                LocalDate.parse(startDate, DateTimeFormatter.ofPattern("ddMMMyyyy")),
                LocalDate.parse(endDate, DateTimeFormatter.ofPattern("ddMMMyyyy")));

        Assert.assertEquals(140 , (int) cheapestRateReward.getTotalCalculatedRate());
        Assert.assertEquals("Ridgewood" , cheapestRateReward.getHotelName());
    }

    @Test
    public void givenInputOfHotelDetailsAndDateRange_GetCheapestThenBestRated_ReturnTrueIfCheapestRateIsReturned(){
        HashMap<CustomerType, RateKey> hotelRateVariable = new HashMap<>();
        hotelRateVariable = new HashMap<>();
        hotelRateVariable.put(CustomerType.REGULAR , new RateKey(30, 30));
        hotelRateVariable.put(CustomerType.REWARD , new RateKey(25, 25));
        creekwood = new Hotel("Creekwood", 1, hotelRateVariable);

        hotelRateVariable = new HashMap<>();
        hotelRateVariable.put(CustomerType.REGULAR , new RateKey(30, 30));
        hotelRateVariable.put(CustomerType.REWARD , new RateKey(25, 25));
        valleywood = new Hotel("Valleywood", 2, hotelRateVariable);

        runnerObject.addHotel(creekwood);
        runnerObject.addHotel(valleywood);

        String startDate = "03Apr2021";
        String endDate = "04Apr2021";

        Result cheapestRateRegular = runnerObject.getCheapestRateForADateRange(CustomerType.REGULAR,
                     LocalDate.parse( startDate, DateTimeFormatter.ofPattern("ddMMMyyyy")),
                     LocalDate.parse( endDate, DateTimeFormatter.ofPattern("ddMMMyyyy")));

        Result cheapestRateReward = runnerObject.getCheapestRateForADateRange(CustomerType.REWARD,
                LocalDate.parse(startDate, DateTimeFormatter.ofPattern("ddMMMyyyy")),
                LocalDate.parse(endDate, DateTimeFormatter.ofPattern("ddMMMyyyy")));

        Assert.assertEquals(60 , (int) cheapestRateRegular.getTotalCalculatedRate());
        Assert.assertEquals(2 , (int) cheapestRateRegular.getHotelRating());
        Assert.assertEquals("Valleywood" , cheapestRateRegular.getHotelName());
    }

    //How to use multiple comparator object to compare in .sorted() method?
}
